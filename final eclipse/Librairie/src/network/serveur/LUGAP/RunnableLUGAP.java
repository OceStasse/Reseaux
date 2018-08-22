package network.serveur.LUGAP;

import java.sql.SQLException;

import database.utilities.Access;
import generic.server.ARequete;
import generic.server.IConsoleServeur;
import network.communication.Communication;
import network.communication.communicationException;
import network.protocole.lugap.reponse.Reponse_Baggages;
import network.protocole.lugap.reponse.Reponse_MAJ_champ;
import network.protocole.lugap.reponse.Reponse_Vols;
import network.protocole.lugap.reponse.Reponse_login;
import network.protocole.lugap.requete.Requete_Baggages;
import network.protocole.lugap.requete.Requete_MAJ_ChampChargement;
import network.protocole.lugap.requete.Requete_MAJ_ChampCheckedCustom;
import network.protocole.lugap.requete.Requete_MAJ_ChampComment;
import network.protocole.lugap.requete.Requete_MAJ_ChampRecevoir;
import network.protocole.lugap.requete.Requete_Vols;
import network.protocole.lugap.requete.Requete_login;
import network.protocole.lugap.requete.Requete_logout;

public class RunnableLUGAP implements Runnable {
    private final PoolThread parent;
    private final IConsoleServeur guiApplication;
    private final Communication c;
    private final Access db;
    private Class<?> previousRequete;
    
    private boolean finTransaction;
    private boolean clientConnected;
    
    RunnableLUGAP(PoolThread parent, IConsoleServeur guiApplication, Communication c, Access db) throws communicationException {
        this.parent = parent;
        this.guiApplication = guiApplication;
        this.c = c;
        this.db = db;
    }
    
    
    @Override
    public void run() {
	boolean failedToConnect;
        this.finTransaction = false;
        
        try {
            this.db.connect();
            failedToConnect = false;
        } catch (ClassNotFoundException | SQLException ex) {
            failedToConnect = true;
        }
        
        try 
        {
            while(!this.finTransaction)
            {
                ARequete req = this.c.receive(ARequete.class);
                System.out.println("Received request type : " + req.getClass().toString());
                
                if(failedToConnect)
                {
                    this.c.send(Reponse_login.KO("Server could not connect to the database."));
                    finTransaction();
                    
                }
                else
                {
                    Runnable runnable = req.createRunnable(this.parent, this.c, this.guiApplication, this.db);

                    if(req instanceof Requete_login)
                    {
                        runnable.run();
                        if(req.requeteSucceeded()){
                            setClientIsConnected();
                            previousRequete = Requete_login.class;
                        }
                    }
                    else if(req instanceof Requete_logout)
                    {
                        runnable.run();
                        finTransaction();
                        previousRequete = null;
                    }
                    else if(req instanceof Requete_Vols)
                    {
                        if( !isClientConnected())
                            this.c.send(Reponse_Vols.KO("Please connect first!"));
                        else{
                            runnable.run();
                            previousRequete = Requete_Vols.class;
                        }
                    }
                    else if(req instanceof Requete_Baggages)
                    {
                        if( !isClientConnected() || !previousRequete.equals(Requete_Vols.class))
                            this.c.send(Reponse_Baggages.KO("Please do a getFlights first!"));
                        else{
                            runnable.run();
                            previousRequete = Requete_Baggages.class;
                        }
                    }
                    else if(req instanceof Requete_MAJ_ChampRecevoir || req instanceof Requete_MAJ_ChampChargement
                            || req instanceof Requete_MAJ_ChampCheckedCustom || req instanceof Requete_MAJ_ChampComment)
                    {
                        if( !isClientConnected() || !previousRequete.equals(Requete_Baggages.class))
                            this.c.send(Reponse_MAJ_champ.KO("Please do a getLuggages first!"));
                        else
                            runnable.run();
                    }
                }
            }
            
            this.db.commit();
            this.guiApplication.TraceEvenements(this.c.getSocket().getRemoteSocketAddress().toString()
                                                + "#Commit successful"
                                                + "#LUGAPRunnable");
        } catch (communicationException | SQLException ex) {
            this.guiApplication.TraceEvenements(this.c.getSocket().getRemoteSocketAddress().toString()
                                                + "#" + ex.getMessage()
                                                + "#LUGAPRunnable");
            try {
                this.db.rollback();
                this.guiApplication.TraceEvenements(this.c.getSocket().getRemoteSocketAddress().toString()
                                                + "#Rollback successful"
                                                + "#LUGAPRunnable");
            } catch (SQLException ex1) {
                this.guiApplication.TraceEvenements(this.c.getSocket().getRemoteSocketAddress().toString()
                                                + "#" + ex.getMessage()
                                                + "#LUGAPRunnable");
            }
        }
    }
    
    private void finTransaction(){
        this.finTransaction = true;
    }
    
    private boolean isClientConnected(){
        return this.clientConnected;
    }
    
    private void setClientIsConnected(){
        this.clientConnected = true;
    }
}

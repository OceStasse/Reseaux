package serveur_bagages;

import communicator.Communicator;
import communicator.CommunicatorException;
import database.utilities.DatabaseAccess;
import java.sql.SQLException;
import lugapm.reponse.ReponseLUGAPM_finishedLoading;
import lugapm.reponse.ReponseLUGAPM_getFlights;
import lugapm.reponse.ReponseLUGAPM_getLuggages;
import lugapm.reponse.ReponseLUGAPM_login;
import lugapm.requete.RequeteLUGAPM;
import lugapm.requete.RequeteLUGAPM_finishedLoading;
import lugapm.requete.RequeteLUGAPM_getFlights;
import lugapm.requete.RequeteLUGAPM_getLuggages;
import lugapm.requete.RequeteLUGAPM_login;
import lugapm.requete.RequeteLUGAPM_logout;
import server.ConsoleServeur;

public class RunnableLUGAPM implements Runnable {
    private final ThreadPoolLUGAPM parent;
    private final ConsoleServeur guiApplication;
    private final Communicator communicator;
    private final DatabaseAccess databaseAccess;
    private Class previousRequete;
    
    private boolean endOfTransaction;
    private boolean clientConnected;

    RunnableLUGAPM(ThreadPoolLUGAPM parent, ConsoleServeur guiApplication, Communicator communicator, DatabaseAccess databaseAccess) throws CommunicatorException {
        this.parent = parent;
        this.guiApplication = guiApplication;
        this.communicator = communicator;
        this.databaseAccess = databaseAccess;
    }
    
    @Override
    public void run() {
        boolean failedToConnect;
        this.endOfTransaction = false;
        
        try {
            this.databaseAccess.connect();
            failedToConnect = false;
        } catch (ClassNotFoundException | SQLException ex) {
            failedToConnect = true;
        }
        
        try {
            while(!this.endOfTransaction){
                RequeteLUGAPM req = this.communicator.receiveSerializable(RequeteLUGAPM.class);
                System.out.println("Received request type : " + req.getClass().toString());
                
                if(failedToConnect){
                    this.communicator.SendSerializable(ReponseLUGAPM_login.KO("Server could not connect to the database."));
                    endTransaction();
                    
                }
                else{
                    Runnable runnable = req.createRunnable(this.parent, this.communicator, this.guiApplication, this.databaseAccess);
                    
                    if(req instanceof RequeteLUGAPM_login){
                        runnable.run();
                        if(req.requeteSucceeded()){
                            setClientIsConnected();
                            previousRequete = RequeteLUGAPM_login.class;
                        }
                    }
                    else if(req instanceof RequeteLUGAPM_logout){
                        runnable.run();
                        endTransaction();
                        previousRequete = null;
                    }
                    else if(req instanceof RequeteLUGAPM_getFlights){
                        if( !isClientConnected() )
                            this.communicator.SendSerializable(ReponseLUGAPM_getFlights.KO("Please do a login first!"));
                        else{
                            runnable.run();
                            previousRequete = RequeteLUGAPM_getFlights.class;
                        }
                    }
                    else if(req instanceof RequeteLUGAPM_getLuggages){
                        if( !isClientConnected() || !previousRequete.equals(RequeteLUGAPM_getFlights.class))
                            this.communicator.SendSerializable(ReponseLUGAPM_getLuggages.KO("Please do a getFlights first!"));
                        else{
                            runnable.run();
                            previousRequete = RequeteLUGAPM_getLuggages.class;
                        }
                    }
                    else if(req instanceof RequeteLUGAPM_finishedLoading){
                        if( !isClientConnected() || !previousRequete.equals(RequeteLUGAPM_getLuggages.class))
                            this.communicator.SendSerializable(ReponseLUGAPM_finishedLoading.KO("Please do a getLuggages first!"));
                        else{
                            runnable.run();
                        }
                    }
                }
            }
            
            this.databaseAccess.commit();
            this.guiApplication.TraceEvenements(this.communicator.getSocket().getRemoteSocketAddress().toString()
                                                + "#Commit successful"
                                                + "#LUGAPMRunnable");
            this.communicator.close();
        } catch (CommunicatorException | SQLException ex) {
            this.guiApplication.TraceEvenements(this.communicator.getSocket().getRemoteSocketAddress().toString()
                                                + "#" + ex.getMessage()
                                                + "#LUGAPMRunnable");
            try {
                endTransaction();
                this.databaseAccess.rollback();
                this.guiApplication.TraceEvenements(this.communicator.getSocket().getRemoteSocketAddress().toString()
                                                + "#Rollback successful"
                                                + "#LUGAPMRunnable");
            } catch (SQLException ex1) {
                this.guiApplication.TraceEvenements(this.communicator.getSocket().getRemoteSocketAddress().toString()
                                                + "#" + ex.getMessage()
                                                + "#LUGAPMRunnable");
            }
        }
    }
    
    private void endTransaction(){
        this.endOfTransaction = true;
    }
    
    private boolean isClientConnected(){
        return this.clientConnected;
    }
    
    private void setClientIsConnected(){
        this.clientConnected = true;
    }
}

package serveur_bagages;

import communicator.Communicator;
import communicator.CommunicatorException;
import database.utilities.DatabaseAccess;
import java.sql.SQLException;
import lugap.reponse.ReponseLUGAP_getFlights;
import lugap.reponse.ReponseLUGAP_getLuggages;
import lugap.reponse.ReponseLUGAP_login;
import lugap.reponse.ReponseLUGAP_updateField;
import lugap.requete.RequeteLUGAP;
import lugap.requete.RequeteLUGAP_getFlights;
import lugap.requete.RequeteLUGAP_getLuggages;
import lugap.requete.RequeteLUGAP_login;
import lugap.requete.RequeteLUGAP_logout;
import lugap.requete.RequeteLUGAP_updateFieldCheckedByCustoms;
import lugap.requete.RequeteLUGAP_updateFieldComments;
import lugap.requete.RequeteLUGAP_updateFieldLoaded;
import lugap.requete.RequeteLUGAP_updateFieldReceived;
import server.ConsoleServeur;

public class RunnableLUGAP implements Runnable {
    private final ThreadPoolLUGAP parent;
    private final ConsoleServeur guiApplication;
    private final Communicator communicator;
    private final DatabaseAccess databaseAccess;
    private Class previousRequete;
    
    private boolean endOfTransaction;
    private boolean clientConnected;

    RunnableLUGAP(ThreadPoolLUGAP parent, ConsoleServeur guiApplication, Communicator communicator, DatabaseAccess databaseAccess) throws CommunicatorException {
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
                RequeteLUGAP req = this.communicator.receiveSerializable(RequeteLUGAP.class);
                System.out.println("Received request type : " + req.getClass().toString());
                
                if(failedToConnect){
                    this.communicator.SendSerializable(ReponseLUGAP_login.KO("Server could not connect to the database."));
                    endTransaction();
                    
                }
                else{
                    Runnable runnable = req.createRunnable(this.parent, this.communicator, this.guiApplication, this.databaseAccess);

                    if(req instanceof RequeteLUGAP_login){
                        runnable.run();
                        if(req.requeteSucceeded()){
                            setClientIsConnected();
                            previousRequete = RequeteLUGAP_login.class;
                        }
                    }
                    else if(req instanceof RequeteLUGAP_logout){
                        runnable.run();
                        endTransaction();
                        previousRequete = null;
                    }
                    else if(req instanceof RequeteLUGAP_getFlights){
                        if( !isClientConnected())
                            this.communicator.SendSerializable(ReponseLUGAP_getFlights.KO("Please connect first!"));
                        else{
                            runnable.run();
                            previousRequete = RequeteLUGAP_getFlights.class;
                        }
                    }
                    else if(req instanceof RequeteLUGAP_getLuggages){
                        if( !isClientConnected() || !previousRequete.equals(RequeteLUGAP_getFlights.class))
                            this.communicator.SendSerializable(ReponseLUGAP_getLuggages.KO("Please do a getFlights first!"));
                        else{
                            runnable.run();
                            previousRequete = RequeteLUGAP_getLuggages.class;
                        }
                    }
                    else if(req instanceof RequeteLUGAP_updateFieldReceived
                            || req instanceof RequeteLUGAP_updateFieldLoaded
                            || req instanceof RequeteLUGAP_updateFieldCheckedByCustoms
                            || req instanceof RequeteLUGAP_updateFieldComments){
                        if( !isClientConnected() || !previousRequete.equals(RequeteLUGAP_getLuggages.class))
                            this.communicator.SendSerializable(ReponseLUGAP_updateField.KO("Please do a getLuggages first!"));
                        else
                            runnable.run();
                    }
                }
            }
            
            this.databaseAccess.commit();
            this.guiApplication.TraceEvenements(this.communicator.getSocket().getRemoteSocketAddress().toString()
                                                + "#Commit successful"
                                                + "#LUGAPRunnable");
            this.communicator.close();
        } catch (CommunicatorException | SQLException ex) {
            this.guiApplication.TraceEvenements(this.communicator.getSocket().getRemoteSocketAddress().toString()
                                                + "#" + ex.getMessage()
                                                + "#LUGAPRunnable");
            try {
                this.databaseAccess.rollback();
                this.guiApplication.TraceEvenements(this.communicator.getSocket().getRemoteSocketAddress().toString()
                                                + "#Rollback successful"
                                                + "#LUGAPRunnable");
            } catch (SQLException ex1) {
                this.guiApplication.TraceEvenements(this.communicator.getSocket().getRemoteSocketAddress().toString()
                                                + "#" + ex.getMessage()
                                                + "#LUGAPRunnable");
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

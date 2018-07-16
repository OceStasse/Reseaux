package serveur_billets;

import communicator.Communicator;
import communicator.CommunicatorException;
import database.utilities.DatabaseAccess;
import java.sql.SQLException;
import server.ConsoleServeur;
import tickmap.reponse.ReponseTICKMAP_login;
import tickmap.requete.RequeteTICKMAP;
import tickmap.requete.RequeteTICKMAP_login;
import tickmap.requete.RequeteTICKMAP_logout;

public class RunnableTICKMAP implements Runnable{
	private final ThreadPoolTICKMAP parent;
	private final ConsoleServeur guiApplication;
	private final Communicator communicator;
	private final DatabaseAccess databaseAccess;
	private Class previousRequete;

	private boolean endOfTransaction;
	private boolean clientConnected;

	public RunnableTICKMAP(ThreadPoolTICKMAP parent, ConsoleServeur guiApplication, Communicator communicator, DatabaseAccess databaseAccess) {
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

		try{
			while(!this.endOfTransaction){
				RequeteTICKMAP req = this.communicator.receiveSerializable(RequeteTICKMAP.class);
				System.out.println("Received request type : " + req.getClass().toString());

                if(failedToConnect){
                    this.communicator.SendSerializable(ReponseTICKMAP_login.KO("Server could not connect to the database."));
                    endTransaction();

                }
                else{
                    Runnable runnable = req.createRunnable(this.parent, this.communicator, this.guiApplication, this.databaseAccess);

                    if(req instanceof RequeteTICKMAP_login){
                        runnable.run();
                        if(req.requeteSucceeded()){
                            setClientIsConnected();
                            previousRequete = RequeteTICKMAP_login.class;
                        }
                    }
                    else if(req instanceof RequeteTICKMAP_logout){
                        runnable.run();
                        endTransaction();
                        previousRequete = null;
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

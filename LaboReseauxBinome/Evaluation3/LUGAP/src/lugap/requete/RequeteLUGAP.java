package lugap.requete;

import communicator.Communicator;
import database.utilities.DatabaseAccess;
import java.io.Serializable;
import server.ConsoleServeur;
import server.Reponse;
import server.Requete;

public abstract class RequeteLUGAP implements Requete, Serializable {
    protected Runnable parent;
    protected Communicator communicator;
    protected ConsoleServeur consoleServeur;
    protected DatabaseAccess databaseAccess;
    
    private final String requestTypeName;
    protected final String sqlStatement;
    protected Reponse reponse;

    protected RequeteLUGAP(String requestTypeName, String sqlStatement) {
        this.requestTypeName = requestTypeName;
        this.sqlStatement = sqlStatement;
    }
    
    @Override
    public Runnable createRunnable(Runnable parent, Communicator communicator, ConsoleServeur consoleServeur, DatabaseAccess databaseAccess) {
        this.parent = parent;
        this.communicator = communicator;
        this.consoleServeur = consoleServeur;
        this.databaseAccess = databaseAccess;
        
        return () -> { doAction(); };
    }
    
    protected abstract void doAction();
    
    protected void traceEvent(String message){
        consoleServeur.TraceEvenements(communicator.getSocket().getRemoteSocketAddress().toString()
                                        + "#" + message
                                        + "#" + requestTypeName + "@LUGAP");
    }
    
    public boolean requeteSucceeded(){
        if(this.reponse == null)
            return false;
        else
            return this.reponse.isSuccessful();
    }
}

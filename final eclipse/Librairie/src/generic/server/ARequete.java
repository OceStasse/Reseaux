package generic.server;

import java.io.Serializable;

import database.utilities.Access;
import network.communication.Communication;

public abstract class ARequete implements Serializable, IRequete {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected Runnable parent;
    protected Communication communication;
    protected IConsoleServeur consoleServeur;
    protected Access database;
    
    protected final String requestTypeName;
    protected final String sqlStatement;
    protected AReponse reponse;
    
    protected ARequete(String requestTypeName, String sqlStatement) {
        this.requestTypeName = requestTypeName;
        this.sqlStatement = sqlStatement;
    }
    
    @Override
    public Runnable createRunnable(Runnable parent, Communication c, IConsoleServeur consoleServeur, Access db) {
	this.parent = parent;
        this.communication = c;
        this.consoleServeur = consoleServeur;
        this.database = db;
        
        return () -> { doAction(); };
    }
    
    protected abstract void doAction();
    
    protected void traceEvent(String message){
        consoleServeur.TraceEvenements(communication.getSocket().getRemoteSocketAddress().toString()
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

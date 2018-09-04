package generic.server;

import javax.crypto.SecretKey;

import database.utilities.Access;
import network.communication.Communication;

public abstract class ASecureSymetricRequete extends ARequete implements ISecureSymetricRequete{

	/**
	 * 
	 */
	private SecretKey key;
	private static final long serialVersionUID = 1L;
	public ASecureSymetricRequete(String requestTypeName, String sqlStatement) {
		super(requestTypeName,sqlStatement);
    }
    
    
    
    
    @Override
	public Runnable createRunnable(Runnable parent, Communication c, IConsoleServeur consoleServeur, Access db,
			SecretKey key) {
    	this.parent = parent;
        this.communication = c;
        this.consoleServeur = consoleServeur;
        this.database = db;
        this.setKey(key);
        return () -> { doAction(); };
	}




	protected void traceEvent(String message){
        consoleServeur.TraceEvenements(communication.getSocket().getRemoteSocketAddress().toString()
                                        + "#" + message
                                        + "#" + requestTypeName + "@LUGAP");
    }
    
	@Override
	protected abstract void doAction() ;




	public SecretKey getKey() {
		return key;
	}




	public void setKey(SecretKey key) {
		this.key = key;
	}

}

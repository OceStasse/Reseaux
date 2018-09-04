package generic.server;

import java.security.PrivateKey;

import javax.crypto.SecretKey;

import database.utilities.Access;
import network.communication.Communication;

public abstract class ASecureAsymetricRequete extends ARequete implements ISecureAsymetricRequete
{

	
	private PrivateKey key;
	protected ASecureAsymetricRequete(String requestTypeName, String sqlStatement) {
		super(requestTypeName, sqlStatement);
		// TODO Auto-generated constructor stub
	}

 
    
    @Override
	public Runnable createRunnable(Runnable parent, Communication c, IConsoleServeur consoleServeur, Access db,
			PrivateKey key) {
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




	public PrivateKey getKey() {
		return key;
	}




	public void setKey(PrivateKey key) {
		this.key = key;
	}

}

package generic.server;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import database.utilities.Access;
import network.communication.Communication;

public abstract class ASecureRequete extends ARequete implements ISecureRequete
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrivateKey keyPrivate;
	private PublicKey keyPublic;
	private SecretKey keySecret;
	private boolean crypted;
	
	public PrivateKey getKeyPrivate() {
		return keyPrivate;
	}



	public void setKeyPrivate(PrivateKey keyPrivate) {
		this.keyPrivate = keyPrivate;
	}



	public PublicKey getKeyPublic() {
		return keyPublic;
	}



	public void setKeyPublic(PublicKey keyPublic) {
		this.keyPublic = keyPublic;
	}



	protected ASecureRequete(String requestTypeName, String sqlStatement) {
		super(requestTypeName, sqlStatement);
	}

 
    
    @Override
	public Runnable createRunnable(Runnable parent, Communication c, IConsoleServeur consoleServeur, Access db,
			PrivateKey key,PublicKey keyPublic, SecretKey keySecret) {
    	this.parent = parent;
        this.communication = c;
        this.consoleServeur = consoleServeur;
        this.database = db;
        this.setKeyPrivate(key);
        this.setKeyPublic(keyPublic);
        this.setKeySecret(keySecret);
        return () -> { doAction(); };
	}




	protected void traceEvent(String message,String protocole){
        consoleServeur.TraceEvenements(communication.getSocket().getRemoteSocketAddress().toString()
                                        + "#" + message
                                        + "#" + requestTypeName + "@"+protocole);
    }
    
	@Override
	protected abstract void doAction() ;



	public SecretKey getKeySecret() {
		return keySecret;
	}



	public void setKeySecret(SecretKey keySecret) {
		this.keySecret = keySecret;
	}



	public boolean isCrypted() {
		return crypted;
	}



	public void enableCrypted() {
		this.crypted = true;
	}
	
	public void disenableCrypted() {
		this.crypted = false;
	}

}

package generic.server;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import database.utilities.Access;
import network.communication.Communication;

public abstract class ASecureAsymetricRequete extends ARequete implements ISecureAsymetricRequete
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrivateKey keyPrivate;
	private PublicKey keyPublic;
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



	protected ASecureAsymetricRequete(String requestTypeName, String sqlStatement) {
		super(requestTypeName, sqlStatement);
		// TODO Auto-generated constructor stub
	}

 
    
    @Override
	public Runnable createRunnable(Runnable parent, Communication c, IConsoleServeur consoleServeur, Access db,
			PrivateKey key,PublicKey keyPublic) {
    	this.parent = parent;
        this.communication = c;
        this.consoleServeur = consoleServeur;
        this.database = db;
        this.setKeyPrivate(key);
        this.setKeyPublic(keyPublic);
        return () -> { doAction(); };
	}




	protected void traceEvent(String message){
        consoleServeur.TraceEvenements(communication.getSocket().getRemoteSocketAddress().toString()
                                        + "#" + message
                                        + "#" + requestTypeName + "@LUGAP");
    }
    
	@Override
	protected abstract void doAction() ;

}

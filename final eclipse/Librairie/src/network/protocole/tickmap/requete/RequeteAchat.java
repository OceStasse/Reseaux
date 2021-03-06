package network.protocole.tickmap.requete;

import java.io.IOException;

import javax.crypto.SecretKey;

import generic.server.ASecureRequete;
import network.crypto.AHMAC;
import network.crypto.ConverterObject;
import network.crypto.HMACException;

public class RequeteAchat extends ASecureRequete {
	
	private byte[] hmac;
	private boolean accepted;
	private int idPassenger;
	protected RequeteAchat(SecretKey key, int idPassenger, boolean accepted) throws HMACException 
	{
		super("GET ACHAT", null);
		//init hmac
		this.hmac = AHMAC.hash("HMAC-MD5", "BC", key, this);
		this.idPassenger = idPassenger;
		this.accepted = accepted;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doAction() 
	{
		//checked hmac
		// checked accept
		try {
			if(AHMAC.verify("HMAC-MD5", "BC", this.hmac, ConverterObject.convertObjectToByte(this), this.getKeySecret()))
			{
				
				if(this.accepted)
				{
					//ajoute les achat dans la table ticket
					
				}
				else
				{
					//remet les places correctement au niveau du vol
					
				}
			}
			else
			{
				// reponse ko
			}
		} catch (HMACException | IOException e) {
			//reponse ko error hmac
		}
	}

}

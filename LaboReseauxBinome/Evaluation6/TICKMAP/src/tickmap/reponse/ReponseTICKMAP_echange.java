package tickmap.reponse;

import java.io.Serializable;
import java.security.PublicKey;
import server.Reponse;

public class ReponseTICKMAP_echange extends Reponse implements Serializable {
	private PublicKey cle;

	private ReponseTICKMAP_echange(String message, boolean successful, PublicKey key){
		super(message, successful);

		this.cle = key;
	}

	public static ReponseTICKMAP_echange OK(PublicKey key){
		return new ReponseTICKMAP_echange("", true, key);
	}

	public static ReponseTICKMAP_echange KO(String message){
		return new ReponseTICKMAP_echange(message, false, null);
	}

	public PublicKey getKey(){
		return this.cle;
	}
}

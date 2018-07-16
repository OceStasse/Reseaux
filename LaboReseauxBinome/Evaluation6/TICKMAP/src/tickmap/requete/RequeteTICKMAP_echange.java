package tickmap.requete;

import java.io.Serializable;
import java.security.PublicKey;

public class RequeteTICKMAP_echange extends RequeteTICKMAP implements Serializable{
	private final PublicKey cleClient;

	public RequeteTICKMAP_echange(PublicKey cle) {
		super("KeyExchange", null);

		this.cleClient = cle;
	}

	@Override
	protected void doAction() {
		
	}

}

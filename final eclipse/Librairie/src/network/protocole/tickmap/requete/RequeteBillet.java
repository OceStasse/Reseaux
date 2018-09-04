package network.protocole.tickmap.requete;

import generic.server.ARequete;
import network.crypto.ACryptographieSymetrique;

public class RequeteBillet extends ARequete {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final byte[] billet;
	
	

	public RequeteBillet()
	{
		super("Add Billet","");
		
		billet = ACryptographieSymetrique.encrypt();
		
	}
	
	@Override
	protected void doAction() {
		// TODO Auto-generated method stub

	}
	
	public byte[] getBillet() {
		return billet;
	}
	
}

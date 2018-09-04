package network.protocole.tickmap.reponse;

import java.util.ArrayList;
import database.entities.Vol;
import generic.server.AReponse;

public class ReponseVols extends AReponse {
	
	ArrayList<Vol> vols;
	private final byte[] signatureMessage;
	
	public ArrayList<Vol> getVols() {
		return vols;
	}

	private ReponseVols(String message, boolean successful, ArrayList<Vol> vols){
		super(message, successful);
		this.vols = vols;
		signatureMessage = null;
	}
	private ReponseVols(String message, boolean successful) {
		super(message, successful);
		this.vols = null;
		this.signatureMessage = null;
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static ReponseVols OK(ArrayList<Vol> vols)
	{
		return new ReponseVols("",true,vols);		
	}
	
	public static ReponseVols KO(String msg)
	{
		return new ReponseVols(msg,false,null);
	}

	public byte[] getSignatureMessage() {
		return signatureMessage;
	}

}

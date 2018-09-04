package network.protocole.tickmap.reponse;

import generic.server.AReponse;

public class ReponsePassenger extends AReponse {

	protected ReponsePassenger(String message, boolean successful) {
		super(message, successful);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static ReponsePassenger OK()
	{
		return new ReponsePassenger("",true);
	}
	
	public static ReponsePassenger KO(String msg)
	{
		return new ReponsePassenger(msg,false);
	}
	
}

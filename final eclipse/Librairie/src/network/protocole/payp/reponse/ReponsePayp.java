package network.protocole.payp.reponse;

import generic.server.AReponse;

public class ReponsePayp extends AReponse
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	public ReponsePayp(String message,boolean success)
	{
		super(message,success);
	}
	
	public static ReponsePayp OK()
	{
		return new ReponsePayp("",true);
	}
	
	public static ReponsePayp KO(String msg)
	{
		return new ReponsePayp(msg,false);
	}	
	
}

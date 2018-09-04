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
	private String _creditCard;
	private String _proprioName;
	private int _transactionMontant;
	
	
	
	public ReponsePayp()
	{
		super("",true);
		this.set_creditCard(null);
		this.set_proprioName("neant");
		this.set_transactionMontant(0);
	}
	public ReponsePayp(String creditCard,String proprio, int montant,String message,boolean success)
	{
		super(message,success);
		this.set_creditCard(creditCard);
		this.set_proprioName(proprio);
		this.set_transactionMontant(montant);
	}
	
	public static ReponsePayp OK(ReponsePayp rep)
	{
		return new ReponsePayp(rep.get_creditCard(),rep.get_proprioName(),rep.get_transactionMontant(),"",true);
	}
	
	public static ReponsePayp KO(ReponsePayp rep)
	{
		return new ReponsePayp(rep.get_creditCard(),rep.get_proprioName(),rep.get_transactionMontant(),"",false);
	}
	
	
	public String get_creditCard() {
		return _creditCard;
	}
	public void set_creditCard(String _creditCard) {
		this._creditCard = _creditCard;
	}
	public String get_proprioName() {
		return _proprioName;
	}
	public void set_proprioName(String _proprioName) {
		this._proprioName = _proprioName;
	}
	public int get_transactionMontant() {
		return _transactionMontant;
	}
	public void set_transactionMontant(int _transactionMontant) {
		this._transactionMontant = _transactionMontant;
	}
	
	
	
	
}

package network.protocole.payp.requete;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import network.crypto.*;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import generic.server.ARequete;
import generic.server.ASecureAsymetricRequete;

public class RequetePayp extends ASecureAsymetricRequete 
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
	
	
	
	public RequetePayp()
	{
		super("","");
		this.set_creditCard(null);
		this.set_proprioName("neant");
		this.set_transactionMontant(0);
	}
	public RequetePayp(String creditCard,String proprio, int montant)
	{
		super("","");
		this.set_creditCard(creditCard);
		this.set_proprioName(proprio);
		this.set_transactionMontant(montant);
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
	protected void doAction() {
		// TODO Auto-generated method stub
		
		 try {
			 
			 
			byte[] bTmp=network.crypto.ACryptographieAsymetrique.decrypt(Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC3"),network.crypto.ConverterObject.convertObjectToByte(this.get_creditCard())  , this.getKey());
			
			
			
			
			//"SHA1withRSA"
			byte[] bTmpSignature=network.crypto.ASignature.signMessage("SHA1withRSA", "RSA/ECB/PKCS1Padding", msg,  this.getKey());
			
			boolean stateSignature=network.crypto.ASignature.verifySig("SHA1withRSA", "RSA/ECB/PKCS1Padding", msg, bTmpSignature, this.getKey());
			
			
		} catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
				| CryptographieAsymetriqueException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		 
	}
	
	
	
	
}
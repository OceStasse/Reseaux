package network.protocole.payp.requete;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import network.communication.communicationException;
import network.crypto.*;
import network.protocole.payp.reponse.ReponsePayp;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import generic.server.ASecureRequete;

public class RequetePayp extends ASecureRequete 
{

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private byte[] _creditCard;
	private String _proprioName;
	private int _transactionMontant;
	
	private byte[] _signature;
	
	
	
	public byte[] get_signature() {
		return _signature;
	}
	public void set_signature(byte[] _signature) {
		this._signature = _signature;
	}
	
	
	public RequetePayp()
	{
		super("","");
	}
	
	public RequetePayp(String creditCard,String proprio, int montant)
	{
		super("GetPayp","");
		this._proprioName = proprio;
		this._transactionMontant = montant;
		
		try {
			_creditCard = ACryptographieAsymetrique.decrypt(Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC3"),network.crypto.ConverterObject.convertObjectToByte(creditCard)  , this.getKeyPrivate());			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    DataOutputStream dos = new DataOutputStream(bos);
		    dos.writeUTF(_proprioName);
		    dos.writeInt(_transactionMontant);
		    dos.write(_creditCard);
		    dos.flush();
		
			//"SHA1withRSA"
			this._signature =  ASignature.signMessage("SHA1withRSA", "RSA/ECB/PKCS1Padding", bos.toByteArray(),  this.getKeyPrivate());
		} catch (NoSuchAlgorithmException  | NoSuchProviderException | NoSuchPaddingException | CryptographieAsymetriqueException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	protected void doAction() {
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    DataOutputStream dos = new DataOutputStream(bos);
		    dos.writeUTF(this._proprioName);
		    dos.writeInt(this._transactionMontant);
		    dos.write(this._creditCard);
		    dos.flush();
			boolean stateSignature = ASignature.verifySig("SHA1withRSA", "RSA/ECB/PKCS1Padding",bos.toByteArray() ,this.get_signature(), this.getKeyPublic());
			
			if(stateSignature == true)
			{
				this.communication.send(ReponsePayp.OK());
			}
			else
			{
				this.communication.send(ReponsePayp.KO("error signature not sure"));
			}
				
		
		} catch (SignatureException | IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (communicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

}
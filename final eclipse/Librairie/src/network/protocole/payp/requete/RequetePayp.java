package network.protocole.payp.requete;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;

import network.communication.communicationException;
import network.crypto.*;
import network.protocole.payp.reponse.ReponsePayp;

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
		this.set_creditCard(null);
		this.set_proprioName("neant");
		this.set_transactionMontant(0);
		
		byte[] bTmp;
		try {
			bTmp = network.crypto.ACryptographieAsymetrique.decrypt(Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC3"),network.crypto.ConverterObject.convertObjectToByte(this.get_creditCard())  , this.getKeyPrivate());
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    DataOutputStream dos = new DataOutputStream(bos);
		    dos.writeUTF(this.get_proprioName());
		    dos.writeInt(this.get_transactionMontant());
		    dos.write(bTmp);
		    dos.flush();
		
		  //"SHA1withRSA"
			this.set_signature(network.crypto.ASignature.signMessage("SHA1withRSA", "RSA/ECB/PKCS1Padding", bos.toByteArray(),  this.getKeyPrivate()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CryptographieAsymetriqueException e) {
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
	public RequetePayp(String creditCard,String proprio, int montant)
	{
		super("","");
		this.set_creditCard(creditCard);
		this.set_proprioName(proprio);
		this.set_transactionMontant(montant);
		
		byte[] bTmp;
		try {
			bTmp = network.crypto.ACryptographieAsymetrique.decrypt(Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC3"),network.crypto.ConverterObject.convertObjectToByte(this.get_creditCard())  , this.getKeyPublic());
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    DataOutputStream dos = new DataOutputStream(bos);
		    dos.writeUTF(this.get_proprioName());
		    dos.writeInt(this.get_transactionMontant());
		    dos.write(bTmp);
		    dos.flush();
		
			//"SHA1withRSA"
			this.set_signature(network.crypto.ASignature.signMessage("SHA1withRSA", "RSA/ECB/PKCS1Padding", bos.toByteArray(),  this.getKeyPublic()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CryptographieAsymetriqueException e) {
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
			byte[] bTmp = null;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    DataOutputStream dos = new DataOutputStream(bos);
		    dos.writeUTF(this.get_proprioName());
		    dos.writeInt(this.get_transactionMontant());
		    dos.write(bTmp);
		    dos.flush();
			boolean stateSignature=network.crypto.ASignature.verifySig("SHA1withRSA", "RSA/ECB/PKCS1Padding",bos.toByteArray() ,this.get_signature(), this.getKeyPublic());
		//ReponsePayp(String creditCard,String proprio, int montant,byte[] signature,String message,boolean success)
			
			if(stateSignature==true)
			{
				ReponsePayp repTmp=new ReponsePayp(this.get_creditCard(),this.get_proprioName(),this.get_transactionMontant(),this.get_signature(),"",true);
				this.communication.send(ReponsePayp.OK(repTmp));
			}
			else
			{
				ReponsePayp repTmp=new ReponsePayp(this.get_creditCard(),this.get_proprioName(),this.get_transactionMontant(),this.get_signature(),"",false);
				this.communication.send(ReponsePayp.KO(repTmp));
			}
				
		
		} catch (SignatureException | IOException | communicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}
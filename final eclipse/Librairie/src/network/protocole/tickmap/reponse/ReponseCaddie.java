package network.protocole.tickmap.reponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import database.entities.Caddie;
import generic.server.AReponse;
import network.crypto.ACryptographieSymetrique;
import network.crypto.ConverterObject;
import network.crypto.CryptographieSymetriqueException;

public class ReponseCaddie extends AReponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte[] caddie;
	
	
	public byte[] getCaddie() {
		return caddie;
	}

	public ReponseCaddie(String message, boolean successful,Caddie caddie,SecretKey key) throws CryptographieSymetriqueException, IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		super(message, successful);
		if(key != null)
		{
			Cipher cipher = Cipher.getInstance( "SHA1withRSA","BC");
			this.caddie = ACryptographieSymetrique.encrypt(cipher, ConverterObject.convertObjectToByte(caddie), key);
		}
		else
		{
			caddie = null;
		}
	}
	
	public static ReponseCaddie OK(Caddie caddie, SecretKey key) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, CryptographieSymetriqueException, IOException
	{
		return new ReponseCaddie("",true,caddie,key);
	}
	
	
	public static ReponseCaddie KO(String msg) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, CryptographieSymetriqueException, IOException
	{
		return new ReponseCaddie(msg,false,null,null);
	}
	
}

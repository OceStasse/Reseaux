package network.protocole.tickmap.reponse;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import generic.server.AReponse;
import network.crypto.ACryptographieSymetrique;
import network.crypto.ConverterObject;
import network.crypto.CryptographieSymetriqueException;

public class ReponseReserver extends AReponse {
	

	private byte[] price;
	private byte[] places;
	
	protected ReponseReserver(String message, boolean successful,int price, ArrayList<?> places,SecretKey key) 
			throws IOException, CryptographieSymetriqueException, NoSuchAlgorithmException, 
			NoSuchProviderException, NoSuchPaddingException {
		super(message, successful);
		
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding", "BC");
			//convert int to byte
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    DataOutputStream dos = new DataOutputStream(bos);
		    dos.writeInt(price);
		    dos.flush();
		    
			this.setPrice(ACryptographieSymetrique.encrypt(cipher,bos.toByteArray(), key));
			this.setPlaces(ACryptographieSymetrique.encrypt(cipher, ConverterObject.convertObjectToByte(places),key));
	}
	
	protected ReponseReserver(String message, boolean successful) {
		super(message, successful);
	}
	
	private static final long serialVersionUID = 1L;
	
	public static ReponseReserver OK(int price, ArrayList<?> places, SecretKey key) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException, CryptographieSymetriqueException
	{
		return new ReponseReserver("",true,price, places,key);
	}
	
	public static ReponseReserver KO(String msg) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException, CryptographieSymetriqueException
	{
		return new ReponseReserver(msg,false);
	}

	public byte[] getPrice() {
		return price;
	}

	public void setPrice(byte[] price) {
		this.price = price;
	}

	public byte[] getPlaces() {
		return places;
	}

	public void setPlaces(byte[] places) {
		this.places = places;
	}

}

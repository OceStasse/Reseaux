package network.protocole.tickmap.reponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import generic.server.AReponse;
import network.crypto.ACryptographieAsymetrique;
import network.crypto.ConverterObject;
import network.crypto.CryptographieAsymetriqueException;

public class ReponseExchangeKey extends AReponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SecretKey keyHMAC;
	private SecretKey keyCipher;
	private byte[] encryptedKeyHMAC;
	private byte[] encryptedKeyCipher;
	
	
	
	public static ReponseExchangeKey OK(SecretKey keyHMAC, SecretKey keyCipher,PublicKey publicKey) 
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, CryptographieAsymetriqueException, 
			IOException{
        return new ReponseExchangeKey("", true,keyHMAC,keyCipher,publicKey);
    }

    public static ReponseExchangeKey KO(String message){
        return new ReponseExchangeKey(message, false);
    }
    
	private ReponseExchangeKey(String message, boolean successful,SecretKey keyHMAC, SecretKey keyCipher,PublicKey publicKey) 
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, CryptographieAsymetriqueException, 
			IOException {
        super(message, successful);
        //crypté les clés
        
        this.setEncryptedKeyCipher(ACryptographieAsymetrique.encrypt(Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC"), ConverterObject.convertObjectToByte(keyCipher), publicKey));
        this.setEncryptedKeyHMAC(ACryptographieAsymetrique.encrypt(Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC"), ConverterObject.convertObjectToByte(keyHMAC), publicKey));
        this.keyCipher = keyCipher;
        this.keyHMAC = keyHMAC;
    }
	
	private ReponseExchangeKey(String message, boolean successful) {
        super(message, successful);
    }
	
	public SecretKey getKeyHMAC() {
		return keyHMAC;
	}

	public SecretKey getKeyCipher() {
		return keyCipher;
	}

	public byte[] getEncryptedKeyHMAC() {
		return encryptedKeyHMAC;
	}

	public void setEncryptedKeyHMAC(byte[] encryptedKeyHMAC) {
		this.encryptedKeyHMAC = encryptedKeyHMAC;
	}

	public byte[] getEncryptedKeyCipher() {
		return encryptedKeyCipher;
	}

	public void setEncryptedKeyCipher(byte[] encryptedKeyCipher) {
		this.encryptedKeyCipher = encryptedKeyCipher;
	}


}

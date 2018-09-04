package network.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public abstract class ACryptographieSymetrique {
	@SuppressWarnings("unused")
	private KeyGenerator _kg;
	@SuppressWarnings("unused")
	private SecretKey _key;
	
	public static SecretKey secretKey(String algorithm, String codeProvider) throws CryptographieSymetriqueException
	{
		KeyGenerator kg;
		try {
			kg = KeyGenerator.getInstance(algorithm, codeProvider);
	        kg.init(new SecureRandom());
	        return kg.generateKey();
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new CryptographieSymetriqueException("ACryptographieSymetrique()->generate(): " + e);
		}
	}
	
	public static byte[] encrypt(Cipher cipher,byte[] req, SecretKey key) throws CryptographieSymetriqueException
	{
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
	        return cipher.doFinal(req);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptographieSymetriqueException("ACryptographieSymetrique()->encrypt()");
		}
	}
	
	public static byte[] decrypt(Cipher cipher,byte[] req, SecretKey key) throws CryptographieSymetriqueException
	{
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
	        return cipher.doFinal(req);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptographieSymetriqueException("ACryptographieSymetrique()->decrypt()");
		}
	}
	
	
}

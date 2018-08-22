package network.crypto;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public abstract class ACryptageAsymétrique {
    
    public static byte[] encrypt(Cipher cipher, byte[] req, PublicKey key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
	cipher.init(Cipher.ENCRYPT_MODE, key);
	
	return cipher.doFinal(req);
    }
    
    public static byte[] decrypt(Cipher cipher, byte[] req, PrivateKey key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
	cipher.init(Cipher.DECRYPT_MODE, key);
	return cipher.doFinal(req);
    }
}

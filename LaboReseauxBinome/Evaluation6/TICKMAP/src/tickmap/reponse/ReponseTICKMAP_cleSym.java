package tickmap.reponse;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import server.Reponse;

public class ReponseTICKMAP_cleSym extends Reponse implements Serializable {
	private byte[] cleAuthCrypt;
	private byte[] cleChiffCrypt;


	private ReponseTICKMAP_cleSym(String message, boolean successful, byte[] auth, byte[] chiff){
		super(message, successful);

		this.cleAuthCrypt = auth;
		this.cleChiffCrypt = chiff;
	}

	public static ReponseTICKMAP_cleSym OK(SecretKey auth, SecretKey chiff){
		return new ReponseTICKMAP_cleSym("", true, auth, chiff);
	}

	public static ReponseTICKMAP_cleSym KO(String message){
		return new ReponseTICKMAP_cleSym(message, false, null, null);
	}


	public SecretKey getCleAuth(PrivateKey key)
			throws NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher chiff = Cipher.getInstance("RSA", "BC");
		chiff.init(Cipher.DECRYPT_MODE, key);
		byte[] cleDecrypt = chiff.doFinal(cleAuthCrypt);

		return new SecretKeySpec(cleDecrypt, "AES");
	}

	public SecretKey getCleChiff(PrivateKey key)
			throws NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher chiff = Cipher.getInstance("RSA", "BC");
		chiff.init(Cipher.DECRYPT_MODE, key);
		byte[] cleDecrypt = chiff.doFinal(cleChiffCrypt);

		return new SecretKeySpec(cleDecrypt, "AES");
	}
}

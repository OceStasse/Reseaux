package network.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class ASignature {
	public static byte[] signMessage(Signature sig,byte[] msg,  PrivateKey key) throws SignatureException
	{
		try {
			sig.initSign(key);
			sig.update(msg);
			return sig.sign();
		} catch (InvalidKeyException | java.security.SignatureException e) {
			throw new SignatureException("ASignature()->sigMessage" + e);
		}
				
	}
	
	public static byte[] signMessage(String algo, String provider,byte[] msg,  PrivateKey key) throws SignatureException
	{
		Signature sig;
		try {
			sig = Signature.getInstance(algo, provider);

			return signMessage(sig,msg,key);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new SignatureException("ASignature()->sigMessage" + e);
		}
				
	}
	
	public static boolean verifySig(Signature sig,byte[] msg, byte[] signature, PublicKey key) throws SignatureException
	{
		try {
			sig.initVerify(key);
			sig.update(msg);
			return sig.verify(signature);
		} catch (InvalidKeyException | java.security.SignatureException e) {
			throw new SignatureException("ASignature()->verifySig" + e);
		}
	}
	
	public static boolean verifySig(String algo, String provider,byte[] msg, byte[] signature, PublicKey key) throws SignatureException
	{
		try {
			Signature sign = Signature.getInstance(algo, provider);
			return verifySig(sign,msg,signature,key);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new SignatureException("ASignature()->verifySig" + e);
		}
	}
}

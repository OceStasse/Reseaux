package network.crypto;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.Mac;
import javax.crypto.SecretKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import generic.server.ARequete;

public abstract class AHMAC {
    static 
    {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    public static byte[] hash(Mac hmac,SecretKey secretKey, ARequete req) throws HMACException 
    {
    	
    	try {
    		hmac.init(secretKey);
			hmac.update(ConverterObject.convertObjectToByte(req));
			return  hmac.doFinal();
			
		} catch (IllegalStateException | IOException | InvalidKeyException e) {
			
			throw new HMACException("HMAC()->hash(): " + e);
		}
    	
    	
    }

    public static byte[] hash(String algo,String provider,SecretKey secretKey, ARequete req) throws HMACException 
    {
    	Mac hmac;
		try {
			hmac = Mac.getInstance(algo, provider);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new HMACException("HMAC()->hash()->Mac.getInstance: " + e);
		} 
    	return hash(hmac, secretKey, req);
    }

    public static boolean verify(Mac hmac,byte[] receivedHashed, byte[] message, SecretKey secretKey) throws HMACException 
    {
    	try {
			hmac.init(secretKey);
	    	hmac.update(message);
	    	byte[] generatedHashed = hmac.doFinal();        
	    	return MessageDigest.isEqual(generatedHashed, receivedHashed);
		} catch (InvalidKeyException e) {
			throw new HMACException("HMAC()->verify()->hmac.init: " + e);
		}
    }

    public static boolean verify(String algo,String provider,byte[] receivedHashed, byte[] message, SecretKey secretKey) throws HMACException 
    {
    	Mac hmac;
		try {
			hmac = Mac.getInstance(algo, provider);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new HMACException("HMAC()->verify()-> mac:" + e);
		} 
    	return verify(hmac, receivedHashed, message, secretKey) ;
    }
    
}

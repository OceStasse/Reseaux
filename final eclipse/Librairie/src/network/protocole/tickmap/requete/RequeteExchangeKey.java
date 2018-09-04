package network.protocole.tickmap.requete;

import java.io.IOException;
import java.security.PrivateKey;

import javax.crypto.SecretKey;

import generic.server.ARequete;
import network.communication.communicationException;
import network.crypto.ACryptographieSymetrique;
import network.crypto.ASignature;
import network.crypto.ConverterObject;
import network.crypto.CryptographieSymetriqueException;
import network.crypto.SignatureException;
import network.protocole.tickmap.reponse.ReponseExchangeKey;

public class RequeteExchangeKey extends ARequete {
	

	private static final long serialVersionUID = 1L;
	private SecretKey keyHMAC;
	private SecretKey keyCipher;
	private String algoCipher;
	private String algoHMAC;
	private String provider;
	private final byte[] sigMessage;
	
	

	public RequeteExchangeKey(PrivateKey key) throws SignatureException, IOException
	{
		super("GetSecretKey",null);
		algoCipher = "DES/ECB/PKCS5Padding";
		algoHMAC = "HMAC-MD5";
		provider = "BC";
		sigMessage = ASignature.signMessage("SHA1withRSA","BC",ConverterObject.convertObjectToByte(this),key);
		
	}
	
	public RequeteExchangeKey(String algoCipher, String algoHMAC, String provider, String algoSignature,PrivateKey key) throws SignatureException, IOException
	{
		super("GetSecretKey",null);
		this.algoCipher = algoCipher;
		this.algoHMAC = algoHMAC;
		this.provider = provider;
		sigMessage = ASignature.signMessage("SHA1withRSA","BC",ConverterObject.convertObjectToByte(this),key);
	}
	
	
	@Override
	protected void doAction() {
		
		try {
			try {
				
				keyHMAC = ACryptographieSymetrique.secretKey(algoHMAC, provider);
				keyCipher = ACryptographieSymetrique.secretKey(algoCipher, provider);
				this.reponse = ReponseExchangeKey.OK(keyHMAC, keyCipher);
				traceEvent("ExcahngeKey ok");
				
			} catch (CryptographieSymetriqueException e) {
				
				this.reponse = ReponseExchangeKey.KO("error to load secret key");
				traceEvent("Exchangekey->getKey: " + e.getMessage());
			}
			
			this.communication.send(this.reponse);
			
		} catch (communicationException e) {
			
			traceEvent("Exchangekey: "+ e.getMessage());
			
		}
		
	}
	
	public SecretKey getKeyHMAC() {
		return keyHMAC;
	}

	public SecretKey getKeyCipher() {
		return keyCipher;
	}

	public byte[] getSigMessage() {
		return sigMessage;
	}
	
}

package network.protocole.tickmap.requete;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import generic.server.ASecureRequete;
import network.communication.communicationException;
import network.crypto.ACryptographieSymetrique;
import network.crypto.CryptographieAsymetriqueException;
import network.crypto.CryptographieSymetriqueException;
import network.protocole.tickmap.reponse.ReponseExchangeKey;

public class RequeteExchangeKey extends ASecureRequete {
	

	private static final long serialVersionUID = 1L;
	private SecretKey keyHMAC;
	private SecretKey keyCipher;
	private String algoCipher;
	private String algoHMAC;
	private String provider;
	
	

	public RequeteExchangeKey() 
	{
		super("GetSecretKey",null);
		algoCipher = "DES/ECB/PKCS5Padding";
		algoHMAC = "HMAC-MD5";
		provider = "BC";
		
	}
	
	public RequeteExchangeKey(String algoCipher, String algoHMAC, String provider)
	{
		super("GetSecretKey",null);
		this.algoCipher = algoCipher;
		this.algoHMAC = algoHMAC;
		this.provider = provider;
	}
	
	
	@Override
	protected void doAction() {
		
		try {
			try {
				
				keyHMAC = ACryptographieSymetrique.secretKey(algoHMAC, provider);
				keyCipher = ACryptographieSymetrique.secretKey(algoCipher, provider);
				this.reponse = ReponseExchangeKey.OK(keyHMAC, keyCipher,this.getKeyPublic());
				traceEvent("ExcahngeKey ok");
				
			} catch (CryptographieSymetriqueException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | CryptographieAsymetriqueException | IOException e) {
				
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

	
}

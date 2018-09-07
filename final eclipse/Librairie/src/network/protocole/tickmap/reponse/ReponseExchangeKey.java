package network.protocole.tickmap.reponse;

import javax.crypto.SecretKey;

import generic.server.AReponse;

public class ReponseExchangeKey extends AReponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SecretKey keyHMAC;
	private final SecretKey keyCipher;
	
	public static ReponseExchangeKey OK(SecretKey keyHMAC, SecretKey keyCipher){
        return new ReponseExchangeKey("", true,keyHMAC,keyCipher);
    }

    public static ReponseExchangeKey KO(String message){
        return new ReponseExchangeKey(message, false,null,null);
    }
    
	private ReponseExchangeKey(String message, boolean successful,SecretKey keyHMAC, SecretKey keyCipher) {
        super(message, successful);
        this.keyCipher = keyCipher;
        this.keyHMAC = keyHMAC;
        
    }
	
	public SecretKey getKeyHMAC() {
		return keyHMAC;
	}

	public SecretKey getKeyCipher() {
		return keyCipher;
	}


}

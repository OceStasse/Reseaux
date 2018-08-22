package network.crypto;

public class EncryptionException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public EncryptionException() {
	super("CommunicatorException()");
    }
    
    public EncryptionException(String msg) {
	super(msg);
    }
}

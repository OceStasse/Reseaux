package encryption;

public class EncryptionException extends Exception {
    public EncryptionException() {
	super("CommunicatorException()");
    }
    
    public EncryptionException(String msg) {
	super(msg);
    }
}

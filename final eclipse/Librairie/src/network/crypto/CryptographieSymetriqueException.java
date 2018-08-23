package network.crypto;

public class CryptographieSymetriqueException extends Exception {
private static final long serialVersionUID = 1L;
    
    public CryptographieSymetriqueException() {
    	super("CryptographieSymetriqueException()");
    }
    
    public CryptographieSymetriqueException(String msg) {
    	super(msg);
    }
}

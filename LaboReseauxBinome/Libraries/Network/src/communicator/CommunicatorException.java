package communicator;

public class CommunicatorException extends Exception {
    public CommunicatorException() {
	super("CommunicatorException()");
    }
    
    public CommunicatorException(String msg) {
	super(msg);
    }
}

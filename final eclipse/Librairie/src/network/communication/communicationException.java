package network.communication;

public class communicationException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public communicationException() {
	super("communicationException()");
    }
    
    public communicationException(String msg) {
	super(msg);
    }
}

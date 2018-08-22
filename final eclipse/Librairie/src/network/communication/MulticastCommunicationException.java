package network.communication;

public class MulticastCommunicationException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public MulticastCommunicationException() {
	super("MulticastCommunicatorException()");
    }

    public MulticastCommunicationException(String msg) {
	super(msg);
    }

}

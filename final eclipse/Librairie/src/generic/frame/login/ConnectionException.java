package generic.frame.login;

public class ConnectionException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public ConnectionException() {
	super("ConnectionException()");
    }
    
    public ConnectionException(String msg) {
	super(msg);
    }

}

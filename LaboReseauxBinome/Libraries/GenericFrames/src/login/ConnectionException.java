package login;

public class ConnectionException extends Exception {
    public ConnectionException() {
	super("ConnectionException()");
    }
    
    public ConnectionException(String msg) {
	super(msg);
    }
}

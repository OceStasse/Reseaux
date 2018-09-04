package network.crypto;

public class SignatureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SignatureException() {
		super("SignatureException()");
	}

	public SignatureException(String msg) {
		super(msg);
	}
}

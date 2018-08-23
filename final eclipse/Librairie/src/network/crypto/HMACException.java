package network.crypto;

public class HMACException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HMACException() {
		super("HMACException()");
	}

	public HMACException(String msg) {
		super(msg);
	}
}

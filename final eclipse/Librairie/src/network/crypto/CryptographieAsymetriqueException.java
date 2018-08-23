package network.crypto;

public class CryptographieAsymetriqueException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public CryptographieAsymetriqueException() {
		super("CryptageAsymétriqueException()");
	}

	public CryptographieAsymetriqueException(String msg) {
		super(msg);
	}
}

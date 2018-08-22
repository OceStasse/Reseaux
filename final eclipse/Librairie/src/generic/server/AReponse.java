package generic.server;

import java.io.Serializable;

public abstract class AReponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final String message;
    private final boolean successful;

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the successful
     */
    public boolean isSuccessful() {
        return successful;
    }

    protected AReponse(String message, boolean successful) {
        this.message = message;
        this.successful = successful;
    }

}

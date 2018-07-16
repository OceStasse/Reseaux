package server;

import java.io.Serializable;

public abstract class Reponse implements Serializable {
    private final String message;
    private final boolean successful;

    protected Reponse(String message, boolean successful) {
        this.message = message;
        this.successful = successful;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public String getMessage(){
        return this.message;
    }
    public boolean isSuccessful(){
        return this.successful;
    }
    //</editor-fold>
}

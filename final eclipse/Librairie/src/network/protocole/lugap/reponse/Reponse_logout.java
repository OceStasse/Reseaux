package network.protocole.lugap.reponse;

import java.io.Serializable;

import generic.server.AReponse;

public class Reponse_logout extends AReponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Reponse_logout(String message, boolean successful) {
        super(message, successful);
    }
    
    public static Reponse_logout OK(){
        return new Reponse_logout("", true);
    }
    
    public static Reponse_logout KO(String message){
        return new Reponse_logout(message, false);
    }
}

package network.protocole.lugap.reponse;

import java.io.Serializable;

import generic.server.AReponse;

public class Reponse_login extends AReponse implements Serializable {
    private Reponse_login(String message, boolean successful) {
        super(message, successful);
    }
    
    public static Reponse_login OK(){
        return new Reponse_login("", true);
    }
    
    public static Reponse_login KO(String message){
        return new Reponse_login(message, false);
    }
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}

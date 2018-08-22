package network.protocole.tickmap.reponse;

import java.io.Serializable;

import generic.server.AReponse;

public class ReponseLogout extends AReponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ReponseLogout(String message, boolean successful) {
        super(message, successful);
    }
    
    public static ReponseLogout OK(){
        return new ReponseLogout("", true);
    }
    
    public static ReponseLogout KO(String message){
        return new ReponseLogout(message, false);
    }
}

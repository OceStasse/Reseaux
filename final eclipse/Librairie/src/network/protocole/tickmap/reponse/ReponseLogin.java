package network.protocole.tickmap.reponse;

import java.io.Serializable;

import generic.server.AReponse;

public class ReponseLogin extends AReponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ReponseLogin(String message, boolean successful) {
        super(message, successful);
    }

    public static ReponseLogin OK(){
        return new ReponseLogin("", true);
    }

    public static ReponseLogin KO(String message){
        return new ReponseLogin(message, false);
    }
}

package network.protocole.lugap.reponse;

import java.io.Serializable;

import generic.server.AReponse;

public class Reponse_MAJ_champ extends AReponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Reponse_MAJ_champ(String message, boolean successful) {
        super(message, successful);
    }
    
    public static Reponse_MAJ_champ OK(){
        return new Reponse_MAJ_champ("", true);
    }
    
    public static Reponse_MAJ_champ KO(String message){
        return new Reponse_MAJ_champ(message, false);
    }
}

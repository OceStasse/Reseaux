package network.protocole.lugap.reponse;

import java.io.Serializable;
import java.util.ArrayList;

import database.entities.Vol;
import generic.server.AReponse;

public class Reponse_Vols extends AReponse implements Serializable {
    
    private ArrayList<Vol> vols;
    
    protected Reponse_Vols(String message, boolean successful, ArrayList<Vol> vols) {
	super(message, successful);
	this.vols = vols;
    }
    
    public static Reponse_Vols OK(ArrayList<Vol> flights){
        return new Reponse_Vols("", true, flights);
    }
    
    public static Reponse_Vols KO(String message){
        return new Reponse_Vols(message, false, null);
    }

    public ArrayList<Vol> getFlights() {
        return vols;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}

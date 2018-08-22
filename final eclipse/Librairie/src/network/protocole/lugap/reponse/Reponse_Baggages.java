package network.protocole.lugap.reponse;

import java.io.Serializable;
import java.util.ArrayList;

import database.entities.Baggage;
import generic.server.AReponse;

public class Reponse_Baggages extends AReponse implements Serializable {
    ArrayList<Baggage> baggages;
    
    private Reponse_Baggages(String message, boolean successful, ArrayList<Baggage> Baggages) {
        super(message, successful);
        this.baggages = Baggages;
    }
    
    public static Reponse_Baggages OK(ArrayList<Baggage> baggages){
        return new Reponse_Baggages("", true, baggages);
    }
    
    public static Reponse_Baggages KO(String message){
        return new Reponse_Baggages(message, false, null);
    }

    public ArrayList<Baggage> getBaggages() {
        return baggages;
    }
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}

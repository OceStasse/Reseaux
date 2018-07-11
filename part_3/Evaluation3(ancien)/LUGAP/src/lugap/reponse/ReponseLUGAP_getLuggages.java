package lugap.reponse;

import entities.Luggage;
import java.io.Serializable;
import java.util.ArrayList;
import server.Reponse;

public class ReponseLUGAP_getLuggages extends Reponse implements Serializable {
    ArrayList<Luggage> luggages;
    
    private ReponseLUGAP_getLuggages(String message, boolean successful, ArrayList<Luggage> luggages) {
        super(message, successful);
        this.luggages = luggages;
    }
    
    public static ReponseLUGAP_getLuggages OK(ArrayList<Luggage> luggages){
        return new ReponseLUGAP_getLuggages("", true, luggages);
    }
    
    public static ReponseLUGAP_getLuggages KO(String message){
        return new ReponseLUGAP_getLuggages(message, false, null);
    }

    public ArrayList<Luggage> getLuggages() {
        return luggages;
    }
}

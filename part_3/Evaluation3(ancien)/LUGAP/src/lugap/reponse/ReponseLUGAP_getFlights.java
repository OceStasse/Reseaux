package lugap.reponse;

import entities.Flight;
import java.io.Serializable;
import java.util.ArrayList;
import server.Reponse;

public class ReponseLUGAP_getFlights  extends Reponse implements Serializable{
    ArrayList<Flight> flights;
    
    private ReponseLUGAP_getFlights(String message, boolean successful, ArrayList<Flight> flights) {
        super(message, successful);
        this.flights = flights;
    }
    
    public static ReponseLUGAP_getFlights OK(ArrayList<Flight> flights){
        return new ReponseLUGAP_getFlights("", true, flights);
    }
    
    public static ReponseLUGAP_getFlights KO(String message){
        return new ReponseLUGAP_getFlights(message, false, null);
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }
}

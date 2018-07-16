package tickmap.reponse;

import entities.Flight;
import java.io.Serializable;
import java.util.ArrayList;
import server.Reponse;

public class ReponseTICKMAP_vols extends Reponse implements Serializable {
	ArrayList<Flight> flights;

    private ReponseTICKMAP_vols(String message, boolean successful, ArrayList<Flight> flights) {
        super(message, successful);
        this.flights = flights;
    }

    public static ReponseTICKMAP_vols OK(ArrayList<Flight> flights){
        return new ReponseTICKMAP_vols("", true, flights);
    }

    public static ReponseTICKMAP_vols KO(String message){
        return new ReponseTICKMAP_vols(message, false, null);
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }
}

package lugap.requete;

import communicator.CommunicatorException;
import entities.Flight;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lugap.reponse.ReponseLUGAP_getFlights;

public class RequeteLUGAP_getFlights extends RequeteLUGAP implements  Serializable {
    public RequeteLUGAP_getFlights() {
        super("GetFlight", "SELECT * "
                            + "FROM flight "
                            + "WHERE departure=CURDATE() "
                            + "ORDER BY takeOffTime ASC");
    }
    
    @Override
    protected void doAction() {
        ArrayList<Flight> flights = new ArrayList<>();

        try {
            try {
                ResultSet resultSet = this.databaseAccess.executeQuery(this.sqlStatement);
                
                System.out.println("\nToday's flights are : ");
                while(resultSet.next()){
                    Flight flight = new Flight(resultSet.getInt("fk_idairplane"),
                           resultSet.getString("fk_idairline"),
                           resultSet.getDate("departure").toLocalDate(),
                           resultSet.getString("destination"),
                           resultSet.getTime("takeOffTime").toLocalTime(),
                           resultSet.getTime("scheduledLanding").toLocalTime());
                    
                    flights.add(flight);
                    System.out.println("    Flight n°" + flights.size() + " : " + flight.toString());
                }
                System.out.println("-------");
                
                this.reponse = ReponseLUGAP_getFlights.OK(flights);
                traceEvent("GetFlight OK (flights found : " + flights.size() + ")");
                
            } catch (SQLException ex) {
                this.reponse = ReponseLUGAP_getFlights.KO("Erreur Serveur (SQL)");
                traceEvent("Erreur SQL/BDD : " + ex.getMessage());
            }
            
            this.communicator.SendSerializable(this.reponse);
        } catch (CommunicatorException ex) {
            traceEvent(ex.getMessage());
        }
    }
}

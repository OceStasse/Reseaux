package lugap.requete;

import communicator.CommunicatorException;
import entities.Flight;
import entities.Luggage;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lugap.reponse.ReponseLUGAP_getLuggages;

public class RequeteLUGAP_getLuggages extends RequeteLUGAP implements  Serializable {
    private final Flight flight;
    
    public RequeteLUGAP_getLuggages(Flight flight) {
        super("getLuggages", "SELECT passenger.firstname, passenger.lastname, luggage.fk_idticket, luggage.idluggage, "
                                + "luggage.weight, luggage.isluggage, luggage.received, luggage.loaded, "
                                + "luggage.checkedbycustom, luggage.comments "
                            + "FROM luggage INNER JOIN ticket "
                                + "ON luggage.fk_idairplane = ticket.fk_idairplane "
                                + "AND luggage.fk_idairline = ticket.fk_idairline "
                                + "AND luggage.fk_departure = ticket.fk_departure "
                                + "AND luggage.fk_destination = ticket.fk_destination "
                                + "AND luggage.fk_idticket = ticket.idticket "
                            + "INNER JOIN passenger "
                                + "ON ticket.fk_idpassenger = passenger.idpassenger "
                            + "WHERE luggage.fk_idairplane = ? "
                                + "AND luggage.fk_idairline = ? "
                                + "AND luggage.fk_departure = ? "
                                + "AND luggage.fk_destination = ? "
                                + "FOR UPDATE");
        
        this.flight = flight;
    }

    @Override
    protected void doAction() {
        ArrayList<Luggage> luggages = new ArrayList<>();
        Map<Integer, Object> statementMap = new HashMap<>();
        statementMap.put(1, this.flight.getIdAirplane());
        statementMap.put(2, this.flight.getIdAirline());
        statementMap.put(3, this.flight.getDepartureDate());
        statementMap.put(4, this.flight.getDestination());
        
        try {
            try {
                PreparedStatement preparedStatement = this.databaseAccess.getPreparedStatement(sqlStatement);
                preparedStatement.setQueryTimeout(5);
                ResultSet resultSet = this.databaseAccess.executeQuery(preparedStatement, statementMap);
                
                System.out.println("\nFlight [" + this.flight.toString() + "]'s luggages : ");
                while(resultSet.next()){
                    Luggage luggage = new Luggage(this.flight,
                            resultSet.getString("firstname"),
                            resultSet.getString("lastname"),
                            resultSet.getInt("fk_idticket"),
                            resultSet.getInt("idluggage"),
                            resultSet.getFloat("weight"),
                            resultSet.getBoolean("isluggage"),
                            resultSet.getString("received").charAt(0),
                            resultSet.getString("loaded").charAt(0),
                            resultSet.getString("checkedbycustom").charAt(0),
                            resultSet.getString("comments"));
                    
                   luggages.add(luggage);
                    System.out.println("    luggage n°" + luggages.size() + " : " + luggage.toString());
                }
                System.out.println("-------");
                
                this.reponse = ReponseLUGAP_getLuggages.OK(luggages);
                traceEvent("GetLuggages OK (luggages found : " + luggages.size() + ")");
            } catch (SQLException ex) {
                this.reponse = ReponseLUGAP_getLuggages.KO("Erreur Serveur (SQL) [" + ex.getMessage() + "]");
                traceEvent("Erreur SQL/BDD : " + ex.getMessage());
            }
            
            this.communicator.SendSerializable(this.reponse);
        } catch (CommunicatorException ex) {
            traceEvent(ex.getMessage());
        }
    }

    public Flight getFlight() {
        return flight;
    }
}

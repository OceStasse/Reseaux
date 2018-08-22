package network.protocole.lugap.requete;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.entities.Baggage;
import database.entities.Vol;
import generic.server.ARequete;
import network.communication.communicationException;
import network.protocole.lugap.reponse.Reponse_Baggages;

public class Requete_Baggages extends ARequete implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final Vol vol;
    
    public Requete_Baggages(Vol vol) {
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
        
        this.vol = vol;
    }
    @Override
    protected void doAction() {
	ArrayList<Baggage> baggages = new ArrayList<>();
        Map<Integer, Object> statementMap = new HashMap<>();
        statementMap.put(1, this.vol.getIdAvion());
        statementMap.put(2, this.vol.getIdCompagnieAvion());
        statementMap.put(3, this.vol.getDepartDate());
        statementMap.put(4, this.vol.getDestination());
        
        try {
            try {
                PreparedStatement preparedStatement = this.database.getPreparedStatement(sqlStatement);
                preparedStatement.setQueryTimeout(5);
                ResultSet resultSet = this.database.executeQuery(preparedStatement, statementMap);
                
                System.out.println("\nVol [" + this.vol.toString() + "]'s baggages : ");
                while(resultSet.next()){
                    Baggage baggage = new Baggage(this.vol,
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
                    
                    baggages.add(baggage);
                    System.out.println("    baggage n°" + baggages.size() + " : " + baggage.toString());
                }
                System.out.println("-------");
                
                this.reponse = Reponse_Baggages.OK(baggages);
                traceEvent("GetBaggages OK (baggages trouver : " + baggages.size() + ")");
            } catch (SQLException ex) {
                this.reponse = Reponse_Baggages.KO("Erreur Serveur (SQL) [" + ex.getMessage() + "]");
                traceEvent("Erreur SQL/BDD : " + ex.getMessage());
            }
            
            this.communication.send(this.reponse);
        } catch (communicationException ex) {
            traceEvent(ex.getMessage());
        }

    }
    
    public Vol getFlight() {
        return vol;
    }

}

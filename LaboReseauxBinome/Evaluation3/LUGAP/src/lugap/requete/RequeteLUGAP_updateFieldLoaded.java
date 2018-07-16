package lugap.requete;

import communicator.CommunicatorException;
import entities.Luggage;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import lugap.reponse.ReponseLUGAP_updateField;

public class RequeteLUGAP_updateFieldLoaded extends RequeteLUGAP implements Serializable {
    private final Luggage luggage;
    private final char newValue;

    public RequeteLUGAP_updateFieldLoaded(Luggage luggage, char newValue) {
        super("updateFieldReceived", "UPDATE luggage "
                                    + "SET loaded=? "
                                    + "WHERE fk_idairplane = ? "
                                    + "AND fk_idairline = ? "
                                    + "AND fk_departure = ? "
                                    + "AND fk_destination = ? "
                                    + "AND fk_idticket = ? "
                                    + "AND idluggage = ?");
        this.luggage = luggage;
        this.newValue = newValue;
    }

    @Override
    protected void doAction() {
        Map<Integer, Object> statementMap = new HashMap<>();
        statementMap.put(1, String.valueOf(this.newValue));
        statementMap.put(2, this.luggage.getFlight().getIdAirplane());
        statementMap.put(3, this.luggage.getFlight().getIdAirline());
        statementMap.put(4, this.luggage.getFlight().getDepartureDate());
        statementMap.put(5, this.luggage.getFlight().getDestination());
        statementMap.put(6, this.luggage.getIdTicket());
        statementMap.put(7, this.luggage.getIdLuggage());
        
        try {
            try {
                int result = this.databaseAccess.executeUpdate(this.sqlStatement, statementMap);
                
                if(result==0){
                    this.reponse = ReponseLUGAP_updateField.KO("No row updated in database");
                    traceEvent("No row updated in database (luggage:" + this.luggage.toString() + ")");
                }
                else{
                    this.reponse = ReponseLUGAP_updateField.OK();
                    traceEvent("Field 'loaded' updated to '" + this.newValue + "' (luggage: " + this.luggage.toString() + ")");
                }
            } catch (SQLException ex) {
                this.reponse = ReponseLUGAP_updateField.KO("Erreur Serveur (SQL)");
                traceEvent("Erreur SQL/BDD : " + ex.getMessage());
            } catch (InterruptedException ex) {
                this.reponse = ReponseLUGAP_updateField.KO("Erreur Serveur (Interrupted)");
                traceEvent("Erreur (Interrupted) : " + ex.getMessage());
            }
            
            this.communicator.SendSerializable(this.reponse);
        } catch (CommunicatorException ex) {
            traceEvent("UpdateFieldLoaded : " + ex.getMessage());
        }
    }
}

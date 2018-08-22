package network.protocole.lugap.requete;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import database.entities.Baggage;
import generic.server.ARequete;
import network.communication.communicationException;
import network.protocole.lugap.reponse.Reponse_MAJ_champ;

public class Requete_MAJ_ChampCheckedCustom extends ARequete implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final Baggage baggage;
    private final char newValue;

    public Requete_MAJ_ChampCheckedCustom(Baggage baggage, char newValue) {
        super("updateFieldReceived", "UPDATE luggage "
                                    + "SET checkedbycustom=? "
                                    + "WHERE fk_idairplane = ? "
                                    + "AND fk_idairline = ? "
                                    + "AND fk_departure = ? "
                                    + "AND fk_destination = ? "
                                    + "AND fk_idticket = ? "
                                    + "AND idluggage = ?");
        this.baggage = baggage;
        this.newValue = newValue;
    }

    @Override
    protected void doAction() {
	Map<Integer, Object> statementMap = new HashMap<>();
        statementMap.put(1, String.valueOf(this.newValue));
        statementMap.put(2, this.baggage.getVol().getIdAvion());
        statementMap.put(3, this.baggage.getVol().getIdCompagnieAvion());
        statementMap.put(4, this.baggage.getVol().getDepartDate());
        statementMap.put(5, this.baggage.getVol().getDestination());
        statementMap.put(6, this.baggage.getIdTicket());
        statementMap.put(7, this.baggage.getIdLuggage());
        
        try {
            try {
                int result = this.database.executeUpdate(this.sqlStatement, statementMap);
                
                if(result==0){
                    this.reponse = Reponse_MAJ_champ.KO("No row updated in database");
                    traceEvent("No row updated in database (luggage:" + this.baggage.toString() + ")");
                }
                else{
                    this.reponse = Reponse_MAJ_champ.OK();
                    traceEvent("Field 'checkedbycustom' updated to '" + this.newValue + "' (luggage: " + this.baggage.toString() + ")");
                }
            } catch (SQLException ex) {
                this.reponse = Reponse_MAJ_champ.KO("Erreur Serveur (SQL)");
                traceEvent("Erreur SQL/BDD : " + ex.getMessage());
            } catch (InterruptedException ex) {
                this.reponse = Reponse_MAJ_champ.KO("Erreur Serveur (Interrupted)");
                traceEvent("Erreur (Interrupted) : " + ex.getMessage());
            }
            
            this.communication.send(this.reponse);
        } catch (communicationException ex) {
            traceEvent("UpdateFieldCheckedByCustoms : " + ex.getMessage());
        }
    }
}

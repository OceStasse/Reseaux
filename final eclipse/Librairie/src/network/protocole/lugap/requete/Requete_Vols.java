package network.protocole.lugap.requete;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.entities.Vol;
import generic.server.ARequete;
import network.communication.communicationException;
import network.protocole.lugap.reponse.Reponse_Vols;

public class Requete_Vols extends ARequete implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public Requete_Vols() {
        super("GetFlight", "SELECT * "
                            + "FROM flight "
                            + "WHERE departure=CURDATE() "
                            + "ORDER BY takeOffTime ASC");
    }
    
    @Override
    protected void doAction() {
	ArrayList<Vol> vols = new ArrayList<>();

        try {
            try {
                ResultSet resultSet = this.database.executeQuery(this.sqlStatement);
                
                System.out.println("\nToday's flights are : ");
                while(resultSet.next()){
                    Vol vol = new Vol(resultSet.getInt("fk_idairplane"),
                                                resultSet.getString("fk_idairline"),
                                                resultSet.getDate("departure"),
                                                resultSet.getString("destination"),
                                                resultSet.getString("fk_idgeographiczone"),
                                                resultSet.getInt("distance"),
                                                resultSet.getTime("takeOffTime"),
                                                resultSet.getTime("scheduledLanding"),
                                                resultSet.getInt("seatsSold"),
                                                resultSet.getDouble("price"),
                                                resultSet.getString("piste")
                                            );
                    
                    vols.add(vol);
                    System.out.println("    Flight n°" + vols.size() + " : " + vol.toString());
                }
                System.out.println("-------");
                
                this.reponse = Reponse_Vols.OK(vols);
                traceEvent("GetFlight OK (flights found : " + vols.size() + ")");
                
            } catch (SQLException ex) {
                this.reponse = Reponse_Vols.KO("Erreur Serveur (SQL)");
                traceEvent("Erreur SQL/BDD : " + ex.getMessage());
            }
            
            this.communication.send(this.reponse);
        } catch (communicationException ex) {
            traceEvent(ex.getMessage());
        }

    }

}

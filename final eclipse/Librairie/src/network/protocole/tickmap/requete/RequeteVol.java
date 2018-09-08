package network.protocole.tickmap.requete;

import java.io.IOException;
import java.security.PrivateKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.entities.Vol;
import generic.server.ARequete;
import network.communication.communicationException;
import network.crypto.SignatureException;
import network.protocole.tickmap.reponse.ReponseVols;

public class RequeteVol extends ARequete {
	
	
	private static final long serialVersionUID = 1L;
	
	
	public RequeteVol()
	{
		super("GetFlight","select * "+
					   "from flight " +
				       "where departure between now() and date_add(NOW(),interval 7 day);");
	}
	
	@Override
	protected void doAction() {
		ArrayList<Vol> vols = new ArrayList<Vol>();
		try
		{
			try
			{
				ResultSet result = this.database.executeQuery(this.sqlStatement);
				System.out.println("\nToday's flights are : ");
				while(result.next())
				{
					Vol vol = new Vol(result.getInt("fk_idairplane"),
							result.getString("fk_idairline"),
							result.getDate("departure"),
							result.getString("destination"),
							result.getString("fk_idgeographiczone"),
							result.getInt("distance"),
							result.getTime("takeOffTime"),
							result.getTime("scheduledLanding"),
							result.getInt("seatsSold"),
							result.getDouble("price"),
							result.getString("piste")
							);
					vols.add(vol);
					System.out.println("    Flight n°" + vols.size() + " : " + vol.toString());
				}
				System.out.println("-------");
				//crypté
				this.reponse = ReponseVols.OK(vols);
				traceEvent("GetFlight OK (flights found : " + vols.size() + ")");
			}
			catch(SQLException e)
			{
				this.reponse = ReponseVols.KO("Erreur Serveur (SQL)");
				traceEvent("Erreur SQL/BDD : " + e.getMessage());
			}

			this.communication.send(this.reponse);
		}
		catch(communicationException ex)
		{
			traceEvent("GetFlight" + ex.getMessage());
		}
	}

}

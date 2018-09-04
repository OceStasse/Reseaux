package network.protocole.tickmap.requete;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import database.entities.Billet;
import database.entities.Caddie;
import generic.server.ASecureSymetricRequete;
import network.communication.communicationException;
import network.crypto.ACryptographieSymetrique;
import network.crypto.ConverterObject;
import network.crypto.CryptographieSymetriqueException;
import network.protocole.tickmap.reponse.ReponseReserver;

public class RequeteReserver extends ASecureSymetricRequete {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final byte[] encryptBillet;
	//requeteCaddi pour que le client l'obtiennent et le passe en paramètre 
	private Caddie caddie;
	

	public RequeteReserver(Cipher cipher, SecretKey key,Billet billet) throws CryptographieSymetriqueException, IOException
	{
		super("Add Billet","insert into ticket (fk_idairplane,fk_idairline,fk_departure,fk_destination,fk_idpassenger,nbaccompagnant,idticket)" + 
										"values(?,?,?,?,?)");
		
		this.encryptBillet = ACryptographieSymetrique.encrypt(cipher,ConverterObject.convertObjectToByte(billet),key);
		
		
	}
	
	//pour l'id du client on crée un client bidon pour les application billets
	
	@Override
	protected void doAction() {
		try {
			try {
				byte[] decryptBillet = ACryptographieSymetrique.decrypt(Cipher.getInstance("DES/ECB/PKCS5Padding","BC" ), encryptBillet, this.getKey());
				Map<Integer, Object> statementMap = new HashMap<>();
				Billet billet = (Billet) ConverterObject.convertByteToObject(decryptBillet);
				ResultSet resultSet;
				ArrayList<Integer> places = new ArrayList<Integer>();
				int priceUnit, placeRestant;
				
				statementMap.put(1, billet.getIdAirPlane());
				statementMap.put(2, billet.getIdAirLine());
				statementMap.put(3, billet.getDeparture());
				statementMap.put(4, billet.getDestination());
				
				//select le nombre de palce restant et le nombre total
				String sql = "SELECT seatsSold, seats, price"
						+ " FROM flight INNER JOIN airplane"
						+ "   ON flight.fk_idairplane = airplane.idairplane"
						+ "   AND flight.fk_idairline = airplane.fk_idairline"
						+ " WHERE fk_idairplane=?"
						+ "   AND flight.fk_idairline=?"
						+ "   AND departure=?"
						+ "   AND destination=?"
						+ " FOR UPDATE";				

				resultSet = this.database.executeQuery(sql, statementMap);

				if(resultSet.next())
				{
					//check si dispo
					if(resultSet.getInt("seatSold") + billet.getNbAccompagnant() <= resultSet.getInt("seats"))
					{
						priceUnit = resultSet.getInt("price");
						placeRestant = resultSet.getInt("seatsSold");
						sql = "UPDATE flight"
								+ " SET seatsSold=" + (resultSet.getInt("seatsSold") + billet.getNbAccompagnant())
								+ " WHERE fk_idairplane=?"
								+ "   AND fk_idairline=?"
								+ "   AND departure=?"
								+ "   AND destination=?";

						if(this.database.executeUpdate(sql, statementMap ) == 1 )
						{
							//Si on a déjà réservé des places pour ce vol, on update le nombre
							statementMap.put(5, caddie.getIdCaddie() );
							sql = "SELECT iditem, reservedSeats"
									+ " FROM caddieitem"
									+ " WHERE fk_idairplane=?"
									+ "   AND fk_idairline=?"
									+ "   AND fk_departure=?"
									+ "   AND fk_destination=?"
									+ "   AND fk_idcaddie=?"
									+ " FOR UPDATE";
							resultSet = this.database.executeQuery(sql, statementMap);

							if(resultSet.next())
							{
								//maj de l'item
								sql = "UPDATE caddieitem"
										+ " SET reservedSeats = " +  (resultSet.getInt("reservedSeats") + billet.getNbAccompagnant())
										+ " WHERE iditem=" + resultSet.getInt("iditem");
							}
							else
							{
								//insert de l'item
								statementMap.put(6,billet.getNbAccompagnant() );
								sql = "INSERT INTO caddieitem"
										+ " (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idcaddie`, `reservedSeats`)"
										+ " VALUES(?, ?, ?, ?, ?, ?)";
							}
							if( this.database.executeUpdate(sql, statementMap) == 1)
							{
								//insertion ok 
								this.database.commit();

								//envoie de la réponse 
								for(int i = 1; i <= billet.getNbAccompagnant(); i++)
								{
									places.add(Integer.valueOf(placeRestant + i));
								}
								
								this.communication.send(ReponseReserver.OK(priceUnit * billet.getNbAccompagnant(),places,getKey()));

							}	
							else
							{
								//error lors de maj ou de l'insert
								this.database.rollback();
								//envoie de la réponse KO
								this.communication.send(ReponseReserver.KO("error de la réservation"));

							}
						}
						else
						{
							this.database.rollback();
							//plus assez de place reponse
							this.communication.send(ReponseReserver.KO("Plus assez de place"));
						}
					}
				} 
			}catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
					| CryptographieSymetriqueException | ClassNotFoundException | IOException e) {

				try {
					this.communication.send(ReponseReserver.KO("Error decrypt"));
				} catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | IOException
						| CryptographieSymetriqueException e1) {
					traceEvent("Error :  " + e1.getMessage());
				}

				traceEvent("Error RequeteVol: " + e.getMessage());
			} catch (SQLException | InterruptedException e) {
				traceEvent("Error db:  " + e.getMessage());
				try {
					this.database.rollback();
				} catch (SQLException e1) {
					traceEvent("Error db:  " + e1.getMessage());
				}
			}
		} catch (communicationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public byte[] getBillet() {
		return encryptBillet;
	}
	
}

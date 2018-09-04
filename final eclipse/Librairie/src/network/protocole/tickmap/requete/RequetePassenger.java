package network.protocole.tickmap.requete;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import database.entities.Passenger;
import generic.server.ASecureSymetricRequete;
import network.crypto.ACryptographieSymetrique;
import network.crypto.ConverterObject;
import network.crypto.CryptographieSymetriqueException;
import network.protocole.tickmap.reponse.ReponsePassenger;

public class RequetePassenger extends ASecureSymetricRequete {
	
	private static final long serialVersionUID = 1L;
	
	private byte[] encryptPassenger;
	public byte[] getEncryptPassenger() {
		return encryptPassenger;
	}

	public RequetePassenger(Passenger passenger,SecretKey key) 
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, CryptographieSymetriqueException, IOException 
	{
		super("GETPASSENGER", "");
		this.encryptPassenger = ACryptographieSymetrique.encrypt(Cipher.getInstance("DES/ECB/PKCS5Padding","BC"),ConverterObject.convertObjectToByte(passenger),key);
		
		
	}

	@Override
	protected void doAction() {
		// decrypt
		Passenger passenger;
		try {
			passenger = (Passenger) ConverterObject.convertByteToObject(ACryptographieSymetrique.decrypt(Cipher.getInstance("DES/ECB/PKCS5Padding","BC"), encryptPassenger, this.getKey()));
			
			String sql = "select idpassenger, lastname, birthday, gender,firstname,login,password"+
								"from passenger"+
								"where idpassenger = " + passenger.getId();
			ResultSet resultSet = this.database.executeQuery(sql);
			
			if(!resultSet.next())
			{
				sql = " insert into passenger (idpassenger, lastname, birthday, gender,firstname,login,password)"
						+ "value (?,?,?,?,?,?,?)";
				Map<Integer, Object> statementMap = new HashMap<>();
				statementMap.put(1, passenger.getId());
				statementMap.put(2, passenger.getLastName());
				statementMap.put(3,passenger.getBirthdate());
				statementMap.put(4, passenger.getGender());
				statementMap.put(5, passenger.getFirstname());
				statementMap.put(6, passenger.getLogin());
				statementMap.put(7, passenger.getPassword());
				if( this.database.executeUpdate(sql, statementMap) == 1)
				{
					//insertion ok 
					this.database.commit();
					this.communication.send(ReponsePassenger.OK());
				}
				else
				{
					this.communication.send(ReponsePassenger.KO("Error Base de donnée"));
					this.database.rollback();
				}
			}
			
		} catch (ClassNotFoundException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
				| IOException | CryptographieSymetriqueException e) {
			traceEvent("Error Cryptage: " + e.getMessage());
			this.communication.send(ReponsePassenger.KO("Error Base de donnée"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

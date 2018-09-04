package network.protocole.tickmap.requete;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.NoSuchPaddingException;

import database.entities.Caddie;
import generic.server.ASecureSymetricRequete;
import network.communication.communicationException;
import network.crypto.CryptographieSymetriqueException;
import network.protocole.tickmap.reponse.ReponseCaddie;

public class RequeteCaddie extends ASecureSymetricRequete {

	private static final long serialVersionUID = 1L;
	private int idpassenger;
	
	
	public RequeteCaddie(int idpassenger)
	{
		super("GETCADDIE","Select idcaddie,fk_idpassenger"
											+ "from caddie"
											+ "where fk_idpassenger = ?");
		this.idpassenger = idpassenger;
	}
	
	@Override
	protected void doAction() {
		Caddie caddie = new Caddie();
		Map<Integer, Object> statementMap = new HashMap<Integer, Object>();
		statementMap.put(1, idpassenger);
		 try {
		
			 PreparedStatement preparedStatement = this.database.getPreparedStatement(sqlStatement);
       
			preparedStatement.setQueryTimeout(5);
			ResultSet resultSet = this.database.executeQuery(preparedStatement, statementMap);
            
			if(resultSet.next())
			{
				caddie.setIdCaddie(resultSet.getInt("idcaddie"));
				caddie.setIdPassenger(resultSet.getInt("fk_idpassenger"));
			}
			else
			{
				String sql = "insert into caddie "+
												" (fk_idpassenger) values(" + this.idpassenger + ")";
				preparedStatement = this.database.getPreparedStatement(sql);
				
				
				
				if(this.database.executeUpdate(sql) == 1)
				{
					preparedStatement = this.database.getPreparedStatement(sqlStatement);
				       
					preparedStatement.setQueryTimeout(5);
					resultSet = this.database.executeQuery(preparedStatement, statementMap);
					if(resultSet.next())
					{
						caddie.setIdCaddie(resultSet.getInt("idcaddie"));
						caddie.setIdPassenger(resultSet.getInt("fk_idpassenger"));
						// reponse ok
						this.database.commit();
						this.communication.send(ReponseCaddie.OK(caddie, this.getKey()));
					}
					else
					{
						//reponse ko
						this.database.rollback();
						this.communication.send(ReponseCaddie.KO("Error with the database"));
					}
				}
				else
				{
					//reponse ko
					this.database.rollback();
					this.communication.send(ReponseCaddie.KO("Error with the database"));
				}
				
			}
			this.communication.send(ReponseCaddie.OK(caddie, this.getKey()));
		} catch (SQLException | InterruptedException e) {
			traceEvent("error database: " + e.getMessage());
			try {
				this.communication.send(ReponseCaddie.KO("Error with the database"));
			} catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
					| communicationException | CryptographieSymetriqueException | IOException e1) {
					
			}
		} catch (NoSuchAlgorithmException | NoSuchProviderException | IOException | NoSuchPaddingException | CryptographieSymetriqueException e) {
			traceEvent("Error with crypto: " + e.getMessage());

			try {
				this.database.rollback();
			} catch (SQLException e2) {
			}
			try {
				this.communication.send(ReponseCaddie.KO("Error with crypto"));
			} catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
					| communicationException | CryptographieSymetriqueException | IOException e1) {
			}
		} catch (communicationException e) {
			traceEvent("Error socket" + e.getMessage());
			try {
				this.database.rollback();
			} catch (SQLException e2) {
			}
		} 
		
		

	}

}

package network.protocole.tickmap.requete;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


import generic.server.ARequete;
import network.communication.communicationException;
import network.crypto.AEncryption;
import network.crypto.EncryptionException;
import network.protocole.tickmap.reponse.ReponseLogin;

public class RequeteLogin extends ARequete implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final String login;
    private final byte[] digestedPassword;
    private final long time;
    private final double rand;
    
    public RequeteLogin(String login, byte[] digestedPassword, long time, double rand) {
	super("login", "SELECT login, password "
		+ "FROM agent INNER JOIN job "
		+ "ON fk_idjob = idjob "
		+ "WHERE agent.login = ?");

	this.login = login;
	this.digestedPassword = digestedPassword;
	this.time = time;
	this.rand = rand;
    }
    @Override
    protected void doAction() {
	Map<Integer, Object> statementMap = new HashMap<Integer, Object>();
         statementMap.put(1, this.login);
	try {
	    try {
		ResultSet resultSet = this.database.executeQuery(this.sqlStatement, statementMap);

		if(!resultSet.next()|| !Arrays.equals(this.digestedPassword,
			AEncryption.saltDigest(resultSet.getString("password"),
				this.time,
				this.rand))){
		    this.reponse = ReponseLogin.KO("Wrong login/password");
		    traceEvent("Wrong login/password (login:" + this.login + ")");
		}
		else{
		    this.reponse = ReponseLogin.OK();
		    traceEvent("Login OK (login: " + this.login + ")");
		}
	    } catch (SQLException ex) {
		this.reponse = ReponseLogin.KO("Erreur Serveur (SQL)");
		traceEvent("Erreur SQL/BDD : " + ex.getMessage());
	    } catch (EncryptionException ex) {
		this.reponse = ReponseLogin.KO("Erreur Serveur (Encrypt)");
		traceEvent("Erreur d'encrypt : " + ex.getMessage());
	    }

	    this.communication.send(this.reponse);
	} catch (communicationException ex) {
	    traceEvent("Login : " + ex.getMessage());
	}
    }

}

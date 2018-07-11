package lugap.requete;

import communicator.CommunicatorException;
import encryption.Encrypt;
import encryption.EncryptionException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lugap.reponse.ReponseLUGAP_login;

public class RequeteLUGAP_login extends RequeteLUGAP implements  Serializable {
    private final String login;
    private final byte[] digestedPassword;
    private final long time;
    private final double rand;

    public RequeteLUGAP_login(String login, byte[] digestedPassword, long time, double rand) {
        super("login", "SELECT login, password "
                        + "FROM agent INNER JOIN job "
                        + "ON fk_idjob = idjob "
                        + "WHERE job.name='Bagagiste'"
                        + "AND agent.login = ?");
        
        this.login = login;
        this.digestedPassword = digestedPassword;
        this.time = time;
        this.rand = rand;
    }

    @Override
    protected void doAction() {
        Map<Integer, Object> statementMap = new HashMap<>();
        statementMap.put(1, this.login);
        
        try {
            try {
                ResultSet resultSet = this.databaseAccess.executeQuery(this.sqlStatement, statementMap);
                
                if(!resultSet.next()|| !Arrays.equals(this.digestedPassword,
                                                        Encrypt.saltDigest(resultSet.getString("password"),
                                                                            this.time,
                                                                            this.rand))){
                    this.reponse = ReponseLUGAP_login.KO("Wrong login/password");
                    traceEvent("Wrong login/password (login:" + this.login + ")");
                }
                else{
                    this.reponse = ReponseLUGAP_login.OK();
                    traceEvent("Login OK (login: " + this.login + ")");
                }
            } catch (SQLException ex) {
                this.reponse = ReponseLUGAP_login.KO("Erreur Serveur (SQL)");
                traceEvent("Erreur SQL/BDD : " + ex.getMessage());
            } catch (EncryptionException ex) {
                this.reponse = ReponseLUGAP_login.KO("Erreur Serveur (Encrypt)");
                traceEvent("Erreur d'encrypt : " + ex.getMessage());
            }
            
            this.communicator.SendSerializable(this.reponse);
        } catch (CommunicatorException ex) {
            traceEvent("Login : " + ex.getMessage());
        }
    }
}

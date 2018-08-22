package network.protocole.lugap.requete;

import java.io.Serializable;

import generic.server.ARequete;
import network.communication.communicationException;
import network.protocole.lugap.reponse.Reponse_logout;

public class Requete_logout extends ARequete implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Requete_logout() {
        super("logout", "");
    }
    
    @Override
    protected void doAction() {
	try {
            this.reponse = Reponse_logout.OK();
            traceEvent("Logout OK");
            this.communication.send(this.reponse);
        } catch (communicationException ex) {
            traceEvent("" + ex.getMessage());
        }
    }

}

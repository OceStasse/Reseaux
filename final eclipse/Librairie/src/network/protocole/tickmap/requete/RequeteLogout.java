package network.protocole.tickmap.requete;

import java.io.Serializable;

import generic.server.ARequete;
import network.communication.communicationException;
import network.protocole.tickmap.reponse.ReponseLogout;

public class RequeteLogout extends ARequete implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public RequeteLogout() {
	super("logout", "");
    }
    @Override
    protected void doAction() {
	try {
            this.reponse = ReponseLogout.OK();
            traceEvent("Logout OK");
            this.communication.send(this.reponse);
        } catch (communicationException ex) {
            traceEvent("" + ex.getMessage());
        }
    }

}

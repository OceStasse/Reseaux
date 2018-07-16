package tickmap.requete;

import communicator.CommunicatorException;
import java.io.Serializable;
import tickmap.reponse.ReponseTICKMAP_logout;

public class RequeteTICKMAP_logout extends RequeteTICKMAP implements  Serializable  {
    public RequeteTICKMAP_logout() {
        super("logout", "");
    }

    @Override
    protected void doAction() {
        try {
            this.reponse = ReponseTICKMAP_logout.OK();
            traceEvent("Logout OK");
            this.communicator.SendSerializable(this.reponse);
        } catch (CommunicatorException ex) {
            traceEvent("" + ex.getMessage());
        }
    }
}

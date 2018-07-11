package lugap.requete;

import communicator.CommunicatorException;
import java.io.Serializable;
import lugap.reponse.ReponseLUGAP_logout;

public class RequeteLUGAP_logout extends RequeteLUGAP implements  Serializable  {
    public RequeteLUGAP_logout() {
        super("logout", "");
    }

    @Override
    protected void doAction() {
        try {
            this.reponse = ReponseLUGAP_logout.OK();
            traceEvent("Logout OK");
            this.communicator.SendSerializable(this.reponse);
        } catch (CommunicatorException ex) {
            traceEvent("" + ex.getMessage());
        }
    }
}

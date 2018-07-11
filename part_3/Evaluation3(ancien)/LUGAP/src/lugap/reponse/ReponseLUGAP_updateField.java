package lugap.reponse;

import java.io.Serializable;
import server.Reponse;

public class ReponseLUGAP_updateField extends Reponse implements Serializable {
    private ReponseLUGAP_updateField(String message, boolean successful) {
        super(message, successful);
    }
    
    public static ReponseLUGAP_updateField OK(){
        return new ReponseLUGAP_updateField("", true);
    }
    
    public static ReponseLUGAP_updateField KO(String message){
        return new ReponseLUGAP_updateField(message, false);
    }
}

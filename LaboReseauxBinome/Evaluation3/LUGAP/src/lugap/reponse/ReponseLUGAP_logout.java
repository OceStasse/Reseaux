package lugap.reponse;

import java.io.Serializable;
import server.Reponse;

public class ReponseLUGAP_logout extends Reponse implements Serializable {
    private ReponseLUGAP_logout(String message, boolean successful) {
        super(message, successful);
    }
    
    public static ReponseLUGAP_logout OK(){
        return new ReponseLUGAP_logout("", true);
    }
    
    public static ReponseLUGAP_logout KO(String message){
        return new ReponseLUGAP_logout(message, false);
    }
}

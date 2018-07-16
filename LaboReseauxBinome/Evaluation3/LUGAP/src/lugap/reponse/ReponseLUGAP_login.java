package lugap.reponse;

import java.io.Serializable;
import server.Reponse;

public class ReponseLUGAP_login extends Reponse implements Serializable {
    private ReponseLUGAP_login(String message, boolean successful) {
        super(message, successful);
    }
    
    public static ReponseLUGAP_login OK(){
        return new ReponseLUGAP_login("", true);
    }
    
    public static ReponseLUGAP_login KO(String message){
        return new ReponseLUGAP_login(message, false);
    }
}

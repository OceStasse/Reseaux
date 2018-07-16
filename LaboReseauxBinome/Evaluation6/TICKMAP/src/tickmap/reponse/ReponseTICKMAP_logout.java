package tickmap.reponse;

import java.io.Serializable;
import server.Reponse;

public class ReponseTICKMAP_logout extends Reponse implements Serializable {
    private ReponseTICKMAP_logout(String message, boolean successful) {
        super(message, successful);
    }
    
    public static ReponseTICKMAP_logout OK(){
        return new ReponseTICKMAP_logout("", true);
    }
    
    public static ReponseTICKMAP_logout KO(String message){
        return new ReponseTICKMAP_logout(message, false);
    }
}

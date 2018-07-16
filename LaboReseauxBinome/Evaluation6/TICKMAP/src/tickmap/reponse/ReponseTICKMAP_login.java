package tickmap.reponse;

import java.io.Serializable;
import server.Reponse;

public class ReponseTICKMAP_login extends Reponse implements Serializable {
    private ReponseTICKMAP_login(String message, boolean successful) {
        super(message, successful);
    }

    public static ReponseTICKMAP_login OK(){
        return new ReponseTICKMAP_login("", true);
    }

    public static ReponseTICKMAP_login KO(String message){
        return new ReponseTICKMAP_login(message, false);
    }
}

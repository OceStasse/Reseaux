package testproject;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JPasswordField;

public class TestProject {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        JPasswordField pwd = new JPasswordField("test");
        System.err.println(String.valueOf(pwd.getPassword()));
     
        
    }
}

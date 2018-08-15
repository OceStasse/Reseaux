package testproject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JPasswordField;

public class TestProject {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

        Vector data = new Vector();
        data.add("test 1");
        data.add("test 2");
        data.add("test 3");
        
        JList l = new JList(data);
        System.err.println(data.size() + " "+ l.getSelectedValuesList().toArray().toString());
        
    }
}

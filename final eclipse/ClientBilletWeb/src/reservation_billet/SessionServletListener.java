package reservation_billet;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import database.utilities.Access;

public class SessionServletListener implements HttpSessionListener {
    
    private final String DB_IP = "localhost";
    private final String DB_PORT = "3306";
    private final String DB_SCHEMA = "BD_AIRPORT";
    private final String DB_USERNAME = "LaboReseaux";
    private final String DB_PASSWORD = "mysql";
    private final String DB_TABLE_CADDIE = "caddie";
    private final String DB_TABLE_RESERVATION = "caddieitem";
    private final String DB_TABLE_VOL = "flight";
    
    private final Access db = new Access(Access.dataBaseType.MYSQL, DB_IP, DB_PORT, DB_SCHEMA, DB_USERNAME, DB_PASSWORD);
    
    
    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
	System.out.println("======================================================");
        System.out.println("ticket_booking.SessionServletListener.sessionCreated()");
        System.out.println("======================================================");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
	System.out.println("======================================================");
        System.out.println("ticket_booking.SessionServletListener.sessionDestroyed()");
        System.out.println("======================================================");
        HttpSession session = arg0.getSession();
	try {
	    
	    if(session.getAttribute("idcaddie") == null)
		return;
	    
	    if(db.getConnection().isClosed())
                db.connect();
            
            HashMap<Integer, Object> preparedMap = new HashMap<>();
            preparedMap.put(1, session.getAttribute("idcaddie"));
            String sql = "UPDATE " + this.DB_TABLE_RESERVATION + " INNER JOIN " + this.DB_TABLE_VOL
                        + " ON " + this.DB_TABLE_RESERVATION + ".fk_idairplane = " + this.DB_TABLE_VOL + ".fk_idairplane"
                        + " AND " + this.DB_TABLE_RESERVATION + ".fk_idairline = " + this.DB_TABLE_VOL + ".fk_idairline"
                        + " AND " + this.DB_TABLE_RESERVATION + ".fk_departure = " + this.DB_TABLE_VOL + ".departure"
                        + " AND " + this.DB_TABLE_RESERVATION + ".fk_destination = " + this.DB_TABLE_VOL + ".destination"
                        + " SET seatsSold=seatsSold-reservedSeats"
                        + " WHERE fk_idcaddie=?";
            db.executeUpdate(sql, preparedMap);
            
            sql = "DELETE FROM " + this.DB_TABLE_RESERVATION
                  + " WHERE fk_idcaddie=?";
            db.executeUpdate(sql, preparedMap);
            
            sql = "DELETE FROM " + this.DB_TABLE_CADDIE
                  +" WHERE idcaddie=?";
            db.executeUpdate(sql, preparedMap);
            
	    db.commit();
            db.disconnect();
	}
	catch (InterruptedException | SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
	    Logger.getLogger(SessionServletListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

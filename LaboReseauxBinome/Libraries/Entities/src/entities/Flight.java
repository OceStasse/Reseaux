package entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

// Date et Time proviennent de Java 8 :
// http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html

public class Flight implements Serializable {
    private int idAirplane;
    private String idAirline;
    private Date departureDate;
    private String destination;
    private String geographiczone;
    private int distance;
    private Time departureTime;
    private Time landingTime;
    private int seats;
    private double price;
    private String piste;

    public Flight() {
    }
    
    public Flight(int idAvion, String idAirline, Date departureDate, String destination, String piste) {
        this.idAirplane = idAvion;
        this.idAirline = idAirline;
        this.departureDate = departureDate;
        this.destination = destination;
        this.piste = piste;
    }
    
    public Flight(int idAvion, String idAirline, Date departureDate, String destination, int seats, double price, String piste) {
        this.idAirplane = idAvion;
        this.idAirline = idAirline;
        this.departureDate = departureDate;
        this.destination = destination;
        this.seats = seats;
        this.price = price;
        this.piste = piste;
    }

    public Flight(int idAvion, String idAirline, Date departureDate, String destination, String geographiczone, int distance, Time departureTime, Time landingTime, int seatsSold, double price, String piste) {
        this.idAirplane = idAvion;
        this.idAirline = idAirline;
        this.departureDate = departureDate;
        this.destination = destination;
        this.geographiczone = geographiczone;
        this.distance = distance;
        this.departureTime = departureTime;
        this.landingTime = landingTime;
        this.seats = seatsSold;
        this.price = price;
        this.piste = piste;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("VOL ");
        stringBuilder.append(getIdAirplane());
        stringBuilder.append(" ");
        stringBuilder.append(getIdAirline());
        stringBuilder.append(" - ");
        stringBuilder.append(getDestination());
        stringBuilder.append(" ");
        stringBuilder.append(new SimpleDateFormat("HH:mm").format(getDepartureTime()));
        
        return stringBuilder.toString();
    }

    public boolean equals(Flight flight) {
        return this.idAirplane == flight.idAirplane
                && this.idAirline.equals(flight.idAirline)
                && this.departureDate.equals(flight.departureDate)
                && this.destination.equals(flight.destination)
                && this.geographiczone.equals(flight.geographiczone)
                && this.distance == flight.distance
                && this.departureTime.equals(flight.departureTime)
                && this.landingTime.equals(flight.landingTime)
                && this.seats == flight.seats
                && this.price == flight.price
                && this.piste.equals(flight.piste);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public int getIdAirplane() {
        return idAirplane;
    }
    public String getIdAirline() {
        return idAirline;
    }
    public Date getDepartureDate() {
        return departureDate;
    }
    public String getDestination() {
        return destination;
    }
    public Time getDepartureTime() {
        return departureTime;
    }
    public Time getLandingTime() {
        return landingTime;
    }
    public String getGeographiczone() {
        return geographiczone;
    }
    public int getDistance() {
        return distance;
    }
    public int getSeats() {
        return seats;
    }
    public double getPrice() {
        return price;
    }
    public String getPiste() {
        return piste;
    }

    public void setIdAirplane(int idAirplane) {
        this.idAirplane = idAirplane;
    }
    public void setIdAirline(String idAirline) {
        this.idAirline = idAirline;
    }
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }
    public void setLandingTime(Time landingTime) {
        this.landingTime = landingTime;
    }
    public void setGeographiczone(String geographiczone) {
        this.geographiczone = geographiczone;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setPiste(String piste) {
        this.piste = piste;
    }
    //</editor-fold>
}

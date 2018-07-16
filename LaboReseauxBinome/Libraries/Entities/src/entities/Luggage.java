package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Luggage implements Serializable {
    private Flight flight;
    private String passengerFirstName;
    private String passengerLastName;
    private int idTicket;
    private int idLuggage;
    private float weight;
    private boolean isLuggage;
    private char received;
    private char loaded;
    private char checkedByCustom;
    private String comments;

    public Luggage() {
    }

    public Luggage(Flight flight, String passengerFirstName, String passengerLastName, int idTicket, int idLuggage, float weight, boolean isLuggage, char received, char loaded, char checkedByCustom, String comments) {
        this.flight = flight;
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.idTicket = idTicket;
        this.idLuggage = idLuggage;
        this.weight = weight;
        this.isLuggage = isLuggage;
        this.received = received;
        this.loaded = loaded;
        this.checkedByCustom = checkedByCustom;
        this.comments = comments;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append(getFlight().getIdAirplane());
        stringBuilder.append("-");
        stringBuilder.append(getPassengerFirstName().toUpperCase().subSequence(0, 2));
        stringBuilder.append(getPassengerLastName().toUpperCase());
        stringBuilder.append("-");
        stringBuilder.append(new SimpleDateFormat("ddMMyyyy").format(flight.getDepartureDate()));
        stringBuilder.append("-");
        stringBuilder.append(String.format("%04d", getIdTicket()));
        stringBuilder.append("-");
        stringBuilder.append(String.format("%03d", getIdLuggage()));
        
        return stringBuilder.toString();
    }
    
    public Object[] toArray(){
        Object[] objects = new Object[7];
        
        objects[0] = this.toString();
        objects[1] = this.getWeight() + " kg";
        objects[2] = this.isIsLuggage() ? "VALISE" : "PAS VALISE";
        objects[3] = this.getReceived();
        objects[4] = this.getLoaded();
        objects[5] = this.getCheckedByCustom();
        objects[6] = this.getComments();
        
        return objects;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public Flight getFlight() {
        return flight;
    }
    public String getPassengerFirstName() {
        return passengerFirstName;
    }
    public String getPassengerLastName() {
        return passengerLastName;
    }
    public int getIdTicket() {
        return idTicket;
    }
    public int getIdLuggage() {
        return idLuggage;
    }
    public float getWeight() {
        return weight;
    }
    public boolean isIsLuggage() {
        return isLuggage;
    }
    public char getReceived() {
        return received;
    }
    public char getLoaded() {
        return loaded;
    }
    public char getCheckedByCustom() {
        return checkedByCustom;
    }
    public String getComments() {
        return comments;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    public void setPassengerFirstName(String passengerFirstName) {
        this.passengerFirstName = passengerFirstName;
    }
    public void setPassengerLastName(String passengerLastName) {
        this.passengerLastName = passengerLastName;
    }
    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }
    public void setIdLuggage(int idLuggage) {
        this.idLuggage = idLuggage;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    public void setIsLuggage(boolean isLuggage) {
        this.isLuggage = isLuggage;
    }
    public void setReceived(char received) {
        this.received = received;
    }
    public void setLoaded(char loaded) {
        this.loaded = loaded;
    }
    public void setCheckedByCustom(char checkedByCustom) {
        this.checkedByCustom = checkedByCustom;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    //</editor-fold>
}
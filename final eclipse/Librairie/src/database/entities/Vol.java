package database.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * @author nicol
 *
 */
public class Vol implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int idAvion;
    private String idCompagnieAvion;
    private Date departDate;
    private String destination;
    private String zoneGeographique;
    private int distance;
    private Time heureDepart;
    private Time heureAtterisage;
    private int place;
    private double prix;
    private String piste;
    
    
    public Vol() 
    {
    }
    
    public Vol(int idAvion, String idAirline, Date departureDate, String destination, String piste) 
    {
        this.idAvion = idAvion;
        this.idCompagnieAvion = idAirline;
        this.departDate = departureDate;
        this.destination = destination;
        this.piste = piste;
    }
    
    public Vol(int idAvion, String idAirline, Date departureDate, String destination, int seats, double price, String piste)
    {
        this.idAvion = idAvion;
        this.idCompagnieAvion = idAirline;
        this.departDate = departureDate;
        this.destination = destination;
        this.place = seats;
        this.prix = price;
        this.piste = piste;
    }
    
    public Vol(int idAvion, String idCompagnieAvion, Date departDate, String destination, String zoneGeographique,
	    int distance, Time heureDepart, Time heureAtterisage, int place, double prix) {
	super();
	this.idAvion = idAvion;
	this.idCompagnieAvion = idCompagnieAvion;
	this.departDate = departDate;
	this.destination = destination;
	this.zoneGeographique = zoneGeographique;
	this.distance = distance;
	this.heureDepart = heureDepart;
	this.heureAtterisage = heureAtterisage;
	this.place = place;
	this.prix = prix;
    }
    public Vol(int idAvion, String idAirline, Date departureDate, String destination, String geographiczone, int distance, 
	       Time departureTime, Time landingTime, int seatsSold, double price, String piste) 
    {
        this.idAvion = idAvion;
        this.idCompagnieAvion = idAirline;
        this.departDate = departureDate;
        this.destination = destination;
        this.zoneGeographique = geographiczone;
        this.distance = distance;
        this.heureDepart = departureTime;
        this.heureAtterisage = landingTime;
        this.place = seatsSold;
        this.prix = price;
        this.piste = piste;
    }
    
    public int getIdAvion() 
    {
	return idAvion;
    }

    public void setIdAvion(int idAvion)
    {
	this.idAvion = idAvion;
    }

    public String getIdCompagnieAvion()
    {
	return idCompagnieAvion;
    }

    public void setIdCompagnieAvion(String idCompagnieAvion) 
    {
	this.idCompagnieAvion = idCompagnieAvion;
    }

    public Date getDepartDate() 
    {
	return departDate;
    }

    public void setDepartDate(Date departDate) 
    {
	this.departDate = departDate;
    }

    public String getDestination() {
	return destination;
    }

    public void setDestination(String destination) 
    {
	this.destination = destination;
    }

    public String getZoneGeographique() 
    {
	return zoneGeographique;
    }

    public void setZoneGeographique(String zoneGeographique)
    {
	this.zoneGeographique = zoneGeographique;
    }

    public int getDistance()
    {
	return distance;
    }

    public void setDistance(int distance) 
    {
	this.distance = distance;
    }

    public Time getHeureDepart() {
	return heureDepart;
    }

    public void setHeureDepart(Time heureDepart) 
    {
	this.heureDepart = heureDepart;
    }

    public Time getHeureAtterisage() {
	return heureAtterisage;
    }

    public void setHeureAtterisage(Time heureAtterisage) 
    {
	this.heureAtterisage = heureAtterisage;
    }

    public int getPlace() 
    {
	return place;
    }

    public void setPlace(int place) 
    {
	this.place = place;
    }

    public double getPrix() 
    {
	return prix;
    }

    public void setPrix(double prix)
    {
	this.prix = prix;
    }

    public String getPiste() 
    {
	return piste;
    }

    public void setPiste(String piste)
    {
	this.piste = piste;
    }
    
    @Override
    public String toString() 
    {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("VOL ");
        stringBuilder.append(getIdAvion());
        stringBuilder.append(" ");
        stringBuilder.append(getIdCompagnieAvion());
        stringBuilder.append(" - ");
        stringBuilder.append(getDestination());
        stringBuilder.append(" ");
        stringBuilder.append(new SimpleDateFormat("HH:mm").format(this.getHeureDepart()));
        
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) 
    {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Vol other = (Vol) obj;
	return this.idAvion == other.idAvion
                && this.idCompagnieAvion.equals(other.idCompagnieAvion)
                && this.departDate.equals(other.departDate)
                && this.destination.equals(other.destination)
                && this.zoneGeographique.equals(other.zoneGeographique)
                && this.distance == other.distance
                && this.heureDepart.equals(other.heureDepart)
                && this.heureAtterisage.equals(other.heureAtterisage)
                && this.place == other.place
                && this.prix == other.prix
                && this.piste.equals(other.piste);
    }
    
   

}

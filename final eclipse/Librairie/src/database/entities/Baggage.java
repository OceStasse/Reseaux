package database.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Baggage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Vol vol;
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
    
    
    public Baggage() 
    {
	
    }

    public Baggage(Vol vol, String passengerFirstName, String passengerLastName, 
	    	   int idTicket, int idLuggage, float weight, boolean isLuggage, char received, char loaded, 
	    	   char checkedByCustom, String comments) 
    {
        this.vol = vol;
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
    public String toString() 
    {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append(getVol().getIdAvion());
        stringBuilder.append("-");
        stringBuilder.append(getPassengerFirstName().toUpperCase().subSequence(0, 2));
        stringBuilder.append(getPassengerLastName().toUpperCase());
        stringBuilder.append("-");
        stringBuilder.append(new SimpleDateFormat("ddMMyyyy").format(vol.getDepartDate()));
        stringBuilder.append("-");
        stringBuilder.append(String.format("%04d", getIdTicket()));
        stringBuilder.append("-");
        stringBuilder.append(String.format("%03d", getIdLuggage()));
        
        return stringBuilder.toString();
    }
    
    public Object[] toArray()
    {
        Object[] objects = new Object[7];
        
        objects[0] = this.toString();
        objects[1] = this.getWeight() + " kg";
        objects[2] = this.isLuggage() ? "VALISE" : "PAS VALISE";
        objects[3] = this.getReceived();
        objects[4] = this.getLoaded();
        objects[5] = this.getCheckedByCustom();
        objects[6] = this.getComments();
        
        return objects;
    }
    
    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() 
    {
        return serialVersionUID;
    }
    /**
     * @return the vol
     */
    public Vol getVol() 
    {
        return vol;
    }
    /**
     * @return the passengerFirstName
     */
    public String getPassengerFirstName() 
    {
        return passengerFirstName;
    }
    /**
     * @return the passengerLastName
     */
    public String getPassengerLastName() 
    {
        return passengerLastName;
    }
    /**
     * @return the idTicket
     */
    public int getIdTicket() 
    {
        return idTicket;
    }
    /**
     * @return the idLuggage
     */
    public int getIdLuggage() 
    {
        return idLuggage;
    }
    /**
     * @return the weight
     */
    public float getWeight() 
    {
        return weight;
    }
    /**
     * @return the isLuggage
     */
    public boolean isLuggage() 
    {
        return isLuggage;
    }
    /**
     * @return the received
     */
    public char getReceived() 
    {
        return received;
    }
    /**
     * @return the loaded
     */
    public char getLoaded() 
    {
        return loaded;
    }
    /**
     * @return the checkedByCustom
     */
    public char getCheckedByCustom() 
    {
        return checkedByCustom;
    }
    /**
     * @return the comments
     */
    public String getComments() 
    {
        return comments;
    }
    /**
     * @param vol the vol to set
     */
    public void setVol(Vol vol) 
    {
        this.vol = vol;
    }
    /**
     * @param passengerFirstName the passengerFirstName to set
     */
    public void setPassengerFirstName(String passengerFirstName) 
    {
        this.passengerFirstName = passengerFirstName;
    }
    /**
     * @param passengerLastName the passengerLastName to set
     */
    public void setPassengerLastName(String passengerLastName) 
    {
        this.passengerLastName = passengerLastName;
    }
    /**
     * @param idTicket the idTicket to set
     */
    public void setIdTicket(int idTicket) 
    {
        this.idTicket = idTicket;
    }
    /**
     * @param idLuggage the idLuggage to set
     */
    public void setIdLuggage(int idLuggage) 
    {
        this.idLuggage = idLuggage;
    }
    /**
     * @param weight the weight to set
     */
    public void setWeight(float weight) 
    {
        this.weight = weight;
    }
    /**
     * @param isLuggage the isLuggage to set
     */
    public void setLuggage(boolean isLuggage) 
    {
        this.isLuggage = isLuggage;
    }
    /**
     * @param received the received to set
     */
    public void setReceived(char received) 
    {
        this.received = received;
    }
    /**
     * @param loaded the loaded to set
     */
    public void setLoaded(char loaded) 
    {
        this.loaded = loaded;
    }
    /**
     * @param checkedByCustom the checkedByCustom to set
     */
    public void setCheckedByCustom(char checkedByCustom) 
    {
        this.checkedByCustom = checkedByCustom;
    }
    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) 
    {
        this.comments = comments;
    }

}

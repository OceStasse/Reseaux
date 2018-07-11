/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Airport;

/**
 *
 * @author jona1993
 */
public class Voyage {
    private int idVol = -1;
    private String compagnie = null;
    private String destination = null;
    private String heure = null;
    
    public Voyage()
    {
        
    }

    /**
     * @return the idVol
     */
    public int getIdVol() {
        return idVol;
    }

    /**
     * @param idVol the idVol to set
     */
    public void setIdVol(int idVol) {
        this.idVol = idVol;
    }

    /**
     * @return the compagnie
     */
    public String getCompagnie() {
        return compagnie;
    }

    /**
     * @param compagnie the compagnie to set
     */
    public void setCompagnie(String compagnie) {
        this.compagnie = compagnie;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return the heure
     */
    public String getHeure() {
        return heure;
    }

    /**
     * @param heure the heure to set
     */
    public void setHeure(String heure) {
        this.heure = heure;
    }
    
    
}

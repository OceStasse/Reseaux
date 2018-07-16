/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

import java.io.Serializable;

/**
 *
 * @author jona1993
 */
public class RequestVols implements Serializable {
    private boolean traitement;
    private int numero;
    private String depart;
    private String arrivee;
    private String destination;
    private String numBillet;
    private String numAvion;

    public RequestVols() {
        traitement = false;
        numero = 0;
        depart = "";
        arrivee = "";
        destination = "";
        numBillet = "";
        numAvion = "";
        
    }

    public RequestVols(boolean traitement, int numero, String depart, String arrivee, String destination, String numBillet, String numAvion) {
        this.traitement = traitement;
        this.numero = numero;
        this.depart = depart;
        this.arrivee = arrivee;
        this.destination = destination;
        this.numBillet = numBillet;
        this.numAvion = numAvion;
    }

    //<editor-fold defaultstate="collapsed" desc=" Gets / Sets ">
    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public void setNumAvion(String numAvion) {
        this.numAvion = numAvion;
    }

    public void setNumBillet(String numBillet) {
        this.numBillet = numBillet;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setTraitement(boolean traitement) {
        this.traitement = traitement;
    }

    public String getArrivee() {
        return arrivee;
    }

    public String getDepart() {
        return depart;
    }

    public String getDestination() {
        return destination;
    }
    
    public String getNumAvion() {
        return numAvion;
    }

    public String getNumBillet() {
        return numBillet;
    }

    public int getNumero() {
        return numero;
    }
    
    public boolean getTraitement() {
        return traitement;
    }
    //</editor-fold>
    
}

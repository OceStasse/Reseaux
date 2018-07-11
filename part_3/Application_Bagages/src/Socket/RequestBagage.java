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
public class RequestBagage implements Serializable {
    private int numero;
    private int poids;
    private boolean valise;

    public RequestBagage() {
        numero = 0;
        poids = 0;
        valise = false;
    }
    
    public RequestBagage(int numero, int poids, boolean valise) {
        this.numero = numero;
        this.poids = poids;
        this.valise = valise;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public void setValise(boolean valise) {
        this.valise = valise;
    }

    public int getNumero() {
        return numero;
    }

    public int getPoids() {
        return poids;
    }
    
    public boolean getValise() {
        return valise;
    }
    
}

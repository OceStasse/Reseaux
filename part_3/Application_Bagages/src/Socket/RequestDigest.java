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
public class RequestDigest implements Serializable{
    String proto;
    String text;
    byte[] digest;
    long temps;
    double random;

    public RequestDigest() {
        proto = "";
        text = "";
        digest = new byte[10];
        temps = 0;
        random = 0;
    }

    public RequestDigest(String proto, String text, byte[] digest, long temps, double random) {
        this.proto = proto;
        this.text = text;
        this.digest = digest;
        this.temps = temps;
        this.random = random;
    }

    
    public byte[] getDigest() {
        return digest;
    }

    public String getProto() {
        return proto;
    }

    public double getRandom() {
        return random;
    }

    public long getTemps() {
        return temps;
    }

    public String getText() {
        return text;
    }

    public void setDigest(byte[] digest) {
        this.digest = digest;
    }

    public void setProto(String proto) {
        this.proto = proto;
    }

    public void setRandom(double random) {
        this.random = random;
    }

    public void setTemps(long temps) {
        this.temps = temps;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Date;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author jona1993
 */
public class BouncyCastleDigest {
    
    private static final String codeProvider = "BC";
    private MessageDigest md;
    private long temps;
    private double random;
    private ByteArrayOutputStream baos;
    private DataOutputStream bdos;

    
    public BouncyCastleDigest() throws NoSuchAlgorithmException, NoSuchProviderException
    {
        Security.addProvider(new BouncyCastleProvider());
        baos = new ByteArrayOutputStream();
        bdos = new DataOutputStream(baos);
        md = MessageDigest.getInstance("SHA-512",codeProvider);
        temps = (new Date()).getTime();
        random = Math.random();
    }
    
    public byte[] CreateDigest(String user, String pwd) throws IOException
    {
        md.update(user.getBytes());
        md.update(pwd.getBytes());
        temps = (new Date()).getTime();
        random = Math.random();
        bdos.writeLong(temps);
        bdos.writeDouble(random);
        md.update(baos.toByteArray());
        byte[] msgD = md.digest();
        
        return msgD;
    }
    
    public byte[] ReceiveDigest(String user, String digestion, long tps, double alea) throws IOException
    {
        System.err.println("In RcvDigest: " + user + " " + digestion + " " + tps + " " + alea);
        md.update(user.getBytes());
        md.update(digestion.getBytes());
        bdos.writeLong(tps);
        bdos.writeDouble(alea);
        md.update(baos.toByteArray());
        return md.digest();
        
    }
    
    public double getRandom() {
        return random;
    }

    public long getTemps() {
        return temps;
    }
}

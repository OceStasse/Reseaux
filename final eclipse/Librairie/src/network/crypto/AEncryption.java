package network.crypto;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public abstract class AEncryption {
    static 
    {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    public static byte[] saltDigest(String message, long time, double rand) throws EncryptionException
    {
        try 
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256", "BC");
            md.update(message.getBytes(StandardCharsets.UTF_8));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeLong(time);
            dos.writeDouble(rand);
            md.update(baos.toByteArray());

            dos.flush();
            return md.digest();
        } 
        catch (NoSuchAlgorithmException | NoSuchProviderException | IOException ex) 
        {
            throw new EncryptionException("Encrypt()->SaltDigest() : " + ex);
        }
    }
    
    public static byte[] saltDigest(char[] message, long time, double rand) throws EncryptionException
    {
        return saltDigest(Arrays.toString(message), time, rand);
    }
    
}

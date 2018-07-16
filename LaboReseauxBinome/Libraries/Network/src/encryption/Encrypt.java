package encryption;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public abstract class Encrypt {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    //<editor-fold desc="DIGEST SALÉ" defaultstate="collapsed">
    public static byte[] saltDigest(String message, long time, double rand) throws EncryptionException{
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256", "BC");
            md.update(message.getBytes(StandardCharsets.UTF_8));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeLong(time);
            dos.writeDouble(rand);
            md.update(baos.toByteArray());

            dos.flush();
            return md.digest();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | IOException ex) {
            throw new EncryptionException("Encrypt()->SaltDigest() : " + ex);
        }
    }

    public static byte[] saltDigest(char[] message, long time, double rand) throws EncryptionException{
        return saltDigest(Arrays.toString(message), time, rand);
    }
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="HASH">
	public static int hash(String message){
		int hashValue = 0;

		for(int i=0; i<message.length(); i++){
			hashValue += (int)message.charAt(i);
		}

		return hashValue%67;
	}
	//</editor-fold>
}

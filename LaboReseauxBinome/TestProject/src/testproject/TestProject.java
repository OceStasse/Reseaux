package testproject;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JPasswordField;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class TestProject {
     static 
    {
        Security.addProvider(new BouncyCastleProvider());
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("D:\\certificat\\JKS\\billet_keystore.jks"),"password".toCharArray());
            Enumeration en = ks.aliases();
            String aliasCourant = null;
            Vector vectaliases = new Vector();
            
            System.out.println("Recuperation de la cle privee");
            PrivateKey cléPrivée;
            cléPrivée = (PrivateKey) ks.getKey("billetKey", "password".toCharArray());
            System.out.println(" *** Cle privee recuperee = " + cléPrivée.toString());
            
            String Message = "Code du jour : CVCCDMMM - bye";
            System.out.println("Message a envoyer au serveur : " + Message);
            byte[] message = Message.getBytes();
            System.out.println("Instanciation de la signature");
            Signature s = Signature.getInstance("SHA1withDSA","BC");
            System.out.println("Initialisation de la signature");
            s.initSign(cléPrivée);
            System.out.println("Hachage du message");
            s.update(message);
            System.out.println("Generation des bytes");
            byte[] signature = s.sign();
            System.out.println("Termine : signature construite");
            System.out.println("Signature = " + new String(signature));
            System.out.println("\nVérification de la signature");
            /*
            while (en.hasMoreElements()) {
                vectaliases.add(en.nextElement());
            }
            Object[] aliases = vectaliases.toArray();
//OU : String[] aliases = (String []) (vectaliases.toArray(new String[0]));
            for (int i = 0; i < aliases.length; i++) {
                if (ks.isKeyEntry(aliasCourant = (String) aliases[i])) {
                    System.out.println((i + 1) + ".[keyEntry] " + aliases[i].toString());
                } else if (ks.isCertificateEntry(aliasCourant)) {
                    System.out.println((i + 1) + ".[trustedCertificateEntry] " + aliases[i].toString()+ " ");
                }
                X509Certificate certif = (X509Certificate) ks.getCertificate(aliasCourant);
                System.out.println("Type de certificat : " + certif.getType());
                System.out.println("Nom du propriétaire du certificat : "
                        + certif.getSubjectDN().getName());
                System.out.println("Recuperation de la cle publique");
                PublicKey cléPublique = certif.getPublicKey();
                System.out.println("*** Cle publique recuperee = " + cléPublique.toString());
                System.out.println("Dates limites de validité : [" + certif.getNotBefore() + " - "
                        + certif.getNotAfter() + "]");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package network.serveur.tickmap;

import java.net.Socket;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import database.utilities.Access;
import generic.server.IConsoleServeur;
import generic.server.ISourceTaches;
import generic.server.multithread.APoolThread;
import network.communication.Communication;
import network.communication.communicationException;
import network.crypto.ACryptographieAsymetrique;
import network.crypto.CryptographieAsymetriqueException;

public class PoolThread extends APoolThread{
	
	private final String DBip;
    private final String DBport;
    private final String DBSID;
    private final String DBschema;
    private final String DBpassword;

    public PoolThread(int nbThreads, ISourceTaches tachesAExecuter, IConsoleServeur guiApplication, int port, 
            String ip, String DBport, String SID, String schema, String password) {
        super(nbThreads, tachesAExecuter, guiApplication, port);
        
        this.DBip = ip;
        this.DBport = DBport;
        this.DBSID = SID;
        this.DBschema = schema;
        this.DBpassword = password;
    }
	
	@Override
	protected Runnable getProtocolRunnable(Socket socket) throws communicationException {
		//recupérer la publickey, privatekey & le certificat via keystore
		X509Certificate cert = null;
		PublicKey publicKey = null;
		PrivateKey privateKey = null;
		//KeyStore ks = KeyStore.getInstance("PKCS12", "BC");
		try {
			KeyStore ks = ACryptographieAsymetrique.keyStore("JKS", "D:\\certificat\\JKS", "password") ;
			cert = ACryptographieAsymetrique.certificate( "D:\\certificat\\JKS", "JKS", "certificat_billet","password");
			publicKey = ACryptographieAsymetrique.publicKeyFromCertificate(ks, "certificat_billet","password");
			privateKey = ACryptographieAsymetrique.privateKey(ks, "password", "certificat_billet");
		} catch (CryptographieAsymetriqueException e) {
			System.err.println("Error Runnable->getProtocole: " + e.getMessage());
		}
		
		
		
		return new RunnableTickimap(this, this.guiApplication, new Communication(socket), 
                new Access(Access.dataBaseType.MYSQL, this.DBip, this.DBport, this.DBSID, this.DBschema, this.DBpassword)
                ,privateKey,publicKey, cert);
	}

}

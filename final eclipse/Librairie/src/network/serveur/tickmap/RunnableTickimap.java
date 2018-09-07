package network.serveur.tickmap;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.sql.SQLException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import database.utilities.Access;
import generic.server.ARequete;
import generic.server.IConsoleServeur;
import network.communication.Communication;
import network.communication.communicationException;
import network.crypto.CryptographieSymetriqueException;
import network.protocole.tickmap.reponse.ReponseAchat;
import network.protocole.tickmap.reponse.ReponseCaddie;
import network.protocole.tickmap.reponse.ReponseCertificat;
import network.protocole.tickmap.reponse.ReponseLogin;
import network.protocole.tickmap.reponse.ReponsePassenger;
import network.protocole.tickmap.reponse.ReponseReserver;
import network.protocole.tickmap.reponse.ReponseVols;
import network.protocole.tickmap.requete.RequeteAchat;
import network.protocole.tickmap.requete.RequeteCaddie;
import network.protocole.tickmap.requete.RequeteCertificate;
import network.protocole.tickmap.requete.RequeteExchangeKey;
import network.protocole.tickmap.requete.RequeteLogin;
import network.protocole.tickmap.requete.RequeteLogout;
import network.protocole.tickmap.requete.RequetePassenger;
import network.protocole.tickmap.requete.RequeteReserver;
import network.protocole.tickmap.requete.RequeteVol;

public class RunnableTickimap  implements Runnable{
	private final PoolThread parent;
    private final IConsoleServeur guiApplication;
    private final Communication c;
    private final Access db;
    private Class<?> previousRequete;
    private X509Certificate cert;
    private SecretKey keyHMAC;
    private SecretKey keyCipher;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private PrivateKey privateKeyClient;
    private PublicKey publicKeyClient;
    private X509Certificate certClient;
    
    
    
    private boolean finTransaction;
    private boolean clientConnected;
	
	public RunnableTickimap(PoolThread poolThread, IConsoleServeur guiApplication, Communication communication,Access access,
			PrivateKey privatekey, PublicKey publicKey, X509Certificate cert)
	{
		this.cert = cert;
		this.privateKey = privatekey;
		this.publicKey = publicKey;
		 this.parent = poolThread;
	     this.guiApplication = guiApplication;
	     this.c = communication;
	     this.db = access;
	}

	@Override
	public void run() {
		this.finTransaction = false;
		try {
			try {
				this.db.connect();
			} catch (ClassNotFoundException | SQLException e) {
				this.c.send(ReponseLogin.KO("Server could not connect to the database."));
	            finTransaction();
			}
			
			while(!this.finTransaction)
			{
				ARequete req = this.c.receive(ARequete.class);
                System.out.println("Received request type : " + req.getClass().toString());
				Runnable runnable = req.createRunnable(this.parent, this.c, this.guiApplication, this.db);
				//LOGIN
				if(req instanceof RequeteLogin)
                {
                    runnable.run();
                    if(req.requeteSucceeded()){
                        setClientIsConnected();
                        previousRequete = RequeteLogin.class;
                    }
                }
				//LOGOUT
                else if(req instanceof RequeteLogout)
                {
                	if(!isClientConnected())
                		this.c.send(ReponseCertificat.KO("Please connect first!"));
                	else
                	{
                		runnable.run();
	                    finTransaction();
	                    previousRequete = null;
                	}
                    
                }
				//CERTIFICAT ECHANGE
                else if(req instanceof RequeteCertificate)
                {
                	if(!isClientConnected())
                		this.c.send(ReponseCertificat.KO("Please connect first!"));
                	else
                	{
                		runnable.run();
	                	if(req.requeteSucceeded())
	                	{
	                		this.c.send(new RequeteCertificate());
	                		ReponseCertificat rep = this.c.receive(ReponseCertificat.class);
	                		certClient = rep.getCertificate();
	                        previousRequete = RequeteCertificate.class;
	                    }
                	}
                }
				//ECHANGE CLE
                else if(req  instanceof RequeteExchangeKey)
                {
                	if(!isClientConnected())
                		this.c.send(ReponseCertificat.KO("Please connect first!"));
                	else
                	{
                		runnable.run();
	                	if(req.requeteSucceeded())
	                	{
	                		this.keyHMAC = ((RequeteExchangeKey)req).getKeyHMAC();
	                		this.keyCipher = ((RequeteExchangeKey)req).getKeyCipher();
	                		previousRequete = RequeteExchangeKey.class;
	                	}
                	}
                	
                }
				//GET VOL
                else if(req instanceof RequeteVol)	
                {
                	if(!isClientConnected())
                	{
                		this.c.send(ReponseVols.KO("Please connect first!"));
                	}
                	else
                	{
                		runnable.run();
	                	if(req.requeteSucceeded())
	                	{
	                		previousRequete = RequeteVol.class;
	                	}
                	}                	
                }
                else if(req instanceof RequetePassenger)
                {
                	if(!isClientConnected())
                		this.c.send(ReponsePassenger.KO("Please connect first!"));
                	else
                	{
                		
                	}
                	
                }
				//GET ID CADDIE
                else if(req instanceof RequeteCaddie)
                {
                	if(!isClientConnected())
                		this.c.send(ReponseCaddie.KO("Please connect first!"));
                	else
                	{
                		runnable.run();
	                	if(req.requeteSucceeded())
	                	{
	                		previousRequete = RequeteCaddie.class;
	                	}
                	}
                	
                }
				//RESERVER VOL
                else if (req instanceof RequeteReserver)
                {
                	if(!isClientConnected())
                		this.c.send(ReponseReserver.KO("Please connect first!"));
                	else
                	{
                		runnable.run();
	                	if(req.requeteSucceeded())
	                	{
	                		previousRequete = RequeteReserver.class;
	                	}
                	}
                	
                }
                else if (req instanceof RequeteAchat)
                {
                	if(!isClientConnected())
                		this.c.send(ReponseAchat.KO("Please connect first!"));
                	else
                	{
                		runnable.run();
                		if(req.requeteSucceeded())
                		{
                			this.previousRequete = RequeteAchat.class;
                		}
                	}
                }
			}
			this.db.commit();
			this.guiApplication.TraceEvenements(this.c.getSocket().getRemoteSocketAddress().toString()
                    + "#Commit successful"
                    + "#RunnableTICKIMAP");
		}
		catch(communicationException | SQLException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | CryptographieSymetriqueException | IOException ex)
		{
			this.guiApplication.TraceEvenements(this.c.getSocket().getRemoteSocketAddress().toString()
                    + "#" + ex.getMessage()
                    + "#RunnableTICKIMAP");
		}
		
		
	}
	
	private void finTransaction(){
        this.finTransaction = true;
    }
    
    private boolean isClientConnected(){
        return this.clientConnected;
    }
    
    private void setClientIsConnected(){
        this.clientConnected = true;
    }

}

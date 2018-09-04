package network.serveur.tickmap;

import java.security.cert.X509Certificate;
import java.sql.SQLException;

import javax.crypto.SecretKey;

import database.utilities.Access;
import generic.server.ARequete;
import generic.server.IConsoleServeur;
import network.communication.Communication;
import network.communication.communicationException;
import network.protocole.tickmap.reponse.ReponseCertificat;
import network.protocole.tickmap.reponse.ReponseLogin;
import network.protocole.tickmap.requete.RequeteCertificate;
import network.protocole.tickmap.requete.RequeteExchangeKey;
import network.protocole.tickmap.requete.RequeteLogin;
import network.protocole.tickmap.requete.RequeteLogout;
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
    
    
    private boolean finTransaction;
    private boolean clientConnected;
	
	public RunnableTickimap(PoolThread poolThread, IConsoleServeur guiApplication, Communication communication,Access access)
	{
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
				if(req instanceof RequeteLogin)
                {
                    runnable.run();
                    if(req.requeteSucceeded()){
                        setClientIsConnected();
                        previousRequete = RequeteLogin.class;
                    }
                }
                else if(req instanceof RequeteLogout)
                {
                    runnable.run();
                    finTransaction();
                    previousRequete = null;
                }
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
	                		cert = rep.getCertificate();
	                        previousRequete = RequeteCertificate.class;
	                    }
                	}
                }
                else if(req  instanceof RequeteExchangeKey)
                {
                	runnable.run();
                	if(req.requeteSucceeded())
                	{
                		this.keyHMAC = ((RequeteExchangeKey)req).getKeyHMAC();
                		this.keyCipher = ((RequeteExchangeKey)req).getKeyCipher();
                		previousRequete = RequeteExchangeKey.class;
                	}
                }
                else if(req instanceof RequeteVol)	
                {
                	runnable.run();
                	if(req.requeteSucceeded())
                	{
                		previousRequete = RequeteVol.class;
                	}
                }
                else if(req instanceof)
                {
                	
                }
			}
		}
		catch(communicationException | SQLException ex)
		{
			this.guiApplication.TraceEvenements(this.c.getSocket().getRemoteSocketAddress().toString()
                    + "#" + ex.getMessage()
                    + "#LUGAPRunnable");
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

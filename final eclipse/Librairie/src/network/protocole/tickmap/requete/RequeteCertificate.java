package network.protocole.tickmap.requete;

import java.security.cert.X509Certificate;

import generic.server.ARequete;
import network.communication.communicationException;
import network.crypto.ACryptographieAsymetrique;
import network.crypto.CryptographieAsymetriqueException;
import network.protocole.tickmap.reponse.ReponseCertificat;

public class RequeteCertificate extends ARequete {
	
	
	
	public RequeteCertificate() {
		super("getCertificate", null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doAction() 
	{
		try {
			
			X509Certificate cert = null;
			
			try {
				cert = ACryptographieAsymetrique.certificate("tt", "tt", "tt", "tt");
				this.reponse = ReponseCertificat.OK(cert);
			} catch (CryptographieAsymetriqueException e) {
				this.reponse = ReponseCertificat.KO("Error to load certificate");
				traceEvent("Error to load Certificat: " + e.getMessage());
			}
			
			this.communication.send(this.reponse);
		} catch (communicationException e) {
			traceEvent("Certificate : " + e.getMessage());
		}
	}

}

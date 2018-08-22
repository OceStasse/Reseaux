package network.protocole.tickmap.reponse;

import java.security.cert.X509Certificate;

import generic.server.AReponse;

public class ReponseCertificat extends AReponse {
    
    private X509Certificate certificate;
    
    protected ReponseCertificat(String message,boolean successful, X509Certificate cert) {
	super(message, successful);
	this.certificate = cert;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static ReponseCertificat OK(X509Certificate cert){
        return new ReponseCertificat("", true,cert);
    }

    public static ReponseCertificat KO(String message){
        return new ReponseCertificat(message, false,null);
    }

    public X509Certificate getCertificate() {
	return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
	this.certificate = certificate;
    }
}

package network.crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public abstract class ACryptographieAsymetrique {
    
	@SuppressWarnings("unused")
	private Cipher c;
	@SuppressWarnings("unused")
	private PrivateKey secretKey;
	@SuppressWarnings("unused")
	private PublicKey publicKey;
	@SuppressWarnings("unused")
	private final String RSA = "RSA/ECB/PKCS1Padding";
	@SuppressWarnings("unused")
	private X509Certificate certificate;
	@SuppressWarnings("unused")
	private KeyStore keyStore;
	
	
	public static byte[] encrypt(Cipher cipher, byte[] req, PublicKey key) throws CryptographieAsymetriqueException 
	{
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(req);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptographieAsymetriqueException("CryptageAsymetrique()->encrypt()" + e);
		}

		
	}
	
	public static byte[] decrypt(Cipher cipher, byte[] req, PrivateKey key) throws CryptographieAsymetriqueException 
	{
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(req);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptographieAsymetriqueException("CryptageAsymetrique()->decrypt()" + e);
			
		}
		
	}
	
	public static byte[] decrypt(String algo, String provinder, byte[] req, PrivateKey key) throws CryptographieAsymetriqueException 
	{
		try {
			return decrypt(Cipher.getInstance(algo,provinder),req,key);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {
			throw new  CryptographieAsymetriqueException("CryptageAsymetrique()->decrypt()" + e);
		}
	}
	
	public static KeyStore keyStore(String typeKS,String location,String pwd) throws CryptographieAsymetriqueException
	{
		try {
			KeyStore ks = KeyStore.getInstance(typeKS);
			ks.load(new FileInputStream(location),pwd.toCharArray());
			return ks;
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			throw new CryptographieAsymetriqueException("CryptageAsymetrique()->keyStore()" + e);
		}
		
	}
	
	public static PublicKey publicKeyFromCertificate(String location) throws CryptographieAsymetriqueException
	{
		FileInputStream fin;
		try {
			fin = new FileInputStream(location);
			CertificateFactory f = CertificateFactory.getInstance("X.509");
	        X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);
	        return certificate.getPublicKey();
		} catch (FileNotFoundException | CertificateException e) {
			throw new CryptographieAsymetriqueException("CryptageAsymetrique()->publicKeyFromCertificate: " + e );
		}
        
	}
	
	public static PublicKey publicKeyFromCertificate(String typeKS, String locKS, String alias,String pwd) throws CryptographieAsymetriqueException
	{
		try {
			KeyStore ks = keyStore(typeKS,locKS,pwd);
			X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
			return (PublicKey) cert.getPublicKey();
		} catch (CryptographieAsymetriqueException | KeyStoreException e) {
			throw new CryptographieAsymetriqueException("CryptageAsymetrique()->publicKeyFromCertificate()" + e);
		}
		
	}
	
	public static PublicKey publicKeyFromCertificate(KeyStore keyStore, String alias,String pwd) throws CryptographieAsymetriqueException
	{
		try {
			
			X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
			return (PublicKey) cert.getPublicKey();
		} catch (KeyStoreException e) {
			throw new CryptographieAsymetriqueException("CryptageAsymetrique()->publicKeyFromCertificate()" + e);
		}
		
	}
	
	
	public static X509Certificate certificate(String location) throws CryptographieAsymetriqueException
	{
		FileInputStream fin;
		try {
			fin = new FileInputStream(location);
	        CertificateFactory f = CertificateFactory.getInstance("X.509");
	        return (X509Certificate)f.generateCertificate(fin);
		} catch (FileNotFoundException | CertificateException e) {
			throw new CryptographieAsymetriqueException("CryptageAsymetrique()->certificate()" + e);
		}
	}
	
	public static X509Certificate certificate(String locationKS, String typeKS,String alias,String pwd) throws CryptographieAsymetriqueException
	{
		try {
			KeyStore ks = keyStore(typeKS,locationKS,pwd);
			return (X509Certificate) ks.getCertificate(alias);
		} catch (CryptographieAsymetriqueException | KeyStoreException e) {
			throw new CryptographieAsymetriqueException("CryptageAsymetrique()->certificate()" + e);			
		}
	}
	
	public static PrivateKey privateKey(String locationKS, String typeKS,String pwdKS, String pwdKey,String alias) throws CryptographieAsymetriqueException
	{
		try {
			KeyStore ks = keyStore(typeKS,locationKS,pwdKS);
			return (PrivateKey) ks.getKey(alias, pwdKey.toCharArray());
		} catch (CryptographieAsymetriqueException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new CryptographieAsymetriqueException("CryptageAsymetrique()->privateKey()" + e);		
			
		}
		
	}
	
	public static PrivateKey privateKey(KeyStore keyStore, String pwdKey,String alias) throws CryptographieAsymetriqueException
	{
		try {
			return (PrivateKey) keyStore.getKey(alias, pwdKey.toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new CryptographieAsymetriqueException("CryptageAsymetrique()->privateKey()" + e);		
			
		}
		
	}
}

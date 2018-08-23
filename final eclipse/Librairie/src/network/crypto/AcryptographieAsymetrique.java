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

public abstract class AcryptographieAsymetrique {
    
	public static byte[] encrypt(Cipher cipher, byte[] req, PublicKey key) throws CryptageAsymetriqueException 
	{
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(req);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptageAsymetriqueException("CryptageAsymetrique()->encrypt()" + e);
		}

		
	}

	public static byte[] encrypt(String algo, String provinder, byte[] req, PublicKey key) throws CryptageAsymetriqueException
	{
		try {
			return encrypt(Cipher.getInstance(algo, provinder),req,key);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {
			throw new CryptageAsymetriqueException("CryptageAsymetrique()->encrypt()" + e);
		}
	}
	
	public static byte[] decrypt(Cipher cipher, byte[] req, PrivateKey key) throws CryptageAsymetriqueException 
	{
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(req);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptageAsymetriqueException("CryptageAsymetrique()->decrypt()" + e);
			
		}
		
	}
	
	public static byte[] decrypt(String algo, String provinder, byte[] req, PrivateKey key) throws CryptageAsymetriqueException 
	{
		try {
			return decrypt(Cipher.getInstance(algo,provinder),req,key);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {
			throw new  CryptageAsymetriqueException("CryptageAsymetrique()->decrypt()" + e);
		}
	}
	
	public static KeyStore keyStore(String typeKS,String location,String pwd) throws CryptageAsymetriqueException
	{
		try {
			KeyStore ks = KeyStore.getInstance(typeKS);
			ks.load(new FileInputStream(location),pwd.toCharArray());
			return ks;
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			throw new CryptageAsymetriqueException("CryptageAsymetrique()->keyStore()" + e);
		}
		
	}
	
	public static PublicKey publicKeyFromCertificate(String location) throws CryptageAsymetriqueException
	{
		FileInputStream fin;
		try {
			fin = new FileInputStream(location);
			CertificateFactory f = CertificateFactory.getInstance("X.509");
	        X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);
	        return certificate.getPublicKey();
		} catch (FileNotFoundException | CertificateException e) {
			throw new CryptageAsymetriqueException("CryptageAsymetrique()->publicKeyFromCertificate: " + e );
		}
        
	}
	
	public static PublicKey publicKeyFromCertificate(String typeKS, String locKS, String alias,String pwd) throws CryptageAsymetriqueException
	{
		try {
			KeyStore ks = keyStore(typeKS,locKS,pwd);
			X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
			return (PublicKey) cert.getPublicKey();
		} catch (CryptageAsymetriqueException | KeyStoreException e) {
			throw new CryptageAsymetriqueException("CryptageAsymetrique()->publicKeyFromCertificate()" + e);
		}
		
	}
	
	public static X509Certificate certificate(String location) throws CryptageAsymetriqueException
	{
		FileInputStream fin;
		try {
			fin = new FileInputStream(location);
	        CertificateFactory f = CertificateFactory.getInstance("X.509");
	        return (X509Certificate)f.generateCertificate(fin);
		} catch (FileNotFoundException | CertificateException e) {
			throw new CryptageAsymetriqueException("CryptageAsymetrique()->certificate()" + e);
		}
	}
	
	public static X509Certificate certificate(String locationKS, String typeKS,String alias,String pwd) throws CryptageAsymetriqueException
	{
		try {
			KeyStore ks = keyStore(typeKS,locationKS,pwd);
			return (X509Certificate) ks.getCertificate(alias);
		} catch (CryptageAsymetriqueException | KeyStoreException e) {
			throw new CryptageAsymetriqueException("CryptageAsymetrique()->certificate()" + e);			
		}
	}
	
	public static PrivateKey privateKey(String locationKS, String typeKS,String pwdKS, String pwdKey,String alias) throws CryptageAsymetriqueException
	{
		try {
			KeyStore ks = keyStore(typeKS,locationKS,pwdKS);
			return (PrivateKey) ks.getKey(alias, pwdKey.toCharArray());
		} catch (CryptageAsymetriqueException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new CryptageAsymetriqueException("CryptageAsymetrique()->privateKey()" + e);		
			
		}
		
	}
}

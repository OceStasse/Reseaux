package generic.server;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import database.utilities.Access;
import network.communication.Communication;

public interface ISecureRequete {
		 public Runnable createRunnable(Runnable parent, Communication c, IConsoleServeur consoleServeur, Access db, PrivateKey key,PublicKey publicKey, SecretKey keySecret);


}
					
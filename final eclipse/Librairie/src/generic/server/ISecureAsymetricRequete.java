package generic.server;

import java.security.PrivateKey;

import javax.crypto.SecretKey;

import database.utilities.Access;
import network.communication.Communication;

public interface ISecureAsymetricRequete {
		 public Runnable createRunnable(Runnable parent, Communication c, IConsoleServeur consoleServeur, Access db, PrivateKey key);


}
					
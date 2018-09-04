package generic.server;

import javax.crypto.SecretKey;

import database.utilities.Access;
import network.communication.Communication;

public interface ISecureSymetricRequete {
	 public Runnable createRunnable(Runnable parent, Communication c, IConsoleServeur consoleServeur, Access db, SecretKey key);

}

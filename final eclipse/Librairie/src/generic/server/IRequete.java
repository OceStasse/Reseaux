package generic.server;

import database.utilities.Access;
import network.communication.Communication;

public interface IRequete {
    public Runnable createRunnable(Runnable parent, Communication c, IConsoleServeur consoleServeur, Access db);
}

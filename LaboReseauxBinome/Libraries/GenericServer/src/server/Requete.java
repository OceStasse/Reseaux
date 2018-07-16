package server;

import communicator.Communicator;
import database.utilities.DatabaseAccess;
import java.io.Serializable;

public interface Requete extends Serializable {
    public Runnable createRunnable(Runnable parent, Communicator communicator, ConsoleServeur consoleServeur, DatabaseAccess databaseAccess);
}

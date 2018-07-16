package serveur_billets;

import communicator.Communicator;
import communicator.CommunicatorException;
import database.utilities.DatabaseAccess;
import java.net.Socket;
import server.ConsoleServeur;
import server.SourceTaches;
import server.multithread.ThreadPool;

public class ThreadPoolTICKMAP extends ThreadPool {
	private final String DBip;
    private final String DBport;
    private final String DBSID;
    private final String DBschema;
    private final String DBpassword;

	public ThreadPoolTICKMAP(int nbThreads, SourceTaches tachesAExecuter, ConsoleServeur guiApplication, int port, String ip, String DBport, String SID, String schema, String password) {
		super(nbThreads, tachesAExecuter, guiApplication, port);

        this.DBip = ip;
        this.DBport = DBport;
        this.DBSID = SID;
        this.DBschema = schema;
        this.DBpassword = password;
	}


	@Override
	protected Runnable getProtocolRunnable(Socket socket) throws CommunicatorException {
		return new RunnableTICKMAP(this, guiApplication, new Communicator(socket), new DatabaseAccess(DatabaseAccess.databaseType.MYSQL, this.DBip, this.DBport, this.DBSID, this.DBschema, this.DBpassword));
	}

}

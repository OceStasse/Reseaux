package network.serveur.tickmap;

import java.net.Socket;

import database.utilities.Access;
import generic.server.IConsoleServeur;
import generic.server.ISourceTaches;
import generic.server.multithread.APoolThread;
import network.communication.Communication;
import network.communication.communicationException;

public class PoolThread extends APoolThread{
	
	private final String DBip;
    private final String DBport;
    private final String DBSID;
    private final String DBschema;
    private final String DBpassword;

    public PoolThread(int nbThreads, ISourceTaches tachesAExecuter, IConsoleServeur guiApplication, int port, 
            String ip, String DBport, String SID, String schema, String password) {
        super(nbThreads, tachesAExecuter, guiApplication, port);
        
        this.DBip = ip;
        this.DBport = DBport;
        this.DBSID = SID;
        this.DBschema = schema;
        this.DBpassword = password;
    }
	
	@Override
	protected Runnable getProtocolRunnable(Socket socket) throws communicationException {
		return new RunnableTickimap(this, this.guiApplication, new Communication(socket), 
                new Access(Access.dataBaseType.MYSQL, this.DBip, this.DBport, this.DBSID, this.DBschema, this.DBpassword));
	}

}

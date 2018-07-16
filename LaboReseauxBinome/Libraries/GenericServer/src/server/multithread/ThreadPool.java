package server.multithread;

import communicator.CommunicatorException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ConsoleServeur;
import server.SourceTaches;

public abstract class ThreadPool extends Thread {
    protected final int listeningPort;
    protected final SourceTaches tachesAExecuter;
    protected final ConsoleServeur guiApplication;
    protected ServerSocket serverSocket = null;
    protected final int nbThreads;
    private final ArrayList<ThreadClient> threadClients;

    public ThreadPool(int nbThreads, SourceTaches tachesAExecuter, ConsoleServeur guiApplication, int port) {
        this.listeningPort = port;
        this.tachesAExecuter = tachesAExecuter;
        this.guiApplication = guiApplication;
        this.nbThreads = nbThreads;
        this.threadClients = new ArrayList<>();
    }
    
    @Override
    public void run(){
        try {
            this.serverSocket = new ServerSocket(this.listeningPort);
        } catch (IOException ex) {
            Logger.getLogger(ThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
            
        //Démarrage du pool de threads
        for(int i=0; i<this.nbThreads; i++){
            ThreadClient client = new ThreadClient(tachesAExecuter, "Thread du pool n°" + String.valueOf(i));
            client.start();
            this.threadClients.add(client);
        }

        //Mise en attente du serveur
        Socket socket;

        while(!isInterrupted()){
            try{
                socket = serverSocket.accept();
                this.guiApplication.TraceEvenements("serveur#Accepted an incoming connection#ThreadPool");
                tachesAExecuter.recordTache(getProtocolRunnable(socket));
            }catch(IOException ex){
                System.err.println("Erreur d'accept ! ? [" + ex.getMessage() + "]");
            }catch(CommunicatorException ex){
                System.err.println("Erreur de communication ! ? [" + ex.getMessage() + "]");
            }
        }
    }
    
    public void doStop(){
        this.guiApplication.TraceEvenements("serveur#Arret du serveur#ThreadPool");
        try {
            if(this.serverSocket != null)
                this.serverSocket.close();
            this.threadClients.forEach(threadClient -> {
                threadClient.interrupt();
            });
        } catch (IOException ex) {
            this.guiApplication.TraceEvenements("serveur#" + ex.getMessage() +"#ThreadPool");
        }
        
        this.interrupt();
    }
    
    protected abstract Runnable getProtocolRunnable(Socket socket) throws CommunicatorException;
}

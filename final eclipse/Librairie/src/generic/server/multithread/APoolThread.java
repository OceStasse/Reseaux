package generic.server.multithread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import generic.server.IConsoleServeur;
import generic.server.ISourceTaches;
import network.communication.communicationException;

public abstract class APoolThread extends Thread {
    protected final int listeningPort;
    protected final ISourceTaches tachesAExecuter;
    protected final IConsoleServeur guiApplication;
    protected ServerSocket serverSocket = null;
    protected final int nbThreads;
    private final ArrayList<ClientThread> clients;
    
    public APoolThread(int nbThreads, ISourceTaches tachesAExecuter, IConsoleServeur guiApplication, int port) {
        this.listeningPort = port;
        this.tachesAExecuter = tachesAExecuter;
        this.guiApplication = guiApplication;
        this.nbThreads = nbThreads;
        this.clients = new ArrayList<>();
    }
    
    @Override
    public void run(){
        try {
            this.serverSocket = new ServerSocket(this.listeningPort);
        } catch (IOException ex) {
            Logger.getLogger(APoolThread.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
            
        //Démarrage du pool de threads
        for(int i=0; i<this.nbThreads; i++){
            ClientThread client = new ClientThread(tachesAExecuter, "Thread du pool n°" + String.valueOf(i));
            client.start();
            this.clients.add(client);
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
            }catch(communicationException ex){
                System.err.println("Erreur de communication ! ? [" + ex.getMessage() + "]");
            }
        }
    }
    
    public void doStop(){
        this.guiApplication.TraceEvenements("serveur#Arret du serveur#ThreadPool");
        try {
            if(this.serverSocket != null)
                this.serverSocket.close();
            this.clients.forEach(threadClient -> {
                threadClient.interrupt();
            });
        } catch (IOException ex) {
            this.guiApplication.TraceEvenements("serveur#" + ex.getMessage() +"#ThreadPool");
        }
        
        this.interrupt();
    }
    
    protected abstract Runnable getProtocolRunnable(Socket socket) throws communicationException;
}

package generic.server.multithread;

import generic.server.ISourceTaches;

public class ClientThread extends Thread {
    private final ISourceTaches tachesAExecuter;
    @SuppressWarnings("unused")
    private final String name;
    private Runnable tacheEnCours;
    
    public ClientThread(ISourceTaches sourceTaches, String name) {
        this.tachesAExecuter = sourceTaches;
        this.name = name;
    }
    @Override
    public void run(){
        while(!isInterrupted()){
            try{
                this.tacheEnCours = tachesAExecuter.getTache();
                this.tacheEnCours.run();
            }catch(InterruptedException e){
                System.err.println("Interruption : " + e.getMessage());
            }
        }
    }
}

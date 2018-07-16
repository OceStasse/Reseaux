package server.multithread;

import server.SourceTaches;

public class ThreadClient extends Thread {
    private final SourceTaches tachesAExecuter;
    private final String name;
    private Runnable tacheEnCours;

    public ThreadClient(SourceTaches sourceTaches, String name) {
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

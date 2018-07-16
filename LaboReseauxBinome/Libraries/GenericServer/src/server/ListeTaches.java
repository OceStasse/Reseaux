package server;

import java.util.LinkedList;

public class ListeTaches implements SourceTaches {
    private final LinkedList<Runnable> listeTaches;

    public ListeTaches() {
        this.listeTaches = new LinkedList<>();
    }
    
    @Override
    public synchronized Runnable getTache() throws InterruptedException {
        while(!existTaches()) wait();
        return listeTaches.remove();
    }

    @Override
    public synchronized boolean existTaches() {
        return !listeTaches.isEmpty();
    }

    @Override
    public synchronized void recordTache(Runnable runnable) {
        listeTaches.addLast(runnable);
        notify();
    }

}

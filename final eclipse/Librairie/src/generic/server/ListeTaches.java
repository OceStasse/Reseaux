package generic.server;

import java.util.LinkedList;

public class ListeTaches implements ISourceTaches {
    
    private final LinkedList<Runnable> listeTaches;

    public ListeTaches() 
    {
        this.listeTaches = new LinkedList<>();
    }
    
    
    @Override
    public Runnable getTache() throws InterruptedException {
	while(!existTaches()) wait();
        return listeTaches.remove();
    }

    @Override
    public boolean existTaches() {
	return !listeTaches.isEmpty();
    }

    @Override
    public void recordTache(Runnable r) {
	listeTaches.addLast(r);
        notify();
    }
    
}

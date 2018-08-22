package generic.server;

public interface ISourceTaches {
    public Runnable getTache() throws InterruptedException;
    public boolean existTaches();
    public void recordTache(Runnable r);
}

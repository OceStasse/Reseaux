package network.serveur.iacop;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import generic.server.IConsoleServeur;
import network.communication.Message;
import network.communication.MulticastCommunication;
import network.communication.MulticastCommunicationException;

public class ThreadUDP extends Thread {
    private final IConsoleServeur parent;
    private final JList<String> listeLog;
    private final DefaultListModel<String> defaultListModel;
    private final MulticastCommunication c;

    public ThreadUDP(IConsoleServeur parent, JList<String> listeLog, DefaultListModel<String> defaultListModel, MulticastCommunication c) {
	this.parent = parent;
	this.listeLog = listeLog;
	this.defaultListModel = defaultListModel;
	this.c = c;
    }
    @Override
    public void run() {
	while(!isInterrupted()){
	    try {
		Message message = c.receive();
		this.defaultListModel.addElement(message.toString());
		this.listeLog.ensureIndexIsVisible( this.defaultListModel.getSize() -1 );
	    } catch (MulticastCommunicationException ex) {
		Logger.getLogger(EspionConversationFrame.class.getName()).log(Level.SEVERE, null, ex);
		parent.TraceEvenements(ex.getMessage());
	    }
	}
    }
    public void doStop(){
	if(this.c!=null)
	    this.c.close();

	this.interrupt();
    }
}

package network.client.iacop;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import generic.client.IConsoleClient;
import network.communication.Message;
import network.communication.MulticastCommunication;
import network.communication.MulticastCommunicationException;

public class ReceptionMessageThread extends Thread {
    private final MulticastCommunication c;
    private final LinkedList<Message> messages;
    private final IConsoleClient consoleClient;
    
    public ReceptionMessageThread(IConsoleClient consoleClient, MulticastCommunication c, LinkedList<Message> messages) {
	this.consoleClient = consoleClient;
	this.c = c;
	this.messages = messages;
    }
    
    @Override
    public void run() {
	while(!isInterrupted()){
	    try{
		Message message = this.c.receive();

		this.messages.add(message);
		this.consoleClient.refreshMessageList();
	    } catch (MulticastCommunicationException ex) {
		Logger.getLogger(ReceptionMessageThread.class.getName()).log(Level.SEVERE, null, ex);
		this.interrupt();
	    }
	}
    }
    
    public void doStop(){
	if(this.c != null)
	    this.c.close();

	this.interrupt();
    }
}

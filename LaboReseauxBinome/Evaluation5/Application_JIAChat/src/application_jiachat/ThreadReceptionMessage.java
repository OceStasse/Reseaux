package application_jiachat;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.Message;
import network.MulticastCommunicator;
import network.MulticastCommunicatorException;

public class ThreadReceptionMessage extends Thread {
	private final MulticastCommunicator communicator;
	private final LinkedList<Message> messages;
	private final JFrameApplication_JIAChat parent;

	public ThreadReceptionMessage(JFrameApplication_JIAChat parent, MulticastCommunicator communicator, LinkedList<Message> messages) {
		this.parent = parent;
		this.communicator = communicator;
		this.messages = messages;
	}

	@Override
	public void run() {
		while(!isInterrupted()){
			try{
				Message message = this.communicator.receiveMessage();

				this.messages.add(message);
				this.parent.refreshMessageList();
			} catch (MulticastCommunicatorException ex) {
				Logger.getLogger(ThreadReceptionMessage.class.getName()).log(Level.SEVERE, null, ex);
				this.interrupt();
			}
		}
	}

	public void doStop(){
		if(this.communicator != null)
			this.communicator.close();

		this.interrupt();
	}
}

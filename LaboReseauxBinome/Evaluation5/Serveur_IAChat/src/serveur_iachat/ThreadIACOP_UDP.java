package serveur_iachat;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import network.Message;
import network.MulticastCommunicator;
import network.MulticastCommunicatorException;

public class ThreadIACOP_UDP extends Thread {
	private final JFrameServeur_IAChat parent;
	private final JList listeLog;
	private final DefaultListModel<String> defaultListModel;
	private final MulticastCommunicator communicator;

	public ThreadIACOP_UDP(JFrameServeur_IAChat parent, JList listeLog, DefaultListModel<String> defaultListModel, MulticastCommunicator communicator) {
		this.parent = parent;
		this.listeLog = listeLog;
		this.defaultListModel = defaultListModel;
		this.communicator = communicator;
	}

	@Override
	public void run() {
		while(!isInterrupted()){
			try {
				Message message = communicator.receiveMessage();
				this.defaultListModel.addElement(message.toString());
				this.listeLog.ensureIndexIsVisible( this.defaultListModel.getSize() -1 );
			} catch (MulticastCommunicatorException ex) {
				Logger.getLogger(JFrameConversationWatcher.class.getName()).log(Level.SEVERE, null, ex);
				parent.TraceEvenements(ex.getMessage());
			}
		}
	}

	public void doStop(){
		if(this.communicator!=null)
			this.communicator.close();

		this.interrupt();
	}
}

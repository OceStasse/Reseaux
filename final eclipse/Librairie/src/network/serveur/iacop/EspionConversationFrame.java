package network.serveur.iacop;

import java.io.IOException;
import java.net.InetAddress;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import generic.server.IConsoleServeur;
import network.communication.MulticastCommunication;

public class EspionConversationFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ThreadUDP thread_UDP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listLog;

    public EspionConversationFrame(IConsoleServeur parent, String ip, int port, String separator, String finTrame) throws IOException {
	initComponents();
	DefaultListModel<String> defaultListModel = new DefaultListModel<>();
	this.listLog.setModel(defaultListModel);

	MulticastCommunication c = new MulticastCommunication(InetAddress.getLocalHost(), InetAddress.getByName(ip), port, separator, finTrame);
	this.thread_UDP = new ThreadUDP(parent, listLog, defaultListModel, c);
	this.thread_UDP.start();
    }

    private void initComponents() {

	jScrollPane1 = new javax.swing.JScrollPane();
	listLog = new javax.swing.JList<>();

	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	addWindowListener(new java.awt.event.WindowAdapter() {
	    public void windowClosing(java.awt.event.WindowEvent evt) {
		formWindowClosing(evt);
	    }
	});

	listLog.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	jScrollPane1.setViewportView(listLog);

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
			.addContainerGap()
			.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
			.addContainerGap())
		);
	layout.setVerticalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
			.addContainerGap()
			.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
			.addContainerGap())
		);

	pack();
	setLocationRelativeTo(null);
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
	close();
    }   

    public void close(){
	this.thread_UDP.doStop();
	this.thread_UDP = null;
	this.dispose();
    }
}

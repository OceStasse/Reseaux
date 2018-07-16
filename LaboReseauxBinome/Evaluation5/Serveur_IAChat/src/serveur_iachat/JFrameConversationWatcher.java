package serveur_iachat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import network.Message;
import network.MulticastCommunicator;
import network.MulticastCommunicatorException;

public class JFrameConversationWatcher extends javax.swing.JFrame {
	private ThreadIACOP_UDP thread_UDP;

    /** Creates new form JFrameConversationListener */
    public JFrameConversationWatcher(JFrameServeur_IAChat parent, String ip, int port, String separator, String finTrame) throws IOException {
		initComponents();
		DefaultListModel defaultListModel = new DefaultListModel<>();
		this.listLog.setModel(defaultListModel);

		MulticastCommunicator communicator = new MulticastCommunicator(InetAddress.getLocalHost(), InetAddress.getByName(ip), port, separator, finTrame);
		this.thread_UDP = new ThreadIACOP_UDP(parent, listLog, defaultListModel, communicator);
		this.thread_UDP.start();
    }

	public void close(){
		this.thread_UDP.doStop();
		this.thread_UDP = null;
		this.dispose();
	}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        close();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listLog;
    // End of variables declaration//GEN-END:variables

}

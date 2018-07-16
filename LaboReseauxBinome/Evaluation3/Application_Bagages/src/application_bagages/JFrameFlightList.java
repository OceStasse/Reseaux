package application_bagages;

import entities.Flight;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.net.Socket;
import javax.swing.DefaultListModel;
import login.ParentLoginFrame;
import communicator.Communicator;
import communicator.CommunicatorException;
import encryption.Encrypt;
import encryption.EncryptionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import login.ConnectionException;
import lugap.reponse.ReponseLUGAP_getFlights;
import lugap.reponse.ReponseLUGAP_login;
import lugap.reponse.ReponseLUGAP_logout;
import lugap.requete.RequeteLUGAP_getFlights;
import lugap.requete.RequeteLUGAP_login;
import lugap.requete.RequeteLUGAP_logout;

public class JFrameFlightList extends ParentLoginFrame {
    private Communicator communicator;
    private ArrayList<Flight> flights;
    

    public JFrameFlightList() {
        initComponents();
        
        setIpAddress("127.0.0.1");
        setPort("26010");
        setLogin("toto");
        setPassword("mdp");
        showConnectionDialog();
        
        initFlightList();

        DefaultListModel<String> model = new DefaultListModel<>();
        for(Flight flight : flights)
            model.addElement(flight.toString());
        
        this.listFlight.setModel(model);
    } 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listFlight = new javax.swing.JList<>();
        labelTitle = new javax.swing.JLabel();
        labelHelp = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        listFlight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listFlightMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listFlight);

        labelTitle.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelTitle.setText("Liste des vols prévus à ce jour :");

        labelHelp.setText("(double-cliquez pour plus de détails)");
        labelHelp.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(labelHelp))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTitle)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitle)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelHelp)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void listFlightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listFlightMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2){
            Rectangle r = listFlight.getCellBounds(0, listFlight.getLastVisibleIndex());
            if (r != null && r.contains(evt.getPoint())){
                int i = listFlight.locationToIndex(evt.getPoint());
                showLuggageList(this.flights.get(i));
            }
            else{
                listFlight.clearSelection();
            }
        }
    }//GEN-LAST:event_listFlightMouseClicked

    @Override
    protected void connect() throws ConnectionException {
        try {
            Socket clientSocket = new Socket(this.getIpAddress(), Integer.parseInt(this.getPort()));
            
            this.communicator = new Communicator(clientSocket);

            double rand = Math.random();
            long time= (new Date()).getTime();
            this.communicator.SendSerializable(new RequeteLUGAP_login(this.getLogin(), Encrypt.saltDigest(this.getPassword(), time, rand), time, rand));
            
            ReponseLUGAP_login reponse = this.communicator.receiveSerializable(ReponseLUGAP_login.class);
            
            if(!reponse.isSuccessful()){
                System.err.println(reponse.getMessage());
                throw new ConnectionException(reponse.getMessage());
            }
        } catch (IOException | CommunicatorException | EncryptionException ex) {
            throw new ConnectionException(ex.getMessage());
        }
    }
    
    @Override
    protected void disconnect() throws ConnectionException {
        try {
            this.communicator.SendSerializable(new RequeteLUGAP_logout());
            
            ReponseLUGAP_logout reponse = this.communicator.receiveSerializable(ReponseLUGAP_logout.class);
            
            if(!reponse.isSuccessful()){
                System.err.println(reponse.getMessage());
                throw new ConnectionException(reponse.getMessage());
            }
            
        } catch (CommunicatorException ex) {
            throw new ConnectionException(ex.getMessage());
        }
    }
    
    private void showLuggageList(Flight flight){
        JDialogLuggageList dialogLuggageList = new JDialogLuggageList(this, communicator, flight);
        dialogLuggageList.pack();
        dialogLuggageList.setLocationRelativeTo(this);
        dialogLuggageList.setVisible(true);
    }
    
    private void initFlightList() {
        try {
            this.communicator.SendSerializable(new RequeteLUGAP_getFlights());
            
            ReponseLUGAP_getFlights reponse = this.communicator.receiveSerializable(ReponseLUGAP_getFlights.class);
            
            if(!reponse.isSuccessful()){
                this.flights = new ArrayList<>();
                System.err.println(reponse.getMessage());
                JOptionPane.showMessageDialog(this, reponse.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                this.flights = reponse.getFlights();
            }
        } catch (CommunicatorException ex) {
            System.err.println(ex.getMessage());
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    protected void exitProcedure(){
        try {
            disconnect();
            this.communicator.close();
        } catch (ConnectionException | CommunicatorException ex) {
            Logger.getLogger(JFrameFlightList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.dispose();
        System.exit(0);
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exitProcedure();
    }//GEN-LAST:event_formWindowClosing
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameFlightList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameFlightList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameFlightList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameFlightList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new JFrameFlightList().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelHelp;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JList<String> listFlight;
    // End of variables declaration//GEN-END:variables
}

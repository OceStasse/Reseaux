package Main;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import database.entities.Vol;
import generic.frame.login.ALoginFrameParent;
import generic.frame.login.ConnectionException;
import network.communication.Communication;
import network.communication.communicationException;
import network.crypto.AEncryption;
import network.crypto.EncryptionException;
import network.protocole.lugap.reponse.Reponse_Vols;
import network.protocole.lugap.reponse.Reponse_login;
import network.protocole.lugap.reponse.Reponse_logout;
import network.protocole.lugap.requete.Requete_Vols;
import network.protocole.lugap.requete.Requete_login;
import network.protocole.lugap.requete.Requete_logout;

public class ListVolFrame extends ALoginFrameParent {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Communication c;
    private ArrayList<Vol> vols;
    
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelHelp;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JList<String> listVol;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    ListVolFrame frame = new ListVolFrame();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public ListVolFrame() {
	initComponents();
        
        this.setAdresseIp("127.0.0.1");
        this.setPort("26010");
        this.setLogin("toto");
        this.setPwd("mdp");
        this.showConnectionDialog();
        
        this.initVolList();

        DefaultListModel<String> model = new DefaultListModel<>();
        for(Vol v : vols)
            model.addElement(v.toString());
        
        this.listVol.setModel(model);

    }
    
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        listVol = new javax.swing.JList<>();
        labelTitle = new javax.swing.JLabel();
        labelHelp = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        listVol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listFlightMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listVol);

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
    }
    
    protected void exitProcedure(){
        try {
            disconnect();
        } catch (ConnectionException ex) {
            Logger.getLogger(ListVolFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.dispose();
        System.exit(0);
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
        exitProcedure();
    } 
    
    
    private void listFlightMouseClicked(java.awt.event.MouseEvent evt) {                                        
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2){
            Rectangle r = listVol.getCellBounds(0, listVol.getLastVisibleIndex());
            if (r != null && r.contains(evt.getPoint())){
                int i = listVol.locationToIndex(evt.getPoint());
                showBaggageList(this.vols.get(i));
            }
            else{
                listVol.clearSelection();
            }
        }
    }
    
    
    @Override
    protected void connect() throws ConnectionException {
        try {
            Socket clientSocket = new Socket(this.getAdresseIp(), Integer.parseInt(this.getPort()));
            
            this.c = new Communication(clientSocket);

            double rand = Math.random();
            long time= (new Date()).getTime();
            this.c.send(new Requete_login(this.getLogin(), AEncryption.saltDigest(this.getPwd(), time, rand), time, rand));
            
            Reponse_login reponse = this.c.receive(Reponse_login.class);
            
            if(!reponse.isSuccessful()){
                System.err.println(reponse.getMessage());
                throw new ConnectionException(reponse.getMessage());
            }
        } catch (IOException | communicationException | EncryptionException ex) {
            throw new ConnectionException(ex.getMessage());
        }
    }
    
    @Override
    protected void disconnect() throws ConnectionException {
        try {
            this.c.send(new Requete_logout());
            
            Reponse_logout reponse = this.c.receive(Reponse_logout.class);
            
            if(!reponse.isSuccessful()){
                System.err.println(reponse.getMessage());
                throw new ConnectionException(reponse.getMessage());
            }
            
        } catch (communicationException ex) {
            throw new ConnectionException(ex.getMessage());
        }
    }
    
    private void showBaggageList(Vol v){
        ListBaggageDialog dialogLuggageList = new ListBaggageDialog(this, c, v);
        dialogLuggageList.pack();
        dialogLuggageList.setLocationRelativeTo(this);
        dialogLuggageList.setVisible(true);
    }
    
    private void initVolList() {
        try {
            this.c.send(new Requete_Vols());
            
            Reponse_Vols reponse = this.c.receive(Reponse_Vols.class);
            
            if(!reponse.isSuccessful()){
                this.vols = new ArrayList<>();
                System.err.println(reponse.getMessage());
                JOptionPane.showMessageDialog(this, reponse.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                this.vols = reponse.getFlights();
            }
        } catch (communicationException ex) {
            System.err.println(ex.getMessage());
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

}

package Main;

import java.awt.Component;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import database.utilities.Access;
import generic.server.IConsoleServeur;
import generic.server.ListeTaches;
import network.serveur.LUGAP.PoolThread;

public class ServeurFrame extends JFrame implements IConsoleServeur {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private javax.swing.JButton buttonClear;
    private javax.swing.JButton jButtonStartStop;
    private javax.swing.JLabel jLabelPort;
    private javax.swing.JLabel jLabelPortMobile;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableConsole;
    private javax.swing.JTextField jTextFieldPort;
    private javax.swing.JTextField jTextFieldPortMobile;
    private javax.swing.JLabel labelServerStatus;
    
    private int ligneConsoleServeur = 1;
    private final String propertyFilePath = "../serveurBagage.conf";
    private final Properties propertyFile = new Properties();
    private PoolThread poolThreadLUGAP;
    private int NB_THREAD_LUGAP;
    private boolean isRunning = false;
    private String PORT_BAGAGES;
    @SuppressWarnings("unused")
    private String PORT_CHECKIN;
    private String DB_IP_ADDRESS;
    private String DB_PORT;
    private String DB_SCHEMA;
    private String DB_LOGIN;
    private String DB_PASSWORD;
    @SuppressWarnings("unused")
    private Access database;
    private final DefaultTableModel defaultTableModel = new DefaultTableModel();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    ServeurFrame frame = new ServeurFrame();
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
    public ServeurFrame() {
        initComponents();
        
        defaultTableModel.addColumn("Ligne");
        defaultTableModel.addColumn("Origine");
        defaultTableModel.addColumn("Message");
        defaultTableModel.addColumn("Lieu");
        jTableConsole.setModel(this.defaultTableModel);

        //On change la taille des colonnes et l'alignement du texte
        jTableConsole.getColumnModel().getColumn(0).setMaxWidth(40);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        jTableConsole.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        jTableConsole.getColumnModel().getColumn(1).setMaxWidth(250);
        jTableConsole.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTableConsole.getColumnModel().getColumn(2).setCellRenderer(new WordWrapCellRenderer());
        jTableConsole.getColumnModel().getColumn(3).setMaxWidth(250);
        jTableConsole.getColumnModel().getColumn(3).setPreferredWidth(115);
        
        loadPropertyFile();
        
        this.jTextFieldPort.setText(this.PORT_BAGAGES);
    }
    
    private void initComponents()
    {
	jLabelPort = new javax.swing.JLabel();
        jTextFieldPort = new javax.swing.JTextField();
        jButtonStartStop = new javax.swing.JButton();
        labelServerStatus = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableConsole = new javax.swing.JTable();
        buttonClear = new javax.swing.JButton();
        jLabelPortMobile = new javax.swing.JLabel();
        jTextFieldPortMobile = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelPort.setText("Port : ");

        jButtonStartStop.setText("Start");
        jButtonStartStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartStopActionPerformed(evt);
            }
        });

        jTableConsole.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableConsole);

        buttonClear.setText("Clear");
        buttonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearActionPerformed(evt);
            }
        });

        jLabelPortMobile.setText("Port mobile : ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPort)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelPortMobile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPortMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelServerStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jButtonStartStop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonClear)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                        .addComponent(jSeparator1))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelPort)
                        .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelPortMobile)
                        .addComponent(jTextFieldPortMobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelServerStatus)
                        .addComponent(buttonClear)
                        .addComponent(jButtonStartStop)))
                .addContainerGap(250, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(4, 4, 4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addGap(5, 5, 5)))
        );

        pack();
        setLocationRelativeTo(null);
    }

    
    private void jButtonStartStopActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        try{
            if(!isRunning){
                TraceEvenements("serveur#Démarrage du serveur...#main");
                this.jButtonStartStop.setText("Stop");
                
                TraceEvenements("serveur#acquisition du port#main");
                
                if(!this.PORT_BAGAGES.equals(this.jTextFieldPort.getText()))
                    setPORT_BAGAGES(this.jTextFieldPort.getText());
                
                
                TraceEvenements("serveur#Lancement des threads#main");
                int portInt = Integer.valueOf(this.PORT_BAGAGES);
                this.poolThreadLUGAP = new PoolThread(this.NB_THREAD_LUGAP, new ListeTaches(), this, portInt, this.DB_IP_ADDRESS, this.DB_PORT, this.DB_SCHEMA, this.DB_LOGIN, this.DB_PASSWORD);
                new ServerSocket(portInt).close();  //On check si la socket est libre ou non
                this.poolThreadLUGAP.start();
                
                this.isRunning = true;
                TraceEvenements("serveur#Threads Lancés#main");
            }
            else{
                TraceEvenements("serveur#Extinction du serveur...#main");
                this.jButtonStartStop.setText("Start");

                this.poolThreadLUGAP.doStop();
                this.poolThreadLUGAP = null;
                
                this.isRunning = false;
            }
        } catch (IOException ex) {
            TraceEvenements("serveur#ERREUR : " + ex.getMessage() + "#main");
            this.jButtonStartStop.setText("Start");
            if(this.poolThreadLUGAP != null)
                this.poolThreadLUGAP.doStop();
            
            this.isRunning = false;
        }
    }  
    
    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {                                            
        ((DefaultTableModel)this.jTableConsole.getModel()).setRowCount(0);
    }
    
    @Override
    public void TraceEvenements(String commentaire) {
	Vector<String> ligne = new Vector<String>();
        ligne.add(Integer.toString(ligneConsoleServeur++));
	StringTokenizer parser = new StringTokenizer(commentaire, "#");
	while(parser.hasMoreTokens())
	    ligne.add(parser.nextToken());
        defaultTableModel.addRow(ligne);
        jScrollPane1.validate();
        JScrollBar bar = this.jScrollPane1.getVerticalScrollBar();
        bar.setValue(bar.getMaximum());
    }
    
    private void setPORT_BAGAGES(String newPort){
        this.PORT_BAGAGES = newPort;
        this.propertyFile.setProperty("PORT_BAGAGES", newPort);

        try (FileOutputStream fileOutputStream = new FileOutputStream(this.propertyFilePath)) {
            this.propertyFile.store(fileOutputStream, null);
        } catch (IOException ex) {
            Logger.getLogger(ServeurFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
    
    private void loadPropertyFile() {
        try (FileInputStream fileInputStream = new FileInputStream(this.propertyFilePath)) {
            this.propertyFile.load(fileInputStream);
        }catch(FileNotFoundException ex){
            try{
                propertyFile.setProperty("THREAD_NB_LUGAP", "10");
                propertyFile.setProperty("PORT_BAGAGES", "26010");
                propertyFile.setProperty("PORT_CHECKIN", "26020");
                
                propertyFile.setProperty("DB_IP_ADDRESS", "127.0.0.1");
                propertyFile.setProperty("DB_PORT", "3306");
                propertyFile.setProperty("DB_SCHEMA", "BD_AIRPORT");
                propertyFile.setProperty("DB_LOGIN", "LaboReseaux");
                propertyFile.setProperty("DB_PASSWORD", "mysql");
                
                try (FileOutputStream fileOutputStream = new FileOutputStream(this.propertyFilePath)) {
                    propertyFile.store(fileOutputStream, null);
                }
            } catch (IOException ex1) {
                Logger.getLogger(ServeurFrame.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServeurFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        this.NB_THREAD_LUGAP = Integer.valueOf(propertyFile.getProperty("THREAD_NB_LUGAP", "10"));
        this.PORT_BAGAGES = propertyFile.getProperty("PORT_BAGAGES", "26010");
        this.PORT_CHECKIN = propertyFile.getProperty("PORT_CHECKIN", "26020");
        
        this.DB_IP_ADDRESS = propertyFile.getProperty("DB_IP_ADDRESS", "127.0.0.1");
        this.DB_PORT = propertyFile.getProperty("DB_PORT", "3306");
        this.DB_SCHEMA = propertyFile.getProperty("DB_SCHEMA", "BD_AIRPORT");
        this.DB_LOGIN = propertyFile.getProperty("DB_LOGIN", "LaboReseaux");
        this.DB_PASSWORD = propertyFile.getProperty("DB_PASSWORD", "mysql");
    }
    
    static class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	WordWrapCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            setOpaque(true);
            setBorder(null);
            
            setForeground(table.getForeground());
            setBackground(table.getBackground());
            
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }

}

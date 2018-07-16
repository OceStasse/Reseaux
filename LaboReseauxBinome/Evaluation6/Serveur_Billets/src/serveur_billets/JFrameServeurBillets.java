package serveur_billets;

import java.awt.Component;
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
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import server.ConsoleServeur;
import server.ListeTaches;

public class JFrameServeurBillets extends javax.swing.JFrame implements ConsoleServeur {
	private int ligneConsoleServeur = 1;
    private final DefaultTableModel defaultTableModel = new DefaultTableModel();
	private boolean isRunning = false;
	private ThreadPoolTICKMAP poolThreadTICKMAP;

	private final String propertyFilePath = "../serveurBillets.conf";
    private final Properties propertyFile = new Properties();
	private int NB_THREAD_TICKMAP;
	private String PORT_BILLETS;
	private String DB_IP_ADDRESS;
    private String DB_PORT;
    private String DB_SCHEMA;
    private String DB_LOGIN;
    private String DB_PASSWORD;

    /** Creates new form JFrameServeurBillets */
    public JFrameServeurBillets() {
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

		this.jTextFieldPort.setText(this.PORT_BILLETS);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelPort = new javax.swing.JLabel();
        jTextFieldPort = new javax.swing.JTextField();
        jButtonStartStop = new javax.swing.JButton();
        labelServerStatus = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableConsole = new javax.swing.JTable();
        buttonClear = new javax.swing.JButton();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelPort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonStartStop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonClear))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(labelServerStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPort)
                    .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonStartStop)
                    .addComponent(buttonClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 143, Short.MAX_VALUE)
                    .addComponent(labelServerStatus)
                    .addGap(0, 144, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonStartStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartStopActionPerformed
        try{
            if(!isRunning){
                TraceEvenements("serveur#Démarrage du serveur...#main");
                this.jButtonStartStop.setText("Stop");

                TraceEvenements("serveur#acquisition du port#main");

                if(!this.PORT_BILLETS.equals(this.jTextFieldPort.getText()))
					setPORT_BILLETS(this.jTextFieldPort.getText());

                TraceEvenements("serveur#Lancement des threads#main");
                int portInt = Integer.valueOf(this.PORT_BILLETS);
                this.poolThreadTICKMAP = new ThreadPoolTICKMAP(this.NB_THREAD_TICKMAP, new ListeTaches(), this, portInt, this.DB_IP_ADDRESS, this.DB_PORT, this.DB_SCHEMA, this.DB_LOGIN, this.DB_PASSWORD);
                new ServerSocket(portInt).close();  //On check si la socket est libre ou non
                this.poolThreadTICKMAP.start();

                this.isRunning = true;
                TraceEvenements("serveur#Threads Lancés#main");
            }
            else{
                TraceEvenements("serveur#Extinction du serveur...#main");
                this.jButtonStartStop.setText("Start");

                this.poolThreadTICKMAP.doStop();
                this.poolThreadTICKMAP = null;

                this.isRunning = false;
            }
        } catch (IOException ex) {
            TraceEvenements("serveur#ERREUR : " + ex.getMessage() + "#main");
            this.jButtonStartStop.setText("Start");
            if(this.poolThreadTICKMAP != null)
            this.poolThreadTICKMAP.doStop();

            this.isRunning = false;
        }
    }//GEN-LAST:event_jButtonStartStopActionPerformed

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
        ((DefaultTableModel)this.jTableConsole.getModel()).setRowCount(0);
    }//GEN-LAST:event_buttonClearActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(JFrameServeurBillets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameServeurBillets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameServeurBillets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameServeurBillets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameServeurBillets().setVisible(true);
            }
        });
    }

	@Override
	public void TraceEvenements(String commentaire) {
		Vector ligne = new Vector();
        ligne.add(Integer.toString(ligneConsoleServeur++));
	StringTokenizer parser = new StringTokenizer(commentaire, "#");
	while(parser.hasMoreTokens())
	    ligne.add(parser.nextToken());
        defaultTableModel.addRow(ligne);
        jScrollPane1.validate();
        JScrollBar bar = this.jScrollPane1.getVerticalScrollBar();
        bar.setValue(bar.getMaximum());
	}

	private void setPORT_BILLETS(String newPort){
        this.PORT_BILLETS = newPort;
        this.propertyFile.setProperty("PORT_BAGAGES", newPort);

        try (FileOutputStream fileOutputStream = new FileOutputStream(this.propertyFilePath)) {
            this.propertyFile.store(fileOutputStream, null);
        } catch (IOException ex) {
            Logger.getLogger(JFrameServeurBillets.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    private void loadPropertyFile() {
        try (FileInputStream fileInputStream = new FileInputStream(this.propertyFilePath)) {
            this.propertyFile.load(fileInputStream);
        }catch(FileNotFoundException ex){
            try{
                propertyFile.setProperty("THREAD_NB_TICKMAP", "10");
                propertyFile.setProperty("PORT_BILLETS", "26070");

                propertyFile.setProperty("DB_IP_ADDRESS", "127.0.0.1");
                propertyFile.setProperty("DB_PORT", "3306");
                propertyFile.setProperty("DB_SCHEMA", "BD_AIRPORT");
                propertyFile.setProperty("DB_LOGIN", "LaboReseaux");
                propertyFile.setProperty("DB_PASSWORD", "mysql");

                try (FileOutputStream fileOutputStream = new FileOutputStream(this.propertyFilePath)) {
                    propertyFile.store(fileOutputStream, null);
                }
            } catch (IOException ex1) {
                Logger.getLogger(JFrameServeurBillets.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            Logger.getLogger(JFrameServeurBillets.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }

        this.NB_THREAD_TICKMAP = Integer.valueOf(propertyFile.getProperty("THREAD_NB_TICKMAP", "10"));
        this.PORT_BILLETS = propertyFile.getProperty("PORT_BILLETS", "26070");

        this.DB_IP_ADDRESS = propertyFile.getProperty("DB_IP_ADDRESS", "127.0.0.1");
        this.DB_PORT = propertyFile.getProperty("DB_PORT", "3306");
        this.DB_SCHEMA = propertyFile.getProperty("DB_SCHEMA", "BD_AIRPORT");
        this.DB_LOGIN = propertyFile.getProperty("DB_LOGIN", "LaboReseaux");
        this.DB_PASSWORD = propertyFile.getProperty("DB_PASSWORD", "mysql");
    }

    static class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClear;
    private javax.swing.JButton jButtonStartStop;
    private javax.swing.JLabel jLabelPort;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableConsole;
    private javax.swing.JTextField jTextFieldPort;
    private javax.swing.JLabel labelServerStatus;
    // End of variables declaration//GEN-END:variables

}

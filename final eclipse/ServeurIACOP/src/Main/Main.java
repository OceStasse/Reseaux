package Main;

import java.awt.Component;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import database.utilities.Access;
import generic.server.IConsoleServeur;
import network.serveur.iacop.EspionConversationFrame;
import network.serveur.iacop.TCPThread;

public class Main extends JFrame implements IConsoleServeur {


    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final String propertyFilePath = "../serveurIAChat.conf";
    private final Properties propertyFile = new Properties();
    private int ligneConsoleServeur = 1;
    private boolean isRunning = false;
    private final DefaultTableModel defaultTableModel = new DefaultTableModel();
    private TCPThread thread_TCP;
    private EspionConversationFrame conversationWatcher = null;

    private String PORT_FLY;
    private String IP_UDP;
    private String PORT_CHAT;
    private String DB_IP;
    private String DB_PORT;
    private String DB_SCHEMA;
    private String DB_LOGIN;
    private String DB_PASSWORD;
    private String SEPARATOR;
    private String FIN_TRAME;
    
    private JButton buttonClear;
    private JButton jButtonStartStop;
    private JLabel jLabelPort;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JTable jTableConsole;
    private JTextField jTextFieldPort;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    Main frame = new Main();
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
    public Main() {
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

	this.jTextFieldPort.setText(String.valueOf(this.PORT_FLY));
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
	this.jTableConsole.scrollRectToVisible(jTableConsole.getCellRect(defaultTableModel.getRowCount()+1, 0, true));
    }
    
    private void initComponents() {

	jLabelPort = new javax.swing.JLabel();
	jTextFieldPort = new javax.swing.JTextField();
	jButtonStartStop = new javax.swing.JButton();
	buttonClear = new javax.swing.JButton();
	jSeparator1 = new javax.swing.JSeparator();
	jScrollPane1 = new javax.swing.JScrollPane();
	jTableConsole = new javax.swing.JTable();

	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	setPreferredSize(new java.awt.Dimension(690, 288));

	jLabelPort.setText("Port : ");

	jButtonStartStop.setText("Start");
	jButtonStartStop.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonStartStopActionPerformed(evt);
	    }
	});

	buttonClear.setText("Clear");
	buttonClear.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		buttonClearActionPerformed(evt);
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
	jTableConsole.setFocusable(false);
	jScrollPane1.setViewportView(jTableConsole);

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
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(buttonClear))
				.addComponent(jSeparator1)
				.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE))
			.addContainerGap())
		);
	layout.setVerticalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
			.addContainerGap()
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
				.addComponent(jLabelPort)
				.addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(buttonClear)
				.addComponent(jButtonStartStop))
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
			.addContainerGap())
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
		int portFLY = Integer.valueOf(this.PORT_FLY);
		int portCHAT = Integer.valueOf(this.PORT_CHAT);
		if(!this.PORT_FLY.equals(this.jTextFieldPort.getText()))
		    setPORT_FLY(this.jTextFieldPort.getText());

		TraceEvenements("serveur#Lancement du thread login#main");
		Access db = new Access(Access.dataBaseType.MYSQL,
			this.DB_IP, this.DB_PORT, this.DB_SCHEMA,
			this.DB_LOGIN, DB_PASSWORD);
		db.connect();
		this.thread_TCP = new TCPThread(this, db, portFLY, IP_UDP, portCHAT, this.SEPARATOR);
		new ServerSocket(portFLY).close();  //On check si la socket est libre ou non
		this.thread_TCP.start();

		this.conversationWatcher = new EspionConversationFrame(this, this.IP_UDP, portCHAT, this.SEPARATOR, this.FIN_TRAME);
		this.conversationWatcher.setVisible(true);

		this.isRunning = true;
		TraceEvenements("serveur#Threads Lancés#main");

	    }
	    else{
		TraceEvenements("serveur#Extinction du serveur...#main");
		this.jButtonStartStop.setText("Start");

		if(this.thread_TCP!= null){
		    this.thread_TCP.doStop();
		    this.thread_TCP = null;
		}

		if(this.conversationWatcher !=null){
		    this.conversationWatcher.close();
		    this.conversationWatcher = null;
		}

		this.isRunning = false;
	    }
	} catch (NumberFormatException | ClassNotFoundException | SQLException | IOException ex) {
	    TraceEvenements("serveur#ERREUR IOException : " + ex.getMessage() + "#main");
	    this.jButtonStartStop.setText("Start");
	    if(this.thread_TCP != null){
		this.thread_TCP.doStop();
		this.thread_TCP = null;
	    }

	    if(this.conversationWatcher != null){
		this.conversationWatcher.dispose();
		this.conversationWatcher = null;
	    }
	}
    }  

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {                                            
	((DefaultTableModel)this.jTableConsole.getModel()).setRowCount(0);
    }     
    
    private void setPORT_FLY(String newPort){
	this.PORT_FLY = newPort;
	this.propertyFile.setProperty("PORT_FLY", newPort);

	try (FileOutputStream fileOutputStream = new FileOutputStream(this.propertyFilePath)) {
	    this.propertyFile.store(fileOutputStream, null);
	} catch (IOException ex) {
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	    System.exit(1);
	}
    }
    
    private void loadPropertyFile() {
	try (FileInputStream fileInputStream = new FileInputStream(this.propertyFilePath)) {
	    this.propertyFile.load(fileInputStream);
	}catch(FileNotFoundException ex){
	    try{
		propertyFile.setProperty("PORT_FLY", "26050");
		propertyFile.setProperty("IP_UDP", "224.0.0.1");
		propertyFile.setProperty("PORT_CHAT", "26051");
		propertyFile.setProperty("SEPARATOR", "#");
		propertyFile.setProperty("FIN_TRAME", "&FINI");

		propertyFile.setProperty("DB_IP_ADDRESS", "127.0.0.1");
		propertyFile.setProperty("DB_PORT", "3306");
		propertyFile.setProperty("DB_SCHEMA", "BD_AIRPORT");
		propertyFile.setProperty("DB_LOGIN", "LaboReseaux");
		propertyFile.setProperty("DB_PASSWORD", "mysql");

		try (FileOutputStream fileOutputStream = new FileOutputStream(this.propertyFilePath)) {
		    propertyFile.store(fileOutputStream, null);
		}
	    } catch (IOException ex1) {
		Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
	    }
	} catch (IOException ex) {
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	    System.exit(1);
	}

	this.PORT_FLY = propertyFile.getProperty("PORT_FLY", "26050");
	this.IP_UDP = propertyFile.getProperty("IP_UDP", "224.0.0.1");
	this.PORT_CHAT = propertyFile.getProperty("PORT_CHAT", "26051");
	this.SEPARATOR = propertyFile.getProperty("SEPARATOR", "#");
	this.FIN_TRAME = propertyFile.getProperty("FIN_TRAME", "&FINI");

	this.DB_IP = propertyFile.getProperty("DB_IP_ADDRESS", "127.0.0.1");
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
	    if(value != null)
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

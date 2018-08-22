package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import generic.client.IConsoleClient;
import generic.frame.login.ALoginFrameParent;
import generic.frame.login.ConnectionException;
import network.client.iacop.ReceptionMessageThread;
import network.communication.Communication;
import network.communication.Message;
import network.communication.MulticastCommunication;
import network.communication.MulticastCommunicationException;
import network.communication.communicationException;
import network.crypto.AEncryption;
import network.protocole.iacop.reponse.ReponseLoginGroup;
import network.protocole.iacop.requete.RequeteLoginGroup;

public class Main extends ALoginFrameParent implements IConsoleClient{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final String separator = "#";
    private final String finTrame = "&FINI";
    private LinkedList<Message> messageList;
    private DefaultListModel<Message> defaultListModel;
    private ReceptionMessageThread threadReceptionMessage;
    private MulticastCommunication communicatorMulti;
    private String chat_IP;
    private int chat_port;

    private javax.swing.JButton jButtonEnvoyer;
    private javax.swing.ButtonGroup jButtonGroupFiltres;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabelFiltre;
    private javax.swing.JLabel jLabelMessage;
    private javax.swing.JLabel jLabelNouveauMessage;
    private javax.swing.JLabel jLabelTitleListeMessage;
    private javax.swing.JLabel jLabelTypeNvMessage;
    private javax.swing.JList<Message> jListMessages;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemDeconnexion;
    private javax.swing.JMenu jMenuProfil;
    private javax.swing.JRadioButton jRadioButtonFiltreInfos;
    private javax.swing.JRadioButton jRadioButtonFiltreQuestion;
    private javax.swing.JRadioButton jRadioButtonFiltreReponse;
    private javax.swing.JRadioButton jRadioButtonFiltreTous;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextaAreaMessageAEnvoyer;

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
	this.jComboBoxType.addItem("Question");
	this.jComboBoxType.addItem("Réponse");
	this.jComboBoxType.addItem("Évenement");
	this.jLabelMessage.setText("");

	setAdresseIp("10.59.22.45");
	setPort("26050");
	setLogin("laurent");
	setPwd("password");

	showConnectionDialog();

    }

    private void initComponents() {

	jButtonGroupFiltres = new javax.swing.ButtonGroup();
	jScrollPane3 = new javax.swing.JScrollPane();
	jTextaAreaMessageAEnvoyer = new javax.swing.JTextArea();
	jButtonEnvoyer = new javax.swing.JButton();
	jLabelMessage = new javax.swing.JLabel();
	jComboBoxType = new JComboBox<String>();
	jLabelTitleListeMessage = new javax.swing.JLabel();
	jLabelFiltre = new javax.swing.JLabel();
	jRadioButtonFiltreTous = new javax.swing.JRadioButton();
	jRadioButtonFiltreQuestion = new javax.swing.JRadioButton();
	jRadioButtonFiltreReponse = new javax.swing.JRadioButton();
	jRadioButtonFiltreInfos = new javax.swing.JRadioButton();
	jLabelTypeNvMessage = new javax.swing.JLabel();
	jLabelNouveauMessage = new javax.swing.JLabel();
	jScrollPane1 = new javax.swing.JScrollPane();
	jListMessages = new javax.swing.JList<>();
	jMenuBar1 = new javax.swing.JMenuBar();
	jMenuProfil = new javax.swing.JMenu();
	jMenuItemDeconnexion = new javax.swing.JMenuItem();

	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	addWindowListener(new java.awt.event.WindowAdapter() {
	    public void windowClosing(java.awt.event.WindowEvent evt) {
		formWindowClosing(evt);
	    }
	});

	jTextaAreaMessageAEnvoyer.setColumns(20);
	jTextaAreaMessageAEnvoyer.setRows(5);
	jScrollPane3.setViewportView(jTextaAreaMessageAEnvoyer);

	jButtonEnvoyer.setText("Envoyer");
	jButtonEnvoyer.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonEnvoyerActionPerformed(evt);
	    }
	});

	jLabelMessage.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
	jLabelMessage.setForeground(new java.awt.Color(0, 153, 0));
	jLabelMessage.setText("jLabel1");

	jLabelTitleListeMessage.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
	jLabelTitleListeMessage.setText("Chat : ");

	jLabelFiltre.setText("Affichez : ");

	jButtonGroupFiltres.add(jRadioButtonFiltreTous);
	jRadioButtonFiltreTous.setMnemonic('1');
	jRadioButtonFiltreTous.setSelected(true);
	jRadioButtonFiltreTous.setText("Tous les messages");
	jRadioButtonFiltreTous.addChangeListener(new javax.swing.event.ChangeListener() {
	    public void stateChanged(javax.swing.event.ChangeEvent evt) {
		jRadioButtonFiltreTousjRadioButtonFiltreStateChanged(evt);
	    }
	});

	jButtonGroupFiltres.add(jRadioButtonFiltreQuestion);
	jRadioButtonFiltreQuestion.setMnemonic('2');
	jRadioButtonFiltreQuestion.setText("Les questions");
	jRadioButtonFiltreQuestion.addChangeListener(new javax.swing.event.ChangeListener() {
	    public void stateChanged(javax.swing.event.ChangeEvent evt) {
		jRadioButtonFiltreQuestionjRadioButtonFiltreStateChanged(evt);
	    }
	});

	jButtonGroupFiltres.add(jRadioButtonFiltreReponse);
	jRadioButtonFiltreReponse.setMnemonic('3');
	jRadioButtonFiltreReponse.setText("Les réponses");
	jRadioButtonFiltreReponse.addChangeListener(new javax.swing.event.ChangeListener() {
	    public void stateChanged(javax.swing.event.ChangeEvent evt) {
		jRadioButtonFiltreReponsejRadioButtonFiltreStateChanged(evt);
	    }
	});

	jButtonGroupFiltres.add(jRadioButtonFiltreInfos);
	jRadioButtonFiltreInfos.setMnemonic('4');
	jRadioButtonFiltreInfos.setText("Les messages Event");
	jRadioButtonFiltreInfos.addChangeListener(new javax.swing.event.ChangeListener() {
	    public void stateChanged(javax.swing.event.ChangeEvent evt) {
		jRadioButtonFiltreInfosjRadioButtonFiltreStateChanged(evt);
	    }
	});

	jLabelTypeNvMessage.setText("Type du message :");

	jLabelNouveauMessage.setText("Nouveau message :");

	jListMessages.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	jScrollPane1.setViewportView(jListMessages);

	jMenuProfil.setText("Profil");

	jMenuItemDeconnexion.setText("Déconnexion");
	jMenuItemDeconnexion.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jMenuItemDeconnexionActionPerformed(evt);
	    }
	});
	jMenuProfil.add(jMenuItemDeconnexion);

	jMenuBar1.add(jMenuProfil);

	setJMenuBar(jMenuBar1);

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
			.addContainerGap()
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(jLabelNouveauMessage)
							.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 413, Short.MAX_VALUE)
							.addComponent(jLabelTypeNvMessage)
							.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(jScrollPane3))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(jButtonEnvoyer, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGroup(layout.createSequentialGroup()
					.addComponent(jLabelMessage)
					.addGap(0, 0, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jRadioButtonFiltreInfos)
						.addComponent(jRadioButtonFiltreReponse)
						.addComponent(jRadioButtonFiltreTous)
						.addComponent(jRadioButtonFiltreQuestion)
						.addComponent(jLabelFiltre))
					.addGap(18, 18, 18)
					.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(jLabelTitleListeMessage)
							.addGap(0, 0, Short.MAX_VALUE))
						.addComponent(jScrollPane1))))
			.addContainerGap())
		);
	layout.setVerticalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
			.addGap(29, 29, 29)
			.addComponent(jLabelTitleListeMessage)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(jLabelFiltre)
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(jRadioButtonFiltreTous)
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(jRadioButtonFiltreQuestion)
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(jRadioButtonFiltreReponse)
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(jRadioButtonFiltreInfos))
				.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
			.addComponent(jLabelMessage)
			.addGap(18, 18, 18)
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
				.addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(jLabelNouveauMessage)
				.addComponent(jLabelTypeNvMessage))
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addGap(7, 7, 7))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
					.addComponent(jButtonEnvoyer)
					.addGap(55, 55, 55))))
		);

	pack();
	setLocationRelativeTo(null);
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
	try {
	    disconnect();
	    this.communicatorMulti.close();
	} catch (ConnectionException ex) {
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	}

	this.dispose();
	System.exit(0);
    }      

    private void jButtonEnvoyerActionPerformed(java.awt.event.ActionEvent evt) {                                               
	this.jLabelMessage.setText("");
	jTextaAreaMessageAEnvoyer.requestFocus();
	if(jTextaAreaMessageAEnvoyer.getText().isEmpty()) {
	    this.jLabelMessage.setText("Erreur, le message à envoyer est vide!");
	    this.jLabelMessage.setForeground(Color.RED);
	    return;
	}

	boolean idIsUsed = true;
	String tag = "";
	Message message = null;
	String msg = jTextaAreaMessageAEnvoyer.getText();

	switch(jComboBoxType.getSelectedIndex()){
	case 0: //Questions
	    // on génère un tag pour la nouvelle question
	    while(idIsUsed) {
		idIsUsed =  false;
		Random rand = new Random();
		tag = Integer.toString(rand.nextInt(99999));

		// On verifie qu'une autre question n'est pas ouverte à ce tag
		for(int i = 2; i < this.messageList.size() && !idIsUsed; i++)
		    if(this.messageList.get(i).getTag().equals(tag))
			idIsUsed = true;
	    }

	    message = new Message(Message.UDP.POST_QUESTION, getLogin(), tag, msg, AEncryption.hash(msg));
	    break;
	case 1: //Réponses
	    if(jListMessages.getSelectedValues().length==0 || jListMessages.getSelectedValues()[0]==null){
		jLabelMessage.setText("Erreur, cliquez sur la question à laquelle vous voulez répondre!");
		jLabelMessage.setForeground(Color.red);
		return;
	    }

	    Message msgSelectionne;
	    msgSelectionne = (Message) jListMessages.getSelectedValues()[0];
	    if(msgSelectionne.getMessageType()!= Message.UDP.POST_QUESTION){
		jLabelMessage.setText("Erreur, le message selectionné n'est pas une question!");
		jLabelMessage.setForeground(Color.red);
		return;
	    }

	    if(msgSelectionne.getDigest() != AEncryption.hash(msgSelectionne.getMessage())){
		jLabelMessage.setText("Erreur, le digest de la question selectionnée n'est pas cohérent avec celui calculé!");
		jLabelMessage.setForeground(Color.red);
		return;
	    }

	    message = new Message(Message.UDP.ANSWER_QUESTION, getLogin(), msgSelectionne.getTag(), msg, msgSelectionne.getDigest());
	    break;
	case 2: //Events
	    message = new Message(Message.UDP.POST_EVENT, getLogin(), "Info", msg, AEncryption.hash(msg));
	    break;
	}

	try {
	    this.communicatorMulti.send(message);
	}
	catch (MulticastCommunicationException ex) {
	    jLabelMessage.setText("Une erreur est survenue lors de l'envoi. reessayez, et si ça continue, redemarrez");
	    jLabelMessage.setForeground(Color.RED);
	    return;
	}

	jLabelMessage.setText("Le message a bien été envoyé");
	jLabelMessage.setForeground(Color.GREEN);

	jComboBoxType.setSelectedIndex(0);
	jListMessages.clearSelection();
	jTextaAreaMessageAEnvoyer.setText("");
    }                                              

    private void change(){
	this.defaultListModel.removeAllElements();

	switch(jButtonGroupFiltres.getSelection().getMnemonic()){
	case '1': //Tous
	    for(Message msg : messageList){
		this.defaultListModel.addElement(msg);
	    }
	    break;
	case '2': //Questions
	    for(Message msg : messageList){
		if(msg.getMessageType()== Message.UDP.POST_QUESTION)
		    this.defaultListModel.addElement(msg);
	    }
	    break;
	case '3': //Réponses
	    for(Message msg : messageList){
		if(msg.getMessageType()== Message.UDP.ANSWER_QUESTION)
		    this.defaultListModel.addElement(msg);
	    }
	    break;
	case '4': //Events
	    for(Message msg : messageList){
		if(msg.getMessageType()== Message.UDP.POST_EVENT)
		    this.defaultListModel.addElement(msg);
	    }
	    break;
	}
    }

    @Override
    protected void connect() throws ConnectionException {
	try{
	    Communication c = null;
	    try {
		Socket clientSocket = new Socket(this.getAdresseIp(), Integer.parseInt(this.getPort()));

		c = new Communication(clientSocket);

		double rand = Math.random();
		long time= (new Date()).getTime();
		//			byte[] digestedPassword = Encrypt.saltDigest(this.getPassword(), time, rand);
		byte[] digestedPassword = this.getPwd().getBytes();
		RequeteLoginGroup requete = new RequeteLoginGroup(this.getLogin(), digestedPassword, time, rand);

		c.send(requete.toNetworkString(separator));

		String[] split = c.receive().split(this.separator);

		ReponseLoginGroup reponse = ReponseLoginGroup.getInstance(split);
		if(reponse==null){
		    System.err.println("Error parsing answer");
		    throw new ConnectionException("Error parsing answer");
		}

		if(!reponse.isSuccessful()){
		    System.err.println(reponse.getErrorMessage());
		    throw new ConnectionException(reponse.getErrorMessage());
		}

		this.chat_IP = reponse.getIp();
		this.chat_port = reponse.getPort();
		defaultListModel = new DefaultListModel<Message>();
		this.messageList = new LinkedList<>();
		this.jListMessages.setModel(this.defaultListModel);

		try {
		    this.communicatorMulti = new MulticastCommunication(InetAddress.getLocalHost(), InetAddress.getByName(this.chat_IP), this.chat_port, this.separator, this.finTrame);
		    String msg = "vient de se connecter";
		    Message message = new Message(Message.UDP.POST_EVENT, getLogin(), "Info", msg, AEncryption.hash(msg));
		    this.communicatorMulti.send(message);

		    jLabelMessage.setText("Connexion réussie");
		    jLabelMessage.setForeground(Color.GREEN);

		    this.threadReceptionMessage = new ReceptionMessageThread(this, this.communicatorMulti, this.messageList);
		    this.threadReceptionMessage.start();
		    this.setVisible(true);
		} catch (MulticastCommunicationException ex) {
		    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		    System.err.println(ex.getMessage());
		    this.dispose();
		}
	    } catch (NumberFormatException | IOException /*| EncryptionException */ ex) {
		throw new ConnectionException(ex.getMessage());
	    }
	}catch(communicationException ex){
	    throw new ConnectionException(ex.getMessage());
	}
    }

    @Override
    protected void disconnect() throws ConnectionException {
	String msg = "vient de se deconnecter";
	Message message = new Message(Message.UDP.POST_EVENT, getLogin(), "Info", msg, AEncryption.hash(msg));

	try {
	    this.communicatorMulti.send(message);
	}
	catch (MulticastCommunicationException ex) {
	    System.err.println("jMenuItemDeconnexionActionPerformed->MulticastCommunicatorException : " + ex.getMessage());
	}

	this.messageList.clear();
	this.jTextaAreaMessageAEnvoyer.setText("");
	this.defaultListModel.clear();
	this.jListMessages.invalidate();
	this.threadReceptionMessage.doStop();
	this.communicatorMulti.close();
	this.setVisible(false);
	showConnectionDialog();

    }

    private void jRadioButtonFiltreTousjRadioButtonFiltreStateChanged(javax.swing.event.ChangeEvent evt) {                                                                      
	change();
    }                                                                     

    private void jRadioButtonFiltreQuestionjRadioButtonFiltreStateChanged(javax.swing.event.ChangeEvent evt) {                                                                          
	change();
    }                                                                         

    private void jRadioButtonFiltreReponsejRadioButtonFiltreStateChanged(javax.swing.event.ChangeEvent evt) {                                                                         
	change();
    }                                                                        

    private void jRadioButtonFiltreInfosjRadioButtonFiltreStateChanged(javax.swing.event.ChangeEvent evt) {                                                                       
	change();
    }                                                                      

    private void jMenuItemDeconnexionActionPerformed(java.awt.event.ActionEvent evt) {                                                     
	try {
	    disconnect();
	} catch (ConnectionException ex) {
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void refreshMessageList() {
	boolean add = false;
	Message message = null;

	if(!this.messageList.isEmpty()){
	    message = this.messageList.get(this.messageList.size()-1);
	    System.out.println(jButtonGroupFiltres.getSelection().getMnemonic());
	    switch(jButtonGroupFiltres.getSelection().getMnemonic()){
	    case '1': //Tous
		add = true;
		break;
	    case '2': //Questions
		if(message.getMessageType()== Message.UDP.POST_QUESTION)
		    add = true;
		break;
	    case '3': //Réponses
		if(message.getMessageType()== Message.UDP.ANSWER_QUESTION)
		    add = true;
		break;
	    case '4': //Events
		if(message.getMessageType()== Message.UDP.POST_EVENT)
		    add = true;
		break;
	    }
	}

	if(add)
	    this.defaultListModel.addElement(message);
	
    }  

}

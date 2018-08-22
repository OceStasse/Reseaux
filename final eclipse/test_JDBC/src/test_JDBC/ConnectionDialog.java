package test_JDBC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.utilities.Access;

public class ConnectionDialog extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private MainFrame parent;
    private javax.swing.JPanel PanelMysql;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonConnect;
    private javax.swing.JButton buttonTest;
    private javax.swing.JLabel labelDatabase;
    private javax.swing.JLabel labelIpAddress;
    private javax.swing.JLabel labelLogin;
    private javax.swing.JLabel labelPasswordMysql;
    private javax.swing.JLabel labelPasswordOracle;
    private javax.swing.JLabel labelPort;
    private javax.swing.JLabel labelSchema;
    private javax.swing.JLabel labelSid;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelStatusMessage;
    private javax.swing.JPanel panelOracle;
    private javax.swing.JPasswordField passwordFieldMysql;
    private javax.swing.JPasswordField passwordFieldOracle;
    private javax.swing.JTabbedPane tabbedPanesDatabaseType;
    private javax.swing.JTextField textFieldDatabase;
    private javax.swing.JTextField textFieldIpAddress;
    private javax.swing.JTextField textFieldLogin;
    private javax.swing.JTextField textFieldPort;
    private javax.swing.JTextField textFieldSchema;
    private javax.swing.JTextField textFieldSid;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	try {
	    ConnectionDialog dialog = new ConnectionDialog();
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    dialog.setVisible(true);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    private Access createDatabaseConnection() throws ClassNotFoundException, SQLException, StringIndexOutOfBoundsException {
        if(tabbedPanesDatabaseType.getSelectedIndex() == 0)
        return new Access( Access.dataBaseType.ORACLE_THIN,
                                    this.textFieldIpAddress.getText(),
                                    this.textFieldPort.getText(),
                                    this.textFieldSid.getText(),
                                    this.textFieldSchema.getText(),
                                    String.valueOf(this.passwordFieldOracle.getPassword()));
        else
            return new Access(Access.dataBaseType.MYSQL,
                                        this.textFieldIpAddress.getText(),
                                        this.textFieldPort.getText(),
                                        this.textFieldDatabase.getText(),
                                        this.textFieldLogin.getText(),
                                        String.valueOf(this.passwordFieldMysql.getPassword()));
    }
    
    public ConnectionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.parent = (MainFrame) parent;
        initComponents();
        this.textFieldIpAddress.setText("127.0.0.1");
        this.textFieldPort.setText("3306");
        
        this.textFieldSid.setText("orcl12c");
        this.textFieldSchema.setText("LaboReseaux");
        this.passwordFieldOracle.setText("oracle");
        
        this.textFieldDatabase.setText("BD_AIRPORT");
        this.textFieldLogin.setText("LaboReseaux");
        this.passwordFieldMysql.setText("mysql");
    }
    /**
     * Create the dialog.
     */
    public ConnectionDialog() {
	setBounds(100, 100, 450, 300);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setLayout(new FlowLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	{
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	    {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	    }
	}
    }
    
    private void initComponents() {

        tabbedPanesDatabaseType = new javax.swing.JTabbedPane();
        panelOracle = new javax.swing.JPanel();
        labelSid = new javax.swing.JLabel();
        textFieldSid = new javax.swing.JTextField();
        labelSchema = new javax.swing.JLabel();
        textFieldSchema = new javax.swing.JTextField();
        labelPasswordOracle = new javax.swing.JLabel();
        passwordFieldOracle = new javax.swing.JPasswordField();
        PanelMysql = new javax.swing.JPanel();
        labelDatabase = new javax.swing.JLabel();
        textFieldDatabase = new javax.swing.JTextField();
        labelLogin = new javax.swing.JLabel();
        textFieldLogin = new javax.swing.JTextField();
        labelPasswordMysql = new javax.swing.JLabel();
        passwordFieldMysql = new javax.swing.JPasswordField();
        labelIpAddress = new javax.swing.JLabel();
        textFieldIpAddress = new javax.swing.JTextField();
        labelPort = new javax.swing.JLabel();
        textFieldPort = new javax.swing.JTextField();
        buttonTest = new javax.swing.JButton();
        labelStatus = new javax.swing.JLabel();
        labelStatusMessage = new javax.swing.JLabel();
        buttonConnect = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelOracle.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelSid.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        labelSid.setText("SID :");

        textFieldSid.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textFieldSid.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        labelSchema.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        labelSchema.setText("Schema:");

        textFieldSchema.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textFieldSchema.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        labelPasswordOracle.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        labelPasswordOracle.setText("Password :");

        javax.swing.GroupLayout panelOracleLayout = new javax.swing.GroupLayout(panelOracle);
        panelOracle.setLayout(panelOracleLayout);
        panelOracleLayout.setHorizontalGroup(
            panelOracleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOracleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOracleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelOracleLayout.createSequentialGroup()
                        .addComponent(labelSid)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFieldSid, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelOracleLayout.createSequentialGroup()
                        .addComponent(labelSchema)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldSchema, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelOracleLayout.createSequentialGroup()
                        .addComponent(labelPasswordOracle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passwordFieldOracle, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        panelOracleLayout.setVerticalGroup(
            panelOracleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOracleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOracleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSid)
                    .addComponent(textFieldSid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelOracleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSchema)
                    .addComponent(textFieldSchema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelOracleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPasswordOracle)
                    .addComponent(passwordFieldOracle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        tabbedPanesDatabaseType.addTab("Oracle", panelOracle);

        PanelMysql.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelDatabase.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        labelDatabase.setText("Database:");

        textFieldDatabase.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

        labelLogin.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        labelLogin.setText("Login : ");
        labelLogin.setToolTipText("");

        textFieldLogin.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textFieldLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        labelPasswordMysql.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        labelPasswordMysql.setText("Password :");

        javax.swing.GroupLayout PanelMysqlLayout = new javax.swing.GroupLayout(PanelMysql);
        PanelMysql.setLayout(PanelMysqlLayout);
        PanelMysqlLayout.setHorizontalGroup(
            PanelMysqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMysqlLayout.createSequentialGroup()
                .addGroup(PanelMysqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMysqlLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelPasswordMysql)
                        .addGap(12, 12, 12)
                        .addComponent(passwordFieldMysql, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelMysqlLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(labelLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelMysqlLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(labelDatabase)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        PanelMysqlLayout.setVerticalGroup(
            PanelMysqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMysqlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelMysqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDatabase))
                .addGap(12, 12, 12)
                .addGroup(PanelMysqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelMysqlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPasswordMysql)
                    .addComponent(passwordFieldMysql, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        tabbedPanesDatabaseType.addTab("MySQL", PanelMysql);

        labelIpAddress.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        labelIpAddress.setText("IP address:");

        textFieldIpAddress.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textFieldIpAddress.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        labelPort.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        labelPort.setText("Port :");

        textFieldPort.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textFieldPort.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        buttonTest.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        buttonTest.setText("Test");
        buttonTest.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        buttonTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTestActionPerformed(evt);
            }
        });

        labelStatus.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        labelStatus.setText("Status:");

        labelStatusMessage.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        labelStatusMessage.setToolTipText("");

        buttonConnect.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        buttonConnect.setText("Connect");
        buttonConnect.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        buttonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConnectActionPerformed(evt);
            }
        });

        buttonCancel.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        buttonCancel.setText("Cancel");
        buttonCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
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
                        .addComponent(labelIpAddress)
                        .addGap(18, 18, 18)
                        .addComponent(textFieldIpAddress)
                        .addGap(18, 18, 18)
                        .addComponent(labelPort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(buttonTest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelStatusMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(buttonConnect)
                .addGap(150, 150, 150)
                .addComponent(buttonCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tabbedPanesDatabaseType)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIpAddress)
                    .addComponent(textFieldIpAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPort)
                    .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tabbedPanesDatabaseType, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonTest)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelStatusMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonConnect)
                    .addComponent(buttonCancel))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }
    
    private void buttonTestActionPerformed(java.awt.event.ActionEvent evt) {                                           
        this.labelStatusMessage.setText("");
        try{
            Access database = createDatabaseConnection();
            database.connect();
            this.labelStatusMessage.setForeground(new Color(0,128,0));
            this.labelStatusMessage.setText("Connection established!");
            database.disconnect();
        } catch (ClassNotFoundException | SQLException ex) {
            this.labelStatusMessage.setForeground(Color.RED);
            this.labelStatusMessage.setText("Could not establish a connection...");
        }
    }
    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {                                             
        this.setVisible(false);
        this.dispose();
    }
    
    private void buttonConnectActionPerformed(java.awt.event.ActionEvent evt) {                                              
        try {
            Access database = createDatabaseConnection();
            database.connect();
            this.parent.connect(database);
            this.setVisible(false);
            this.dispose();
        } catch (ClassNotFoundException | SQLException ex) {
            this.labelStatusMessage.setForeground(Color.RED);
            this.labelStatusMessage.setText("Could not establish a connection...");
        }
    }      
    
}

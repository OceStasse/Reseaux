package generic.frame.login;

import javax.swing.JDialog;
import java.awt.Color;

public class ConnectionDialog extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final ALoginFrameParent parent;

    public ConnectionDialog(ALoginFrameParent parent) {
        super(parent, true);
        initComponents();
        
        this.parent = parent;
        textFieldIpAddress.setText(this.parent.getAdresseIp());
        textFieldPort.setText(this.parent.getPort());
        textFieldLogin.setText(this.parent.getLogin());
        textFieldPassword.setText(this.parent.getPwd());
        
        labelConnectError.setForeground(Color.red);
    }
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonConnect;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelConnectError;
    private javax.swing.JLabel labelConnectToServer;
    private javax.swing.JLabel labelIpAddress;
    private javax.swing.JLabel labelLogin;
    private javax.swing.JLabel labelLoginCredentials;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelPort;
    private javax.swing.JTextField textFieldIpAddress;
    private javax.swing.JTextField textFieldLogin;
    private javax.swing.JTextField textFieldPassword;
    private javax.swing.JTextField textFieldPort;

   

    /**
     * Create the dialog.
     */
    public void initComponents() {
	labelConnectToServer = new javax.swing.JLabel();
        labelIpAddress = new javax.swing.JLabel();
        textFieldIpAddress = new javax.swing.JTextField();
        labelPort = new javax.swing.JLabel();
        textFieldPort = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        buttonConnect = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        labelLogin = new javax.swing.JLabel();
        textFieldLogin = new javax.swing.JTextField();
        labelPassword = new javax.swing.JLabel();
        textFieldPassword = new javax.swing.JTextField();
        labelLoginCredentials = new javax.swing.JLabel();
        labelConnectError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelConnectToServer.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelConnectToServer.setText("Connect to server :");

        labelIpAddress.setLabelFor(textFieldIpAddress);
        labelIpAddress.setText("IP address :");

        textFieldIpAddress.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textFieldIpAddress.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        labelPort.setLabelFor(textFieldPort);
        labelPort.setText("Port :");

        textFieldPort.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textFieldPort.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        labelLogin.setLabelFor(textFieldLogin);
        labelLogin.setText("Login :");

        labelPassword.setLabelFor(textFieldPassword);
        labelPassword.setText("Password :");

        labelLoginCredentials.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelLoginCredentials.setText("Login credentials :");

        labelConnectError.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelPort)
                                    .addComponent(labelIpAddress)
                                    .addComponent(labelLogin)
                                    .addComponent(labelPassword))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textFieldIpAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(labelConnectToServer)
                            .addComponent(labelLoginCredentials))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(buttonConnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addComponent(buttonCancel)
                        .addGap(35, 35, 35))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelConnectError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelConnectToServer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIpAddress)
                    .addComponent(textFieldIpAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPort)
                    .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelLoginCredentials)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLogin)
                    .addComponent(textFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPassword)
                    .addComponent(textFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(labelConnectError)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonConnect)
                    .addComponent(buttonCancel))
                .addContainerGap())
        );

        pack();
    }
    
    private void buttonConnectActionPerformed(java.awt.event.ActionEvent evt) {                                              
        //IPAddress ok?
        if(textFieldIpAddress.getText().isEmpty()){
            labelConnectError.setText("Error : IP address can't be empty");
            return;
        }
        
        //Port ok?
        if(textFieldPort.getText().isEmpty()){
            labelConnectError.setText("Error : Port can't be empty");
            return;
        }
        else{
            try{
                Integer.parseInt(textFieldPort.getText());
            } catch(NumberFormatException ex){
                labelConnectError.setText("Error : Port is not a number");
                return;
            }
        }
        
        //Login ok?
        if(textFieldLogin.getText().isEmpty()){
            labelConnectError.setText("Error : Login can't be empty");
            return;
        }

        this.parent.setAdresseIp(textFieldIpAddress.getText());
        this.parent.setPort(textFieldPort.getText());
        this.parent.setLogin(textFieldLogin.getText());
        this.parent.setPwd(textFieldPassword.getText());
        
        try{
            this.parent.connect();
            this.setVisible(false);
            this.dispose();
        } catch(ConnectionException ex){
            labelConnectError.setText(ex.getMessage());
        }
    }                                             

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {                                             
        this.setVisible(false);
        System.exit(0);
    }     
}

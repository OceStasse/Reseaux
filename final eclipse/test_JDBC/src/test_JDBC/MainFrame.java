package test_JDBC;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import database.utilities.Access;

public class MainFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Access database;
    private javax.swing.JButton buttonCountExecute;
    private javax.swing.JButton buttonFreeExecute;
    private javax.swing.JButton buttonSelectExecute;
    private javax.swing.JButton buttonUpdateExecute;
    private javax.swing.JComboBox<String> comboBoxCountFrom;
    private javax.swing.JComboBox<String> comboBoxSelectFrom;
    private javax.swing.JComboBox<String> comboBoxUpdateSet;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel labelCountFrom;
    private javax.swing.JLabel labelCountResult;
    private javax.swing.JLabel labelCountResultMessage;
    private javax.swing.JLabel labelCountSelect;
    private javax.swing.JLabel labelCountWhere;
    private javax.swing.JLabel labelFreeResult;
    private javax.swing.JLabel labelFreeResultMessage;
    private javax.swing.JLabel labelSelectFrom;
    private javax.swing.JLabel labelSelectSelect;
    private javax.swing.JLabel labelSelectWhere;
    private javax.swing.JLabel labelUpdateResult;
    private javax.swing.JLabel labelUpdateResultMessage;
    private javax.swing.JLabel labelUpdateSet;
    private javax.swing.JLabel labelUpdateUpdate;
    private javax.swing.JLabel labelUpdateWhere;
    private javax.swing.JMenu menuConnection;
    private javax.swing.JPanel panelCount;
    private javax.swing.JPanel panelFree;
    private javax.swing.JPanel panelSelect;
    private javax.swing.JPanel panelUpdate;
    private javax.swing.JScrollPane scrollPaneFree;
    private javax.swing.JScrollPane scrollPaneSelect;
    private javax.swing.JTabbedPane tabbedPaneRequestType;
    private javax.swing.JTable tableFreeResult;
    private javax.swing.JTable tableSelectResult;
    private javax.swing.JTextArea textAreaFree;
    private javax.swing.JTextField textFieldCountSelect;
    private javax.swing.JTextField textFieldCountWhere;
    private javax.swing.JTextField textFieldSelectSelect;
    private javax.swing.JTextField textFieldSelectWhere;
    private javax.swing.JTextField textFieldUpdateSet;
    private javax.swing.JTextField textFieldUpdateWhere;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    MainFrame frame = new MainFrame();
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
    public MainFrame() {
	initComponents();
        this.database = null;
        this.tabbedPaneRequestType.setVisible(false);
        this.scrollPaneFree.setVisible(false);
        this.labelFreeResultMessage.setVisible(false);
    }
    
    private void initComponents()
    {
	tabbedPaneRequestType = new javax.swing.JTabbedPane();
        panelSelect = new javax.swing.JPanel();
        labelSelectSelect = new javax.swing.JLabel();
        textFieldSelectSelect = new javax.swing.JTextField();
        labelSelectFrom = new javax.swing.JLabel();
        comboBoxSelectFrom = new javax.swing.JComboBox<>();
        labelSelectWhere = new javax.swing.JLabel();
        textFieldSelectWhere = new javax.swing.JTextField();
        scrollPaneSelect = new javax.swing.JScrollPane();
        tableSelectResult = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        buttonSelectExecute = new javax.swing.JButton();
        panelCount = new javax.swing.JPanel();
        labelCountSelect = new javax.swing.JLabel();
        textFieldCountSelect = new javax.swing.JTextField();
        labelCountFrom = new javax.swing.JLabel();
        comboBoxCountFrom = new javax.swing.JComboBox<>();
        labelCountWhere = new javax.swing.JLabel();
        textFieldCountWhere = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        labelCountResult = new javax.swing.JLabel();
        labelCountResultMessage = new javax.swing.JLabel();
        buttonCountExecute = new javax.swing.JButton();
        panelUpdate = new javax.swing.JPanel();
        labelUpdateSet = new javax.swing.JLabel();
        textFieldUpdateSet = new javax.swing.JTextField();
        labelUpdateUpdate = new javax.swing.JLabel();
        comboBoxUpdateSet = new javax.swing.JComboBox<>();
        labelUpdateWhere = new javax.swing.JLabel();
        textFieldUpdateWhere = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        labelUpdateResult = new javax.swing.JLabel();
        labelUpdateResultMessage = new javax.swing.JLabel();
        buttonUpdateExecute = new javax.swing.JButton();
        panelFree = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaFree = new javax.swing.JTextArea();
        jSeparator4 = new javax.swing.JSeparator();
        labelFreeResult = new javax.swing.JLabel();
        buttonFreeExecute = new javax.swing.JButton();
        scrollPaneFree = new javax.swing.JScrollPane();
        tableFreeResult = new javax.swing.JTable();
        labelFreeResultMessage = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuConnection = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelSelectSelect.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelSelectSelect.setText("SELECT");

        labelSelectFrom.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelSelectFrom.setText("FROM");

        labelSelectWhere.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelSelectWhere.setText("WHERE");

        tableSelectResult.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollPaneSelect.setViewportView(tableSelectResult);

        buttonSelectExecute.setText("execute");
        buttonSelectExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSelectExecuteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSelectLayout = new javax.swing.GroupLayout(panelSelect);
        panelSelect.setLayout(panelSelectLayout);
        panelSelectLayout.setHorizontalGroup(
            panelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneSelect, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                    .addGroup(panelSelectLayout.createSequentialGroup()
                        .addGroup(panelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelSelectSelect)
                            .addComponent(labelSelectFrom)
                            .addComponent(labelSelectWhere))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSelectLayout.createSequentialGroup()
                                .addComponent(comboBoxSelectFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonSelectExecute))
                            .addGroup(panelSelectLayout.createSequentialGroup()
                                .addGroup(panelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textFieldSelectWhere, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                                    .addComponent(textFieldSelectSelect))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addComponent(jSeparator1)
        );
        panelSelectLayout.setVerticalGroup(
            panelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSelectSelect)
                    .addComponent(textFieldSelectSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxSelectFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSelectFrom)
                    .addComponent(buttonSelectExecute))
                .addGap(18, 18, 18)
                .addGroup(panelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldSelectWhere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSelectWhere))
                .addGap(12, 12, 12)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPaneSelect, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneRequestType.addTab("SELECT", panelSelect);

        labelCountSelect.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelCountSelect.setText("SELECT");

        textFieldCountSelect.setText("COUNT(*)");
        textFieldCountSelect.setEnabled(false);

        labelCountFrom.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelCountFrom.setText("FROM");

        labelCountWhere.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelCountWhere.setText("WHERE");

        labelCountResult.setText("Result :");

        buttonCountExecute.setText("execute");
        buttonCountExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCountExecuteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCountLayout = new javax.swing.GroupLayout(panelCount);
        panelCount.setLayout(panelCountLayout);
        panelCountLayout.setHorizontalGroup(
            panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(panelCountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCountLayout.createSequentialGroup()
                        .addGroup(panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelCountSelect)
                            .addComponent(labelCountFrom)
                            .addComponent(labelCountWhere))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboBoxCountFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCountResultMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldCountSelect, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                            .addComponent(textFieldCountWhere))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(buttonCountExecute))
                    .addGroup(panelCountLayout.createSequentialGroup()
                        .addComponent(labelCountResult)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCountLayout.setVerticalGroup(
            panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCountSelect)
                    .addComponent(textFieldCountSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxCountFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCountFrom)
                    .addComponent(buttonCountExecute))
                .addGap(18, 18, 18)
                .addGroup(panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldCountWhere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCountWhere))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCountResult)
                    .addComponent(labelCountResultMessage))
                .addContainerGap(148, Short.MAX_VALUE))
        );

        tabbedPaneRequestType.addTab("COUNT", panelCount);

        labelUpdateSet.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelUpdateSet.setText("SET");

        labelUpdateUpdate.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelUpdateUpdate.setText("UPDATE");

        labelUpdateWhere.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelUpdateWhere.setText("WHERE");

        labelUpdateResult.setText("Result :");

        labelUpdateResultMessage.setToolTipText("");

        buttonUpdateExecute.setText("execute");
        buttonUpdateExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUpdateExecuteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelUpdateLayout = new javax.swing.GroupLayout(panelUpdate);
        panelUpdate.setLayout(panelUpdateLayout);
        panelUpdateLayout.setHorizontalGroup(
            panelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(panelUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUpdateLayout.createSequentialGroup()
                        .addGroup(panelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelUpdateSet)
                            .addComponent(labelUpdateWhere))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textFieldUpdateSet, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                            .addComponent(textFieldUpdateWhere))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonUpdateExecute))
                    .addGroup(panelUpdateLayout.createSequentialGroup()
                        .addComponent(labelUpdateResult)
                        .addGap(18, 18, 18)
                        .addComponent(labelUpdateResultMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 411, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelUpdateLayout.createSequentialGroup()
                .addComponent(labelUpdateUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboBoxUpdateSet, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelUpdateLayout.setVerticalGroup(
            panelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxUpdateSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelUpdateUpdate))
                .addGap(18, 18, 18)
                .addGroup(panelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUpdateSet)
                    .addComponent(textFieldUpdateSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonUpdateExecute))
                .addGap(18, 18, 18)
                .addGroup(panelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldUpdateWhere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelUpdateWhere))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUpdateResult)
                    .addComponent(labelUpdateResultMessage))
                .addContainerGap(146, Short.MAX_VALUE))
        );

        tabbedPaneRequestType.addTab("UPDATE", panelUpdate);

        textAreaFree.setColumns(20);
        textAreaFree.setRows(5);
        jScrollPane2.setViewportView(textAreaFree);

        labelFreeResult.setText("Result :");

        buttonFreeExecute.setText("execute");
        buttonFreeExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFreeExecuteActionPerformed(evt);
            }
        });

        tableFreeResult.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollPaneFree.setViewportView(tableFreeResult);

        javax.swing.GroupLayout panelFreeLayout = new javax.swing.GroupLayout(panelFree);
        panelFree.setLayout(panelFreeLayout);
        panelFreeLayout.setHorizontalGroup(
            panelFreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator4)
            .addGroup(panelFreeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFreeLayout.createSequentialGroup()
                        .addComponent(labelFreeResult)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelFreeResultMessage)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelFreeLayout.createSequentialGroup()
                        .addGroup(panelFreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFreeLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addComponent(buttonFreeExecute))
                            .addComponent(scrollPaneFree))
                        .addContainerGap())))
        );
        panelFreeLayout.setVerticalGroup(
            panelFreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFreeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFreeLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFreeLayout.createSequentialGroup()
                        .addComponent(buttonFreeExecute)
                        .addGap(47, 47, 47)))
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFreeResult)
                    .addComponent(labelFreeResultMessage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneFree, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneRequestType.addTab("Free choice", panelFree);

        menuConnection.setText("Connect");
        menuConnection.setToolTipText("");
        menuConnection.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuConnectionMouseClicked(evt);
            }
        });
        jMenuBar1.add(menuConnection);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPaneRequestType)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPaneRequestType)
                .addContainerGap())
        );

        tabbedPaneRequestType.getAccessibleContext().setAccessibleName("tab");

        pack();
        setLocationRelativeTo(null);
    }
    
    private void menuConnectionMouseClicked(java.awt.event.MouseEvent evt) {                                            
        if(database == null){
            System.out.println("connecting...");
            ConnectionDialog connectionDialog = new ConnectionDialog(this, true);
            connectionDialog.pack();
            connectionDialog.setLocationRelativeTo(this);
            connectionDialog.setVisible(true);
        }
        else{
            System.out.println("disconnecting...");
            database.disconnect();
            menuConnection.setText("Connect");
            database = null;
            this.tabbedPaneRequestType.setVisible(false);
            comboBoxSelectFrom.removeAllItems();
            comboBoxCountFrom.removeAllItems();
            comboBoxUpdateSet.removeAllItems();
        }
    } 
    
    private void buttonCountExecuteActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        StringBuilder builder = new StringBuilder();
        
        builder.append("SELECT ");
        builder.append(this.textFieldCountSelect.getText());
        
        builder.append(" FROM ");
        builder.append(this.comboBoxCountFrom.getSelectedItem().toString());
        if(!this.textFieldCountWhere.getText().isEmpty()){
            builder.append(" WHERE ");
            builder.append(this.textFieldCountWhere.getText());
        }
        
        try {
            System.out.println(builder.toString());
            ResultSet resultSet = this.database.executeQuery(builder.toString());
            resultSet.next();
            this.labelCountResultMessage.setText(String.valueOf(resultSet.getInt(1)));
        } catch (SQLException ex) {
            this.labelCountResultMessage.setText(ex.getMessage());
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void buttonSelectExecuteActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        DefaultTableModel dtm = (DefaultTableModel) this.tableSelectResult.getModel();
        dtm.setRowCount(0);
        
        StringBuilder builder = new StringBuilder();
        
        builder.append("SELECT ");
        if(this.textFieldSelectSelect.getText().isEmpty())
            builder.append("*");
        else
            builder.append(this.textFieldSelectSelect.getText());
        
        builder.append(" FROM ");
        builder.append(this.comboBoxSelectFrom.getSelectedItem().toString());
        
        if(!this.textFieldSelectWhere.getText().isEmpty()){
            builder.append(" WHERE ");
            builder.append(this.textFieldSelectWhere.getText());
        }
        
        try {
            System.err.println(builder.toString());
            ResultSet cursor = this.database.executeQuery(builder.toString());
            this.tableSelectResult.setModel(this.database.buildTableModel(cursor));
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void buttonUpdateExecuteActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        this.labelUpdateResultMessage.setText("");
        if(this.textFieldUpdateSet.getText().isEmpty())
            return;
        
        StringBuilder builder = new StringBuilder();
        
        builder.append("UPDATE ");
        builder.append(this.comboBoxUpdateSet.getSelectedItem().toString());
        
        builder.append(" SET ");
        builder.append(this.textFieldUpdateSet.getText());
        
        if(!this.textFieldUpdateWhere.getText().isEmpty()){
            builder.append(" WHERE ");
            builder.append(this.textFieldUpdateWhere.getText());
        }
        
        try {
            System.err.println(builder.toString());
            int nbLinesModified = this.database.executeUpdate(builder.toString());
            this.labelUpdateResultMessage.setText(String.valueOf(nbLinesModified));
            this.database.commit();
        } catch (SQLException | InterruptedException ex) {
            this.labelUpdateResultMessage.setText(ex.getMessage());
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void buttonFreeExecuteActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        DefaultTableModel dtm = (DefaultTableModel) this.tableFreeResult.getModel();
        dtm.setRowCount(0);
        this.labelFreeResultMessage.setText("");
        
        String statement = this.textAreaFree.getText().toLowerCase();
        try {
            if(statement.startsWith("select")){
                ResultSet resultSet = this.database.executeQuery(statement);
                this.tableFreeResult.setModel(this.database.buildTableModel(resultSet));
                this.scrollPaneFree.setVisible(true);
                this.labelFreeResultMessage.setVisible(false);
            }
            else if(statement.startsWith("update")){
                int nbLinesUpdated = this.database.executeUpdate(statement);
                this.database.commit();
                this.labelFreeResultMessage.setText(String.valueOf(nbLinesUpdated));
                this.labelFreeResultMessage.setVisible(true);
                this.scrollPaneFree.setVisible(false);
            }
            else{
                this.labelFreeResultMessage.setText("The statement should start with either Select or Update!");
                this.labelFreeResultMessage.setVisible(true);
                this.scrollPaneFree.setVisible(false);
            }
        } catch (SQLException | InterruptedException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void connect(Access newAccess){
        if(this.database!=null)
            this.database.disconnect();

        this.database = newAccess;
        
        if(newAccess!=null){
            this.tabbedPaneRequestType.setVisible(true);
            try {
                ArrayList<String> tableNames;
                tableNames= database.getTableNames();
                for(String name : tableNames){
                    comboBoxSelectFrom.addItem(name);
                    comboBoxCountFrom.addItem(name);
                    comboBoxUpdateSet.addItem(name);
                }
                comboBoxSelectFrom.setSelectedIndex(-1);
                comboBoxUpdateSet.setSelectedIndex(-1);
                comboBoxCountFrom.setSelectedIndex(-1);
                menuConnection.setText("Disconnect");
            } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
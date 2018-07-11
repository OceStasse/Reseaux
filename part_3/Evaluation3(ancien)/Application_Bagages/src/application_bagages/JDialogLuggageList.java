package application_bagages;

import lugap.requete.RequeteLUGAP_updateFieldComments;
import lugap.requete.RequeteLUGAP_updateFieldCheckedByCustoms;
import lugap.requete.RequeteLUGAP_updateFieldLoaded;
import communicator.Communicator;
import communicator.CommunicatorException;
import entities.Flight;
import entities.Luggage;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import lugap.reponse.ReponseLUGAP_getLuggages;
import lugap.reponse.ReponseLUGAP_updateField;
import lugap.requete.RequeteLUGAP_getLuggages;
import lugap.requete.RequeteLUGAP_updateFieldReceived;

public class JDialogLuggageList extends javax.swing.JDialog implements TableModelListener {
    private DefaultTableModel model = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column > 2;
        }
    };
    private final Flight flight;
    private final HashMap<String, Luggage> luggages;
    private final Communicator communicator;
    private static final String[] TABLEHEADER = {"Identifiant", "Poids", "Type", "Réceptionné (O/N)", "Chargé en soute (O/N/R)", "Vérifié par la douane (O/N)", "Remarques"};
    
    public JDialogLuggageList(JFrameFlightList parent, Communicator communicator, Flight flight) {
        super(parent, true);
        initComponents();
        
        this.model.setColumnIdentifiers(TABLEHEADER);
        this.model.addTableModelListener(this);
        tableLuggageList.setModel(model);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableLuggageList.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        tableLuggageList.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableLuggageList.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableLuggageList.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableLuggageList.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        
        tableLuggageList.getColumnModel().getColumn(0).setMaxWidth(350);
        tableLuggageList.getColumnModel().getColumn(0).setPreferredWidth(280);
        tableLuggageList.getColumnModel().getColumn(1).setMaxWidth(75);
        tableLuggageList.getColumnModel().getColumn(1).setPreferredWidth(60);
        tableLuggageList.getColumnModel().getColumn(2).setMaxWidth(75);
        tableLuggageList.getColumnModel().getColumn(2).setPreferredWidth(75);
        tableLuggageList.getColumnModel().getColumn(3).setMaxWidth(100);
        tableLuggageList.getColumnModel().getColumn(3).setMinWidth(30);
        tableLuggageList.getColumnModel().getColumn(3).setPreferredWidth(40);
        tableLuggageList.getColumnModel().getColumn(4).setMaxWidth(120);
        tableLuggageList.getColumnModel().getColumn(4).setMinWidth(30);
        tableLuggageList.getColumnModel().getColumn(4).setPreferredWidth(40);
        tableLuggageList.getColumnModel().getColumn(5).setMaxWidth(150);
        tableLuggageList.getColumnModel().getColumn(5).setMinWidth(30);
        tableLuggageList.getColumnModel().getColumn(5).setPreferredWidth(40);
        
        this.communicator = communicator;
        this.flight = flight;
        this.luggages = new HashMap<>();
        
        labelFlight.setText(flight.toString());
        fetchLuggages();
    }
    
    private void fetchLuggages(){
        try {
            communicator.SendSerializable(new RequeteLUGAP_getLuggages(flight));
            
            ReponseLUGAP_getLuggages reponse  = communicator.receiveSerializable(ReponseLUGAP_getLuggages.class);
            
            if(!reponse.isSuccessful()){
                System.err.println(reponse.getMessage());
                JOptionPane.showMessageDialog(this, reponse.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
            else{
                if(reponse.getLuggages().isEmpty()){
                    System.err.println("There are no luggages for this flight!");
                    JOptionPane.showMessageDialog(this, "There are no luggages for this flight!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                }
                
                for(Luggage luggage : reponse.getLuggages()){
                    Object[] objs = luggage.toArray();
                    model.addRow(objs);
                    this.luggages.put(luggage.toString(), luggage);
                }
            } 
            
        } catch (CommunicatorException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelBaggageDe = new javax.swing.JLabel();
        labelFlight = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableLuggageList = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        labelBaggageDe.setText("Baggages de :");

        labelFlight.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        tableLuggageList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tableLuggageList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelBaggageDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelFlight, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBaggageDe)
                    .addComponent(labelFlight))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        boolean found = false;
        for (Iterator iterator = this.luggages.values().iterator(); !found && iterator.hasNext();) {
             Luggage next = (Luggage) iterator.next();
            if(next.getLoaded() == 'N'){
                String msg = "One or more luggage(s) are still not loaded. Canceling closing the window.";
                System.err.println(msg);
                JOptionPane.showMessageDialog(this, msg, "?!?", JOptionPane.INFORMATION_MESSAGE);
                found = true;
            }
        }
        if(found)
            return;
        ((JFrameFlightList)getParent()).exitProcedure();
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelBaggageDe;
    private javax.swing.JLabel labelFlight;
    private javax.swing.JTable tableLuggageList;
    // End of variables declaration//GEN-END:variables

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        if(column != -1){
            DefaultTableModel dtm = (DefaultTableModel) e.getSource();

            String idLuggage = (String) dtm.getValueAt(row, 0);
            Luggage modifiedLuggage = this.luggages.get(idLuggage);

            ReponseLUGAP_updateField reponse;
            String str = (String) dtm.getValueAt(row, column);
            try {
                switch(column){
                    case 3: //Réceptionné (O/N)
                        char newReceived = str.charAt(0);
                        
                        if(str.length() !=1 || (newReceived!='O' && newReceived!='N')){
                            dtm.setValueAt(String.valueOf(modifiedLuggage.getReceived()), row, column);
                            JOptionPane.showMessageDialog(this, "Value must be either O or N.", "WARNING", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        
                        if(modifiedLuggage.getReceived() == newReceived)
                            break;
                        
                        this.communicator.SendSerializable(new RequeteLUGAP_updateFieldReceived(modifiedLuggage, newReceived));
                        reponse = this.communicator.receiveSerializable(ReponseLUGAP_updateField.class);
    
                        if(!reponse.isSuccessful()){
                            dtm.setValueAt(String.valueOf(modifiedLuggage.getReceived()), row, column);
                            System.err.println(reponse.getMessage());
                            JOptionPane.showMessageDialog(this, reponse.getMessage(), "WARNING", JOptionPane.WARNING_MESSAGE);
                        }
                        else
                            modifiedLuggage.setReceived(newReceived);
                    break;
                    case 4: //Chargé en soute (O/N/R)
                        char newLoaded = str.charAt(0);
                        
                        if(str.length() !=1 || (newLoaded!='O' && newLoaded!='N' && newLoaded!='R')){
                            dtm.setValueAt(String.valueOf(modifiedLuggage.getLoaded()), row, column);
                            JOptionPane.showMessageDialog(this, "Value must be either O, N or R.", "WARNING", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        
                        if(modifiedLuggage.getLoaded() == newLoaded)
                            break;

                        this.communicator.SendSerializable(new RequeteLUGAP_updateFieldLoaded(modifiedLuggage, newLoaded));
                        reponse = this.communicator.receiveSerializable(ReponseLUGAP_updateField.class);

                        if(!reponse.isSuccessful()){
                            dtm.setValueAt(String.valueOf(modifiedLuggage.getLoaded()), row, column);
                            System.err.println(reponse.getMessage());
                            JOptionPane.showMessageDialog(this, reponse.getMessage(), "WARNING", JOptionPane.WARNING_MESSAGE);
                        }
                        else
                            modifiedLuggage.setLoaded(newLoaded);
                    break;
                    case 5: //Vérifié par la douane (O/N)
                        char newCheckedByCustoms = str.charAt(0);
                        
                        if(str.length() !=1 || (newCheckedByCustoms!='O' && newCheckedByCustoms!='N')){
                            dtm.setValueAt(String.valueOf(modifiedLuggage.getLoaded()), row, column);
                            JOptionPane.showMessageDialog(this, "Value must be either O or N.", "WARNING", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                        
                        if(modifiedLuggage.getLoaded() == newCheckedByCustoms)
                            break;

                        this.communicator.SendSerializable(new RequeteLUGAP_updateFieldCheckedByCustoms(modifiedLuggage, newCheckedByCustoms));
                        reponse = this.communicator.receiveSerializable(ReponseLUGAP_updateField.class);
    
                        if(!reponse.isSuccessful()){
                            dtm.setValueAt(modifiedLuggage.getCheckedByCustom(), row, column);
                            System.err.println(reponse.getMessage());
                            JOptionPane.showMessageDialog(this, reponse.getMessage(), "WARNING", JOptionPane.WARNING_MESSAGE);
                        }
                        else
                            modifiedLuggage.setCheckedByCustom(newCheckedByCustoms);
                    break;
                    case 6: //Remarques
                        if(modifiedLuggage.getComments().equals(str))
                            break;

                        this.communicator.SendSerializable(new RequeteLUGAP_updateFieldComments(modifiedLuggage, str));
                        reponse = this.communicator.receiveSerializable(ReponseLUGAP_updateField.class);
    
                        if(!reponse.isSuccessful()){
                            dtm.setValueAt(modifiedLuggage.getComments(), row, column);
                            System.err.println(reponse.getMessage());
                            JOptionPane.showMessageDialog(this, reponse.getMessage(), "WARNING", JOptionPane.WARNING_MESSAGE);
                        }
                        else
                            modifiedLuggage.setComments(str);
                    break;
                    default:
                    break;
                }
            } catch (CommunicatorException ex) {
                System.err.println(ex.getMessage());
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }
}

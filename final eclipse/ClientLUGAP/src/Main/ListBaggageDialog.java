package Main;

import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import database.entities.Baggage;
import database.entities.Vol;
import network.communication.Communication;
import network.communication.communicationException;
import network.protocole.lugap.reponse.Reponse_Baggages;
import network.protocole.lugap.reponse.Reponse_MAJ_champ;
import network.protocole.lugap.requete.Requete_Baggages;
import network.protocole.lugap.requete.Requete_MAJ_ChampChargement;
import network.protocole.lugap.requete.Requete_MAJ_ChampCheckedCustom;
import network.protocole.lugap.requete.Requete_MAJ_ChampComment;
import network.protocole.lugap.requete.Requete_MAJ_ChampRecevoir;

public class ListBaggageDialog extends JDialog implements TableModelListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private DefaultTableModel model = new DefaultTableModel(){
        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
        public boolean isCellEditable(int row, int column) {
            return column > 2;
        }
    };
    private final Vol vol;
    private final HashMap<String, Baggage> baggages;
    private final Communication c;
    private static final String[] TABLEHEADER = {"Identifiant", "Poids", "Type", "Réceptionné (O/N)", "Chargé en soute (O/N/R)", "Vérifié par la douane (O/N)", "Remarques"};
    
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelBaggageDe;
    private javax.swing.JLabel labelFlight;
    private javax.swing.JTable tableLuggageList;
    
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
    }
    
    

    /**
     * Create the dialog.
     */
    public ListBaggageDialog(ListVolFrame parent, Communication c, Vol v) {
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
        
        this.c = c;
        this.vol = v;
        this.baggages = new HashMap<>();
        
        labelFlight.setText(v.toString());
        fetchBaggages();
    }
    
    private void fetchBaggages(){
        try {
            c.send(new Requete_Baggages(vol));
            
            Reponse_Baggages reponse  = c.receive(Reponse_Baggages.class);
            
            if(!reponse.isSuccessful()){
                System.err.println(reponse.getMessage());
                JOptionPane.showMessageDialog(this, reponse.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
            else{
                if(reponse.getBaggages().isEmpty()){
                    System.err.println("There are no luggages for this flight!");
                    JOptionPane.showMessageDialog(this, "There are no luggages for this flight!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                }
                
                for(Baggage b : reponse.getBaggages()){
                    Object[] objs = b.toArray();
                    model.addRow(objs);
                    this.baggages.put(b.toString(), b);
                }
            } 
            
        } catch (communicationException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
        boolean found = false;
        for (Iterator<Baggage> iterator = this.baggages.values().iterator(); !found && iterator.hasNext();) {
             Baggage next = iterator.next();
            if(next.getLoaded() == 'N'){
                String msg = "One or more luggage(s) are still not loaded. Canceling closing the window.";
                System.err.println(msg);
                JOptionPane.showMessageDialog(this, msg, "?!?", JOptionPane.INFORMATION_MESSAGE);
                found = true;
            }
        }
        if(found)
            return;
        ((ListVolFrame)getParent()).exitProcedure();
        this.dispose();
    }   
    
    @Override
    public void tableChanged(TableModelEvent e) {
	 int row = e.getFirstRow();
	        int column = e.getColumn();
	        if(column != -1){
	            DefaultTableModel dtm = (DefaultTableModel) e.getSource();

	            String idLuggage = (String) dtm.getValueAt(row, 0);
	            Baggage modifiedLuggage = this.baggages.get(idLuggage);

	            Reponse_MAJ_champ reponse;
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
	                        
	                        this.c.send(new Requete_MAJ_ChampRecevoir(modifiedLuggage, newReceived));
	                        reponse = this.c.receive(Reponse_MAJ_champ.class);
	    
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

	                        this.c.send(new Requete_MAJ_ChampChargement(modifiedLuggage, newLoaded));
	                        reponse = this.c.receive(Reponse_MAJ_champ.class);

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

	                        this.c.send(new Requete_MAJ_ChampCheckedCustom(modifiedLuggage, newCheckedByCustoms));
	                        reponse = this.c.receive(Reponse_MAJ_champ.class);
	    
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

	                        this.c.send(new Requete_MAJ_ChampComment(modifiedLuggage, str));
	                        reponse = this.c.receive(Reponse_MAJ_champ.class);
	    
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
	            } catch (communicationException ex) {
	                System.err.println(ex.getMessage());
	                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
	                System.exit(1);
	            }
	        }
	            
	
    }

}

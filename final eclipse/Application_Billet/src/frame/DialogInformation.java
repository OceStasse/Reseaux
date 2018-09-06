package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogInformation extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
    private JTextField textVolVoyageur;
    private JLabel labelVol;
    private JTextField textId;
    private JTextField textNom;
    private JTextField textPrenom;
    private JTextField textDateNaiss;
    private JTextField textNbAccomp;
    private JTextField textLogin;
    private JPasswordField textPassword;
    
    
    private String vol;
    
    

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	try {
	    DialogInformation dialog = new DialogInformation();
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    dialog.setVisible(true);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Create the dialog.
     */
    public DialogInformation() {
    	setTitle("Information du voyageur");
	setBounds(100, 100, 450, 552);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	SpringLayout sl_contentPanel = new SpringLayout();
	contentPanel.setLayout(sl_contentPanel);
	{
		labelVol = new JLabel("Vol:");
		labelVol.setFont(new Font("Monospaced", Font.BOLD, 14));
		contentPanel.add(labelVol);
	}
	{
		textVolVoyageur = new JTextField();
		sl_contentPanel.putConstraint(SpringLayout.NORTH, textVolVoyageur, 9, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, textVolVoyageur, 95, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, textVolVoyageur, -55, SpringLayout.EAST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, labelVol, 1, SpringLayout.NORTH, textVolVoyageur);
		sl_contentPanel.putConstraint(SpringLayout.EAST, labelVol, -22, SpringLayout.WEST, textVolVoyageur);
		textVolVoyageur.setEditable(false);
		textVolVoyageur.setFont(new Font("Monospaced", Font.PLAIN, 12));
		contentPanel.add(textVolVoyageur);
		textVolVoyageur.setColumns(10);
	}
	
	JLabel lblNCarteDidentit = new JLabel("N\u00B0 Carte d'identit\u00E9:");
	sl_contentPanel.putConstraint(SpringLayout.NORTH, lblNCarteDidentit, 38, SpringLayout.SOUTH, textVolVoyageur);
	sl_contentPanel.putConstraint(SpringLayout.WEST, lblNCarteDidentit, 10, SpringLayout.WEST, contentPanel);
	lblNCarteDidentit.setFont(new Font("Monospaced", Font.BOLD, 14));
	contentPanel.add(lblNCarteDidentit);
	
	textId = new JTextField();
	sl_contentPanel.putConstraint(SpringLayout.NORTH, textId, -1, SpringLayout.NORTH, lblNCarteDidentit);
	sl_contentPanel.putConstraint(SpringLayout.WEST, textId, 20, SpringLayout.EAST, lblNCarteDidentit);
	sl_contentPanel.putConstraint(SpringLayout.EAST, textId, -60, SpringLayout.EAST, contentPanel);
	textId.setFont(new Font("Monospaced", Font.PLAIN, 12));
	contentPanel.add(textId);
	textId.setColumns(10);
	
	JLabel lblNom = new JLabel("Nom:");
	sl_contentPanel.putConstraint(SpringLayout.NORTH, lblNom, 17, SpringLayout.SOUTH, lblNCarteDidentit);
	sl_contentPanel.putConstraint(SpringLayout.EAST, lblNom, 0, SpringLayout.EAST, lblNCarteDidentit);
	lblNom.setFont(new Font("Monospaced", Font.BOLD, 14));
	contentPanel.add(lblNom);
	
	JLabel lblPrnom = new JLabel("Pr\u00E9nom:");
	sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPrnom, 21, SpringLayout.SOUTH, lblNom);
	sl_contentPanel.putConstraint(SpringLayout.EAST, lblPrnom, -252, SpringLayout.EAST, contentPanel);
	lblPrnom.setFont(new Font("Monospaced", Font.BOLD, 14));
	contentPanel.add(lblPrnom);
	
	JLabel lblDateDeNaissance = new JLabel("Date de naissance:");
	sl_contentPanel.putConstraint(SpringLayout.NORTH, lblDateDeNaissance, 18, SpringLayout.SOUTH, lblPrnom);
	sl_contentPanel.putConstraint(SpringLayout.EAST, lblDateDeNaissance, 0, SpringLayout.EAST, lblNCarteDidentit);
	lblDateDeNaissance.setFont(new Font("Monospaced", Font.BOLD, 14));
	contentPanel.add(lblDateDeNaissance);
	
	textNom = new JTextField();
	sl_contentPanel.putConstraint(SpringLayout.NORTH, textNom, -1, SpringLayout.NORTH, lblNom);
	sl_contentPanel.putConstraint(SpringLayout.WEST, textNom, 0, SpringLayout.WEST, textId);
	sl_contentPanel.putConstraint(SpringLayout.EAST, textNom, -60, SpringLayout.EAST, contentPanel);
	textNom.setFont(new Font("Monospaced", Font.PLAIN, 12));
	contentPanel.add(textNom);
	textNom.setColumns(10);
	
	textPrenom = new JTextField();
	sl_contentPanel.putConstraint(SpringLayout.NORTH, textPrenom, -1, SpringLayout.NORTH, lblPrnom);
	sl_contentPanel.putConstraint(SpringLayout.WEST, textPrenom, 0, SpringLayout.WEST, textId);
	sl_contentPanel.putConstraint(SpringLayout.EAST, textPrenom, -60, SpringLayout.EAST, contentPanel);
	textPrenom.setFont(new Font("Monospaced", Font.PLAIN, 12));
	contentPanel.add(textPrenom);
	textPrenom.setColumns(10);
	
	textDateNaiss = new JTextField();
	sl_contentPanel.putConstraint(SpringLayout.NORTH, textDateNaiss, -1, SpringLayout.NORTH, lblDateDeNaissance);
	sl_contentPanel.putConstraint(SpringLayout.WEST, textDateNaiss, 0, SpringLayout.WEST, textId);
	sl_contentPanel.putConstraint(SpringLayout.EAST, textDateNaiss, -60, SpringLayout.EAST, contentPanel);
	textDateNaiss.setFont(new Font("Monospaced", Font.PLAIN, 12));
	contentPanel.add(textDateNaiss);
	textDateNaiss.setColumns(10);
	
	JLabel lblGenre = new JLabel("Genre:");
	sl_contentPanel.putConstraint(SpringLayout.NORTH, lblGenre, 23, SpringLayout.SOUTH, lblDateDeNaissance);
	sl_contentPanel.putConstraint(SpringLayout.EAST, lblGenre, 0, SpringLayout.EAST, lblNCarteDidentit);
	lblGenre.setFont(new Font("Monospaced", Font.BOLD, 14));
	contentPanel.add(lblGenre);
	
	JComboBox<?> BoxGenre = new JComboBox();
	sl_contentPanel.putConstraint(SpringLayout.NORTH, BoxGenre, -1, SpringLayout.NORTH, lblGenre);
	sl_contentPanel.putConstraint(SpringLayout.WEST, BoxGenre, 0, SpringLayout.WEST, textId);
	sl_contentPanel.putConstraint(SpringLayout.EAST, BoxGenre, -58, SpringLayout.EAST, contentPanel);
	BoxGenre.setModel(new DefaultComboBoxModel(new String[] {"Choisir un genre...", "Femme", "Homme"}));
	BoxGenre.setEditable(true);
	BoxGenre.setFont(new Font("Monospaced", Font.PLAIN, 12));
	contentPanel.add(BoxGenre);
	
	JLabel lblNombreDaccompagnant = new JLabel("Nbr d'accompagnant:");
	sl_contentPanel.putConstraint(SpringLayout.EAST, lblNombreDaccompagnant, 0, SpringLayout.EAST, lblNCarteDidentit);
	lblNombreDaccompagnant.setFont(new Font("Monospaced", Font.BOLD, 14));
	contentPanel.add(lblNombreDaccompagnant);
	
	textNbAccomp = new JTextField();
	textNbAccomp.setFont(new Font("Monospaced", Font.PLAIN, 12));
	sl_contentPanel.putConstraint(SpringLayout.NORTH, textNbAccomp, 1, SpringLayout.NORTH, lblNombreDaccompagnant);
	sl_contentPanel.putConstraint(SpringLayout.WEST, textNbAccomp, 3, SpringLayout.WEST, textId);
	sl_contentPanel.putConstraint(SpringLayout.EAST, textNbAccomp, 0, SpringLayout.EAST, textVolVoyageur);
	contentPanel.add(textNbAccomp);
	textNbAccomp.setColumns(10);
	
	JLabel lblNewLabel = new JLabel("Login:");
	sl_contentPanel.putConstraint(SpringLayout.NORTH, lblNewLabel, 21, SpringLayout.SOUTH, lblGenre);
	sl_contentPanel.putConstraint(SpringLayout.EAST, lblNewLabel, 0, SpringLayout.EAST, lblNCarteDidentit);
	lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
	contentPanel.add(lblNewLabel);
	
	JLabel lblMotDePasse = new JLabel("Mot de passe:");
	sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblMotDePasse, -134, SpringLayout.SOUTH, contentPanel);
	sl_contentPanel.putConstraint(SpringLayout.NORTH, lblNombreDaccompagnant, 26, SpringLayout.SOUTH, lblMotDePasse);
	sl_contentPanel.putConstraint(SpringLayout.EAST, lblMotDePasse, 0, SpringLayout.EAST, lblNCarteDidentit);
	lblMotDePasse.setFont(new Font("Monospaced", Font.BOLD, 14));
	contentPanel.add(lblMotDePasse);
	
	textLogin = new JTextField();
	textLogin.setFont(new Font("Monospaced", Font.PLAIN, 12));
	sl_contentPanel.putConstraint(SpringLayout.NORTH, textLogin, 1, SpringLayout.NORTH, lblNewLabel);
	sl_contentPanel.putConstraint(SpringLayout.WEST, textLogin, 0, SpringLayout.WEST, textId);
	sl_contentPanel.putConstraint(SpringLayout.EAST, textLogin, -5, SpringLayout.EAST, textVolVoyageur);
	contentPanel.add(textLogin);
	textLogin.setColumns(10);
	
	textPassword = new JPasswordField();
	textPassword.setFont(new Font("Monospaced", Font.PLAIN, 12));
	sl_contentPanel.putConstraint(SpringLayout.NORTH, textPassword, 1, SpringLayout.NORTH, lblMotDePasse);
	sl_contentPanel.putConstraint(SpringLayout.WEST, textPassword, 0, SpringLayout.WEST, textId);
	sl_contentPanel.putConstraint(SpringLayout.EAST, textPassword, -5, SpringLayout.EAST, textVolVoyageur);
	contentPanel.add(textPassword);
	{
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    
			    /* envoie de la requete d'achat de billets : requete crypté au moyen de la clé secrète 
			     * vérifie la disponibilité => si ok alors enregistre la reservation dans BD_AIRPORT 
			     * Retourne les numeros de place attribuées sur le vol et le prix a payer => crypté */
			    
			    
			    /* J'ignore si on affiche une messagebox ou une autre dialogue pour afficher les place + prix.. */
			    /* Le client doit confirmer (accompagné d'un HMAC)  */
			    
			    
			    dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	    {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	    }
	}
    }

    public DialogInformation(String vol) {
	// TODO Auto-generated constructor stub
	
	this.vol = vol;
	
	textVolVoyageur.setText(vol);
	
	
    }
}

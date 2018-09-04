package frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JList;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

public class FrameBillet extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    FrameBillet frame = new FrameBillet();
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
    public FrameBillet() {
    	setTitle("Application Billets");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	SpringLayout sl_contentPane = new SpringLayout();
	contentPane.setLayout(sl_contentPane);
	
	JList listVol = new JList();
	listVol.setBorder(new TitledBorder(null, "Liste des vols", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	listVol.setFont(new Font("Monospaced", Font.PLAIN, 12));
	sl_contentPane.putConstraint(SpringLayout.NORTH, listVol, 10, SpringLayout.NORTH, contentPane);
	sl_contentPane.putConstraint(SpringLayout.WEST, listVol, 10, SpringLayout.WEST, contentPane);
	sl_contentPane.putConstraint(SpringLayout.SOUTH, listVol, 241, SpringLayout.NORTH, contentPane);
	sl_contentPane.putConstraint(SpringLayout.EAST, listVol, 164, SpringLayout.WEST, contentPane);
	contentPane.add(listVol);
	
	
	if(listVol.getSelectedValue() != null)
	{
	    /* Passer le vol choissi en parametre */
	    String vol = "vol";
	    DialogInformation dialog = new DialogInformation(vol); 
	    dialog.setVisible(true);
	}
	
	
    }
}

package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.SpringLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameConnect extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textLogin;
    private JPasswordField textPassword;
    private JTextField textError;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    FrameConnect frame = new FrameConnect();
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
    public FrameConnect() {
    	setTitle("Connexion");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 466, 272);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	SpringLayout sl_contentPane = new SpringLayout();
	contentPane.setLayout(sl_contentPane);
	
	JLabel labelLogin = new JLabel("Login:");
	labelLogin.setFont(new Font("Monospaced", Font.BOLD, 14));
	contentPane.add(labelLogin);
	
	JLabel lblNewLabel = new JLabel("Mot de passe:");
	sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 82, SpringLayout.NORTH, contentPane);
	sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 25, SpringLayout.WEST, contentPane);
	sl_contentPane.putConstraint(SpringLayout.WEST, labelLogin, 0, SpringLayout.WEST, lblNewLabel);
	sl_contentPane.putConstraint(SpringLayout.SOUTH, labelLogin, -20, SpringLayout.NORTH, lblNewLabel);
	lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
	contentPane.add(lblNewLabel);
	
	textLogin = new JTextField();
	sl_contentPane.putConstraint(SpringLayout.NORTH, textLogin, -1, SpringLayout.NORTH, labelLogin);
	sl_contentPane.putConstraint(SpringLayout.WEST, textLogin, 36, SpringLayout.EAST, labelLogin);
	textLogin.setFont(new Font("Monospaced", Font.PLAIN, 12));
	contentPane.add(textLogin);
	textLogin.setColumns(10);
	
	textPassword = new JPasswordField();
	sl_contentPane.putConstraint(SpringLayout.EAST, textLogin, 0, SpringLayout.EAST, textPassword);
	sl_contentPane.putConstraint(SpringLayout.NORTH, textPassword, -1, SpringLayout.NORTH, lblNewLabel);
	sl_contentPane.putConstraint(SpringLayout.WEST, textPassword, 13, SpringLayout.EAST, lblNewLabel);
	sl_contentPane.putConstraint(SpringLayout.EAST, textPassword, -74, SpringLayout.EAST, contentPane);
	textPassword.setFont(new Font("Monospaced", Font.PLAIN, 12));
	contentPane.add(textPassword);
	
	JButton ButtonConnect = new JButton("Connect");
	ButtonConnect.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    
		    if(textLogin.getText().isEmpty() || String.valueOf(textPassword).isEmpty())
			textError.setText("Tous les champs doivent être complétés.");
		    else
		    {
			/* digest sale + handshake */
			// si handshake ok: 
			
			FrameBillet frame = new FrameBillet();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
			
			
		    }
		}
	});
	sl_contentPane.putConstraint(SpringLayout.NORTH, ButtonConnect, 28, SpringLayout.SOUTH, textPassword);
	sl_contentPane.putConstraint(SpringLayout.WEST, ButtonConnect, 175, SpringLayout.WEST, contentPane);
	ButtonConnect.setFont(new Font("Monospaced", Font.BOLD, 14));
	contentPane.add(ButtonConnect);
	
	textError = new JTextField();
	textError.setEditable(false);
	sl_contentPane.putConstraint(SpringLayout.WEST, textError, 43, SpringLayout.WEST, contentPane);
	sl_contentPane.putConstraint(SpringLayout.SOUTH, textError, -10, SpringLayout.SOUTH, contentPane);
	sl_contentPane.putConstraint(SpringLayout.EAST, textError, 13, SpringLayout.EAST, textLogin);
	contentPane.add(textError);
	textError.setColumns(10);
    }
}

package personnel;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import personnel.MyTextField;


public class Authentification extends JFrame implements ActionListener {
	private JLabel titre;
	private JLabel lblUserName;
    private MyTextField txtUserName;
    private JLabel lblPassword;
    private MyTextField txtPassword;
    private JButton button;
    private JLabel info;
    private Employe employe;
    private GestionPersonnel gestPers;
    //private MDIWindow MDIParent;
 
    
    public Authentification(GestionPersonnel GP) throws Exception {
        super();
        
        initComponents();
 
        setSize(400, 250);
        this.setTitle("Login");
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(44, 92, 158));
        
        gestPers = GP;
    }
 
    
    private void initComponents() {
    	int x = (400 - 200)/2;
        titre = new JLabel("Bienvenue", SwingConstants.CENTER);
        titre.setLocation(x, 20);
        titre.setSize(200, 30);
        titre.setForeground(Color.WHITE);
        
        x = (400 - 180)/2; 
        lblUserName = new JLabel("Login : ", SwingConstants.LEFT);
        lblUserName.setLocation(x, 70);
        lblUserName.setSize(80, 20);
        lblUserName.setForeground(Color.WHITE);
        
        x = x + 10 + 80;
        txtUserName = new MyTextField(5, 10);
        txtUserName.setLocation(x, 70);
        txtUserName.setSize(90, 20);
        
        x = (400 - 180)/2; 
        lblPassword = new JLabel("Password : ", SwingConstants.LEFT);
        lblPassword.setLocation(x, 110);
        lblPassword.setSize(80, 20);
        lblPassword.setForeground(Color.WHITE);
        
        x = x + 10 + 80;
        txtPassword = new MyTextField(5, 10);
        txtPassword.setLocation(x, 110);
        txtPassword.setSize(90, 20);
        
        
        x = (400 - 120)/2;
        button = new JButton("Connexion");
        button.setLocation(x, 170);
        button.setSize(120, 20);
        
        info = new JLabel();
        info.setLocation(30, 140);
        info.setSize(250, 20);
        info.setForeground(Color.WHITE);
        
        setLayout(null);
 
        add(titre);
        add(lblUserName);
        add(txtUserName);
        add(lblPassword);
        add(txtPassword);
        add(button);
        add(info);
        button.addActionListener(this);
        
        txtUserName.setText("Toor");
        txtPassword.setText("Mitoor");
    }
    
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }
    
    public void actionPerformed(ActionEvent event) {
    	if(isNullOrEmpty(txtUserName.getText().trim()))
    		info.setText("Vous n'avez pas renseigné votre login.");
    	else if (isNullOrEmpty(txtPassword.getText().trim()))
    		info.setText("Vous n'avez pas renseigné votre password.");
    	else
    	{
    		try {
				validated();
			} catch (Exception e) {
				e.printStackTrace();
//    			info.setText("Bonjour " + txtUserName.getText());
			}
    	}
    }
    
    public void validated() throws Exception {
    	String strLogin = txtUserName.getText();
    	String strPwd = txtPassword.getText();
    	
    	employe = Employe.getMyRoot(gestPers, strLogin, strPwd);
    	
    	if(employe.getNom().equals(strLogin) && employe.getPassword().equals(strPwd))
    	{
	    	info.setText("Youpi ça fonctionne !!!");
	    	
	    	//MDIParent.setEmploye(employe);
	    	MDIWindow myWindow = new MDIWindow(gestPers, employe);
	    	myWindow.setVisible(true);
	    	this.dispose();
	    }
    	else
    		info.setText("Login ou mot de passe incorrect.");
    }
}

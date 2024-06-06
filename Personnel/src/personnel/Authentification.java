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
 
        setSize(500, 300);
        this.setTitle("Login");
        this.setLocationRelativeTo(null);
        
        gestPers = GP;
    }
 
    
    private void initComponents() {
    	int x = (500 - 240)/2;
        titre = new JLabel("Bienvenue", SwingConstants.CENTER);
        titre.setLocation(x, 20);
        titre.setSize(240, 30);
        
        x = (500 - 170)/2; 
        lblUserName = new JLabel("Login : ", SwingConstants.LEFT);
        lblUserName.setLocation(x, 70);
        lblUserName.setSize(80, 20);
        
        x = x + 10 + 80;
        txtUserName = new MyTextField(5, 10);
        txtUserName.setLocation(x, 70);
        txtUserName.setSize(90, 20);
        
        x = (500 - 170)/2; 
        lblPassword = new JLabel("Password : ", SwingConstants.LEFT);
        lblPassword.setLocation(x, 110);
        lblPassword.setSize(80, 20);
        
        x = x + 10 + 80;
        txtPassword = new MyTextField(5, 10);
        txtPassword.setLocation(x, 110);
        txtPassword.setSize(90, 20);
        
        
        x = (500 - 120)/2;
        button = new JButton("Connexion");
        button.setLocation(x, 180);
        button.setSize(120, 20);
        
        info = new JLabel();
        info.setLocation(30, 140);
        info.setSize(250, 20);
        
        setLayout(null);
 
        add(titre);
        add(lblUserName);
        add(txtUserName);
        add(lblPassword);
        add(txtPassword);
        add(button);
        add(info);
        button.addActionListener(this);
        
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

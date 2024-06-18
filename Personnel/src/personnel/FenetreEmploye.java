package personnel;

import java.awt.Color;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

public class FenetreEmploye extends JInternalFrame implements ActionListener {

	private Employe myEmploye;
	private GestionPersonnel gestPers;
	private Ligue myLigue;
	private String typeEmploye;
	private JPanel pan;
	private JLabel lblTitre;
	private JCheckBox estAdmin;
	private JLabel lblNom;
	private JLabel lblPrenom;
	private JLabel lblMail;
	private JLabel lblPwd;
	private JLabel lblDateArrivee;
	private JLabel lblDateDepart;
	private MyTextField txtNom;
	private MyTextField txtPrenom;
	private MyTextField txtMail;
	private MyTextField txtPwd;
	private MyTextField txtDateArrivee;
	private MyTextField txtDateDepart;
	private JButton btnValidate;
	private JButton btnSuppr;
	private JSeparator sep;
	private JTextArea lblInfo;
	private JButton btnCancel;
	
	public Employe getMyEmploye() {
		return myEmploye;
	}

	public void setMyEmploye(Employe myEmploye) {
		this.myEmploye = myEmploye;
	}
    
	
	
	public FenetreEmploye(GestionPersonnel GP, Ligue ligue, Employe employe) {
		
		super();
		pan = new JPanel();
		this.setContentPane(pan);
		pan.setLayout(null);
        this.setSize(500, 300);
        this.setResizable(false);
        
		typeEmploye = "employe";
		this.myLigue = ligue;
		this.gestPers = GP;
		if(employe != null)
			this.myEmploye = employe;
        
		initComponents();
	}
	
	public FenetreEmploye(GestionPersonnel GP, Employe root) throws Exception {
		
		super();
		pan = new JPanel();
		this.setContentPane(pan);
		pan.setLayout(null);
        this.setSize(500, 300);
        this.setResizable(false);
        
        typeEmploye = "root";
        this.gestPers = GP;
        this.myEmploye = root;
        
        initComponents();
	}
	
	private void initComponents(){
	
		this.getContentPane().setBackground(new Color(44, 92, 158));
		
		lblTitre = new JLabel("Changer les informations du compte root.");
		lblTitre.setLocation(130, 30);
		lblTitre.setSize(300, 20);
		lblTitre.setForeground(Color.WHITE);
		
		lblNom = new JLabel("Nom : ");
		lblNom.setLocation(50, 80);
		lblNom.setSize(50, 20);
		lblNom.setForeground(Color.WHITE);
		
		txtNom = new MyTextField(20, 20);
		txtNom.setLocation(120, 80);
		txtNom.setSize(100, 20);
		
		lblPrenom = new JLabel("Prénom : ");
		lblPrenom.setLocation(260, 80);
		lblPrenom.setSize(60, 20);
		lblPrenom.setForeground(Color.WHITE);
		
		txtPrenom = new MyTextField(20, 20);
		txtPrenom.setLocation(350, 80);
		txtPrenom.setSize(100, 20);
		
		lblMail = new JLabel("Mail : ");
		lblMail.setLocation(50, 130);
		lblMail.setSize(50, 20);
		lblMail.setForeground(Color.WHITE);
		
		txtMail = new MyTextField(40, 40);
		txtMail.setLocation(120, 130);
		txtMail.setSize(100, 20);
		
		lblPwd = new JLabel("Password : ");
		lblPwd.setLocation(260, 130);
		lblPwd.setSize(80, 20);
		lblPwd.setForeground(Color.WHITE);
		
		txtPwd = new MyTextField(40, 20);
		txtPwd.setLocation(350, 130);
		txtPwd.setSize(100, 20);
		
		btnCancel = new JButton("Annuler");
		btnCancel.setLocation(300, 220);
		btnCancel.setSize(100, 20);
		btnCancel.addActionListener(new ListenerCancel());
		
		if(myEmploye != null)
		{
			if(!isNullOrEmpty(myEmploye.getNom()))
				txtNom.setText(myEmploye.getNom());
			if(!isNullOrEmpty(myEmploye.getPrenom()))
				txtPrenom.setText(myEmploye.getPrenom());
			if(!isNullOrEmpty(myEmploye.getMail()))
				txtMail.setText(myEmploye.getMail());
			if(!isNullOrEmpty(myEmploye.getPassword()))
				txtPwd.setText(myEmploye.getPassword());
			
		}
		
		btnValidate = new JButton("Valider");
		btnValidate.setLocation(100, 220);
		btnValidate.setSize(100, 20);
		
		
		
		switch (typeEmploye)
		{
		case "root" : 
			this.setTitle("Gestion du compte Root");
			btnValidate.addActionListener(new ListenerModifier());
			break;
			
		case "employe" :
			
			lblTitre.setLocation(30, 20);
			
			estAdmin = new JCheckBox("Administrateur");
			estAdmin.setLocation(300, 20);
			estAdmin.setSize(120, 20);
			estAdmin.setEnabled(false);
			estAdmin.setBackground(new Color(44, 92, 158));
			estAdmin.setForeground(Color.WHITE);
			
			txtMail.setSize(150, 20);
			txtPwd.setSize(150, 20);
			
			lblNom.setLocation(30, 60);
			txtNom.setLocation(130, 60);
			
			
			lblPrenom.setLocation(300, 60);
			txtPrenom.setLocation(400, 60);
			
			
			lblMail.setLocation(30, 100);
			txtMail.setLocation(130, 100);
			
			
			lblPwd.setLocation(300, 100);
			txtPwd.setLocation(400, 100);
			

			
			lblDateArrivee = new JLabel("Date d'arrivée : ");
			lblDateArrivee.setLocation(30, 140);
			lblDateArrivee.setSize(100, 20);
			lblDateArrivee.setForeground(Color.WHITE);
			
			txtDateArrivee = new MyTextField(15, 10);
			txtDateArrivee.setLocation(130, 140);
			txtDateArrivee.setSize(70, 20);
							
			
			
			lblDateDepart = new JLabel("Date de départ : ");
			lblDateDepart.setLocation(300, 140);
			lblDateDepart.setSize(100, 20);
			lblDateDepart.setForeground(Color.WHITE);
			
			
			txtDateDepart = new MyTextField(15, 10);
			txtDateDepart.setLocation(400, 140);
			txtDateDepart.setSize(70, 20);
			
			
			
			if(myEmploye == null)
			{
				this.setTitle("Création d'un nouvel employé");
				lblTitre.setText("Ligue : " + myLigue.getNom());
				
				txtNom.setEditable(true);
				txtPrenom.setEditable(true);
				txtMail.setEditable(true);
				txtPwd.setEditable(true);
				txtDateArrivee.setEditable(true);
				txtDateDepart.setEditable(true);
				
				
				btnValidate.setText("Enregistrer");			
				btnValidate.setLocation(250, 180);
				btnValidate.addActionListener(new ListenerModifier());
			}
			else
			{
				this.setTitle("Modification de l'employé " + myEmploye.getNom() + " " + myEmploye.getPrenom());
				lblTitre.setText("Ligue : " + myLigue.getNom());
				
				txtNom.setEditable(false);
				txtPrenom.setEditable(false);
				txtMail.setEditable(false);
				txtPwd.setEditable(false);
				txtDateArrivee.setEditable(false);
				txtDateDepart.setEditable(false);
				
				
				
				btnValidate.setText("Modifier");			
				btnValidate.setLocation(150, 180);
				btnValidate.addActionListener(new ListenerModifier());

				
				btnSuppr = new JButton("Supprimer");
				btnSuppr.setLocation(350, 180);
				btnSuppr.setSize(100, 20);
				btnSuppr.addActionListener(new ListenerSupprimer());
				
				pan.add(btnSuppr);
				
				if(myEmploye.getdateArrivee()!=null && !isNullOrEmpty(myEmploye.getdateArrivee().toString()))
					txtDateArrivee.setText(myEmploye.getdateArrivee().toString());
				
				if(myEmploye.getdateDepart()!=null && !isNullOrEmpty(myEmploye.getdateDepart().toString()))
					txtDateDepart.setText(myEmploye.getdateDepart().toString());
			}
			
			//Taille de la fenêtre pour la création ou la modification d'un employé.
			this.setSize(600, 350);
			
			//Positionnement des composants pour la fenêtre de création/modification d'un employé.

			sep = new JSeparator();
			sep.setLocation(0, 220);
			sep.setSize(600, 2);
			
			lblInfo = new JTextArea();
			lblInfo.setLocation(30, 230);
			lblInfo.setSize(540, 60);
			lblInfo.setForeground(Color.RED);
			lblInfo.setEditable(false);
			lblInfo.setLineWrap(true);
			lblInfo.setWrapStyleWord(true);
			lblInfo.setBackground(new Color(44, 92, 158));
			
			pan.add(estAdmin);
			pan.add(lblDateArrivee);
			pan.add(txtDateArrivee);
			pan.add(lblDateDepart);
			pan.add(txtDateDepart);
			pan.add(sep);
			pan.add(lblInfo);
			
			break;
		}
		
		pan.add(lblTitre);
		pan.add(lblNom);
		pan.add(txtNom);
		pan.add(lblPrenom);
		pan.add(txtPrenom);
		pan.add(lblMail);
		pan.add(txtMail);
		pan.add(lblPwd);
		pan.add(txtPwd);
		pan.add(btnValidate);
		pan.add(btnCancel);
	}
	
	private boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }
	
	

	private class ListenerModifier implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(btnValidate.getText().equals("Modifier"))
			{
				estAdmin.setEnabled(true);
	    		txtNom.setEditable(true);
	    		txtPrenom.setEditable(true);
	    		txtMail.setEditable(true);
	    		txtPwd.setEditable(true);
	    		txtDateArrivee.setEditable(true);
	    		txtDateDepart.setEditable(true);
	    		btnValidate.setText("Enregistrer");
			}
			//Partie Modification d'un employé existant
			else if(myEmploye != null && btnValidate.getText().equals("Enregistrer"))
			{
				try {
					if(estAdmin.isSelected())
						myEmploye.setAdminLigue(true);
					myEmploye.setNom(txtNom.getText());
					myEmploye.setPrenom(txtPrenom.getText());
					myEmploye.setMail(txtMail.getText());
					myEmploye.setPassword(txtPwd.getText());
					if(!isNullOrEmpty(txtDateArrivee.getText()) || (myEmploye.getdateArrivee()!=null && !isNullOrEmpty(myEmploye.getdateArrivee().toString()) && myEmploye.getdateArrivee() != LocalDate.parse(txtDateArrivee.getText())))
							myEmploye.setdateArrivee(LocalDate.parse(txtDateArrivee.getText()));
					if(!isNullOrEmpty(txtDateDepart.getText()) || (myEmploye.getdateDepart()!=null && !isNullOrEmpty(myEmploye.getdateDepart().toString()) && myEmploye.getdateDepart() != LocalDate.parse(txtDateDepart.getText())))
						myEmploye.setdateDepart(LocalDate.parse(txtDateDepart.getText()));
					
					CallLigueWindow();
					
				} catch (Exception e) {
					lblInfo.setText(e.getMessage());
				}
				
			}
			//Cas de la création d'un nouvel employé
			else if(myEmploye == null && btnValidate.getText().equals("Enregistrer"))
			{
				String dateArrivee = txtDateArrivee.getText();
				
				if(isNullOrEmpty(dateArrivee))
					dateArrivee = LocalDate.now().toString();
				
				try 
				{
					myEmploye = new Employe(gestPers, myLigue, txtNom.getText(), txtPrenom.getText(), txtMail.getText(), txtPwd.getText(), LocalDate.parse(dateArrivee), null);
										
					myLigue.addEmploye(myEmploye);
				}
				catch (Exception e) 
				{
					lblInfo.setText(e.getMessage());
				}
				
				CallLigueWindow();
			}
			else // Cas du root
			{
				if(typeEmploye.equals("root"))
				{
					try 
					{
						myEmploye.setNom(txtNom.getText());
						myEmploye.setPrenom(txtPrenom.getText());
						myEmploye.setMail(txtMail.getText());
						myEmploye.setPassword(txtPwd.getText());
						dispose();
					} 
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						lblInfo.setText(e.getMessage());
					}
					
				}
			
			}
		}
	}
	
	private class ListenerSupprimer implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				myEmploye.remove();
				CallLigueWindow();
			} catch (SauvegardeImpossible e) {
				e.getMessage();
			}
		}
	}
	

	private void CallLigueWindow()
	{
		//Appel de la fenetreLigue(GestPers, LigueEnCours)
		if(typeEmploye.equals("employe"))
		{
			FenetreLigue fenetreLigue = new FenetreLigue(gestPers, myLigue);
			fenetreLigue.setVisible(true);
		}
		dispose();
	}
	
	private class ListenerCancel implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
}

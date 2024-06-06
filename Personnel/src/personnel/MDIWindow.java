package personnel;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import personnel.MyTextField;

public class MDIWindow extends JFrame implements ActionListener {
	
	private Employe root;
	private GestionPersonnel gestPers;
	

    public Employe getEmploye() {
    	return root;
    }
    
    public void setEmploye(Employe employe) {
    	this.root=employe;
    }
	
	public MDIWindow(GestionPersonnel GP, Employe employe) throws Exception {
		super();
		 
        this.setSize(800, 500);
        this.setTitle("Gestionnaire de ligues");
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.gestPers = GP;
        this.root = employe;
        
        initComponents();
	}
	
	private void initComponents() throws Exception {
		JMenuBar bar = new JMenuBar();
		
		JMenu mnuLigue = new JMenu("Gestion des ligues");
		
		//Ajout des JMenuItem liés à la gestion des ligues.
		JMenuItem mnuPrint = new JMenuItem("Afficher les ligues");
		mnuLigue.add(mnuPrint);
		mnuPrint.addActionListener(this);
		
		
		///Ajout des JMenuItem liés à la gestion du Root.
		JMenu mnuRoot = new JMenu("Gestion du Root");

		mnuPrint = new JMenuItem("Afficher le compte Root");
		mnuRoot.add(mnuPrint);
		
		JMenuItem mnuChangerNom = new JMenuItem("Changer le nom");
		mnuRoot.add(mnuChangerNom);
		
		JMenuItem mnuChangerPrenom = new JMenuItem("Changer le prénom");
		mnuRoot.add(mnuChangerPrenom);
		
		JMenuItem mnuChangerMail = new JMenuItem("Changer le mail");
		mnuRoot.add(mnuChangerMail);
		
		JMenuItem mnuChangerPassword = new JMenuItem("Changer le password");
		mnuRoot.add(mnuChangerPassword);
		
		JMenuItem mnuChangerDateArrivee = new JMenuItem("Changer la date d'arrivée");
		mnuRoot.add(mnuChangerDateArrivee);
		
		JMenuItem mnuChangerDateDepart = new JMenuItem("Changer la date de départ");
		mnuRoot.add(mnuChangerDateDepart);
		

		///Ajouter les JMenuItem liés au menu quitter.
		JMenu mnuQuit = new JMenu("Quitter");
		
		JMenuItem mnuSave = new JMenuItem("Quitter et enregistrer");
		mnuQuit.add(mnuSave);
		
		JMenuItem mnuDontSave = new JMenuItem("Quitter sans enregistrer");
		mnuQuit.add(mnuDontSave);
		
		
		bar.add(mnuLigue);
		bar.add(mnuRoot);
		bar.add(mnuQuit);
		this.setJMenuBar(bar);
	}
	
	private void goToLigue( ) throws Exception {
		FenetreLigue ligue = new FenetreLigue(gestPers, root);
    	ligue.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) {
    		try {
				goToLigue();
			} catch (Exception e) {
				e.printStackTrace();
    		}
    }
	
	
}

package personnel;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import personnel.MyTextField;

public class MDIWindow extends JFrame implements ActionListener 
{
	
	private final JDesktopPane desktopPane = new JDesktopPane();	
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
		 
        this.setSize(900, 600);
        this.setTitle("Gestionnaire de ligues");
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.getContentPane().setBackground(new Color(210, 215, 255));
        this.add(desktopPane, BorderLayout.CENTER);
        
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
		mnuPrint.addActionListener(new ListenerLigue());
		
		
		///Ajout des JMenuItem liés à la gestion du Root.
		JMenu mnuRoot = new JMenu("Gestion du Root");

		mnuPrint = new JMenuItem("Afficher le compte Root");
		mnuRoot.add(mnuPrint);
		mnuPrint.addActionListener(new ListenerRoot());
		
//		JMenuItem mnuChangerNom = new JMenuItem("Changer le nom");
//		mnuRoot.add(mnuChangerNom);
//		
//		JMenuItem mnuChangerPrenom = new JMenuItem("Changer le prénom");
//		mnuRoot.add(mnuChangerPrenom);
//		
//		JMenuItem mnuChangerMail = new JMenuItem("Changer le mail");
//		mnuRoot.add(mnuChangerMail);
//		
//		JMenuItem mnuChangerPassword = new JMenuItem("Changer le password");
//		mnuRoot.add(mnuChangerPassword);
//		
//		JMenuItem mnuChangerDateArrivee = new JMenuItem("Changer la date d'arrivée");
//		mnuRoot.add(mnuChangerDateArrivee);
//		
//		JMenuItem mnuChangerDateDepart = new JMenuItem("Changer la date de départ");
//		mnuRoot.add(mnuChangerDateDepart);
		

		///Ajouter les JMenuItem liés au menu quitter.
		JMenu mnuQuit = new JMenu("Quitter");
		
		JMenuItem mnuExit = new JMenuItem("Quitter");
		mnuExit.addActionListener(new ListenerClose());
		mnuQuit.add(mnuExit);
		
		
		bar.add(mnuLigue);
		bar.add(mnuRoot);
		bar.add(mnuQuit);
		this.setJMenuBar(bar);
	}
	
	private void goToLigue( ) throws Exception 
	{
		///TODO Mettre le code d'appel en JInternalFrame
		
		FenetreLigue ligue = new FenetreLigue(gestPers);
    	ligue.setVisible(true);
    	desktopPane.add(ligue);
	}
	
	private class ListenerLigue implements ActionListener {
		public void actionPerformed(ActionEvent event) {
    		try {
				goToLigue();
			} catch (Exception e) {
				e.printStackTrace();
    		}
		}
	}
	
	
	private void goToRoot() throws Exception 
	{
		///TODO Mettre Remplacer l'extend JFrame par JInternalFrame dans les fenêtres FenetreEmploye et FenetreLigue
		FenetreEmploye fRoot = new FenetreEmploye(gestPers, root);
		fRoot.setVisible(true);
		desktopPane.add(fRoot);
	}

	private class ListenerRoot implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				goToRoot();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private class ListenerClose implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				dispose();
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

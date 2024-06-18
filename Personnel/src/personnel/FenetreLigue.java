package personnel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.midi.SysexMessage;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import commandLineMenus.Action;

public class FenetreLigue extends JInternalFrame implements ListSelectionListener, ActionListener{
	
	private GestionPersonnel gestPers;
	private Employe employe;
	private Ligue ligueEnCours;
	private JPanel pan;
	private JButton btnAddLigue;
	private DefaultListModel<String> myListLigues;
	private JList<String> lstLigues;
	private JSeparator separatorV;
	private JLabel lblNomLigue;
	private MyTextField txtNomLigue;
	private JLabel lblNomAdmin;
	private MyTextField txtNomAdmin;
	private JLabel lblListeEmploye;
	private DefaultListModel<String> myListEmployes;
	private JList<String> lstEmployes;
	private JButton btnAddEmploye;
	private JSeparator separatorH;
	private JButton btnModifier;
	private JButton btnSupprimer;
	private JButton btnEnregistrer;
	private JScrollPane scrollLstLigue;
	private JScrollPane scrollLstEmploye;
	private JButton btnFermer;
	
	public FenetreLigue(GestionPersonnel GP, Ligue ligue) {
		super();
		
		pan = new JPanel();
		this.setContentPane(pan);
		pan.setLayout(null);
        this.setSize(800, 500);
        this.setResizable(false);
        this.setTitle("Gestion des ligues");
        
        this.gestPers = GP;
        this.ligueEnCours = ligue;
        
        initComponents();
        getLstLigues();
        getLstEmployes();
	}
	
	public FenetreLigue(GestionPersonnel GP) throws Exception {
		super();
		 
		
		pan = new JPanel();
		this.setContentPane(pan);
		pan.setLayout(null);
        this.setSize(800, 500);
        this.setResizable(false);
        this.setTitle("Gestion des ligues");
        
        this.gestPers = GP;
        
        initComponents();
        getLstLigues();
        getLstEmployes();
        //Appel de la fonction pour charger liste ligues
	}
	
	private void initComponents() {

		this.getContentPane().setBackground(new Color(44, 92, 158));
		myListLigues = new DefaultListModel<>();
		
		lstLigues = new JList<>(myListLigues);
		//lstLigues.setBounds(20, 20, 150, 250);
		lstLigues.addListSelectionListener(this);
		scrollLstLigue = new JScrollPane(lstLigues);
		scrollLstLigue.setBounds(20, 20, 200, 390);
		scrollLstLigue.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		separatorV = new JSeparator(SwingConstants.VERTICAL);
		separatorV.setLocation(250, 0);
		separatorV.setSize(2, 500);
		
		btnAddLigue = new JButton("Créer une nouvelle ligue");
		btnAddLigue.setLocation(350, 20);
		btnAddLigue.setSize(320, 20);
		btnAddLigue.addActionListener(new ListenerCreerLigue());

		lblNomLigue = new JLabel("Ligue : ");
		lblNomLigue.setForeground(Color.WHITE);
		lblNomLigue.setLocation(350, 60);
		lblNomLigue.setSize(50, 20);
		
		txtNomLigue = new MyTextField(10, 20);
		txtNomLigue.setEditable(false);
		txtNomLigue.setLocation(400, 60);
		txtNomLigue.setSize(100, 20);
		
		lblNomAdmin = new JLabel("Admin : ");
		lblNomAdmin.setForeground(Color.WHITE);
		lblNomAdmin.setLocation(350, 100);
		lblNomAdmin.setSize(50, 20);
		
		txtNomAdmin = new MyTextField(10, 20);
		txtNomAdmin.setEditable(false);
		txtNomAdmin.setLocation(400, 100);
		txtNomAdmin.setSize(100, 20);
		
		lblListeEmploye = new JLabel("Liste des employés : ");
		lblListeEmploye.setForeground(Color.WHITE);
		lblListeEmploye.setLocation(350, 140);
		lblListeEmploye.setSize(200, 20);
		
		//Positionnement de la liste des employés :
		myListEmployes = new DefaultListModel<>();
		
		lstEmployes = new JList<>(myListEmployes);
//		lstEmployes.setBounds(350, 170, 320, 180);
		lstEmployes.addMouseListener(new MouseAdapter());
		scrollLstEmploye = new JScrollPane(lstEmployes);
		scrollLstEmploye.setBounds(350, 170, 320, 180);
		scrollLstEmploye.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		btnAddEmploye = new JButton("Ajouter un employe");
		btnAddEmploye.setLocation(350, 370);
		btnAddEmploye.setSize(320, 20);
		btnAddEmploye.addActionListener(new ListenerEmploye());
		if(ligueEnCours == null)
			btnAddEmploye.setEnabled(false);
		
		separatorH = new JSeparator();
		separatorH.setLocation(250, 410);
		separatorH.setSize(550, 2);
		
		//Positionnement sous le bouton "ajouter un employé : 
		btnModifier = new JButton("Modifier");
		btnModifier.setLocation(350, 430);
		btnModifier.setSize(150, 20);
		btnModifier.setVisible(false);
		btnModifier.addActionListener(new ActionLigue());
		
		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setLocation(520, 430);
		btnSupprimer.setSize(150, 20);
		btnSupprimer.setVisible(false);
		btnSupprimer.addActionListener(new ActionLigue());
		
		btnEnregistrer = new JButton("Enregistrer");
		btnEnregistrer.setLocation(350, 430);
		btnEnregistrer.setSize(320, 20);
		btnEnregistrer.setVisible(false);
		btnEnregistrer.addActionListener(new ListenerEnregistrer());
		
		btnFermer = new JButton("Fermer");
		btnFermer.setLocation(20, 430);
		btnFermer.setSize(100, 20);
		btnFermer.addActionListener(new ActionLigue());
		
		//Positionnement sous la liste des ligues : 
//		btnModifier = new JButton("Modifier");
//		btnModifier.setLocation(20, 400);
//		btnModifier.setSize(200, 20);
//		
//		btnSupprimer = new JButton("Supprimer");
//		btnSupprimer.setLocation(20, 430);
//		btnSupprimer.setSize(200, 20);
		
		//Ajout des éléments sur le pannel.
		//pan.add(lstLigues);
		pan.add(scrollLstLigue);
		pan.add(separatorV);
		pan.add(btnAddLigue);
		pan.add(lblNomLigue);
		pan.add(txtNomLigue);
		pan.add(lblNomAdmin);
		pan.add(txtNomAdmin);
		pan.add(lblListeEmploye);
//		pan.add(lstEmployes);
		pan.add(scrollLstEmploye);
		pan.add(btnAddEmploye);
		pan.add(separatorH);
		pan.add(btnModifier);
		pan.add(btnSupprimer);
		pan.add(btnEnregistrer);
		pan.add(btnFermer);
	}
	
	public void actionPerformed(ActionEvent event) {
		event.getSource();
	}
	
	public void valueChanged(ListSelectionEvent event) {
		
		txtNomLigue.setText("");
		txtNomAdmin.setText("");
		myListEmployes.clear();
		ligueEnCours = null;
		String selectLigue = (String)lstLigues.getSelectedValue();
		txtNomLigue.setText(selectLigue);
		for (Ligue ligue : gestPers.getLigues())
		{
			if(ligue.getNom().equals(selectLigue))
			{
				ligueEnCours = ligue;
				break;
			}
		}
		if(ligueEnCours!=null && ligueEnCours.getAdministrateur() != null)
		{
			txtNomAdmin.setText(ligueEnCours.getAdministrateur().getNom()+" "+ligueEnCours.getAdministrateur().getPrenom());
			btnAddEmploye.setEnabled(true);
		}
		
		getLstEmployes();
		btnModifier.setVisible(true);
		btnSupprimer.setVisible(true);
//			myListEmployes.addElement(ligue.getEmployes().toString());
	}
	
	private void getLstLigues() {
		if(gestPers.getLigues().size() > 0)
		{
			myListLigues.removeAllElements();
			int index = 0;
			for (Ligue ligue : gestPers.getLigues())
			{
				myListLigues.addElement(ligue.getNom());
				
				if(ligueEnCours != null && ligueEnCours.getNom().equals(ligue.getNom()))
					lstLigues.setSelectedIndex(index);
				index++;
			}
			
		}
	}
	
	private void getLstEmployes() {
		
		if(ligueEnCours != null && ligueEnCours.getEmployes().size() > 0)
		{
			myListEmployes.clear();
			for (Employe employe : ligueEnCours.getEmployes())
			{
				if(employe.getdateArrivee()!=null)
					myListEmployes.addElement(employe.getNom() + " " + employe.getPrenom());
			}
		}
	}
	
	
	private void createEmploye() throws Exception {
		FenetreEmploye CreerEmploye = new FenetreEmploye(gestPers, ligueEnCours, null);
    	CreerEmploye.setVisible(true);
    	
    	dispose();
	}
	
	private class ListenerEmploye implements ActionListener {
		public void actionPerformed(ActionEvent event) {
    		try {
				createEmploye();
			} catch (Exception e) {
				e.printStackTrace();
    		}
		}
	}
	
	private class ListenerCreerLigue implements ActionListener {
		public void actionPerformed(ActionEvent event) {
    		try {
				txtNomLigue.setText("");
				txtNomAdmin.setText("");
				txtNomLigue.setEditable(true);
				btnEnregistrer.setVisible(true);
				btnModifier.setVisible(false);
				btnSupprimer.setVisible(false);
				myListEmployes.clear();
				
			} catch (Exception e) {
				e.printStackTrace();
    		}
		}
	}
	
	private class ListenerEnregistrer implements ActionListener {
		public void actionPerformed(ActionEvent event)
		{
			try
			{
				btnEnregistrer.setVisible(false);
				btnModifier.setVisible(true);
				btnSupprimer.setVisible(true);
				txtNomLigue.setEditable(false);
				
				ligueEnCours=gestPers.addLigue(txtNomLigue.getText());
				myListLigues.addElement(txtNomLigue.getText());
				lstLigues.setSelectedValue(txtNomLigue.getText(), true);
			}
			catch (Exception ex)
			{
				ex.getMessage();
			}
		}
	}
	
	
	private class ActionLigue implements ActionListener {
		public void actionPerformed(ActionEvent event)
		{
			Object source=event.getSource();
			try 
			{
				if(source == btnSupprimer)
				{
					//Suppression en base de la ligue + suppression de la ligue dans la liste des ligues.
					gestPers.remove(ligueEnCours);
					myListLigues.removeElement(ligueEnCours.getNom());
					
					ligueEnCours = null;				 
				}
				else if(source == btnModifier)
				{
					if(btnModifier.getText().equals("Modifier"))
					{
						txtNomLigue.setEditable(true);
						btnModifier.setText("Enregistrer");					
					}					
					else if(btnModifier.getText().equals("Enregistrer"))
					{
						String oldName=ligueEnCours.getNom();
						ligueEnCours.setNom(txtNomLigue.getText());
						txtNomLigue.setEditable(false);
						btnModifier.setText("Modifier");
						
						getLstLigues();
						
					}
				}
				else if(source == btnFermer)
					dispose();
			}
			catch (Exception e)
			{
				System.out.print(e.getMessage());
			}
		}
	}
	
	private class MouseAdapter implements MouseListener 
	{
		public void mouseClicked(MouseEvent evt) 
		{
			//JList<String> list = (JList<String>) evt.getSource();
			if (evt.getClickCount() == 2) 
			{
	    	// Double-clic détecté
				int index = lstEmployes.locationToIndex(evt.getPoint());
				Object selectedItem = lstEmployes.getModel().getElementAt(index);
				System.out.println("Double-clic sur : " + selectedItem);
				FenetreEmploye ModifierEmploye=null;
				for (Employe employe : ligueEnCours.getEmployes())
				{
					if(selectedItem.equals(employe.getNom() + " " + employe.getPrenom()))
					{
						// Appel du constructeur FenetreEmploye avec GestPers, LigueEnCours et employe
						try
						{
							ModifierEmploye = new FenetreEmploye(gestPers, ligueEnCours, employe);
							ModifierEmploye.setVisible(true);
							
							dispose();
						}
						catch (Exception ex)
						{
							System.out.println(ex.getMessage());							
						}
						
						break;
					}
				}
			}
		}
		

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}	
	}
}

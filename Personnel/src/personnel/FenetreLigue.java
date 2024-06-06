package personnel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class FenetreLigue extends JFrame implements ActionListener {
	
	private Employe root;
	private GestionPersonnel gestPers;
	private Ligue lstLigue;
	private JPanel pan;
	private JButton btnAddLigue;
	private JComboBox <String> cbLigues;
	private JSeparator separator;
	private JLabel lblNomLigue;
	private MyTextField txtNomLigue;
	private JLabel lblNomAdmin;
	private MyTextField txtNomAdmin;
	private JLabel lblListeEmploye;
	private DefaultListModel<String> myList;
	private JList<String> employe;
	private JButton btnAddEmploye;
	private JButton btnModifier;
	private JButton btnSupprimer;
	
	
	public Employe getEmploye() {
    	return root;
    }
    
    public void setEmploye(Employe employe) {
    	this.root=employe;
    }

	public FenetreLigue(GestionPersonnel GP, Employe employe) throws Exception {
		super();
		 
		
		pan = new JPanel();
		this.setContentPane(pan);
		pan.setLayout(null);
        this.setSize(800, 500);
        this.setResizable(false);
        this.setTitle("Gestion des ligues");
        this.setLocationRelativeTo(null);
        
        this.gestPers = GP;
        this.root = employe;
        
        initComponents();
	}
	
	private void initComponents(){

//Création d'une ComboBox affichant la liste des ligues
		Object[] ligues = new Object[] {
				"Ligue 1",
				"Ligue 2",
				"Ligue 3"
		};
		cbLigues = new JComboBox(ligues);
		cbLigues.setBounds(20, 20, 200, 50);
		cbLigues.addActionListener(this);
//Fin ajout ComboBox affichant la liste des ligues.
		
		separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setLocation(250, 0);
		separator.setSize(2, 500);
		
		btnAddLigue = new JButton("Ajouter une ligue");
		btnAddLigue.setLocation(350, 20);
		btnAddLigue.setSize(320, 20);

		lblNomLigue = new JLabel("Ligue : ");
		lblNomLigue.setLocation(350, 80);
		lblNomLigue.setSize(50, 20);
		
		txtNomLigue = new MyTextField(10, 20);
		txtNomLigue.setText(cbLigues.getSelectedItem().toString());
		txtNomLigue.setEditable(false);
		txtNomLigue.setLocation(400, 80);
		txtNomLigue.setSize(100, 20);
		
		lblNomAdmin = new JLabel("Admin : ");
		lblNomAdmin.setLocation(350, 140);
		lblNomAdmin.setSize(50, 20);
		
		txtNomAdmin = new MyTextField(10, 20);
		txtNomAdmin.setEditable(false);
		txtNomAdmin.setLocation(400, 140);
		txtNomAdmin.setSize(100, 20);
		
		lblListeEmploye = new JLabel("Liste des employés : ");
		lblListeEmploye.setLocation(350, 180);
		lblListeEmploye.setSize(200, 20);
		
		//Positionnement de la liste des employés :
		myList = new DefaultListModel<>();
		myList.addElement("Employe 1");
		myList.addElement("Employe 2");
		myList.addElement("Employe 3");
		myList.addElement("Employe 4");
		
		employe = new JList<>(myList);
		employe.setBounds(350, 210, 320, 220);
		
		btnAddEmploye = new JButton("Ajouter un employe");
		btnAddEmploye.setLocation(350, 435);
		btnAddEmploye.setSize(320, 20);
		
		//Positionnement sous le bouton "ajouter un employé : 
//		btnModifier = new JButton("Modifier");
//		btnModifier.setLocation(350, 430);
//		btnModifier.setSize(150, 20);
//		
//		btnSupprimer = new JButton("Supprimer");
//		btnSupprimer.setLocation(520, 430);
//		btnSupprimer.setSize(150, 20);
		
		
		//Positionnement sous la liste des ligues : 
		btnModifier = new JButton("Modifier");
		btnModifier.setLocation(20, 400);
		btnModifier.setSize(200, 20);
		
		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setLocation(20, 430);
		btnSupprimer.setSize(200, 20);
		
		//Ajout des éléments sur le pannel.
		pan.add(cbLigues);
		pan.add(separator);
		pan.add(btnAddLigue);
		pan.add(lblNomLigue);
		pan.add(txtNomLigue);
		pan.add(lblNomAdmin);
		pan.add(txtNomAdmin);
		pan.add(lblListeEmploye);
		pan.add(employe);
		pan.add(btnAddEmploye);
		pan.add(btnModifier);
		pan.add(btnSupprimer);
	}
	
	public void actionPerformed(ActionEvent event) {
		event.getSource();
		//String result = (String) cbLigues.getSelectedItem();
		//lblNomLigue.setText("Vous avez sélectionné la " + result);
}
	
}

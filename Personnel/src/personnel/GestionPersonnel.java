package personnel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Gestion du personnel. Un seul objet de cette classe existe.
 * Il n'est pas possible d'instancier directement cette classe, 
 * la méthode {@link #getGestionPersonnel getGestionPersonnel} 
 * le fait automatiquement et retourne toujours le même objet.
 * Dans le cas où {@link #sauvegarder()} a été appelé lors 
 * d'une exécution précédente, c'est l'objet sauvegardé qui est
 * retourné.
 */

public class GestionPersonnel implements Serializable
{
	private static final long serialVersionUID = -105283113987886425L;
	private static GestionPersonnel gestionPersonnel = null;
	private SortedSet<Ligue> ligues;
	private SortedSet<Employe> employes;
	private Employe root;
	public final static int SERIALIZATION = 1, JDBC = 2, 
			TYPE_PASSERELLE = JDBC;
	private static Passerelle passerelle = TYPE_PASSERELLE == JDBC ? new jdbc.JDBC() : new serialisation.Serialization();	
	
	/**
	 * Retourne l'unique instance de cette classe.
	 * Crée cet objet s'il n'existe déjà.
	 * @return l'unique objet de type {@link GestionPersonnel}.
	 */
	
	public static GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible
	{
		if (gestionPersonnel == null)
		{
			gestionPersonnel = passerelle.getGestionPersonnel();
			if (gestionPersonnel == null)
				gestionPersonnel = new GestionPersonnel();
			
			
		}
		return gestionPersonnel;
	}

	public GestionPersonnel() throws SauvegardeImpossible
	{		
		if (gestionPersonnel != null)
			throw new RuntimeException("Vous ne pouvez créer qu'une seule instance de cet objet.");
		
		gestionPersonnel = this;
		ligues = new TreeSet<>();
		employes = new TreeSet<>();
		//Vérification de l'existance d'un root en BDD
		this.root= Employe.getMyRoot(this,"root", "toor");
		//Dans le cas où il n'existerait pas, on l'ajoute en BDD avec ses valeurs par défaut.
		if(this.root==null)
			this.root=new Employe(this,null,"root","","","toor",null,null);
		
		employes.add(this.root);
	}
	
	public void sauvegarder() throws SauvegardeImpossible
	{
		passerelle.sauvegarderGestionPersonnel(this);
	}
	
	/**
	 * Retourne la ligue dont administrateur est l'administrateur,
	 * null s'il n'est pas un administrateur.
	 * @param administrateur l'administrateur de la ligue recherchée.
	 * @return la ligue dont administrateur est l'administrateur.
	 */
	
	public Ligue getLigue(Employe administrateur)
	{
		if (administrateur.estAdmin(administrateur.getLigue()))
			return administrateur.getLigue();
		else
			return null;
	}

	/**
	 * Retourne toutes les ligues enregistrées.
	 * @return toutes les ligues enregistrées.
	 */
	
	public SortedSet<Ligue> getLigues()
	{
		return Collections.unmodifiableSortedSet(ligues);
	}
	
	public SortedSet<Employe> getEmployes()
	{
		return Collections.unmodifiableSortedSet(employes);
	}
	
//	public SortedSet<Employe> getEmployes(Ligue ligue)
//	{
//		SortedSet<Employe> lstEmployeFromLigue=new TreeSet<>();
//		System.out.println("ligueID recherchée = "+ligue.getId());
//		for(Employe emp :employes)
//		{	
//			System.out.println("Ligue de l'employé : "+emp.getLigue().getId());
//			//System.out.println(emp.getNom());
//			if(emp.getLigue().getId()==ligue.getId())
//				lstEmployeFromLigue.add(emp);
//		}
//		return Collections.unmodifiableSortedSet(lstEmployeFromLigue);
//	}

	public Ligue addLigue(String nom) throws SauvegardeImpossible
	{
		Ligue ligue = new Ligue(this, nom);
		ligues.add(ligue);
		return ligue;
	}
	
	public Ligue addLigue(int id, String nom) throws SauvegardeImpossible
	{
		Ligue ligue = new Ligue(this, id, nom);
		ligues.add(ligue);
		return ligue;
	}
	
//	public Employe addEmploye(Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart) throws Exception
//	{
//		Employe employe = new Employe(this, ligue, nom, prenom, mail, password, dateArrivee, dateDepart);
//		employes.add(employe);
//		return employe;
//	}
//	public Employe addEmploye(Employe employe) throws Exception
//	{
//		Employe myEmploye = new Employe(this,  employe.getLigue(), employe.getNom(), employe.getPrenom(), employe.getMail(), employe.getPassword(), employe.getdateArrivee(), employe.getdateDepart(), employe.getId());
//		employes.add(myEmploye);
//		return myEmploye;
//	}
	
	
	//Il faut créer une méthode addRoot() pour charger un root présent dans la bdd
		//Le root déclaré en dur en variable d'instance doit être modifié. Il doit hériter des données de celui dans la base.
		//Sa différence avec les autres employés est son appartenance à aucune ligue.
	public Employe addRoot(String nom, String password) throws SauvegardeImpossible
	{
		
		///TODO Vérifier l'existence d'un root en BDD
		//SI Existe Récupérer les valeurs afin de modifier this.root
		Employe myRoot = null;
		try 
		{
			myRoot=passerelle.getMyRoot(this);
			
			this.root = myRoot;						
		}
		catch (Exception e)
		{
			System.err.println("Une erreur est survenue lors de la récupération des informations du root en BDD : ");
			e.printStackTrace();
		}
		return myRoot;
	}
	
	int insertRoot(Employe root) throws SauvegardeImpossible
	{
		return passerelle.insertRoot(root);
	}
	
	

	void remove(Ligue ligue)
	{
		ligues.remove(ligue);
	}
	
	int insert(Ligue ligue) throws SauvegardeImpossible
	{
		return passerelle.insert(ligue);
	}
	
	void update(Ligue ligue) throws SauvegardeImpossible
	{
		passerelle.update(ligue);
	}
	
	void updateAdmin(Ligue ligue, Employe employe) throws SauvegardeImpossible
	{
		passerelle.updateAdministrateur(ligue, employe);
	}
	
	int insertEmploye(Employe employe) throws SauvegardeImpossible
	{
		return passerelle.insertEmploye(employe);
	}
		
	void updateEmploye(Employe employe) throws SauvegardeImpossible
	{
		passerelle.updateEmploye(employe);
	}
	
	void deleteEmploye(Employe employe) throws SauvegardeImpossible
	{
		passerelle.deleteEmploye(employe);
	}
	
	void delete(Ligue ligue) throws SauvegardeImpossible
	{
		passerelle.delete(ligue);
	}
	/**
	 * Retourne le root (super-utilisateur).
	 * @return le root.
	 */
	
	public Employe getRoot()
	{
		return root;
	}
}

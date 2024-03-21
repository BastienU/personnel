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
	private Employe root = new Employe(this, null, "root", "", "", "toor", null, null);
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
			throw new RuntimeException("Vous ne pouvez créer qu'une seuls instance de cet objet.");
		ligues = new TreeSet<>();
		employes = new TreeSet<>();
		gestionPersonnel = this;
		gestionPersonnel.addRoot(root.getNom(), root.getPassword());
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
	
	public Employe addEmploye(Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart) throws Exception
	{
		Employe employe = new Employe(this, ligue, nom, prenom, mail, password, dateArrivee, dateDepart);
		employes.add(employe);
		return employe;
	}
	public Employe addEmploye(Employe employe) throws Exception
	{
		Employe myEmploye = new Employe(this,  employe.getLigue(), employe.getNom(), employe.getPrenom(), employe.getMail(), employe.getPassword(), employe.getdateArrivee(), employe.getdateDepart(), employe.getId());
		employes.add(myEmploye);
		return myEmploye;
	}
	
	public Employe addRoot(String nom, String password)
	{
		Employe root = this.root;
		employes.add(root);
		return root;
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
	
	int insertEmploye(Employe employe) throws SauvegardeImpossible
	{
		return passerelle.insertEmploye(employe);
	}
	
	int insertRoot(Employe root) throws SauvegardeImpossible
	{
		return passerelle.insertRoot(root);
	}
	
	void updateEmploye(Employe employe) throws SauvegardeImpossible
	{
		passerelle.updateEmploye(employe);
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

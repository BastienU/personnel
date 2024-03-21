package personnel;

import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Convert;
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.RegexConversion;

import com.mysql.cj.util.StringUtils;

/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent 
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé, 
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 */

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private Ligue ligue;
	private SortedSet<Employe> employes;
	private GestionPersonnel gestionPersonnel;
	private LocalDate dateArrivee;
	private LocalDate dateDepart;
	private DateTimeFormatter dtf;
	private boolean isAdmin;
	private int id;
	
	public Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart, int id)
	{
		this.gestionPersonnel = gestionPersonnel;
		if(ligue != null)
			this.ligue = ligue;
		if(nom != null)
			this.nom = nom;
		if(prenom != null)
			this.prenom = prenom;
		if(mail != null)
			this.mail = mail;
		if(password != null)
			this.password = password;		
		
		if(dateArrivee != null)
			this.dateArrivee = dateArrivee;
		if(dateDepart != null)
			this.dateDepart = dateDepart;
		this.id = id;
	}
	
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart) throws SauvegardeImpossible
	{
		this(gestionPersonnel, ligue, nom, prenom, mail, password, dateArrivee, dateDepart, -1);
		this.id = gestionPersonnel.insertRoot(this);
	}

//	Employe(GestionPersonnel gestionPersonnel, int id, Ligue ligue,String nom, String prenom, String password, String mail,  LocalDate dateArrivee, LocalDate dateDepart) throws Exception
//	{
//		this.setNom(nom);
//		this.gestionPersonnel = gestionPersonnel;
//		this.id = id;
//	}
	
	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	
	/**
	 *  Getteur si l'employé est un admin ou non d'une ligue
	 */
	 public boolean estAdmin(Ligue ligue)
	{
		isAdmin = ligue.getAdministrateur() == this;
		return isAdmin;
	}
	 
	/* Methode de mise à jour d'un admin d'une ligue*/
	 public void setAdminLigue(boolean admin) throws SauvegardeImpossible
	{
		this.isAdmin=admin;
	
		ligue = this.getLigue();
		if(ligue!=null)
			ligue.setAdministrateur(this);
	}
	
	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 */
	
	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé. 
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 * @throws Exception 
	 */
	
	public void setNom(String nom) throws Exception
	{
		if(nom != null)
		{
			this.nom = nom;
			gestionPersonnel.updateEmploye(this);
		}
		else
			throw new Exception("Le nom ne peut pas être vide");
	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 * @throws Exception 
	 */

	public void setPrenom(String prenom) throws Exception
	{
		if(prenom != null)
		{
			this.prenom = prenom;
			gestionPersonnel.updateEmploye(this);
		}
		else
			throw new Exception("Le prénom ne peut pas être vide");
		
	}	
	

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 * @throws Exception 
	 */

	public void setMail(String mail) throws Exception
	{
		if(mail != null)
		{
			String regx = "^(.+)@(\\S+)$";
			if(regx == getMail())
			{
				this.mail = mail;
				gestionPersonnel.updateEmploye(this);
			}
			else
				throw new Exception ("Le format du mail doit être de la forme xxxx@xxxx.xx");
		}
		else
			throw new Exception("Le mail ne peut pas être vide");
	}

	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé. 
	 * @throws Exception 
	 */
	
	public void setPassword(String password) throws Exception
	{
		if(password != "")
		{
			this.password = password;
			gestionPersonnel.updateEmploye(this);
		}
		else
			throw new Exception("Le mot de passe ne peut pas être vide");
	}
	
	public String getPassword()
	{
		return password;
	}

	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}
	
	public SortedSet<Employe> getEmployes() {
		return employes;
	}

	public void setEmployes(SortedSet<Employe> employes) {
		this.employes = employes;
	}
	
	public int getId()
	{
		return id;
	}
	
	//Format des dates : yyyy-MM-dd
	public LocalDate getdateArrivee()
	{
		if(dateArrivee!=null && !StringUtils.isNullOrEmpty(dateArrivee.toString()))
		{
			dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
			String strDA = dateArrivee.format(dtf);
			return LocalDate.parse(strDA, dtf);			
		}
		else
			return null;
	}
	
	public void setdateArrivee(LocalDate dateArrivee) throws DateInvalide, SauvegardeImpossible
	{ 		
		if(dateArrivee != null) 
		{				
			if (dateDepart!=null && dateArrivee.isAfter(dateDepart))
				throw new DateInvalide(new Exception("La date d'arrivée ne peut être postérieure à celle de départ"));
			else
			{
				this.dateArrivee = dateArrivee;			
				gestionPersonnel.updateEmploye(this);				
			}
		}
		else
			throw new DateInvalide(new Exception ("La date d'arrivée ne peut pas être nulle."));
	}
	
	//Format des dates : yyyy-MM-dd
	public LocalDate getdateDepart()
	{
		if(dateDepart!=null && !StringUtils.isNullOrEmpty(dateDepart.toString()))
		{
			dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
			String strDD = dateDepart.format(dtf);
			return LocalDate.parse(strDD, dtf);		
		}
		else
			return null;
	}
	
	public void setdateDepart(LocalDate dateDepart) throws DateInvalide, SauvegardeImpossible
	{
		if(dateDepart != null)
		{
			if(dateArrivee != null)
			{
				if (dateDepart.isBefore(dateArrivee)) 
					throw new DateInvalide(new Exception("La date de départ ne peut pas être antérieure à celle d'arrivée"));
				else
				{
					this.dateDepart = dateDepart;
					gestionPersonnel.updateEmploye(this);				
				}
			}
			else
				throw new DateInvalide(new Exception("La date d'arrivée ne peut pas être nulle"));
		}
		else
			this.dateDepart = null;
//			throw new DateInvalide(new Exception("La date de départ ne peut pas être nulle"));
	}

	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 * @throws SauvegardeImpossible 
	 */
	
	public void remove() throws SauvegardeImpossible
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			getLigue().remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}
	
	/*
	 * Permet d'afficher les informations complètes de l'employé sélectionné	 * 
	 */
	public String infoEmploye()
	{
		String infoPerso = "Nom : "+nom+"\r\nPrénom : "+prenom+"\r\nMail : "+mail+"\r\nMDP:"+password+"\r\nDate d'arrivée : "+getdateArrivee()+"\r\nDate de départ : "+getdateDepart();
		return infoPerso;
	}
	
}

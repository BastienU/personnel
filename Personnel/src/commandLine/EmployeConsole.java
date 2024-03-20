package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;

import com.mysql.cj.util.StringUtils;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import personnel.SauvegardeImpossible;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
//		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
		
		// Permet d'afficher les informations complètes de l'employé sélectionné 
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe.infoEmploye());});
	}

	ListOption<Employe> selectEmploye()
	{
		return (employe) -> selectEmploye(employe);		
	}
	
	ListOption<Employe> editerEmploye()
	{
		return (employe) -> editerEmploye(employe);		
	}
	
	ListOption<Employe> deleteEmploye()
	{
		return (employe) -> deleteEmploye(employe);		
	}
	

	Option editerEmploye(Employe employe)
	{
			Menu menu = new Menu("Modifier le compte " + employe.getNom(), "m");
			menu.add(afficher(employe));
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
//			Personnel 2 Saisie des dates en ligne de commande ajouté le 21/02/2024
			menu.add(changerDateArrivee(employe));
			menu.add(changerDateDepart(employe));
//			Fin ajout Saisie des dates.
			menu.addBack("q");
			return menu;
	}
	
	Option selectEmploye(Employe employe)
	{
		Menu menu = new Menu("Gérer le compte de " + employe.getNom(), "g");
		menu.add(editerEmploye(employe));
		menu.add(changerAdministrateur(employe));
		menu.add(deleteEmploye(employe));
		menu.addBack("q");
		return menu;
	}
	
	Option deleteEmploye(Employe employe)
	{
		Menu menu = new Menu("Supprimer l'employe " + employe.getNom(), "d");
		menu.add(deleteSelectedEmploye(employe));
		menu.addBack("q");
		return menu;
	}

	private Option deleteSelectedEmploye(final Employe employe)
	{
		return new Option("Confirmer la suppression de " + employe.getNom(), "d", () -> {try {
			employe.remove();
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}});
		
		
		// Faire en sorte de revenir sur le menu avant la sélection de l'employé
		
	}
	
	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", () -> {try {
			employe.setNom(getString("Nouveau nom : "));
		} catch (Exception e) {
			e.printStackTrace();
		}});
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {try {
			employe.setPrenom(getString("Nouveau prénom : "));
		} catch (Exception e) {
			e.printStackTrace();
		}});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {try {
			employe.setMail(getString("Nouveau mail : "));
		} catch (Exception e) {
			e.printStackTrace();
		}});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {try {
			employe.setPassword(getString("Nouveau password : "));
		} catch (Exception e) {
			e.printStackTrace();
		}});
	}
	
	
	private Option changerAdministrateur(final Employe employe)
	{
		return new Option("Changer l'administrateur de la ligue", "y", () -> {try {
			employe.setAdminLigue(true);
		} catch (Exception e) {
			e.printStackTrace();
		}});
	}
	
	private Option changerDateArrivee(final Employe employe)
	{
		//Permet d'afficher la date d'arrivée enregistrée en BDD
//		LocalDate dateArrivee = employe.getdateArrivee();
//		if(dateArrivee != null)
//			System.out.println("La date d'arrivée : " + dateArrivee.toString());
		
		return new Option("Changer la date d'arrivée", "a", () -> {try {
			String newDate=getString("Nouvelle date d'arrivée (au format YYYY-mm-DD) : ");
			if(!StringUtils.isNullOrEmpty(newDate))
			{
				LocalDate newDateArrivee = LocalDate.parse(newDate);
				employe.setdateArrivee(newDateArrivee);				
			}
			else
				System.out.println("La date d'arrivée ne peut être nulle.");
		} catch (Exception e) {
			e.printStackTrace();
		}});
	}
	
	private Option changerDateDepart(final Employe employe)
	{
		//Permet d'afficher la date de départ enregistrée en BDD
//		LocalDate dateDepart = employe.getdateDepart();
//		if(dateDepart != null)
//			System.out.println("La date de départ : " + dateDepart.toString());
		
		return new Option("Changer la date de départ", "d", () -> {try {
			String newDate=getString("Nouvelle date de départ (au format YYYY-mm-DD) : ");
			if(!StringUtils.isNullOrEmpty(newDate))
			{
				LocalDate newDateDepart = LocalDate.parse(newDate);
				employe.setdateDepart(newDateDepart);				
			}
			else
				employe.setdateDepart(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}});
	}

}

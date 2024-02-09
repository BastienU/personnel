package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
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
		menu.add(DeleteSelectedEmploye(employe));
		menu.addBack("q");
		return menu;
	}

	private Option DeleteSelectedEmploye(final Employe employe)
	{
		return new Option("Confirmer la suppression de " + employe.getNom(), "d", () -> {employe.remove();});
		
		
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

}

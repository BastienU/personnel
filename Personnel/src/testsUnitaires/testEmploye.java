package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.hamcrest.text.IsEmptyString;
import org.junit.jupiter.api.Test;

import personnel.DateInvalide;
import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;


class testEmploye {
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

	
	// test avec assertThrows sur le setteur dateArrivee
	@Test
	  void testsurSetDateArrivee() throws SauvegardeImpossible{
		//creation d'empluyés 
		
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14")); 
		assertEquals(LocalDate.parse("2024-01-13"), employe.getdateArrivee());
		// tester valeur dateDepart, nom, prénom, mdp, …
		
		employe = ligue.addEmploye("Boucharde", "Gérarde", "g.bouchard@gmail.come", "azertye", LocalDate.parse("2020-01-13"), LocalDate.parse("2022-01-14")); 
		assertEquals(LocalDate.parse("2020-01-13"), employe.getdateArrivee());
		// pareil
	    }
	
    @Test
    void testSetdateArrivee_InvalidDate() throws SauvegardeImpossible {
        Ligue liga = gestionPersonnel.addLigue("Liga");
        // Test pour une date d'arrivée invalide
        assertThrows(DateTimeParseException.class, () -> {
            Employe goat = liga.addEmploye("Jude", "Belli", "heyJude@rm.com", "goal", LocalDate.parse("2021-05-0"), null);
            }, "La format de date d'arrivée doit être YYYY-MM-DD");
    }
	//tests sur les formats de dates possibles
	@Test
	void testCreationEmploye() throws SauvegardeImpossible
	{
        // test format de date arrivée pas dans le bon format 
		 Ligue liga = gestionPersonnel.addLigue("Liga");	
 		 assertThrows(DateTimeParseException.class, () -> {
 	 	     Employe goat = liga.addEmploye("Jude", "Belli", "heyJude@rm.com", "goal", null,LocalDate.parse("dat-invalide"));
	        }, "la date de départ doit être au format yyyy-mm-dd.");	
	}
	
	@Test 
	//TU sur la dateArrivee
	void testSetDateArrivee() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléches");
		Employe employe = ligue.addEmploye("Bouchar", "Gérar", "bouchard@gmail.com", "zerty", LocalDate.parse("2024-01-13"), LocalDate.parse("2024-01-14"));
		
		//test sur le setteur
		try {
			employe.setdateArrivee(LocalDate.parse("2020-10-10"));
			assertEquals(LocalDate.parse("2020-10-10"), employe.getdateArrivee());
		} 
		catch (DateInvalide e) 
		{
			e.printStackTrace();			
		}
		
		//le setteur marche bien
		
		try {
			employe.setdateArrivee(LocalDate.parse("2021-10-10"));
			assertEquals(LocalDate.parse("2021-10-10"), employe.getdateArrivee());
		} catch (DateInvalide e) 
		{
			e.printStackTrace();
			
		}
		
		// on peut mettre une date à null
		try {
			employe.setdateArrivee(null);
			assertEquals(null, employe.getdateArrivee());
		} catch (DateInvalide e) {
			
			e.printStackTrace();
		}	
		
		// cas ou la date n'est pas renseignée à la création de l'employe
		 employe = ligue.addEmploye("Bouchar", "Gérar", "bouchard@gmail.com", "zerty", null, null);
		 assertEquals(null, employe.getdateArrivee());
		//on peut bien modifier une date qui était à null
		 
		 try 
		 {
			employe.setdateArrivee(LocalDate.parse("2022-01-08"));
			 assertEquals(LocalDate.parse("2022-01-08"), employe.getdateArrivee());
		 } 
		 catch (DateInvalide e) 
		 {
			  e.printStackTrace();
		 };
		
	}
	// TESTS sur les Dates d'arrivée et de Depart
	
	@Test 
	// tests sur la date de départ
	void testSetDateDepart() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Liga");
		Employe employe = ligue.addEmploye("Vinicius", "Junior", "vini.jr@gmail.com", "azerty", LocalDate.parse("2018-01-13"), null);
		
		//Initialisation de la date d'arrivée :
		try 
		{
			employe.setdateArrivee(LocalDate.parse("2020-10-10"));
			assertEquals(LocalDate.parse("2020-10-10"), employe.getdateArrivee());
		} 
		catch (DateInvalide e) 
		{
			e.printStackTrace();			
		}
		// Cas #1 - date départ à null
		assertEquals(null, employe.getdateDepart());
		
		//test setteur : cas normal:ok
		try 
		{
			employe.setdateDepart(LocalDate.parse("2024-01-10"));
			assertEquals(LocalDate.parse("2024-01-10"), employe.getdateDepart());
		} 
		catch (DateInvalide e) 
		{
			e.printStackTrace();			
		}
		
		// test setteur : cas date depart antérieure à date arrivee
		
		 try 
			{
				employe.setdateDepart(LocalDate.parse("2017-04-13"));
				assertEquals(LocalDate.parse("2017-04-13"), employe.getdateDepart());
			} 
			catch (DateInvalide e) 
			{
				e.printStackTrace();			
			}
		 
		 
		//on essaie de mettre une date de depart, alors qu'il n'y a pas de date d'arrivee
			
		//Une date d'arrivée nulle n'est pas possible car déjà géré dans le setter de la date d'arrivée
		 try
		 {
			 employe.setdateArrivee(null);
			 employe.setdateDepart(LocalDate.parse("2024-01-10"));
			 
		 }
		 catch (DateInvalide e) 
			{
				e.printStackTrace();			
			}
		 
		 //On test le cas du 29-02-2023
		 
		 try
		 {
			 employe.setdateArrivee(LocalDate.parse("2017-01-10"));
			 employe.setdateDepart(LocalDate.parse("2023-02-29"));			 
		 }
		 catch (DateTimeParseException e) 
		{
			e.printStackTrace();			
		}
		 catch (DateInvalide e) 
		{
			e.printStackTrace();			
		}
		 
	}
	
	
		 @Test
		 //test sur le format du mail
		 void testSetMail() throws SauvegardeImpossible{
			 
			 //test cas où le mail est null
			 Ligue ligue = gestionPersonnel.addLigue("BernaLigue");
			 Employe employe = ligue.addEmploye("Bernadette", "Delaroche", "Bernadette.dlroche@gmail.com", "Bernadette1905", LocalDate.parse("2012-09-11"), LocalDate.parse("2023-03-28"));
			 
			 try
			 {
				 employe.setMail(null);
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }
			 
			 
			 //test cas où le mail est de la forme xxx.xxx@xxx.xx
			 
			 try 
			 {
				 employe.setMail("Bernadette.dlroche@gmail.com");
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }
			 
			 //test cas où le mail est de la forme
			 
			 try
			 {
				 employe.setMail("B3rn@d3tt3@gm41l.c0m");
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }
			 
			 //test cas où le mail est différent de @gmail.com
			 
			 try
			 {
				 employe.setMail("Bernadette.dlroche@aol.fr");
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }
			 
			 //test cas normal
			 
			 try
			 {
				 employe.setMail("Bernadette.dlroche.@gmail.com");
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }
		 }
		 
		 @Test
		 //test sur le prenom
		 void testSetPrenom() throws SauvegardeImpossible{
			 
			 Ligue ligue = gestionPersonnel.addLigue("Ligue Jeanjean");
			 Employe employe = ligue.addEmploye("Jean-Eude", "Isril", "jean-eude.isril@gmail.com", "19785296", LocalDate.parse("2012-12-12"), LocalDate.parse("2014-10-13"));
			 
			 //test prenom null
			 
			 try
			 {
				 employe.setPrenom(null);
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }
			 
		 }
		 
		 @Test
		 //test sur le nom
		 void testSetNom() throws SauvegardeImpossible{
			 
			 Ligue ligue = gestionPersonnel.addLigue("Gisligue");
			 Employe employe = ligue.addEmploye("Gislène", "Gabros", "gigigabros@gmail.com", "Gagisbros95", LocalDate.parse("1993-06-29"), LocalDate.parse("2000-09-08"));
			 
			 //test nom null
			 
			 try
			 {
				 employe.setNom(null);
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }
			 
		 }
		 
		 @Test
		 //test sur le password
		 void testSetPassword() throws SauvegardeImpossible{
			 
			 Ligue ligue = gestionPersonnel.addLigue("Liga");
			 Employe employe = ligue.addEmploye("Gislène", "Gabros", "gigigabros@gmail.com", "Gagisbros95", LocalDate.parse("1993-06-29"), LocalDate.parse("2000-09-08"));
			 
			 //test password null
			 
			 try
			 {
				 employe.setPassword("");
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }
			 
		 }
		 
		 
		 
		 @Test
		 //test suppression d'un employé
		 void testRemoveEmploye() throws SauvegardeImpossible{
			 
			 Ligue ligue = gestionPersonnel.addLigue("Ligue");
			 Employe employe = ligue.addEmploye("Dupont", "Michel", "dupontlajoie@gmail.com", "", null, null);
			 ligue.setAdministrateur(employe);

			 
				 if(employe != null) //test existance de l'employé.
				 {
					 if(employe.estRoot()) //vérifie si l'employé est root.
						 System.out.println("Vous ne pouvez pas supprimer le root !");
					 else
					 {						 
						 if(employe.estAdmin(ligue)) //vérifie si l'employé est admin.
						 {
							 employe.remove();
							 System.out.println("L'administrateur a bien été supprimé");
						 }
						 else
						 {
							 employe.remove();
							 System.out.println("L'employé " + employe + " a bien été supprimé de la ligue " + ligue + ".");
						 }
					 }
				 }
				 else
					 System.out.println("Il n'existe pas d'employé");
		 }
		 
		 
		 @Test
		 //test suppression d'un employé
		 void testRemoveLigue() throws SauvegardeImpossible{
			 
			 Ligue ligue = gestionPersonnel.addLigue("Ligue");
			 Employe employe = ligue.addEmploye("Dupont", "Michel", "dupontlajoie@gmail.com", "", null, null);
		 
			 try
			 {
				 ligue.remove();
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
			 }
		 }

}
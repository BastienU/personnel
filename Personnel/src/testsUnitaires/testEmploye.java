package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
	        
		/* Ligue liga = gestionPersonnel.addLigue("Liga");	
		Employe goat = liga.addEmploye("Vini", "jr", "vini.jr@rm.com", "vini", LocalDate.parse("2020-10-1"),null);
		//test avec AssertThrows sur des dates à null
	    assertThrows(DateTimeParseException.class, () -> {
	    goat.setdateArrivee(LocalDate.parse("2020-10-10"));
	    assertEquals(LocalDate.parse("2020-10-10"), goat.getdateArrivee());
	    }, "Le test devrait fonctionner.");*/
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
//		assertThrows(DateInvalide.class, () -> {
//			employe.setdateDepart(LocalDate.parse("2024-01-13"));
//		}, "La date de départ est invalide");
		
		// test setteur : cas date depart antérieure à date arrivee
//		 assertThrows(DateInvalide.class, () -> {
//				employe.setdateDepart(LocalDate.parse("2017-04-13"));
//		 }, "La date d'arrivée ne doit pas être sup à celle de départ");
		
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
}
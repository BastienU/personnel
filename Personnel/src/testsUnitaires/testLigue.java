package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.parse("1903-06-09"), LocalDate.parse("2024-06-09")); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void addTest() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addTest("Ligue de test");
		Employe employe = ligue.addTest("Dupont", "Michel", "Michel.Dupont@gmail.com", "Mot de passe très original", LocalDate.parse("2024-06-09"),LocalDate.parse("1976-12-25")); 
		
	}
}

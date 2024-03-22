package personnel;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel();
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible;
	public int insertEmploye(Employe employe) throws SauvegardeImpossible;
	public int insertRoot(Employe root) throws SauvegardeImpossible;
	public void update(Ligue ligue) throws SauvegardeImpossible;
	public void updateEmploye(Employe employe);
	public void deleteEmploye(Employe employe);
}

package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel()
	{
		GestionPersonnel gestionPersonnel = null;
		try {
			gestionPersonnel = new GestionPersonnel();
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}
		try 
		{
			String requete = "SELECT * FROM ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				try {
					Ligue myLigue=new Ligue(gestionPersonnel,ligues.getInt(1), ligues.getString(2));
					
					gestionPersonnel.addLigue(myLigue.getId(), myLigue.getNom());
					
					PreparedStatement ps = connection.prepareStatement("SELECT * FROM employe WHERE idLigue = ?");
					ps.setInt(1, ligues.getInt(1));
					System.out.println("idLigue = " + ligues.getInt(1));
					ResultSet employes = ps.executeQuery();
					System.out.println("Pour la ligue = " + myLigue.getNom());
					while(employes.next())
					{
						System.out.println("nom = " + employes.getString(2));
						System.out.println("prenom = " + employes.getString(3));
						System.out.println("mail = " + employes.getString(4));
						System.out.println("pwd = " + employes.getString(5));
						System.out.println("dteArrivee = " + employes.getDate(6));
						System.out.println("dteDep = " + employes.getDate(7));
						System.out.println("idEmploye = " + employes.getInt(1));
						
						LocalDate da = null;
						LocalDate dd = null;
						
						if(employes.getDate(6) != null)
							da = LocalDate.parse(employes.getString(6));
						if(employes.getDate(7) != null)
							dd = LocalDate.parse(employes.getString(7));
						
						
						Employe employe = new Employe(gestionPersonnel, myLigue, employes.getString(2), employes.getString(3), employes.getString(4), employes.getString(5), da, dd, employes.getInt(1));
						gestionPersonnel.addEmploye(employe);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}	
	

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("INSERT INTO ligue (nom) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	public void update(Ligue ligue) throws SauvegardeImpossible
	{
		try 
		{
			PreparedStatement instruction;		
			instruction = connection.prepareStatement("UPDATE ligue SET nom = ? WHERE idLigue = ?");
			instruction.setString(1, ligue.getNom());
			//System.out.println(ligue.getNom());
			instruction.setInt(2, ligue.getId());
			//System.out.println(ligue.getId());
			instruction.executeUpdate();
			
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}
	
	public int insertRoot(Employe root) throws SauvegardeImpossible
	{
		try 
	    {
			PreparedStatement checkIfExists = connection.prepareStatement("SELECT * FROM employe WHERE nom = ?");
	        checkIfExists.setString(1, root.getNom());
	        ResultSet result = checkIfExists.executeQuery();
	        result.next();
	        int id = result.getInt(1);
	        if (id > 0)
	          System.out.println("L'identifiant du compte root est : " + id);
	        else
	        {	
	        	PreparedStatement instruction;
	        	instruction = connection.prepareStatement("INSERT INTO employe (nom, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
	        	instruction.setString(1, root.getNom());
	        	instruction.setString(2, root.getPassword());
	        	instruction.executeUpdate();
	        	result = instruction.getGeneratedKeys();
	        	result.next();
	        	id = result.getInt(1);	        
	        }
	        
	        return id;
	    }
		catch (SQLException exception)
		{
			exception.printStackTrace();
	        throw new SauvegardeImpossible(exception);
		}
	}
	
	public void updateEmploye(Employe employe)
	{
		try 
		{
			PreparedStatement instruction;		
			instruction = connection.prepareStatement("UPDATE employe SET nom = ?, prenom = ?, mail = ?, password = ? WHERE idEmploye = ?");
			instruction.setString(1, employe.getNom());
			System.out.println(employe.getNom());
			instruction.setString(2, employe.getPrenom());
			System.out.println(employe.getPrenom());
			instruction.setString(3, employe.getMail());
			System.out.println(employe.getMail());
			instruction.setString(4, employe.getPassword());
			System.out.println(employe.getPassword());
			instruction.setInt(5, employe.getId());
			System.out.println(employe.getId());
			instruction.executeUpdate();
			
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();			
		}
	}

	public int insertEmploye(Employe employe) throws SauvegardeImpossible 
	{
	    try 
	    {
	        PreparedStatement instruction;
	        if (employe.getLigue() != null) 
	        {
	            instruction = connection.prepareStatement("INSERT INTO employe (nom, prenom, mail, password, dateArrivee, dateDepart, estAdmin, idLigue) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
	            instruction.setInt(8, employe.getLigue().getId());
	        } 
	        else 
	            instruction = connection.prepareStatement("INSERT INTO employe (nom, prenom, mail, password, dateArrivee, dateDepart, estAdmin) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
	        	        
	        instruction.setString(1, employe.getNom());
	        instruction.setString(2, employe.getPrenom());
	        instruction.setString(3, employe.getMail());
	        instruction.setString(4, employe.getPassword());
	        instruction.setDate(5, java.sql.Date.valueOf(employe.getdateArrivee()));
	        instruction.setDate(6, java.sql.Date.valueOf(employe.getdateDepart()));
	        instruction.setBoolean(7,  employe.estAdmin(null));

	        instruction.executeUpdate();
	        ResultSet id = instruction.getGeneratedKeys();
	        id.next();
	        return id.getInt(1);
	    } 
	    catch (SQLException exception) 
	    {
	        exception.printStackTrace();
	        throw new SauvegardeImpossible(exception);
	    }
	}
}
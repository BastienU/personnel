package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
					gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
				} catch (SauvegardeImpossible e) {
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
	
	public int update(Ligue ligue) throws SauvegardeImpossible
	{
		try 
		{
			PreparedStatement instruction;		
			instruction = connection.prepareStatement("UPDATE ligue SET nom = ? WHERE idLigue = ?", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());
			//System.out.println(ligue.getNom());
			instruction.setInt(2, ligue.getId());
			//System.out.println(ligue.getId());
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			if (id.next())
				return id.getInt(1);
			else
				return -1;
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
			PreparedStatement checkIfExists = connection.prepareStatement("SELECT COUNT(*) FROM employe WHERE nom = ?");
	        checkIfExists.setString(1, root.getNom());
	        ResultSet result = checkIfExists.executeQuery();
	        result.next();
	        int rowCount = result.getInt(1);
	        if (rowCount > 0)
	          System.out.println("Un utilisateur root existe déjà !");
	        
	        PreparedStatement instruction;
	        instruction = connection.prepareStatement("INSERT INTO employe (nom, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
	        instruction.setString(1, root.getNom());
	        instruction.setString(2, root.getPassword());
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
package cool.ic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cool.ic.beans.IntegrityDBDetails;
import cool.ic.utils.Commons;

public class IntegrityDB 
{
	private static Logger log = Logger.getLogger(IntegrityDB.class);
	
	public void createIntegrityTable() 
	{
		Connection cn = openConnection();
		
		String ddl ="CREATE TABLE IntegrityData (FullFileName TEXT (1000) PRIMARY KEY, HashValue TEXT (150) NOT NULL, HashAlgorithem TEXT (10) NOT NULL, FileSize	NUMERIC NOT NULL)";

		Statement stmt = null;
		
		try 
		{
			stmt = cn.createStatement();
		} 
		catch (SQLException e) 
		{
			log.error("Cannot create statement. Exiting.", e);
			System.exit(0);
		}
		
		try 
		{
			stmt.executeUpdate(ddl);
		} 
		catch (SQLException e) 
		{
			log.error("Cannot create table. Exiting.", e);
			System.exit(0);
		}
		
		try 
		{
			stmt.close();
			cn.close();
		} 
		catch (SQLException e) 
		{
			log.error("Error closing statement or connection", e);
		}
	}

	private Connection openConnection() 
	{
	    Connection c = null;
	    try 
	    {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Integrity.db");
	    } 
	    catch ( Exception e ) 
	    {
	      log.error("Cannot open connection to database. Exiting.", e);
	      System.exit(0);
	    }
	    
		return c;
	}
	
	public void insert(String fileName, String hashValue, long fileSize) throws Exception
	{
		String insert = "INSERT INTO IntegrityData VALUES (?,?,?,?)";
		
		Connection cn = openConnection();
		
		PreparedStatement stmt = cn.prepareStatement(insert);
		
		stmt.setString(1, fileName);
		stmt.setString(2, hashValue);
		stmt.setString(3, "SHA-512");
		stmt.setLong(4, fileSize);
		
		stmt.executeUpdate();
	}

	public Map<String, IntegrityDBDetails> getFileMap() 
	{
		Map <String, IntegrityDBDetails> dbMap = new HashMap <String, IntegrityDBDetails>();
		String select = "select * from IntegrityData";
		
		Connection cn = openConnection();
		
		Statement stmt = null;
		
		try 
		{
			stmt = cn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		} 
		catch (SQLException e) 
		{
			Commons.logErrAndExit(log, "Error creating statement", e);
		}
		
		ResultSet rs = null;
		try 
		{
			rs = stmt.executeQuery(select);
		} 
		catch (SQLException e) 
		{
			Commons.logErrAndExit(log, "Error while execuiting query", e);
		}
		
		try 
		{
			while(rs.next())
			{
				String fileName = rs.getString("FullFileName");
				String hashValue = rs.getString("HashValue");
				String hashAlgo = rs.getString("HashAlgorithem");
				long fileSize = rs.getLong("FileSize");
				
				dbMap.put(fileName, new IntegrityDBDetails(hashValue, hashAlgo, fileSize));
			}
		} 
		catch (SQLException e) 
		{
			Commons.logErrAndExit(log, "Error while iterating result set", e);
		}
		
		return dbMap;
	}
}

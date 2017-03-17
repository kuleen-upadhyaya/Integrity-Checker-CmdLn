package cool.ic.init;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class InitializationProperties 
{
	private static Logger log = Logger.getLogger(InitializationProperties.class);
	
	private static Properties properties;
	
	private InitializationProperties()
	{
		
	}
	
	private static void initialize()
	{
		InputStream is = null;
		
		try
		{
			is = new FileInputStream("init.properties");
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		
		if(is == null)
		{
			log.error("init.properties file not found");
			System.exit(0);
		}
		
		properties = new Properties();

		try 
		{
			properties.load(is);
		}
		catch (IOException e) 
		{
			log.error("Cannot load properties", e);
			System.exit(0);
		};
	}
	
	public static String getRoot()
	{
		if(properties == null)
		{
			initialize();
		}
		
		return properties.getProperty("ROOT_DIR");
	}

	public static String getDBName() 
	{
		if(properties == null)
		{
			initialize();
		}
		
		return properties.getProperty("DB_NAME");
	}
}

package cool.ic;

import org.apache.log4j.Logger;

public class Main 
{
	private static Logger log = Logger.getLogger(Main.class);
	
	public static void main(String ... args) 
	{
		log.info("Starting Integrity Checker");
		
		IntegrityChecker ic = new IntegrityChecker();
		
		ic.start();
		
		log.info("Exiting Integrity Checker");
	}
}

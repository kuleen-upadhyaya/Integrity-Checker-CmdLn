package cool.ic.utils;

import org.apache.log4j.Logger;

public class Commons 
{
	private Commons()
	{
		
	}
	
	public static void logErrAndExit(Logger logger, String message, Throwable t)
	{
		logger.error(message, t);
	}
}

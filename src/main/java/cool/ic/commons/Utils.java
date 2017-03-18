package cool.ic.commons;

import org.apache.log4j.Logger;

public class Utils 
{
	private Utils()
	{
		
	}
	
	public static void logErrAndExit(Logger logger, String message, Throwable t)
	{
		logger.error(message, t);
	}
	
	public static long toMB(long fileSize) 
	{
		return fileSize/(1024*1024);
	}

	public static String getTimeString(long startTime, long endTime) 
	{
		StringBuilder sbrTime = new StringBuilder();
		
		long timeTakenSec = (endTime - startTime)/1000000000L;
		
		int hours = (int) timeTakenSec / 3600;
	    int remainder = (int) timeTakenSec - hours * 3600;
	    int mins = remainder / 60;
	    remainder = remainder - mins * 60;
	    int secs = remainder;

	    sbrTime.append("Total time taken : ");
		sbrTime.append(hours);
		sbrTime.append(" Hr ");
		sbrTime.append(mins);
		sbrTime.append(" Min ");
		sbrTime.append(secs);
		sbrTime.append(" Sec");
		
	    return sbrTime.toString();
	}
}

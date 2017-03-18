package cool.ic;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cool.ic.Files.FileLister;
import cool.ic.beans.FileStatistics;
import cool.ic.beans.IntegrityDBDetails;
import cool.ic.checksum.CheckSum;
import cool.ic.db.IntegrityDB;
import cool.ic.init.InitializationProperties;
import cool.ic.progress.ConsoleProgressBar;
import cool.ic.utils.Commons;

public class InitializeIntegrity 
{
	private static Logger log = Logger.getLogger(InitializeIntegrity.class);
	
	public void initialize() 
	{
		long startTime = System.nanoTime();
		
		Map <String, IntegrityDBDetails> dbMap = new HashMap <String, IntegrityDBDetails>();
		
		String dbName = InitializationProperties.getDBName();
		
		/*Recreate database file if already exists*/
		log.info("Removing any pre-existing database");
		File dbFile = new File (dbName);
		
		if(dbFile.exists())
		{
			log.info("Existing database found. Removing it.");
			dbFile.delete();
		}
		
		IntegrityDB idb = new IntegrityDB();
		
		log.info("Creating table");
		idb.createIntegrityTable();
		
		log.info("Creating list of files");
		 
		FileStatistics fs = FileLister.getFileStats();
		 
		Set <File> fileSet = fs.getFileSet();
		 
		log.info("Files to process : " + fileSet.size());

		log.info("Starting calculation of file checksum");
		
		long processedFileSize=0;
		int noOfFilesProcessed=0;
		int totalNoOfFiles = fileSet.size();
		long totalFileSize = fs.getTotalFileSize();
		long totalFileSizeMB = Commons.toMB(totalFileSize);
		int percentage = 0;
		
		for(File file : fileSet)
		{
			if(!file.canRead())
			{
				log.warn("Cannot read file : " + file.getAbsolutePath());
				continue;
			}
			
			String hashValue = CheckSum.getCheckSum(file);
			
			if(hashValue != null)
			{
				try 
				{
					IntegrityDBDetails idd = new IntegrityDBDetails(hashValue, "SHA-512", file.length());
					dbMap.put(file.getAbsolutePath(), idd);
				} 
				catch (Exception e) 
				{
					log.error("Error while inserting record", e);
				}
			}
			
			noOfFilesProcessed++;
			processedFileSize += file.length();
			
			percentage = (int) (processedFileSize * 100 / totalFileSize);
			ConsoleProgressBar.showProgress(percentage, noOfFilesProcessed, totalNoOfFiles, Commons.toMB(processedFileSize), totalFileSizeMB);
		}
		
		idb.insertBulk(dbMap);
		
		percentage = (int) (processedFileSize * 100 / totalFileSize);
		ConsoleProgressBar.showProgress(percentage, noOfFilesProcessed, totalNoOfFiles, Commons.toMB(processedFileSize), totalFileSizeMB);
		
		long endTime = System.nanoTime();
		
		String totalTimeTaken = Commons.getTimeString(startTime, endTime);
		
		log.info(totalTimeTaken);
		System.out.println("\n\n" + totalTimeTaken);
	}

}

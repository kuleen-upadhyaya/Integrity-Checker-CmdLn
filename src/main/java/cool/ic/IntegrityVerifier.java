package cool.ic;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import cool.ic.Files.FileLister;
import cool.ic.beans.FileStatistics;
import cool.ic.beans.IntegrityDBDetails;
import cool.ic.checksum.CheckSum;
import cool.ic.db.IntegrityDB;
import cool.ic.progress.ConsoleProgressBar;
import cool.ic.utils.Commons;

public class IntegrityVerifier 
{
	private static Logger log = Logger.getLogger(IntegrityVerifier.class);

	public void verifyIntegrity() 
	{
		long startTime = System.nanoTime();
		
		log.info("Preparing list from Integrity DB");
		
		IntegrityDB idb = new IntegrityDB();
		Map <String, IntegrityDBDetails> dbMap = idb.getFileMap();

		log.info("Preparing list from file system");
		FileStatistics fs = FileLister.getFileStats();
		Set <File> fileSet = fs.getFileSet();
		
		log.info("Comparing DB entries with file system entries");
		Set <String> deletedFilesFromFileSystem = compareDBWithFileSystemEntries(dbMap, fileSet);
		
		log.info("Comparing file system entries with DB entries");
		Set <String> newFilesInFileSystem = compareFileSystemEntriesWithDB(fileSet, dbMap);
		
		log.info("Checking integrity of the files that matched DB");
		Set<String> filesWithDifferentHashValue = checkIntegrityForRestOfTheFiles(dbMap, fileSet, deletedFilesFromFileSystem, newFilesInFileSystem);
		
		log.info("Files deleted from file system");
		for(String fileName : deletedFilesFromFileSystem)
		{
			log.info(fileName);
		}

		log.info("Files added to file system");
		for(String fileName : newFilesInFileSystem)
		{
			log.info(fileName);
		}
		
		log.info("Files that could have lost integrity");
		
		for(String fileName : filesWithDifferentHashValue)
		{
			log.info(fileName);
		}
		
		long endTime = System.nanoTime();
		
		String totalTimeTaken = Commons.getTimeString(startTime, endTime);
		
		log.info(totalTimeTaken);
		System.out.println("\n\n" + totalTimeTaken);

	}

	private Set<String> checkIntegrityForRestOfTheFiles(
			Map<String, IntegrityDBDetails> dbMap, Set<File> fileSet,
			Set<String> deletedFilesFromFileSystem,
			Set<String> newFilesInFileSystem) 
	{
		Set<String> filesWithDifferentHashValue = new HashSet<String> ();
		
		Set <String> dbSet = copy(dbMap.keySet());
		dbSet.removeAll(deletedFilesFromFileSystem);
		dbSet.removeAll(newFilesInFileSystem);
		
		long processedFileSize=0;
		int noOfFilesProcessed=0;
		int totalNoOfFiles = fileSet.size();
		long totalFileSize = getTotalFileSize(dbMap);
		long totalFileSizeMB = Commons.toMB(totalFileSize);
		int percentage = 0;
		
		for(String fileName : dbSet)
		{
			String hashValue = CheckSum.getCheckSum(fileName);
			
			IntegrityDBDetails idb = dbMap.get(fileName);

			if(!idb.getHashValue().equals(hashValue))
			{
				filesWithDifferentHashValue.add(fileName);
			}
			
			noOfFilesProcessed++;
			processedFileSize += idb.getFileSize();
			
			percentage = (int) (processedFileSize * 100l / totalFileSize);
			ConsoleProgressBar.showProgress(percentage, noOfFilesProcessed, totalNoOfFiles, Commons.toMB(processedFileSize), totalFileSizeMB);
		}

		percentage = (int) (processedFileSize * 100l / totalFileSize);
		ConsoleProgressBar.showProgress(percentage, noOfFilesProcessed, totalNoOfFiles, Commons.toMB(processedFileSize), totalFileSizeMB);
		
		return filesWithDifferentHashValue;
	}

	private long getTotalFileSize(Map<String, IntegrityDBDetails> dbMap)
	{
		long totalFileSize = 0;
		
		for(Entry <String, IntegrityDBDetails> entry : dbMap.entrySet())
		{
			IntegrityDBDetails idb = entry.getValue();
			totalFileSize += idb.getFileSize();
		}
		
		return totalFileSize;
	}
	
	// checks if file exists in database but not in file system.
	private Set <String> compareDBWithFileSystemEntries(Map<String, IntegrityDBDetails> dbMap, Set <File> fileSet)
	{
		Set <String> fileSetStr = convertToStringSet(fileSet);
		
		Set <String> dbSet = copy(dbMap.keySet());
		
		dbSet.removeAll(fileSetStr);
		
		return dbSet;
	}

	// checks if the file exists in file system but not in database
	private Set <String> compareFileSystemEntriesWithDB(Set <File> fileSet, Map<String, IntegrityDBDetails> dbMap) 
	{
		Set <String> fileSetStr = convertToStringSet(fileSet);
		
		Set <String> dbSet = copy(dbMap.keySet());
		
		fileSetStr.removeAll(dbSet);
		
		return fileSetStr;
	}
	
	private Set <String> convertToStringSet (Set <File> fileSet)
	{
		Set <String> fileSetStr = new HashSet <String>();
		
		for(File file : fileSet)
		{
			fileSetStr.add(file.toString());
		}
		
		return fileSetStr;
	}
	
	private Set <String> copy (Set <String> original)
	{
		Set <String> copy = new HashSet<String>();
		
		for(String str : original)
		{
			copy.add(str);
		}
		
		return copy;
	}
}
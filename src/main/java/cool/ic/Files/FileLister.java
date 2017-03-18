package cool.ic.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import cool.ic.beans.FileStatistics;
import cool.ic.init.InitializationProperties;

public final class FileLister 
{
	private static Set <File> fileSet = new HashSet<File>();
	private static long totalFileSize;
	private static Logger log = Logger.getLogger(FileLister.class);
	
  public static FileStatistics getFileStats()
  {
	System.out.println("Preparing file list. Please wait ...");
	
    String rootDir = InitializationProperties.getRoot();
    FileVisitor<Path> fileProcessor = new ProcessFile();
    
    fileSet.clear();
    totalFileSize = 0;
    
    try 
    {
    	
    	
		Files.walkFileTree(Paths.get(rootDir), fileProcessor);
	} 
    catch (IOException e) 
    {
    	log.error("Error while preparing list of files", e);
	}
    
    System.out.println("File list prepared ...\n");
    
    return new FileStatistics(fileSet, totalFileSize);
  }

  private static final class ProcessFile extends SimpleFileVisitor<Path> 
  {
    @Override 
    public FileVisitResult visitFile(Path aFile, BasicFileAttributes aAttrs) throws IOException 
    {
    	fileSet.add(aFile.toFile());
    	totalFileSize+= aFile.toFile().length();
    	
		return FileVisitResult.CONTINUE;
    }
    
    @Override
    public FileVisitResult preVisitDirectory(Path aDir, BasicFileAttributes aAttrs) throws IOException 
    {
   		return FileVisitResult.CONTINUE;
    }
    
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) throws IOException 
    {
    	log.error("Visiting failed for :" + file);
        return FileVisitResult.SKIP_SUBTREE;
    }
  }
} 
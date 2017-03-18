package cool.ic.checksum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import cool.ic.commons.Globals;
 
public class CheckSum 
{
 
    private static Logger log = Logger.getLogger(CheckSum.class);
    private static long processedFileSize = 0;
    private static long totalFileSize = 0;
    
    public static String getCheckSum(String fileName)
    {
    	return getCheckSum(new File(fileName));
    }
    
	public static String getCheckSum(File file) 
	{
		MessageDigest md = null;
		try 
		{
			md = MessageDigest.getInstance("SHA-512");
		} 
		catch (NoSuchAlgorithmException e) 
		{
			log.error("Cannot find Message Digest for SHA-512");
			System.exit(0);
		}

		FileInputStream fis = null;
		
		try 
		{
			fis = new FileInputStream(file);
		}
		catch (FileNotFoundException e)
		{
			log.error("File not found : " + file, e);
			return null;
		}

		byte[] dataBytes = new byte[16384];
		
		totalFileSize = file.length();
		processedFileSize = 0;
		
		int nread = 0;
		
		try
		{
			while ((nread = fis.read(dataBytes)) != -1)
			{
				Globals.indvPers = (int)(processedFileSize * 100 / totalFileSize);
				md.update(dataBytes, 0, nread);
				processedFileSize += 16384;
			}
			
			Globals.indvPers = 100;
		} 
		catch (IOException e) 
		{
			log.error("Error while reading file : " + file, e);
			return null;
		}

		try
		{
			fis.close();
		}
		catch (IOException e)
		{
			log.error("Cannot close file", e);
		}

		byte[] mdbytes = md.digest();

		// convert the byte to hex format
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mdbytes.length; i++)
		{
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();
	}
}
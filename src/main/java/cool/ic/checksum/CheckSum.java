package cool.ic.checksum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
 
public class CheckSum 
{
 
    private static Logger log = Logger.getLogger(CheckSum.class);

    public static String getCheckSum(String fileName)
    {
    	return getCheckSum(new File(fileName));
    }
    
	public static String getCheckSum(File fileName) 
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
			fis = new FileInputStream(fileName);
		}
		catch (FileNotFoundException e)
		{
			log.error("File not found : " + fileName, e);
			return null;
		}

		byte[] dataBytes = new byte[1024];

		int nread = 0;

		try
		{
			while ((nread = fis.read(dataBytes)) != -1)
			{
				md.update(dataBytes, 0, nread);
			}
		} 
		catch (IOException e) 
		{
			log.error("Error while reading file : " + fileName, e);
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
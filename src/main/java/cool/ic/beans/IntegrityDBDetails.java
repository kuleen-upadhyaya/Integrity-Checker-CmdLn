package cool.ic.beans;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class IntegrityDBDetails 
{
	String hashValue;
	String hashAlgo;
	long fileSize;

	public IntegrityDBDetails(String hashValue, String hashAlgo, long fileSize)
	{
		this.hashValue = hashValue;
		this.hashAlgo = hashAlgo;
		this.fileSize = fileSize;
	}
	
	public String getHashValue() 
	{
		return hashValue;
	}
	
	public String getHashAlgo() 
	{
		return hashAlgo;
	}
	
	public long getFileSize() 
	{
		return fileSize;
	}

	public void setFileSize(long fileSize) 
	{
		this.fileSize = fileSize;
	}
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31).
				append(hashValue).
				append(hashAlgo).
				append(fileSize).
				hashCode();
	}
	
	public boolean equals(Object other)
	{
		if (!(other instanceof IntegrityDBDetails))
            return false;
		
        if (other == this)
            return true;

        IntegrityDBDetails rhs = (IntegrityDBDetails) other;
        
        return new EqualsBuilder().
            append(hashValue, rhs.hashValue).
            append(hashAlgo, rhs.hashAlgo).
            append(fileSize, rhs.fileSize).
            isEquals();

	}
}

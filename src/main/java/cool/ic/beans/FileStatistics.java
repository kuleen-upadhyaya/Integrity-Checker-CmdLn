package cool.ic.beans;

import java.io.File;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class FileStatistics 
{
	Set <File> fileSet;
	long totalFileSize;

	public FileStatistics (Set <File> fileSet, long totalFileSize)
	{
		this.fileSet = fileSet;
		this.totalFileSize = totalFileSize;
	}
	
	public Set <File> getFileSet() 
	{
		return fileSet;
	}
		
	public long getTotalFileSize() 
	{
		return totalFileSize;
	}
	
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31).
				append(fileSet).
				append(totalFileSize).
				hashCode();
	}
	
	public boolean equals(Object other)
	{
		if (!(other instanceof FileStatistics))
            return false;
		
        if (other == this)
            return true;

        FileStatistics rhs = (FileStatistics) other;
        
        return new EqualsBuilder().
            append(fileSet, rhs.fileSet).
            append(totalFileSize, rhs.totalFileSize).
            isEquals();
	}
}

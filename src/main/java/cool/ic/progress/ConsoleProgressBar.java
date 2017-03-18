package cool.ic.progress;
public class ConsoleProgressBar 
{
    public static void showProgress(int percentage, long processedFileSize, long totalFileSize, long processedMB, long totalMB)
    {
    	final int progressBarMaxLength = 20;
    	final char progressBarDarkChar = '#';
    	final char progressBarLightChar = ' ';
    	
    	System.out.print("\r[");
    	
    	int progressBarCurrentLength = progressBarMaxLength*percentage/100;
    	
    	for(int ctr=0; ctr<progressBarCurrentLength; ctr++)
    	{
    		System.out.print(progressBarDarkChar);
    	}
    	
    	for (int ctr=0; ctr<progressBarMaxLength-progressBarCurrentLength;ctr++)
    	{
    		System.out.print(progressBarLightChar);
    	}
    	
    	System.out.print("]");

    	System.out.printf(" %3s %% Files Processed : %d/%d %d/%d MB", percentage, processedFileSize, totalFileSize, processedMB, totalMB);
    }
}
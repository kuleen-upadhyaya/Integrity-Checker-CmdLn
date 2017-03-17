package cool.ic.progress;
public class ConsoleProgressBar 
{
    public static void showProgress(int percentage, long processedFileSize, long totalFileSize)
    {
    	int progressBarMaxLength = 40;
    	char progressBarDarkChar = 219;
    	char progressBarLightChar = 176;
    	
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

    	System.out.printf(" %3s %% Files Processed : %d/%d", percentage, processedFileSize, totalFileSize);
    }
}
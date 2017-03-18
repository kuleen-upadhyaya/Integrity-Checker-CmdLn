package cool.ic.progress;
public class ConsoleProgressBar 
{
    public static void showProgress(int overallPers, int indvPers, long processedFileSize, long totalFileSize, long processedMB, long totalMB)
    {
    	System.out.print("\r");
    	
    	drawProgressBar(overallPers);
    	drawProgressBar(indvPers);
    	
    	System.out.printf(" %d/%d Files %d/%d MB",  processedFileSize, totalFileSize, processedMB, totalMB);
    }

	private static void drawProgressBar(int percentage) 
	{
		final int progressBarMaxLength = 20;
    	final char progressBarDarkChar = '#';
    	final char progressBarLightChar = ' ';
    	
    	System.out.print("[");
    	
    	int progressBarCurrentLength = progressBarMaxLength*percentage/100;
    	
    	for(int ctr=0; ctr<progressBarCurrentLength; ctr++)
    	{
    		System.out.print(progressBarDarkChar);
    	}
    	
    	for (int ctr=0; ctr<progressBarMaxLength-progressBarCurrentLength;ctr++)
    	{
    		System.out.print(progressBarLightChar);
    	}
    	
    	System.out.printf(" %3s%%]", percentage);
	}
}
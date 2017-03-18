package cool.ic.commons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import cool.ic.progress.ConsoleProgressBar;

public class Globals 
{
	public static int overallPers;
	public static int indvPers;
	public static long noOfFilesProcessed;
	public static long totalNoOfFiles;
	public static long processedFileSize;
	public static long totalFileSizeMB;
	
	public static Timer progressTimer = new Timer(1000, new ActionListener()
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			showProgressbar();
		}
	});
	
	public static void showProgressbar()
	{
		ConsoleProgressBar.showProgress(overallPers, indvPers, noOfFilesProcessed, totalNoOfFiles, Utils.toMB(processedFileSize), totalFileSizeMB);
	}
}
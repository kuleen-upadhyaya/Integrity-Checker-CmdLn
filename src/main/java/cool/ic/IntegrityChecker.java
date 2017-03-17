package cool.ic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class IntegrityChecker 
{
	private static Logger log = Logger.getLogger(IntegrityChecker.class);
	
	public void start() 
	{
		showInitialOptions();
	}
	
	private void showInitialOptions() 
	{
		log.info("Showing initial Options");
		
		System.out.println("");
		System.out.println("[1] Initialize Integrity");
		System.out.println("[2] Verify Integrity");
		System.out.println("[3] Exit");
		System.out.println("Choose your option : ");
		
		log.info("Gathering input");
		
		String input = getInput();
		
		log.info("User selected : " + input);
		
		if(!isInputValid(input))
		{
			log.info("Invalid input. Asking again for input.");
			
			System.out.println("Invalid Input ... Please Try Again ...");
			
			showInitialOptions();
		}
		
		if(input.equals("1"))
		{
			System.out.println("Initializing Integrity ...");
			System.out.println("");
			initializeIntegrity();
			System.out.println("");
			System.out.println("");
			System.out.println("Integrity Successfully Initialized ...");
			System.out.println("");
			showInitialOptions();
		}
		else if (input.equals("2"))
		{
			System.out.println("Verifying Integrity ...");
			System.out.println("");
			verifyIntegrity();
			System.out.println("");
			System.out.println("");
			System.out.println("Verification Completed Successfully ...");
			System.out.println("");
			showInitialOptions();
		}
		else if (input.equals("3"))
		{
			log.info("User selected to exit");
		}
		
		
	}

	private void verifyIntegrity() 
	{
		IntegrityVerifier vi = new IntegrityVerifier();
		
		log.info("Verifying integrity");
		
		vi.verifyIntegrity();
		
		log.info("Verification completed");
	}

	private void initializeIntegrity() 
	{
		InitializeIntegrity i = new InitializeIntegrity();
		
		log.info("Initializing integrity database");
		
		i.initialize();
		
		log.info("Initializing integrity completed");
	}

	private boolean isInputValid(String input) 
	{
		Set <String> validInputs = new HashSet <String>();
		
		validInputs.add("1");
		validInputs.add("2");
		validInputs.add("3");
		
		if(validInputs.contains(input))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	private String getInput() 
	{
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		String selectedOption = "";
		try 
		{
			selectedOption = bufferReader.readLine();
		}
		catch (IOException e) 
		{
			log.error("Error while getting input", e);
		}

		return selectedOption;
	}
}

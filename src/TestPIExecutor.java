//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     5
//
//  File Name:     TestPIExecutor.java
//
//  Course:        COSC-4302 Operating Systems
//
//  Due Date:      03/29/2022 (Original, moved)
//
//  Instructor:    Fred Kumi 
//
//  Chapter:       5
//
//  Description:   A driver class to estimate PI using the monte carlo
//                 technique. This driver class displays instructions to 
//                 the user and passes user input to the PIEstimator class 
//                 for processing.
//
//*********************************************************************

import java.util.Scanner;
	
public class TestPIExecutor {
	
	private Scanner userIn = new Scanner(System.in);
	
    //***************************************************************
    //
    //  Method:       main
    // 
    //  Description:  The main method of the project
    //
    //  Parameters:   String array
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	public static void main(String[] args) {
		
		TestPIExecutor obj = new TestPIExecutor();
		PIEstimator estimator = new PIEstimator();
		obj.developerInfo();
		obj.printInstructions();
		
		int points = obj.getPoints();
		while(points != 0) { // user quits with 0
			
			double result = estimator.estimatePI(points); 
			System.out.println("Estimation: " + result);
			points = obj.getPoints();
		}
		
		System.out.println("Goodbye");
		
	}// end main method
	
    //***************************************************************
    //
    //  Method:       getPoints (Non Static)
    // 
    //  Description:  Attempts to get a positive integer from the user
    //                representing the number of random points to be 
    //                used for calculating PI.
    //
    //  Parameters:   None
    //
    //  Returns:      int
    //
    //***************************************************************
	public int getPoints() {
		
		boolean isValid = false;
		int points = 0;
		
		while(!isValid) {
			System.out.print("\nPlease enter the number of points: ");
			String input = userIn.nextLine();
			try {
				int temp = Integer.parseInt(input);
				if(temp >= 0) {
					points = temp;
					isValid = true;
				} else {
					System.out.println("Must be positive");
				}
			} catch (NumberFormatException e) {
				System.out.println("Must be an integer");
			}
		}
		
		return points;
		
	}// end getPoints method
	

    //***************************************************************
    //
    //  Method:       printInstructions (Non Static)
    // 
    //  Description:  Displays simple instructions to the user on how
    //                the use the program.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	public void printInstructions() {
		
		System.out.println("\nMonte Carlo Program");
		System.out.println("--------------------------");
		System.out.println("Enter a number of points");
		System.out.println("Enter 0 to quit");
		System.out.println("--------------------------");	
		
	}// end printInstructions method
	
    //***************************************************************
    //
    //  Method:       developerInfo (Non Static)
    // 
    //  Description:  The developer information method of the program.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void developerInfo()
    {
       System.out.println("Name:    Jeremy Aubrey");
       System.out.println("Course:  COSC 4302 Operating Systems");
       System.out.println("Program: 5");

    }// end developerInfo method
    
}// end TestPIExecutor class
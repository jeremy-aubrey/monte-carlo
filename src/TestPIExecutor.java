//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     4
//
//  File Name:     ThreadExecutor.java
//
//  Course:        COSC-4302 Operating Systems
//
//  Due Date:      03/13/2022
//
//  Instructor:    Fred Kumi 
//
//  Chapter:       4
//
//  Description:   A multi-threaded sorting application that generates a 
//                 random array of integers based on user input (a count)
//                 and distributes the sorting and merging work to separate
//                 threads.
//
//*********************************************************************

import java.util.Scanner;
	
public class TestPIExecutor {
	
	private Scanner userIn = new Scanner(System.in);
	private static int points = 0;
	
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
		obj.developerInfo();
		obj.printInstructions();
		
		int points = obj.getPoints();
		
		while(points != 0) {
			PIEstimator estimator = new PIEstimator();
			double result = estimator.calculatePI(points);
			System.out.println("Result: " + result);
			points = obj.getPoints();
		}
		
		System.out.println("Goodbye");
	}// end main method
	
	
	public int getPoints() {
		
		boolean isValid = false;
		int points = 0;
		
		while(!isValid) {
			System.out.print("Please enter the number of points: ");
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
	}
	

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
    //  Method:       getInput (Non Static)
    // 
    //  Description:  Gets input from the user.
    // 
    //  Parameters:   None
    //
    //  Returns:      String 
    //
    //**************************************************************
	public String getInput () {
		
		String input = userIn.nextLine();
		return input;
		
	}// end getInput method
	
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
    
}// end ThreadExecutor class
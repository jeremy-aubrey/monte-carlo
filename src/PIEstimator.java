//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     5
//
//  File Name:     PIEstimator.java
//
//  Course:        COSC-4302 Operating Systems
//
//  Due Date:      03/29/2022 (Original, moved)
//
//  Instructor:    Fred Kumi 
//
//  Chapter:       5
//
//  Description:   A class that performs estimations of PI using randomly
//                 generated points. Random point generation is performed
//                 in separate threads and updates to the static circlePoints 
//                 instance variable is performed in a synchronized manner
//                 using a Reentrant mutex lock.
//
//*********************************************************************

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PIEstimator {
	
	private int totalPoints; // total points (provided by user)
	private static int circlePoints; // global "in-circle" point count
	private int coreCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService pool;
	private Lock lock = new ReentrantLock(); // mutex lock
	
    //***************************************************************
    //
    //  Method:       setTotalPoints (Non Static)
    // 
    //  Description:  Sets the total number of points to be used to 
    //                estimate PI.
    //
    //  Parameters:   int
    //
    //  Returns:      N/A
    //
    //***************************************************************
	private void setTotalPoints(int points) {
		if(points > 0) {
			totalPoints = points;
		} else {
			totalPoints = 0;
		}
	}// end setTotalPoints method
	
    //***************************************************************
    //
    //  Method:       incrementCirclePoints (Non Static)
    // 
    //  Description:  Increments the points that are considered "in the circle".
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //***************************************************************
	private void incrementCirclePoints() {
		circlePoints++;
	}//end incrementCirclePoints method 
	
    //***************************************************************
    //
    //  Method:       resetPoints (Non Static)
    // 
    //  Description:  Sets total points and circle points to 0.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //***************************************************************
	private void resetPoints() {
		totalPoints = 0;
		circlePoints = 0;
	}//end resetPoints method
	
    //***************************************************************
    //
    //  Method:       testPoint (Non Static)
    // 
    //  Description:  Tests a coordinate point to determine if the point
    //                falls within the coordinates of the circle's area.
    //
    //  Parameters:   double[] (x, y)
    //
    //  Returns:      boolean
    //
    //***************************************************************
	private boolean testPoint(double[] coordinate) {
		
		double xSquared = Math.pow(coordinate[0], 2);
		double ySquared = Math.pow(coordinate[1], 2);
		double sumSquares = xSquared + ySquared;
		double result = Math.sqrt(sumSquares);
		
		return result < 1.0;
		
	}// end testPoint method
	
    //***************************************************************
    //
    //  Method:       getRandomPoint (Non Static)
    // 
    //  Description:  Generates a random array representing an x and y 
    //                coordinate.
    //
    //  Parameters:   None
    //
    //  Returns:      double[] (coordinate)
    //
    //***************************************************************
	private double[] getRandomPoint() {
		
		double x = getRandomDouble();
		double y = getRandomDouble();
		return new double[]{x, y};
		
	}// end getRandomPoint method
	
    //***************************************************************
    //
    //  Method:       getRandomDouble (Non Static)
    // 
    //  Description:  Generates a random double between 1 and -1.
    //
    //  Parameters:   None
    //
    //  Returns:      double[] (coordinate)
    //
    //***************************************************************
	private double getRandomDouble() {
		
		Random rand = new Random();
		return rand.nextDouble(2) - 1;
		
	}// end getRandomDouble method
	
    //***************************************************************
    //
    //  Method:       generateRandomPoints (Non Static)
    // 
    //  Description:  Generates a series of random points (the number 
    //                provided by the user and updates the global inCircle
    //                count if the point is within the circle. Each point
    //                is generated in a new thread and uses a mutex lock 
    //                for updating the inCircle count.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //***************************************************************
	private void generateRandomPoints() {
		
		pool = Executors.newFixedThreadPool(coreCount);
		if(totalPoints > 0) {
			for(int i = 0; i < totalPoints; i++) {
				pool.submit(new Thread() {
					@Override 
					public void run() {
						
						double[] randomPoint = getRandomPoint();
						boolean inCircle = testPoint(randomPoint);
						if(inCircle) { // prevent unnecessary locking
							lock.lock(); // **** CRITICAL SECTION ****
							   try {
							       incrementCirclePoints();
							   } finally {
								   lock.unlock(); // **** END CRITICAL SECTION ****
							   }
						}
					}
				});
			}
			pool.shutdown();
			try {
				pool.awaitTermination(1, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}		
	}//end generateRandomPoints
	
    //***************************************************************
    //
    //  Method:       estimatePI (Non Static)
    // 
    //  Description:  Calls helper methods to generate random points
    //                and then estimates PI by dividing the total number
    //                of points by the number of random points that ended 
    //                up in the circle and multiplying by 4.
    //
    //  Parameters:   int points (provided by the client / user)
    //
    //  Returns:      double (an estimation of PI)
    //
    //***************************************************************
	public double estimatePI(int points) {
		
		resetPoints();
		setTotalPoints(points);
		generateRandomPoints();

		return 4 * ((double)circlePoints / (double)totalPoints);
		
	}// end estimatePI method 

}// end PIEstimator class
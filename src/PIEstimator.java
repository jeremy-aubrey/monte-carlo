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
	private ExecutorService pool = Executors.newFixedThreadPool(coreCount);
	private Lock lock = new ReentrantLock(); // mutex lock
	
	private void setTotalPoints(int points) {
		if(points > 0) {
			totalPoints = points;
		} else {
			totalPoints = 0;
		}
	};
	
	private void incrementCirclePoints() {
		circlePoints++;
	}
	
	private void resetPoints() {
		totalPoints = 0;
		circlePoints = 0;
	}
	
	private boolean testPoint(double[] coordinate) {
		
		double xSquared = Math.pow(coordinate[0], 2);
		double ySquared = Math.pow(coordinate[1], 2);
		double sumSquares = xSquared + ySquared;
		double result = Math.sqrt(sumSquares);
		
		return result < 1.0;
		
	}
	
	private double[] getRandomPoint() {
		double x = getRandomFloat();
		double y = getRandomFloat();
		return new double[]{x, y};
	}
	
	private double getRandomFloat() {
		Random rand = new Random();
		return rand.nextDouble(2) - 1;
	}
	
	
	// method to find circle points (spin up threads / join)
	private void generateRandomPoints() {
		if(totalPoints > 0) {
			
			for(int i = 0; i < totalPoints; i++) {
				pool.submit(new Thread() {
					@Override 
					public void run() {
						
						double[] randomPoint = getRandomPoint();
						boolean inCircle = testPoint(randomPoint);
						if(inCircle) {
							lock.lock(); // **** critical section ****
							   try {
							       incrementCirclePoints();
							   } finally {
								   lock.unlock(); // **** end critical section ****
							   }
						}
					}
				});
			}
			
			pool.shutdown();
			try {
				pool.awaitTermination(1, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				
			}
		}		
	}//end generateRandomPoints
	
	// method to calculate pi
	public double calculatePI(int points) {
		
		resetPoints();
		setTotalPoints(points);
		generateRandomPoints();

		return 4 * ((double)circlePoints / (double)totalPoints);
	}

}

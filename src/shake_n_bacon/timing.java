/*
 *@author Fardos Mohamed, Vivyan Woods
 * @UWNetID ffm2, vivyanw
 * @studentID 1037290, 1327679
 * @email ffm2@uw.edu, vivyanw@uw.edu
 * 
 * 
 * This is a timing file to test the different implementations and find the avg time for each implementation. 
*/


package shake_n_bacon;


public class timing{
	
	private static final int NUM_TIMING= 10;
	private static final int NUM_WARMUP = 5; //this will exclude the Warmup 
	private static final String TEXT = "hamlet.txt";
	
	public static void main (String [] args){
		String [] name = new String[] {"-o", TEXT};
		double runTime = avgRunTime(name);
		System.out.println("run time: "+ runTime);
	}
	
	private static double avgRunTime(String[] args){
		double time = 0.0;
		for (int i = 0; i < NUM_TIMING; i++){
			long startTime = System.currentTimeMillis();
			WordCount.main(args);
			long endTime = System.currentTimeMillis();
			if(NUM_WARMUP <= i){
				time += (endTime - startTime);
			}
		}
		return time / (NUM_TIMING - NUM_WARMUP);
	}
	
}
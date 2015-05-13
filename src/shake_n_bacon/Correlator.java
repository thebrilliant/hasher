package shake_n_bacon;

import java.io.*;
import java.util.Scanner;

import providedCode.*;

/**
 * @author Vivyan Woods, Fardos Mohamed
 * @UWNetID vivyanw, ffm2
 * @studentID 1327679, 1037290
 * @email vivyanw@uw.edu, ffm2@uw.edu
 * 
 *        TODO: REPLACE this comment with your own as appropriate.
 * 
 *        This should be done using a *SINGLE* iterator. This means only 1
 *        iterator being used in Correlator.java, *NOT* 1 iterator per
 *        DataCounter (You should call dataCounter.getIterator() just once in
 *        Correlator.java). Hint: Take advantage of DataCounter's method.
 * 
 *        Although you can share argument processing code with WordCount, it
 *        will be easier to copy & paste it from WordCount and modify it here -
 *        it is up to you. Since WordCount does not have states, making private
 *        method public to share with Correlator is OK. In general, you are not
 *        forbidden to make private method public, just make sure it does not
 *        violate style guidelines.
 * 
 *        Make sure WordCount and Correlator do not print anything other than
 *        what they are supposed to print (e.g. do not print timing info, input
 *        size). To avoid this, copy these files into package writeupExperiment
 *        and change it there as needed for your write-up experiments.
 */
public class Correlator {
	
	public static final double MIN = .0001;
	public static final double MAX = .01;

	public static void main(String[] args) throws FileNotFoundException {
		String counterType = args[0];
		String fileOne = args[1];
		String fileTwo = args[2];
		
		DataCounter hamletWords;
		DataCounter atlantisWords;
		if (counterType.equals("-s")) {
			hamletWords = new HashTable_SC(new StringComparator(), new StringHasher());
			atlantisWords = new HashTable_SC(new StringComparator(), new StringHasher());
		} else {
			hamletWords = new HashTable_OA(new StringComparator(), new StringHasher());
			atlantisWords = new HashTable_OA(new StringComparator(), new StringHasher());	
		}
		
		// TODO: Compute this variance
		double variance = compare(hamletWords, atlantisWords, fileOne, fileTwo);
		// IMPORTANT: Do not change printing format. Just print the double.
		System.out.println(variance);
	}
	
	public static double compare(DataCounter words1, DataCounter words2, 
								String file1, String file2) {
		int wordCount1 = countWords(file1, words1);
		int wordCount2 = countWords(file2, words2);
		SimpleIterator itr = words1.getIterator();
		double sum = 0.0;
		while(itr.hasNext()) {
			DataCount next = itr.next();
			int inBoth = words2.getCount(next.data);
			if (inBoth >= 0) {
				double freq1 = (double) next.count/wordCount1;
				double freq2 = (double) inBoth/wordCount2;
				if ( ( freq1 <= MAX || freq1 >= MIN) &&
					(freq2 <= MAX || freq2 >= MIN)) {
					double diff = freq1 - freq2;
					double square = Math.pow(diff, 2);
					sum += square;
				}
			}
		}
		
		return sum;
	}
	
	private static int countWords(String file, DataCounter counter) {
		int result = -1;
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			result = 0;
			while (word != null) {
				result++;
				counter.incCount(word);
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
		}
		return result;
	}
}

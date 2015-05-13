package shake_n_bacon;

import java.io.*;

import providedCode.*;

/**
 * @author Vivyan Woods, Fardos Mohamed
 * @UWNetID vivyanw, ffm2
 * @studentID 1327679, 1037290
 * @email vivyanw@uw.edu, ffm2@uw.edu
 * 
 *        This class compares two files using either separate chaining hash
 *        tables or open addressing hash tables.  It takes the information
 *        passed through args to get the type of table to be used as well
 *        as two file names.
 */
public class Correlator {
	
	public static final double MIN = .0001;
	public static final double MAX = .01;

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 3) {
			usage();
		}
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
		
		double variance = compare(hamletWords, atlantisWords, fileOne, fileTwo);
		// IMPORTANT: Do not change printing format. Just print the double.
		System.out.println(variance);
	}
	
	//Compares the 
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
	
	/*
	 * Print error message and exit
	 */
	private static void usage() {
		System.err
				.println("Usage: [-s | -o] <filename of document to analyze>");
		System.err.println("-s for hashtable with separate chaining");
		System.err.println("-o for hashtable with open addressing");
		System.exit(1);
	}
}

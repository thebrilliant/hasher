package shake_n_bacon;

import java.io.IOException;

import providedCode.*;

public class Timing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Comparator<String> comp = new StringComparator();
		Hasher hash = new StringHasher();
		DataCounter OACounter = new HashTable_OA(comp, hash);
		DataCounter SCCounter = new HashTable_OA(comp, hash);
		
		System.out.println("Timing  " +
				"both types of implemented hash tables:");
		long OATime = timeHash("hamlet.txt", OACounter);
		System.out.println("Linear probing: " + OATime + " nanoseconds");
		long SCTime = timeHash("hamlet.txt", SCCounter);
		System.out.println("Separate chaining: " + SCTime + " nanoseconds");

		System.out.println("Timing the insertion of the words in The New Atlantis into " +
				"both types of implemented hash tables:");
		OATime = timeHash("the-new-atlantis.txt", OACounter);
		SCTime = timeHash("the-new-atlantis.txt", SCCounter);
		System.out.println("Linear probing: " + OATime + " nanoseconds");
		System.out.println("Separate chaining: " + SCTime + " nanoseconds");
		
		System.out.println("Timing the insertion of the words in Hamlet into " +
				"both types of implemented hash tables:");
		OATime = timeHash("1777.txt", OACounter);
		SCTime = timeHash("theboondocksaints.txt", SCCounter);
		System.out.println("Linear probing: " + OATime + " nanoseconds");
		System.out.println("Separate chaining: " + SCTime + " nanoseconds");
	}

	public static long timeHash(String file, DataCounter counter) {
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			long time = System.nanoTime();
			long totalTime = 0;
			while (word != null) {
				time = System.nanoTime();
				counter.incCount(word);
				time = System.nanoTime() - time;
				totalTime = time + totalTime;
				word = reader.nextWord();
			}
			return totalTime;
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
		}
		return 0;
	}
}
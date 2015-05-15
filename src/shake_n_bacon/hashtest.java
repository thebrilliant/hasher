package shake_n_bacon;

import providedCode.*;


public class hashtest {
	public static void main(String[] args) {
		
		Hasher h = new StringHasher();
		StringComparator c = new StringComparator();

		DataCounter separate_chaining = new HashTable_SC(c, h);
		DataCounter open_addressing = new HashTable_OA(c, h);

	/*	System.out.println("Separate-Chaining: ");
		theSize(separate_chaining);
		theSize(separate_chaining);
		count(separate_chaining);
		testIterator(separate_chaining);
		*/

		System.out.println("Open Addressing: "); // uncomment to test for open addressing
		theSize(open_addressing);
		theSize(open_addressing);
		testIterator(open_addressing);
	}

	public static void testIterator(DataCounter table) {
		SimpleIterator itr = table.getIterator();
		int i = 0;
		while (itr.hasNext()) {
			DataCount dataCount = itr.next();
			//			System.out.println("data = " + dataCount.data);
			//			System.out.println("count = " + dataCount.count);
			i++;
		}
		System.out.println("Iterated through " + i + " elements.");
	}
	
	public static void count(DataCounter table) {
		if (table.getCount("1") == 2) {
			System.out.println("Single increment count test passed.");
		} else {
			System.out.println("Incorrect count.");
		}

		for (int i = 1; i <= 100; i++) {
			table.incCount("" + i);
		}

		boolean know = true;
		for (int i = 1; i <= 50; i++) {
			if (table.getCount("" + i) != 3) {
				know = false;
			}
		}

		if (know) {
			System.out.println("increment passed");
		} else {
			System.out.println("the count is not correct");
		}     
	}
	
	public static void theSize(DataCounter table) {
		int index = 10000;
		System.out.println(index + " elements");
		for (int i = 1; i <= index; i++) {
			table.incCount("" + i);
		}

		System.out.println("Reported size = " + table.getSize());
		if (table.getSize() == index) {
			System.out.println("the test passed");
		} else {
			System.out.println("Incorrect size.");
		}
	}
}
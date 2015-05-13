package shake_n_bacon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import providedCode.*;

/**
 * @author Vivyan Woods, Fardos Mohamed
 * @UWNetID vivyanw, ffm2
 * @studentID 1327679, 1037290
 * @email vivyanw@uw.edu, ffm2@uw.edu
 * 
 *        This class creates a table of words that contains the word
 *        and the number of occurrences for each word.
 *        
 *        TODO: Develop appropriate tests for your HashTable.
 */
public class HashTable_SC extends DataCounter {
	private int[] primes = {11,23,47,97,193,409,757,1423,2843,6217,14029,
							28031,56369,109913,130873,180001,200003};
	private List<LinkedList<DataCount>> table;
	private List[] table2;
	private int tableSize;
	private int currentPrime;
	private Hasher hash;
	private Comparator<String> comp;
	private int countElem;

	/**
	 * given a comparator and hasher, initializes the fields
	 * to the parameters, sets the current prime to be used,
	 * the size of the table to used and creates the empty
	 * table.
	 * 
	 * @param c, the comparator object used to compare strings
	 * @param h, used for getting the hash codes for strings
	 */
	public HashTable_SC(Comparator<String> c, Hasher h) {
		comp = c;
		hash = h;
		currentPrime = 0;
		tableSize = primes[currentPrime];
		table = new ArrayList<LinkedList<DataCount>>(tableSize);
		table2 = new LinkedList[tableSize];
	}

	/**
	 * Adds the given word to the table at the index of the hash
	 * number of the word, if the word already exists in the table,
	 * it increments the occurrence count of the word in the table,
	 * resizes the table when the table starts to get too many more
	 * elements than the size of the table
	 * 
	 * @param data, the word to be added to the table
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void incCount(String data) {
		if(calcLoadFactor() > 1.0) {
			resize();
		}
		int place = (hash.hash(data)) % tableSize;
		DataCount newElem = new DataCount(data, 1);
		if (table2[place] == null) {
			LinkedList<DataCount> newList = new LinkedList<DataCount>();
			newList.add(newElem);
			table2[place] = newList;
			countElem++;
		} else {
			DataCount word = find(data, place);
			if (word != null) {
				word.count++;	
			} else {
				table2[place].add(newElem);
				countElem++;
			}
		}
	}
	
	/**
	 * searches through the list of words at the given table index
	 * for the given string, if it's found, returns the word object
	 * else returns null.
	 * 
	 * @param data, the word that is being searched for
	 * @param hashNum, the hash code for the string
	 * @return returns the DataCount object for the given string
	 * if it exists, else it returns null
	 */
	@SuppressWarnings("unchecked")
	private DataCount find(String data, int hashNum) {
		List<DataCount> hashList = table2[hashNum];
		if (hashList != null) {
			for (int i = 0; i < hashList.size(); i ++) {//null pointer...
				DataCount temp = hashList.get(i);
				if (comp.compare(data, temp.data) == 0) {
					return temp;
				}
			}
		}
		return null;
	}
	
	/**
	 * calculates the load factor based on the number of
	 * elements over the table size and returns it
	 * 
	 * @return returns the load factor of the table
	 */
	private double calcLoadFactor() {
		return (double) countElem / tableSize;
	}
	
	/**
	 * resizes the table to be a size that keeps the load factor
	 * less than or equal to 2, re-hashes all the elements in the
	 * table and puts them in their new place in the table.
	 */
	private void resize() {
		setTableSize();
		List<DataCount>[] tempTable = new LinkedList[tableSize];
		
		for (int i = 0; i < table2.length; i++) {
			if (table2[i] != null) {
				for (int j = 0; j < table2[i].size(); j++) {
					@SuppressWarnings("unchecked")
					List<DataCount> tempList = table2[i];
					DataCount temp = tempList.get(j);
					int place = (hash.hash(temp.data)) % tableSize;
					if (tempTable[place] == null) {
						LinkedList<DataCount> newList = new LinkedList<DataCount>();
						newList.add(temp);
						tempTable[place] = newList;
					} else {
						tempTable[place].add(temp);
					}
				}
			}
		}
		table2 = tempTable;
		
	}
	
	/**
	 * sets the table size to be the next prime that
	 * keeps the load factor no greater than 2.0
	 */
	private void setTableSize() {
		int temp = currentPrime++;
		double load = (double) countElem / primes[temp];
		while (load > 1.0) {
			temp++;
			load = (double) countElem / primes[temp];
		}
		currentPrime = temp;
		tableSize = primes[temp];
	}
	
	/**
	 * @return the number of unique elements in the table
	 */
	@Override
	public int getSize() {
		return countElem;
	}

	/**
	 * Looks for the word that matches the given string
	 * and returns the number of times that word occurs
	 * 
	 * @param data, the word for the count number
	 * @return returns the number of times that word appears
	 */
	@Override
	public int getCount(String data) {
		int place = (hash.hash(data)) % tableSize;
		DataCount isFound = find(data, place);
		if (isFound != null) {
			return isFound.count;
		}
		return -1;
	}

	/**
	 * @return returns an instance of the SimpleIterator
	 */
	@Override
	public SimpleIterator getIterator() {
		return new DataCountIterator();
	}
	
	/**
	 * A private class implementation of SimpleIterator
	 * to iterate over a list of DataCount objects
	 */
	private class DataCountIterator implements SimpleIterator {
		int tablePlace;
		@SuppressWarnings("rawtypes")
		public List[] allElem = table2;
		@SuppressWarnings("unchecked")
		public List<DataCount> list = table2[tablePlace];
		public int index;

		/**
		 * @exception throws NoSuchElementException if there isn't
		 * another item in the list
		 * @return returns the next item in the list
		 */
		@Override
		public DataCount next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			DataCount temp = list.get(index);
			index++;
			return temp;
		}

		/**
		 * @return returns true if there is another item in
		 * the list, returns false otherwise
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean hasNext() {
			boolean next = true;
			System.out.println("sorry i'm so slow!");
			if (allElem[tablePlace] == null || (list.size() == index && tablePlace < tableSize)) {
				tablePlace++;
				while (tablePlace < tableSize) {
					if (allElem[tablePlace] == null) {
						tablePlace++;
					} else {
						break;
					}
				}
				if (tablePlace < tableSize) {
					list = allElem[tablePlace];
				} else {
					System.out.println("there is no more...");
					next = false;
				}
				index = 0;
			}
			return next;
		}
		
	}

}
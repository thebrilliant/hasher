package shake_n_bacon;

import java.util.LinkedList;
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
	private LinkedList[] table;
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
		table = new LinkedList[tableSize];
	}

	/**
	 * Adds the given word to the table at the index of the hash
	 * number of the word, if the word already exists in the table,
	 * it increments the occurrence count of the word in the table,
	 * resizes the table when the table starts to get too many more
	 * elements than the size of the table
	 * 
	 * @param data, the word to be added to the hash table
	 */
	@Override
	public void incCount(String data) {
		if(calcLoadFactor() > 2.0) {
			resize();
		}
		int place = (hash.hash(data)) % tableSize;
		DataCount newElem = new DataCount(data, 0);
		if (table[place] == null) {
			LinkedList<DataCount> newList = new LinkedList<DataCount>();
			newList.add(newElem);
			table[place] = newList;
			countElem++;
		} else {
			DataCount word = find(data, place);
			if (word != null) {
				word.count++;	
			} else {
				table[place].add(newElem);
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
	private DataCount find(String data, int hashNum) {
		LinkedList<DataCount> hashList = table[hashNum];
		DataCountIterator itr = new DataCountIterator();
		itr.list = hashList;
		while (itr.hasNext()) {
			DataCount temp = itr.next();
			if (comp.compare(data, temp.data) == 0) {
				return temp;
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
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				DataCountIterator itr = new DataCountIterator();
				itr.list = table[i];
				while(itr.hasNext()) {
					DataCount temp = itr.next();
					int place = (hash.hash(temp.data)) % tableSize;
					if (table[place] == null) {
						LinkedList<DataCount> newList = new LinkedList<DataCount>();
						newList.add(temp);
						table[place] = newList;
					} else {
						DataCount word = find(temp.data, place);
						table[place].add(temp);
					}
				}
			}
		}
		
	}
	
	/**
	 * sets the table size to be the next prime that
	 * keeps the load factor no greater than 2.0
	 */
	private void setTableSize() {
		int temp = currentPrime++;
		double load = (double) countElem / primes[temp];
		while (load > 2.0) {
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
	 * Looks for the word object that matches the given string
	 * and returns the number of times that word is counted
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
	 * @return returns an instance of the SimpleIterator();
	 */
	@Override
	public SimpleIterator getIterator() {
		return new DataCountIterator();
	}
	
	/**
	 * A private class implementation of SimpleIterator
	 * to iterate over a list of DataCount objects
	 * 
	 * @author Vivyan Woods
	 */
	private class DataCountIterator implements SimpleIterator {
		int tablePlace;
		public LinkedList<DataCount> list = table[tablePlace];
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
		@Override
		public boolean hasNext() {
			boolean next = true;
			if (list.size() == index && tablePlace < tableSize) {
				tablePlace++;
				while (tablePlace < tableSize && table[tablePlace] == null) {
					tablePlace++;
				}
				if (tablePlace < tableSize) {
					list = table[tablePlace];
				} else {
					next = false;
				}
				index = 0;
			}
			return next;
		}
		
	}

}
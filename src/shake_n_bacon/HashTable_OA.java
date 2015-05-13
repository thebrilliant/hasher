package shake_n_bacon;

import java.util.NoSuchElementException;

import providedCode.*;

/**
 * @author Fardos Mohamed, Vivyan Woods
 * @UWNetID ffm2, vivyanw
 * @studentID 1037290, 1327679
 * @email ffm2@uw.edu, vivyanw@uw.edu
 * 
 *        This class creates a table of words that contains the word
 *        and the number of occurrences for each word.
 * 
 *        TODO: Develop appropriate tests for your HashTable.
 */
public class HashTable_OA extends DataCounter {
	private int[] primes = {11,23,47,97,193,409,757,1423,2843,6217,14029,
							28031,56369,109913,130873,180001,200003};
	private DataCount[] table;
	private int tableSize;
	private int currentPrime;
	private Hasher hash;
	private Comparator<String> comp;
	private int countElem;
	private DataCount theData;
	
	/**
	 * given a comparator and hasher, initializes the fields
	 * to the parameters, sets the current prime to be used,
	 * the size of the table to used and creates the empty
	 * table.
	 * 
	 * @param c, the comparator object used to compare strings
	 * @param h, used for getting the hash codes for strings
	 */
	public HashTable_OA(Comparator<String> c, Hasher h) {
		comp = c;
		hash = h;
		currentPrime = 0;
		tableSize = primes[currentPrime];
		table = new DataCount[tableSize];
		
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
	@Override
	public void incCount(String data) {
		if((double) countElem / tableSize >= 1.0){
			SimpleIterator itr = getIterator();
			currentPrime++;
			DataCount [] newElem = new DataCount[primes[currentPrime]];
			while (itr.hasNext()){
				DataCount temp = itr.next();
				int place = hash.hash(temp.data) % tableSize;
				while(newElem[place] != null){
					place++;
					if(place == tableSize){
						place = 0;
					}
				}
				newElem[place] = temp;
			}
			table = newElem; 
		}
		theData = add(data);
	}
	
	/**
	 * Looks through the current words to find where to place the
	 * given word into the table, once it finds an empty spot, it
	 * puts the word into the table.  Returns back the word created.
	 * 
	 * @param data, the word to be added
	 * @return returns the word and its occurrence count
	 */
	private DataCount add (String data){
		int temp = hash.hash(data) % tableSize;
		int index = probe(data, temp);
		if (index != -1) {
			if(table[index] == null){
				table[index] = new DataCount(data, 1);
				countElem++;
			} else {
				table[index].count++;
			}
		}
		return table[temp];
	}
	
	/**
	 * Looks through the table starting at the given index
	 * to find an empty space or if the given word is already
	 * in the table, returns the index of the slot found, if
	 * none found, returns -1
	 * 
	 * @param data, the word to be added
	 * @param temp, the index to start looking at
	 * @return returns the index for the word to be added into
	 */
	private int probe (String data, int temp) {
		int init = temp;
		while (table[temp] != null && comp.compare(data,  table[temp].data) != 0){
			temp++;
			if(temp == table.length){
				temp = 0;
			}
			if (temp == init) {
				break;
			}
		}
		if (temp == init) {
			return -1;
		}
		return temp;
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
		int temp = hash.hash(data) % tableSize;
		int index = probe(data, temp);
		if (index != -1) {
			DataCount isFound = table[index];
			return isFound.count;
		}
		return index;
	}

	/**
	 * @return returns an instance of the SimpleIterator
	 */
	@Override
	public SimpleIterator getIterator() {
		return new CountIterator();
	}
	
	/**
	 * A private class implementation of SimpleIterator
	 * to iterate over a list of DataCount objects
	 */
	private class CountIterator implements SimpleIterator {
		public DataCount[] list =  table;
		public int index;
		
		/**
		 * @exception throws NoSuchElementException if there isn't
		 * another item in the list
		 * @return returns the next item in the list
		 */
		public DataCount next(){
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			DataCount temp = list[index];
			index++;
			return temp;
		}
		
		/**
		 * @return returns true if there is another item in
		 * the list, returns false otherwise
		 */
		public boolean hasNext(){
			return !(list.length ==  index);
		}
	}

}
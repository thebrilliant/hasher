package shake_n_bacon;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import providedCode.*;

/**
 * @author Fardos Mohamed, Vivyan Woods
 * @UWNetID ffm2, vivyanw
 * @studentID 1037290, 1327679
 * @email ffm2@uw.edu, vivyanw@uw.edu
 * 
 *        TODO: Replace this comment with your own as appropriate.
 * 
 *        1. You may implement HashTable with open addressing as discussed in
 *        class; You can choose one of those three: linear probing, quadratic
 *        probing or double hashing. The only restriction is that it should not
 *        restrict the size of the input domain (i.e., it must accept any key)
 *        or the number of inputs (i.e., it must grow as necessary).
 * 
 *        2. Your HashTable should rehash as appropriate (use load factor as
 *        shown in the class).
 * 
 *        3. To use your HashTable for WordCount, you will need to be able to
 *        hash strings. Implement your own hashing strategy using charAt and
 *        length. Do NOT use Java's hashCode method.
 * 
 *        4. HashTable should be able to grow at least up to 200,000. We are not
 *        going to test input size over 200,000 so you can stop resizing there
 *        (of course, you can make it grow even larger but it is not necessary).
 * 
 *        5. We suggest you to hard code the prime numbers. You can use this
 *        list: http://primes.utm.edu/lists/small/100000.txt NOTE: Make sure you
 *        only hard code the prime numbers that are going to be used. Do NOT
 *        copy the whole list!
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
	

	public HashTable_OA(Comparator<String> c, Hasher h) {
		comp = c;
		hash = h;
		currentPrime = 0;
		tableSize = primes[currentPrime];
		table = new DataCount[tableSize];
		
	}

	@Override
	public void incCount(String data) {
		if((double) countElem / primes[currentPrime] >= 2){
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
		theData = find(data,true);
	}
	
	private DataCount find (String data, boolean num){
		int temp = hash.hash(data) % tableSize;
		while (comp.compare(data,  table[temp].data) != 0 && table[temp] != null){
			temp++;
			if(temp == table.length){
				temp = 0;
			}
		}
		if(table[temp] == null){
			return new DataCount(data,0);
		}else if(table[temp] == null && num){
			table[temp] = new DataCount(data, 0);
			tableSize++;
		}
		return table[temp];
	}

	@Override
	public int getSize() {
		return tableSize;
	}

	@Override
	public int getCount(String data) {
		int index = (hash.hash(data)) % tableSize;
		return find(data, true).count;
	}

	@Override
	public SimpleIterator getIterator() {
		return new CountIterator();
	}
	
	private class CountIterator implements SimpleIterator {
		public LinkedList<DataCount> list;
		public int index;
		public DataCount next(){
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			DataCount temp = list.get(index);
			index++;
			return temp;
		}
		
		public boolean hasNext(){
			return !(list.size() ==  index);
		}
	}

}
package shake_n_bacon;

import providedCode.Hasher;

/**
 * @author Vivyan Woods, Fardos Mohamed
 * @UWNetID vivyanw, ffm2
 * @studentID 1327679, 1037290
 * @email vivyanw@uw.edu, ffm2@uw.edu
 * 
 * 			This class hashes strings by its own function
 */
public class StringHasher implements Hasher {

	/**
	 * Given a string, breaks down the string into a number
	 * based on the characters and their indexes.  Returns
	 * the created number.
	 * 
	 * @param str, the string to be hashed
	 * @return returns the number of the hashed string
	 */
	@Override
	public int hash(String str) {
		int result = 0;
		for (int i = 0; i < str.length(); i++) {
			int temp = (int) str.charAt(i);
			result += (temp * (Math.pow(37, i)));
		}
		return result;
	}
}

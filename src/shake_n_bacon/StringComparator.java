package shake_n_bacon;

import providedCode.*;

/**
 * @author Vivyan Woods
 * @UWNetID vivyanw
 * @studentID 1327679
 * @email vivyanw@uw.edu
 * 
 *        TODO: REPLACE this comment with your own as appropriate.
 */
public class StringComparator implements Comparator<String> {

	/**
	 * Takes in two Strings to compare which alphabetically comes first
	 * returns a negative value when the first string comes first
	 * alphabetically, returns a positive value if the first value comes
	 * second alphabetically, returns 0 if the strings are equal.
	 */
	@Override
	public int compare(String s1, String s2) {
		int length = s1.length();
		if (s2.length() < length) {
			length = s2.length();
		}
		int index = 0;
		while (index < length) {
			int s1Char = (int) s1.charAt(index);
			int s2Char = (int) s2.charAt(index);
			if(s1Char != s2Char) {
				return s1Char - s2Char;
			}
			index++;
		}
		return s1.length() - s2.length();
	}
}

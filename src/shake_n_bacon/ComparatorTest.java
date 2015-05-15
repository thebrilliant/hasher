package shake_n_bacon;

import java.util.*;

public class ComparatorTest {
	public static void main (String[] aargs){
		StringComparator c = new StringComparator();
		boolean test = true;
		String testing = "hello this is a random sentence for testing purposes";
		
		for(int i = 0; i < 100; i++){
			String one = "";
			String two = "";
			
			for(int k = 0; k < 10; k++){
				Random rand = new Random();
				char char1 = testing.charAt(rand.nextInt(20));
				char char2 = testing.charAt(rand.nextInt(20));
				
				one = one + char1;
				two = two + char2;
			}
			if(c.compare(one, two) != one.compareTo(two)){
				test = false;
			}
			System.out.println(test);
		}
		
	}
}
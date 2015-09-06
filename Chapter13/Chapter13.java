import java.util.*;

public class Chapter13 {
	public static String sortString(String s) {
		int len = s.length();
		if(len == 1) {
			return s;
		}
		String left = sortString(s.substring(0, len/2));
		String right = sortString(s.substring(len/2, len));
		String merge = merge(left, right);
		return merge;
	}

	public static String merge(String left, String right) {
		String retval = "";
		int leftIndex = 0;
		int rightIndex = 0;
		while(leftIndex < left.length() && rightIndex < right.length()) {
			if(left.substring(leftIndex, leftIndex+1).compareTo(right.substring(rightIndex, rightIndex+1))<0) {
				retval += left.substring(leftIndex, leftIndex+1);
				leftIndex++;
			} else {
				retval += right.substring(rightIndex, rightIndex+1);
				rightIndex++;
			}
		}

		if(leftIndex == left.length()) {
			retval += right.substring(rightIndex);
		} else {
			retval += left.substring(leftIndex);
		}
		return retval;
	}

	public static ArrayList<ArrayList<String>> anagrams(String[] dict) {
		HashMap<String, ArrayList<String>> hash = new HashMap<String, ArrayList<String>>();
		for(int i=0; i < dict.length; i++) {
			String sort = sortString(dict[i]);
			ArrayList<String> val = hash.get(sort);
			if(val == null) {
				val = new ArrayList<String>();
			}
			val.add(dict[i]);
			hash.put(sort, val);
		}	
		ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
		for(ArrayList<String> strings : hash.values()) {
			if(strings.size() > 1) {
				ret.add(strings);
			}
		}
		return ret;
	}

	public static boolean palindrome(String s) {
		HashMap<Character, Integer> hash = new HashMap<Character, Integer>();
		for(int i=0; i < s.length(); i++) {
			char c = s.charAt(i);
			int count = hash.get(c) != null ? hash.get(c) + 1 : 1;
			hash.put(c, count);
		}
		boolean foundOdd = false;
		boolean isPalindrome = true;
		for(int count : hash.values()) {
			if(count % 2 != 0) {
				if(foundOdd) {
					isPalindrome = false;
				} else {
					foundOdd = true;
				}
			}
		}
		return isPalindrome;
	}

				

	public static void main(String[] args) {
		System.out.println(palindrome("edified"));
	}
}
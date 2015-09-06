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

	public static Map<Character, Integer> countFrequency(String text) {
		Map<Character, Integer> count = new HashMap<Character, Integer>();
		for(int i=0; i < text.length(); i++) {
			char c = text.charAt(i);
			int freq = count.get(c) == null ? 1 : count.get(c) + 1;
			count.put(c, freq);
		}
		return count;
	}

	public static boolean anonymousLetter(String letter, String mag) {
		Map<Character, Integer> letterHash = countFrequency(letter);
		for(int i=0; i < mag.length(); i++) {
			if(letterHash.isEmpty()) {
				return true;
			}
			char c = mag.charAt(i);
			if(letterHash.containsKey(c)) {
				int freq = letterHash.get(c);
				if(freq == 1) {
					letterHash.remove(c);
				} else {
					letterHash.put(c, letterHash.get(c) - 1);
				}
			}
		}
		return false;
	}

	public static class Node<A> {
		Node<A> next;
		A v;

		public Node(A v, Node<A> next) {
			this.v = v;
			this.next = next;
		}

		public Node(A v) {
			this.v = v;
		}
	}


	public static class Queue<A> {
		Node<A> head;
		Node<A> tail;

		public void enqueue(A e) {
			if(head == null) {
				head = tail = new Node<A>(e);
			} else {
				tail.next = new Node<A>(e);
				tail = tail.next;
			}
		}

		public void enqueue(Node<A> e) {
			if(head == null) {
				head = tail = e;
			} else {
				tail.next = e;
				tail = tail.next;
			}
		}

		public A dequeue() {
			if(head != null) {
				A ret = head.v;
				head = head.next;
				return ret;
			}
			return null;
		}

		public void makeHead(Node<A> n) {
			A save = del(n);
			Node<A> newHead = new Node<A>(save);
			newHead.next = head;
			head = newHead;
		}

		public A del(Node<A> n) {
			A save = n.v;
			if(n.next != null) {
				n.v = n.next.v;
				n.next = n.next.next;
			} else {
				n = null;
			}
			return save;
		}
			
	}

	public static class ISBN {
		public String s;
		public double d;
	}

	public static class Cache {
		Queue<ISBN> q;
		HashMap<String, Node<ISBN>> hash;
		int capacity;
		
		public void insert(ISBN i) {
			if(hash.containsKey(i.s)) {
				q.makeHead(hash.get(i.s));
			} else {
				if(hash.size() == capacity) {
					String d = q.dequeue().s;
					hash.remove(d);
				} else {
					Node<ISBN> newNode = new Node<ISBN>(i);
					hash.put(i.s, newNode);
					q.enqueue(newNode);
				}
			}
		}

		public double lookup(String s) {
			if(hash.containsKey(s)) {
				q.makeHead(hash.get(s));
				return hash.get(s).v.d;
			}
			return 0.0;
		}

		public void del(String s) {
			if(hash.containsKey(s)) {
				q.del(hash.get(s));
				hash.remove(s);
			}
		}
	}
					
	public static void main(String[] args) {
		System.out.println(palindrome("edified"));
	}
}
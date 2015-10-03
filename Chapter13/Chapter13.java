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

	public static int closestPair(String[] words) {
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		int closest = Integer.MAX_VALUE;
		for(int i=0; i < words.length; i++) {
			if(hash.containsKey(words[i])) {
				int pos = hash.get(words[i]);
				closest = i-pos < closest ? i-pos : closest;
			}
			hash.put(words[i], i);
		} 
		return closest;
	}

	public static int[] smallestSubarray(String[] text, HashSet<String> search) {
		int begin = 0;
		int end = text.length;
		int diff = Integer.MAX_VALUE;
		int i=0;
		int j=-1;
		while(i < text.length) {
			HashSet<String> notSeen = new HashSet<String>(search);
			while(!notSeen.isEmpty() && j < text.length-1) {
				j++;
				if(notSeen.contains(text[j])) {
					notSeen.remove(text[j]);
				}
			}
			if(notSeen.isEmpty()) {
				while(!search.contains(text[i])) {
					i++;
				}
				if(j-i < diff) {
					diff = j-i;
					begin = i;
					end = j;
				}
			}
			i++;
		}
		int[] ret = {begin, end};
		return ret;
	}

	public static int[] smallestSubarrayDistinct(String[] text, ArrayList<String> search) {
		int i=0;
		int j=0;
		int begin = 0;
		int end = text.length;
		int diff = Integer.MAX_VALUE;
		while(i < text.length && j < text.length) { 
			Stack<String> s = new Stack<String>();
			for(int k=search.size()-1; k >= 0; k--) {
				s.push(search.get(k));
			}
			while(!s.empty() && j < text.length) {
				if(text[j].equals(s.peek())) {
					s.pop();
				}
				if(!s.empty()) {
					j++;
				}
			}
			if(s.empty()) {
				while(!search.get(0).equals(text[i])) {
					i++;
				}
				if(j-i < diff) {
					diff = j-i;
					begin = i;
					end = j;
				}
				i++;
			}
		}
		int[] ret = {begin, end};
		return ret;
	}

	public static ArrayList<String> longestSubarrayDistinct(String[] arr) {
		int beginIndex = 0;
		int retLeft=0;
		int retRight=0;
		int maxLength = Integer.MIN_VALUE;
		HashMap<String, Integer> after = new HashMap<String, Integer>();
		HashSet<String> seen = new HashSet<String>();
		String curr = arr[0];
		for(int i=0; i < arr.length; i++) {
			if(!arr[i].equals(curr)) {
				after.put(curr, i);
				curr = arr[i];
			}
			if(seen.contains(arr[i])) {
				if(i - beginIndex > maxLength) {
					maxLength = i - beginIndex;
					retLeft = beginIndex;
					retRight = i;
				}
				beginIndex = after.get(arr[i]);
			} else {
				seen.add(arr[i]);
			}
		}
		ArrayList<String> ret = new ArrayList<String>();
		for(int i=retLeft; i < retRight; i++) {
			ret.add(arr[i]);
		}
		return ret;
	}

	public static int containedRange(int[] arr) {
		HashSet<Integer> set = new HashSet<Integer>();
		int max = Integer.MIN_VALUE;
		for(int i=0; i < arr.length; i++) {
			set.add(arr[i]);
		}
		for(int i=0; i < arr.length; i++) {
			if(set.contains(arr[i])) {
				int counter=1;
				int total = 1;
				set.remove(arr[i]);
				while(set.contains(arr[i]+counter)) {
					set.remove(arr[i]+counter);
					counter++;
					total++;
				}
				counter = -1;
				while(set.contains(arr[i]+counter)) {
					set.remove(arr[i]+counter);
					counter--;
					total++;
				}
				if(total > max) {
					max = total;
				}
			}
		}
		return max;
	}

	public static class MinHeap {
		public int size;
		public int[] heap;
		public int currSize;

		public MinHeap(int size) {
			this.size = size;
			this.heap = new int[size];
			this.currSize = 0;
		}

		public int extractMin() {
			int save = heap[0];
			heap[0] = heap[currSize-1];
			currSize--;
			if(currSize != 0) {
				bubbleDown(0);
			}
			return save;
		}

		public void insert(int n) {
			if(currSize < size) {
				heap[currSize] = n;
				bubbleUp(currSize);
				currSize++;
			}
		}

		public void bubbleUp(int index) {
			if(index != 0) {
				if(heap[index] > heap[(index-1)/2]) {
					int save = heap[index];
					heap[index] = heap[(index-1)/2];
					heap[(index-1)/2] = save;
					bubbleUp((index-1)/2);
				}
			}
		}

		public void bubbleDown(int index) {
			int minIndex;
			if(2*index+2 >= currSize) {
				if(2*index+1 >= currSize) {
					return;
				} else {
					minIndex = 2*index+1;
				}
			} else {
				if(heap[2*index+2] < heap[2*index+1]) {
					minIndex = 2*index+2;
				} else {
					minIndex = 2*index+1;
				}
			}
			if(heap[index] > heap[minIndex]) {
				int save = heap[minIndex];
				heap[minIndex] = heap[index];
				heap[index] = save;
				bubbleDown(minIndex);
			}
		}

		public boolean notFull() {
			return currSize < size;
		}

		public double average() {
			double ret = 0;
			if(notFull()) {
				return 0.0;
			}
			for(int i=0; i < size; i++) {
				ret += heap[i];
			}
			return ret / size;
		}

		public int peek() {
			return heap[0];
		}
	}

	public static class Score {
		public int id;
		public int score;
	}

	public static int averageScores(Score[] arr) {
		HashMap<Integer, MinHeap> hash = new HashMap<Integer, MinHeap>();
		for(int i=0; i < arr.length; i++) {
			if(hash.containsKey(arr[i].id)) {
				MinHeap m = hash.get(arr[i].id);
				if(m.notFull()) {
					m.insert(arr[i].score);
				} else if(arr[i].score > m.peek()) {
					m.extractMin();
					m.insert(arr[i].score);
				}
			} else {
				MinHeap m = new MinHeap(3);
				m.insert(arr[i].score);
			}
		}
		double highestAverage = 0;
		int id = 0;
		for(Map.Entry<Integer, MinHeap> entry : hash.entrySet()) {
			double average = entry.getValue().average();
			if(average > highestAverage) {
				highestAverage = average;
				id = entry.getKey();
			}
		}
		return id;
	}

	public static ArrayList<String> stringDecomposition(String sentence, String[] words) {
		ArrayList<String> ret = new ArrayList<String>();
		int wordLength = words.length > 0 ? words[0].length() : 0;
		if(wordLength == 0) {
			return ret;
		}
		HashSet<String> h = new HashSet<String>();
		for(String word : words) {
			h.add(word);
		}
		for(int i=0; i < sentence.length() - (wordLength); i++) {
			String substr = sentence.substring(i, i+wordLength);
			if(h.contains(substr)) {
				int index = i + wordLength;
				HashSet<String> copy = new HashSet<String>(h);
				String s = "";
				while(!copy.isEmpty() && copy.contains(substr) && index + wordLength <= sentence.length()) {
					s += substr;
					copy.remove(substr);
					substr = sentence.substring(index, index + wordLength);
					index = index + wordLength;
				}
				if(copy.isEmpty()) {
					ret.add(s);
				}
			}
		}
		return ret;
	}

	public static class Pair {
		public String a;
		public String b;
		
		public Pair(String a, String b) {
			this.a = this.a;
			this.b = this.b;
		}

		public boolean comp(Pair p) {
			if((a.equals(p.a) && b.equals(p.b)) || (a.equals(p.b) && b.equals(p.a))) {
				return true;
			}
			return false;
		}
	}

	public static Pair highestAffinity(String[] logs) {
		HashMap<String, ArrayList<String>> users = new HashMap<String, ArrayList<String>>();
		HashMap<Pair, Integer> pairs = new HashMap<Pair, Integer>();
		for(int i=0; i < logs.length; i++) {
			String[] view = logs[i].split(",");
			if(!users.containsKey(view[1])) {
				ArrayList<String> pages = new ArrayList<String>();
				pages.add(view[0]);
				users.put(view[1], pages);
			} else { //contains the user
				ArrayList<String> pages = users.get(view[1]);
				if(!pages.contains(view[0])) {
					for(int j=0; j < pages.size(); j++) {
						Pair p1 = new Pair(pages.get(j), view[0]);
						Pair p2 = new Pair(view[0], pages.get(j));
						if(pairs.containsKey(p1)) {
							pairs.put(p1, pairs.get(p1) + 1);
						} else if(pairs.containsKey(p2)) {
							pairs.put(p2, pairs.get(p2) + 1);
						} else { //contains neither pair
							pairs.put(p1, 1);
						}
					}
					pages.add(view[0]);
				}
			}
		}
		int max = Integer.MIN_VALUE;
		Pair ret = new Pair("", "");
		for(Pair key : pairs.keySet()) {
			if(pairs.get(key) > max) {
				ret = key;
			}
		}
		return ret;
	}
						
				
	public static void main(String[] args) {
		String sentence = "amanaplanacanalpanama";
		String[] words = {"can", "apl", "ana"};
		ArrayList<String> a = stringDecomposition(sentence, words);
		for(String s : a) {
			System.out.println(a);
		}
	}
}
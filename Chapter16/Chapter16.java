import java.util.*;
public class Chapter16 {
	
	// Problem 16.2
	public static ArrayList<ArrayList<Integer>> queens(int n) {
		ArrayList<Integer> currentConfiguration = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> allConfigurations = new ArrayList<ArrayList<Integer>>();
		findQueens(currentConfiguration, allConfigurations, 0, n);
		return allConfigurations;
	}

	public static void findQueens(ArrayList<Integer> currentConfiguration, ArrayList<ArrayList<Integer>> allConfigurations, int col, int n) {
		if(col == n) {
			ArrayList<Integer> valid = new ArrayList<Integer>(currentConfiguration);
			allConfigurations.add(valid); // If I straight up add currentConfiguration, future modifications to it will affect allConfigurations, so need to make a deep copy of it in valid. The problem is that objects are passed around by reference. Integers are immutable, so I can just make a new ArrayList like this.
		} else {
			for(int row = 0; row < n; row++) {
				currentConfiguration.add(row);
				if(isValid(currentConfiguration)) {
					findQueens(currentConfiguration, allConfigurations, col+1, n);
					System.out.println(allConfigurations);
				}
				currentConfiguration.remove(currentConfiguration.size() - 1);
			}
		}
	}

	public static boolean isValid(ArrayList<Integer> configuration) {
		int newestEltIndex = configuration.size()-1;
		int newestElt = configuration.get(newestEltIndex);
		for(int col=0; col < newestEltIndex; col++) {
			int currentElt = configuration.get(col);
			if(currentElt == newestElt ||
				(currentElt == newestElt - (newestEltIndex-col)) ||
				(currentElt == newestElt + (newestEltIndex-col))) {
				return false;
			}
		}
		return true;
	}
	
	// Problem 16.3
	public static ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> uniqueInts) {
		ArrayList<Integer> currentPerm = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> allPerms = new ArrayList<ArrayList<Integer>>();
		findPermutes(uniqueInts, currentPerm, allPerms);
		return allPerms;
	}

	public static void findPermutes(ArrayList<Integer> uniqueInts, ArrayList<Integer> currentPerm, ArrayList<ArrayList<Integer>> allPerms) {
		if(uniqueInts.size() == 0) {
			ArrayList<Integer> perm = new ArrayList<Integer>(currentPerm);
			allPerms.add(perm);
		} else {
			for(int i=0; i < uniqueInts.size(); i++) {
				currentPerm.add(uniqueInts.get(i)); //add this int to the current permutation
				int save = uniqueInts.remove(i); // make sure it doesn't appear in future permutations
				findPermutes(uniqueInts, currentPerm, allPerms); //find
				currentPerm.remove(currentPerm.size()-1); //found unique permutation, so remove 
				uniqueInts.add(i, save);//add back in to int bank
			}
		}
	}
		
	// Problem 16.4
	public static ArrayList<ArrayList<Integer>> powerSet(ArrayList<Integer> set) {
		ArrayList<ArrayList<Integer>> allSets = new ArrayList<ArrayList<Integer>>();
		computePowerSet(set, allSets);
		return allSets;
	}

	public static void computePowerSet(ArrayList<Integer> currentSet, ArrayList<ArrayList<Integer>> allSets) {
		if(currentSet.size() == 0) {
			ArrayList<Integer> emptySet = new ArrayList<Integer>();
			allSets.add(emptySet);
		} else {
			int save = currentSet.remove(0);
			computePowerSet(currentSet, allSets);
			int preSize = allSets.size();
			for(int i=0; i < preSize; i++) {
				ArrayList<Integer> dup = new ArrayList<Integer>(allSets.get(i));
				dup.add(save);
				allSets.add(dup);
			}
		}
	}
	
	// Problem 16.5
	public static ArrayList<ArrayList<Integer>> kSubset(int n, int k) {
		ArrayList<Integer> curr = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> allSubsets = new ArrayList<ArrayList<Integer>>();
		kSubsetCompute(n, k, 1, curr, allSubsets);
		return allSubsets;
	}

	public static void kSubsetCompute(int n, int k, int start, ArrayList<Integer> curr, ArrayList<ArrayList<Integer>> allSubsets) {
		if(k==0) {
			ArrayList<Integer> save = new ArrayList<Integer>(curr);
			allSubsets.add(save);
		} else {
			for(int i=start; i <= n; i++) {
				curr.add(i);
				kSubsetCompute(n, k-1, i+1, curr, allSubsets);
				curr.remove(curr.size()-1);
			}
		}
	}

	//Problem 16.6
	public static ArrayList<String> parens(int n) {
		ArrayList<String> all = new ArrayList<String>();
		findParensSmart(n, n,  "", all);
		return all;
	}

	public static void findParensNaive(int n, String curr, ArrayList<String> all, HashSet<String> seen) {
		if(n == 0) {
			all.add(curr);
		} else {
			for(int i=0; i < curr.length()+1; i++) {
				String newCurr = curr.substring(0, i) + "()" + curr.substring(i, curr.length());
				if(!seen.contains(newCurr)) {
					seen.add(newCurr);
					findParensNaive(n-1, newCurr, all, seen);
				}
			}
		}
	}

	public static void findParensSmart(int leftCount, int rightCount, String curr, ArrayList<String> all) {
		if(leftCount == 0 && rightCount == 0) {
			all.add(curr);
		} else {
			if(leftCount > 0) {
				findParensSmart(leftCount-1, rightCount, curr + "(", all);
			} 

			if(leftCount < rightCount) {
				findParensSmart(leftCount, rightCount-1, curr + ")", all);
			}
		}
	}

	public static ArrayList<ArrayList<String>> palindromicDecomposition(String s) {
		ArrayList<String> curr = new ArrayList<String>();
		ArrayList<ArrayList<String>> decompositions = new ArrayList<ArrayList<String>>();
		findPalindromicDecomposition(s, curr, decompositions);
		return decompositions;
	}

	public static void findPalindromicDecomposition(String s, ArrayList<String> curr, ArrayList<ArrayList<String>> decompositions) {
		if(s.equals("")) {
			ArrayList<String> valid = new ArrayList<String>(curr);
			decompositions.add(valid);
		} else {
			for(int i=0; i < s.length(); i++) {
				String sub = s.substring(0, i+1);
				if(isPalindrome(sub)) {
					curr.add(sub);
					findPalindromicDecomposition(s.substring(i+1), curr, decompositions);
					curr.remove(sub);
				}
			}
		}
	}

	public static boolean isPalindrome(String s) {
		int len = s.length()-1;
		boolean isPalindrome = true;
		int counter = 0;
		while(isPalindrome && len >= 0) {
			if(!(s.substring(counter, counter+1).equals(s.substring(len, len+1)))) {
				isPalindrome = false;
			}
			len--;
			counter++;
		}
		return isPalindrome;
	}


	public static void main(String[] args) {
		String s = "20218814444";
		System.out.println(palindromicDecomposition(s));
	}

}
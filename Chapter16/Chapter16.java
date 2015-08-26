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
				currentPerm.add(uniqueInts.get(i));
				int save = uniqueInts.remove(i);
				findPermutes(uniqueInts, currentPerm, allPerms);
				currentPerm.remove(currentPerm.size()-1);
				uniqueInts.add(i, save);
			}
		}
	}
		
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

	public static void main(String[] args) {
		ArrayList<Integer> uniqueInts = new ArrayList<Integer>();
		uniqueInts.add(1);
		uniqueInts.add(2);
		uniqueInts.add(3);
		uniqueInts.add(4);
		System.out.println(powerSet(uniqueInts));
	}

}
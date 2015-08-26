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

	public static void main(String[] args) {
		System.out.println(queens(4));
	}

}
public class Chapter12 {
	public static int firstOccurrence(int[] arr, int k) {
		int l = 0;
		int r = arr.length-1;
		int lowestIndex = -1;
		while(l <= r) {
			int m = l + (r-l)/2;
			if(arr[m] == k) {
				lowestIndex=m;
				r = m-1;
			} else if(arr[m] > k) {
				r = m-1;
			} else { // arr[m] < k
				l = m+1; 
			}
		}
		return lowestIndex;
	}

	public static boolean search2d(int[][] sorted, int target) {
		int numRows = sorted.length;
		int numCols = sorted[0].length;
		int currCol = 0;
		int currRow = numRows-1;
		while(numRows != 0 && numCols != 0) {
			if (sorted[currRow][currCol] > target) {
				numRows--;
				currRow--;
			} else if(sorted[currRow][currCol] < target) {
				numCols--;
				currCol++;
			} else {
				return true;
			}
		}
		return false;
	}
			

	public static void main(String[] args) {
		int[] arr = {-14, -10, 2, 108, 108, 243, 285, 285, 285, 401};
		System.out.println(firstOccurrence(arr, 285));
	}
}
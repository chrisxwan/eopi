public class Chapter14 {
	// [5,13,17,_,_,_,_,_] and [3,7,11,19]
	public static int[] mergeSortInPlace(int[] a, int[] b) {
		int aPointer = 0;
		for(int i = 0; i < a.length-1; i++) {
			if(a[i+1] != 0) {
				aPointer++;
			}
		}
		int bPointer = b.length-1;
		int currPointer = a.length - 2;
		while (bPointer >= 0) {
			if(aPointer < 0 || b[bPointer] > a[aPointer]) {
				a[currPointer] = b[bPointer];
				bPointer--;
			} else {
				a[currPointer] = a[aPointer];
				aPointer--;
			}
			currPointer--;
		}
		return a;
	}

	public static int[][] disjointUnion(int[][] input, int[] newInterval) {
		int[][] ret = new int[input.length+1][2];
		int counter = 0;
		boolean processingIntersection = false;
		for(int i=0; i < input.length; i++) {
			int[] interval = input[i];
			int left = interval[0];
			int right = interval[1];
			if(processingIntersection && left > newInterval[1]) {
				ret[counter] = newInterval;
				counter++;
				processingIntersection = false;
			}
			if(newInterval[0] <= right && newInterval[0] >= left) {
				processingIntersection = true;
				newInterval[0] = left;
			}
			if(newInterval[1] >= left && newInterval[1] <= right) {
				newInterval[1] = right;
			}
			if((newInterval[0] > right) || (newInterval[1] < left)) {
				ret[counter] = interval;
				counter++;
			}
		}
		return ret;
	}



	public static void main(String[] args) {
		int[][] input = {{-4,-1},{0,2},{3,6},{7,9},{11,12},{14,17}};
		int[] newInterval = {1,8};
		input = disjointUnion(input, newInterval);
		for(int i=0; i < input.length; i++) {
			for(int j=0 ; j < 2; j++) {
				System.out.print(input[i][j]);
			}
			System.out.println();
		}
	}
}	
import java.util.*;

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

	public static class Point {
		public int point;
		public boolean open;

		public Point (int point, boolean open) {
			this.point = point;
			this.open = open;
		}
	}

	public static class EndPoint {
		public Point left;
		public Point right;

		public EndPoint(Point left, Point right) {
			this.left = left;
			this.right = right;
		}
	}

	public static void  mergeSort(EndPoint[] arr, EndPoint[] tmp, int left, int right) {
		if(left < right) {
			int mid = (left + right) / 2;
			mergeSort(arr, tmp, left, mid);
			mergeSort(arr, tmp, mid+1, right);
			merge(arr, tmp, left, mid+1, right);
		}
	}

	public static void merge(EndPoint[] arr, EndPoint[] tmp, int left, int right, int rightEnd) {
		int leftEnd = right-1;
		int k = left;
		int total = rightEnd-left+1;

		while(left <= leftEnd && right <= rightEnd) {
			if(arr[left].left.point < arr[right].left.point) {
				tmp[k++] = arr[left++];
			} else {
				tmp[k++] = arr[right++];
			}
		}

		while(left <= leftEnd) {
			tmp[k++] = arr[left++];
		}

		while (right <= rightEnd) {
			tmp[k++] = arr[right++];
		}

		for(int i=0; i < total; i++, rightEnd--) {
			arr[rightEnd] = tmp[rightEnd];
		}
	}

	public static ArrayList<EndPoint> intervalUnion(EndPoint[] arr) {
		ArrayList<EndPoint> ret = new ArrayList<EndPoint>();
		EndPoint[] tmp = new EndPoint[arr.length];
		mergeSort(arr, tmp, 0, arr.length-1);
		Point currLeft = arr[0].left;
		Point currRight = arr[0].right;
		for(int i=0; i < arr.length; i++) {
			if(arr[i].left.point > currRight.point) {
				EndPoint interval = new EndPoint(currLeft, currRight);
				ret.add(interval);
				currLeft = arr[i].left;
				currRight = arr[i].right;
			} else {
				if(arr[i].right.point > currRight.point || 
				  (!arr[i].right.open && arr[i].right.point == currRight.point)) {
				  	currRight = arr[i].right;
				}
				if(arr[i].left.point == currLeft.point && !arr[i].left.open) {
					currLeft = arr[i].left;
				}
			}
		}
		return ret;
	}

	public static void sort(int[] arr) {
		int[] ret = new int[arr.length];
		HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
		for(int i=0; i < arr.length; i++) {
			int put = hash.containsKey(arr[i]) ? hash.get(arr[i]) + 1 : 1;
			hash.put(arr[i], put);
		}
		HashMap<Integer, Integer> index = new HashMap<Integer, Integer>();
		int offset = 0;
		for(int key : hash.keySet()) {
			index.put(key, offset);
			offset += hash.get(key);
		}
		int currIndex = 0;
		int size = arr.length;
		while(size > 0) {
			int curr = arr[currIndex];
			int pos = index.get(curr);
			index.put(curr, index.get(curr) + 1);
			int temp = arr[pos];
			arr[pos] = curr;
			arr[currIndex] = temp;
			if (pos == currIndex)
				currIndex++;
			size--;
		}
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
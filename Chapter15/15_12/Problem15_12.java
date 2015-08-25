public class Problem15_12 {
	public static class BinaryTree {
		public BinaryTree right;
		public BinaryTree left;
		public int key;
		
		public BinaryTree(int key, BinaryTree right, BinaryTree left) {
			this.key = key;
			this.right = right;
			this.left = left;
		}

		public BinaryTree(int key) {
			this.key = key;
		}
	}
	/* I hate this solution so much */
	public static boolean isTotallyOrderedShitty(BinaryTree middle, BinaryTree key1, BinaryTree key2) {
		boolean foundMiddle = false;
		boolean foundDescendant = false;
		BinaryTree temp1 = key1;
		BinaryTree temp2 = key2;
		BinaryTree tempMid = middle;
		while(!foundMiddle) {
			if(temp1 == null && temp2 == null) {
				return false; // neither key1 nor key2 are ancestors
			} else {
				if(temp1.key > middle.key) {
					temp1 = temp1.left;
				} else if (temp1.key < middle.key) {
					temp1 = temp1.right;
				} else { // key 1 is the ancestor. check if key 2 is descendant
					foundMiddle = true;
					while(!foundDescendant) {
						if(tempMid == null) {
							return false; //key 2 is not a descendant!
						} else {
							if(tempMid.key > key2.key) {
								tempMid = tempMid.left;
							} else if(tempMid.key < key2.key) {
								tempMid = tempMid.right;
							} else { //key 2 is a descendant
								return true;
							}
						}
					}
				}
				if(temp2.key > middle.key) {
					temp2 = temp2.left;
				} else if(temp2.key < middle.key) {
					temp2 = temp2.right;
				} else { // key 2 is the ancestor. check if key 1 is the descendant.
					foundMiddle = true;
					while(!foundDescendant) {
						if(tempMid == null) { // key 1 is not a descendant!
							return false;
						} else {
							if(tempMid.key > key1.key) {
								tempMid = tempMid.left;
							} else if(tempMid.key < key1.key) {
								tempMid = tempMid.right;
							} else {
								return true; // key 1 is a descendant
							}
						}
					}
				}
			}
		}
		return foundMiddle && foundDescendant;
	}
}
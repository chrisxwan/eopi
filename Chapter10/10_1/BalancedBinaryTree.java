import java.util.*;

public class BalancedBinaryTree {
	BalancedBinaryTree left;
	BalancedBinaryTree right;
	int value;
	
	/* Actual Binary Tree not implemented. Fields are included just for reference */

	public static int[] isBalanced(BalancedBinaryTree tree) {
		int[] returnArray = new int[2];
		if(tree.value == null) {
			returnArray = [0, -1];
			return returnArray;
		}
		
		int[] leftSubtree = isBalanced(tree.left);
		if(!leftSubtree[0]) {
			returnArray = [0, leftSubtree[1]];
			return returnArray;
		}

		int[] rightSubtree = isBalanced(tree.right);
		if(!rightSubtree[0]) {
			returnArray = [0, rightSubtree[1]];
			return returnArray;
		}

		returnArray[0] = Math.abs(rightSubtree[0] - leftSubtree[1] <= 1) ? 1 : 0;
		returnArray[1] = Math.max(rightSubtree[1], leftSubtree[1]) + 1;
		return returnArray;
	}
}
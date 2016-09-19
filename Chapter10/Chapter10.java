import java.util.*;
import java.lang.*;

public class Chapter10 {
	public static class BalancedBinaryTree {
		BalancedBinaryTree left;
		BalancedBinaryTree right;
		int value;
	}

	public static class NodeInfo {
		BalancedBinaryTree node;
		int count;

		public NodeInfo(BalancedBinaryTree node, int count) {
			this.node = node;
			this.count = count;
		}
	}
	
	/* Actual Binary Tree not implemented. Fields are included just for reference */

	public static int[] isBalanced(BalancedBinaryTree tree) {
		int[] returnArray = new int[2];
		if(tree.value == 0) {
			returnArray[0] = 0;
			returnArray[1] = -1;
			return returnArray;
		}
		
		int[] leftSubtree = isBalanced(tree.left);
		if(leftSubtree[0] == 0) {
			returnArray[0] = 0;
			returnArray[1] = leftSubtree[1];
			return returnArray;
		}

		int[] rightSubtree = isBalanced(tree.right);
		if(rightSubtree[0] == 0) {
			returnArray[0] = 0;
			returnArray[1] = rightSubtree[1];
			return returnArray;
		}

		returnArray[0] = Math.abs(rightSubtree[0] - leftSubtree[1]) <= 1 ? 1 : 0;
		returnArray[1] = Math.max(rightSubtree[1], leftSubtree[1]) + 1;
		return returnArray;
	}

	public static boolean checkSymmetry(BalancedBinaryTree left, BalancedBinaryTree right) {
		if (left == null && right == null) {
			return true;
		}

		if (left.value == right.value) {
			boolean s1 = checkSymmetry(left.right, right.left);
			boolean s2 = checkSymmetry(left.left, right.right);
			return s1 && s2;
		}
		return false;
	}

	public static boolean isSymmetric(BalancedBinaryTree tree) {
		return checkSymmetry(tree.left, tree.right);
	}

	public static NodeInfo search(BalancedBinaryTree tree, BalancedBinaryTree n1, BalancedBinaryTree n2) {
		if (tree.value == 0) {
			return new NodeInfo(null, 0);
		}
		NodeInfo searchLeft = search(tree.left, n1, n2);
		if (searchLeft.count == 2) {
			return searchLeft;
		}
		NodeInfo searchRight = search(tree.right, n1, n2);
		if (searchRight.count == 2) {
			return searchRight;
		}
		int curr_match = tree.value == n1.value || tree.value == n2.value ? 1 : 0;
		int num_search = searchLeft.count + searchRight.count + curr_match;
		return new NodeInfo(num_search == 2 ? tree : null, num_search);
	}
}
import java.util.*;

public class Chapter9 {
	public static class Node {
		private int elt;
		private int max;
		private Node prev;

		public Node(int elt) {
			this.elt = elt;
			this.max = 0;
		}

		public void setMax(int max) {
			this.max = max;
		}

		public void setPrev(Node prev) {
			this.prev = prev;
		}
	}

	public static class MaxStack {
		private Node head;
		private Node curr;

		public MaxStack(Node head) {
			this.head = head;
			this.curr = head;
		}

		public int pop() {
			int ret = this.curr.elt;
			this.curr = this.curr.prev;
			return ret;
		}

		public int retMax() {
			return this.curr.max;
		}

		public void push(int elt) {
			Node newNode = new Node(elt);
			if (elt > this.curr.max) {
				newNode.max = elt;
			} else {
				newNode.max = this.curr.max;
			}
			newNode.prev = this.curr;
			this.curr = newNode;
		}
	}

	public static int rpn(String expr) {
		String[] arr = expr.split(",");
		Stack<Integer> s = new Stack<Integer>();
		for(int i=0; i < arr.length; i++) {
			if (arr[i].equals("*")) {
				int n1 = s.pop();
				int n2 = s.pop();
				s.push(n1 * n2);
			} else if (arr[i].equals("+")) {
				int n1 = s.pop();
				int n2 = s.pop();
				s.push(n1 + n2);
			} else if (arr[i].equals("-")) {
				int n1 = s.pop();
				int n2 = s.pop();
				s.push(n2 - n1);
			} else if (arr[i].equals("/")) {
				int n1 = s.pop();
				int n2 = s.pop();
				s.push(n2 / n1);
			} else {
				int curr = Integer.parseInt(arr[i]);
				s.push(curr);
			}
		}
		return s.pop();
	}

	public static void main(String[] args) {
		int ret = rpn("3,4,+,2,*");
		System.out.println(ret);
	}
}
			
			

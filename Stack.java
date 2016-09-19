import java.util.*;

public class Stack {
	private int size;
	private int[] stack;
	private int currIndex;

	public Stack(int size) {
		this.size = size;
		this.stack = new int[size];
		this.currIndex = 0;
	}

	public void push(int elt) {
		if(currIndex == size) {
			System.out.println("Stack is full.");
			return;
		}
		stack[currIndex] = elt;
		currIndex++;
	}

	public int pop() {
		if(currIndex == 0) {
			throw new EmptyStackException();
		}
		currIndex--;
		int ret = stack[currIndex];
		stack[currIndex] = 0;
		return ret;
	}
}
	
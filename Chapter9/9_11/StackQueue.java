import java.util.*;

public class StackQueue {
	Stack<Integer> enqueueStack;
	Stack<Integer> dequeueStack;

	public StackQueue() {
		enqueueStack = new Stack<Integer>();
		dequeueStack = new Stack<Integer>();
	}

	public void enqueue(int elt) {
		enqueueStack.push(elt);
	}

	public int dequeue() {
		if(dequeueStack.empty()){ 
			while(!enqueueStack.empty()) {
				int popElt = enqueueStack.pop();
				dequeueStack.push(popElt);
			}
		}
		int returnElt = dequeueStack.pop();
		return returnElt;
	}

}	

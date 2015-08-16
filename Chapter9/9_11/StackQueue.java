import java.util.Stack;

public class StackQueue {
	Stack<Int> enqueueStack;
	Stack<Int> dequeueStack;

	public StackQueue {
		enqueueStack = new Stack<Int>();
		dequeueStack = new Stack<Int>();
	}

	public void enqueue(int elt) {
		enqueueStack.push(elt);
	}

	public int dequeue() {
		if(dequeueStack.empty(){ 
			while(!enqueueStack.empty() {
				int popElt = enqueueStack.pop();
				dequeueStack.push(popElt);
			}
		}
		int returnElt = dequeueStack.pop();
		return returnElt;
	}

}	

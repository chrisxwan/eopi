
public class CircularQueue {
	int numElements;
	int enqueueIndex;
	int dequeueIndex;
	int size;
	int[] queue;

	public CircularQueue(int size) {
		numElements = 0;
		this.size = size;
		enqueueIndex = 0;
		dequeueIndex = 0;
		queue = new int[size];
	}

	public int getSize() {
		return size;
	}

	public void enqueue(int elt) {
		if(enqueueIndex == dequeueIndex && numElements > 0) {
			int[] resizeQueue = new int[size * 2];
			for(int i=0; i < size; i++) {
				resizeQueue[i+enqueueIndex] = queue[(i+enqueueIndex)%size];
			}
			enqueueIndex += size;
			size *= 2;
			queue = resizeQueue;
		}
		queue[enqueueIndex] = elt;
		enqueueIndex = (enqueueIndex + 1) % size;
		numElements++;
	}

	public int dequeue() {
		if(numElements == 0) {
			return 0;
		}
		int returnElt = queue[dequeueIndex];
		queue[dequeueIndex] = 0;
		dequeueIndex = (dequeueIndex+1) % size;
		numElements--;
		return returnElt;
	}

	public void print() {
		for(int i=0; i < queue.length; i++) {
			System.out.println(queue[i]);
		}
	}

	public static void main(String[] args) {
		CircularQueue cq = new CircularQueue(5);
		for(int i=1; i < 10; i++) {
			cq.enqueue(i);
			if(i >= 8) {
				cq.dequeue();
			}
		}

		cq.print();
	}
}

		

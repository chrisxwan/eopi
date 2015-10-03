public static class Node<A> {
	Node<A> next;
	A v;

	public Node(A v, Node<A> next) {
		this.v = v;
		this.next = next;
	}

	public Node(A v) {
		this.v = v;
	}
}


public static class Queue<A> {
	Node<A> head;
	Node<A> tail;

	public void enqueue(A e) {
		if(head == null) {
			head = tail = new Node<A>(e);
		} else {
			tail.next = new Node<A>(e);
			tail = tail.next;
		}
	}

	public void enqueue(Node<A> e) {
		if(head == null) {
			head = tail = e;
		} else {
			tail.next = e;
			tail = tail.next;
		}
	}

	public A dequeue() {
		if(head != null) {
			A ret = head.v;
			head = head.next;
			return ret;
		}
		return null;
	}

	public void makeHead(Node<A> n) {
		A save = del(n);
		Node<A> newHead = new Node<A>(save);
		newHead.next = head;
		head = newHead;
	}

	public A del(Node<A> n) {
		A save = n.v;
		if(n.next != null) {
			n.v = n.next.v;
			n.next = n.next.next;
		} else {
			n = null;
		}
		return save;
	}
		
}

public static class MinHeap {
	public int size;
	public int[] heap;
	public int currSize;

	public MinHeap(int size) {
		this.size = size;
		this.heap = new int[size];
		this.currSize = 0;
	}

	public int extractMin() {
		int save = heap[0];
		heap[0] = heap[currSize-1];
		currSize--;
		if(currSize != 0) {
			bubbleDown(0);
		}
		return save;
	}

	public void insert(int n) {
		if(currSize < size) {
			heap[currSize] = n;
			bubbleUp(currSize);
			currSize++;
		}
	}

	public void bubbleUp(int index) {
		if(index != 0) {
			if(heap[index] > heap[(index-1)/2]) {
				int save = heap[index];
				heap[index] = heap[(index-1)/2];
				heap[(index-1)/2] = save;
				bubbleUp((index-1)/2);
			}
		}
	}

	public void bubbleDown(int index) {
		int minIndex;
		if(2*index+2 >= currSize) {
			if(2*index+1 >= currSize) {
				return;
			} else {
				minIndex = 2*index+1;
			}
		} else {
			if(heap[2*index+2] < heap[2*index+1]) {
				minIndex = 2*index+2;
			} else {
				minIndex = 2*index+1;
			}
		}
		if(heap[index] > heap[minIndex]) {
			int save = heap[minIndex];
			heap[minIndex] = heap[index];
			heap[index] = save;
			bubbleDown(minIndex);
		}
	}

	public boolean notFull() {
		return currSize < size;
	}

	public double average() {
		double ret = 0;
		if(notFull()) {
			return 0.0;
		}
		for(int i=0; i < size; i++) {
			ret += heap[i];
		}
		return ret / size;
	}

	public int peek() {
		return heap[0];
	}
}
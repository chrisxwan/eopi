import java.util.*;
public class Chapter19 {
	public static class Coordinate {
		public int x;
		public int y;

		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static int[][] booleanMatrix(int[][] grid, int x, int y) {
		Queue<Coordinate> queue = new LinkedList<Coordinate>();
		queue.add(new Coordinate(x, y));
		int color = grid[x][y];
		while(!queue.isEmpty()) {
			Coordinate c = queue.remove();
			grid[c.x][c.y] = color == 1 ? 0 : 1;
			int[][] possibleMoves = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
			for(int[] move : possibleMoves) {
				int newX = c.x+move[0];
				int newY = c.y+move[1];
				if(newX < grid[0].length && newX >= 0 && newY < grid.length && newY >= 0 && grid[newX][newY] == color) {
					queue.add(new Coordinate(newX, newY));
				}
			}		
		}
		return grid;
	
	}

	public static int[][] paintBooleanMatrix(int[][] grid, int x, int y, int color) {
		if(x < grid[0].length && y < grid.length && y >=0 && x >= 0 && grid[x][y] == color) {
			grid[x][y] = color == 1 ? 0 : 1;
			int[][] possibleMoves = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
			for(int[] move : possibleMoves) {
				grid = paintBooleanMatrix(grid, x + move[0], y + move[1], color);
			}
		}
		return grid;
	}

	public static String[][] enclosed(String[][] grid) {
		HashSet<Coordinate> seen = new HashSet<Coordinate>();
		for(int i=0; i < grid.length; i++) {
			for(int j=0; j < grid[i].length; j++) {
				Coordinate curr = new Coordinate(i, j);
				if(!seen.contains(curr) && isEnclosed(grid, i, j, seen)) {
					grid = color(grid, i, j);
				}
			}
		}
		return grid;
	}

	public static boolean isEnclosed(String[][] grid, int x, int y, HashSet<Coordinate> seen) {
		if(x > 0 && y > 0 && x < grid[0].length-1 && y < grid.length-1 && grid[x][y].equals("W")) {
				seen.add(new Coordinate(x, y));
				grid[x][y] = "B";
				boolean up = grid[x][y+1].equals("W") ? isEnclosed(grid, x, y+1, seen) : true;
				boolean down = grid[x][y-1].equals("W") ? isEnclosed(grid, x, y-1, seen) : true;
				boolean right = grid[x+1][y].equals("W") ? isEnclosed(grid, x+1, y, seen) : true;
				boolean left = grid[x-1][y].equals("W") ? isEnclosed(grid, x-1, y, seen) : true;
				return (up || down || right || left);
		}
		return false;
	}

	public static String[][] color(String[][] grid, int x, int y) {
		if(x < grid[0].length && y < grid.length && x >= 0 && y >= 0 && grid[x][y] == "W") {
			grid[x][y] = "B";
			int[][] possibleMoves = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
			for(int[] move : possibleMoves) {
				grid = color(grid, x + move[0], y + move[1]);
			}	
		}
		return grid;
	}


	//Problem 16.5
	public static class Graph {
		public boolean visited;
		public Graph[] vertices;
	}

	public static boolean minConnected(Graph g) {
		for(Graph v : g.vertices) {
			if(hasCycle(v)) {
				return false;
			}
		}
		return true;
	}

	public static boolean hasCycle(Graph g) {
		g.visited = true;
		for(Graph v : g.vertices) {
			if(v.visited) {
				return true;
			} else {
				return hasCycle(v);
			}
		}
		return false;
	}

	public static class DirectedGraph {
		public DirectedGraph[] vertices;
		public String key;

		public DirectedGraph(String key, int numNeighbors) {
			this.key = key;
			this.vertices = new DirectedGraph[numNeighbors];
		}

		public DirectedGraph(DirectedGraph g) {
			this.key = g.key;
			this.vertices = new DirectedGraph[g.vertices.length];
		}
	}

	public static class Node<A> {
		public Node<A> next;
		public A v;

		public Node(A v, Node<A> next) {
			this.next = next;
			this.v = v;
		}

		public Node(A v) {
			this.v = v;
		}
	}

	public static class MyQueue<A> {
		public Node<A> head;
		public Node<A> tail;

		public void enqueue(A elt) {
			if(head == null) {
				head = tail = new Node<A>(elt);
			} else {
				tail.next = new Node<A>(elt);
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

		public boolean empty() {
			if(head == null) {
				return true;
			}
			return false;
		}
	}

	public static DirectedGraph copy(DirectedGraph g) {
		HashMap<DirectedGraph, DirectedGraph> hash = new HashMap<DirectedGraph, DirectedGraph>();
		DirectedGraph curr = g;
		MyQueue<DirectedGraph> q = new MyQueue<DirectedGraph>();
		DirectedGraph copy = new DirectedGraph(g);
		hash.put(curr, copy);
		q.enqueue(curr);
		while(!q.empty()) {
			curr = q.dequeue();
			copy = hash.get(curr);
			for(int i=0; i < curr.vertices.length; i++) {
				DirectedGraph neighbor = curr.vertices[i];
				if(!hash.containsKey(neighbor)) {
					DirectedGraph newVertex = new DirectedGraph(neighbor);
					hash.put(neighbor, newVertex);
					q.enqueue(neighbor);
					copy.vertices[i] = newVertex;
				} else {
					copy.vertices[i] = hash.get(neighbor);
				}
			}
		}
		return hash.get(g);
	}

	public static boolean isBipartite(Graph g) {
		MyQueue<Graph> q = new MyQueue<Graph>();
		HashMap<Graph, Integer> hash = new HashMap<Graph, Integer>();
		q.enqueue(g);
		hash.put(g, 1);
		while(!q.empty()) {
			Graph curr = q.dequeue();
			int currColor = hash.get(curr);
			for(int i=0; i < curr.vertices.length; i++) {
				Graph next = curr.vertices[i];
				if(!hash.containsKey(next)) {
					hash.put(next, currColor * -1);
					q.enqueue(next);
				} else {
					if(hash.get(next) == currColor) {
						return false;
					}
				}	
			}
		}
		return true;
	}
	
	public static class Tuple<K, V> {
		public K k;
		public V v;

		public Tuple(K k, V v) {
			this.k = k;
			this.v = v;
		}
	}

	public static int transformString(HashSet<String> dict, String s, String t) {
		MyQueue<Tuple<String, Integer>> q = new MyQueue<Tuple<String, Integer>>();
		if(dict.contains(s)) {
			Tuple<String, Integer> tuple = new Tuple<String, Integer>(s, 0);
			q.enqueue(tuple);
			dict.remove(s);
		}
		while(!q.empty()) {
			Tuple<String, Integer> curr = q.dequeue();
			if(curr.k == t) {
				return curr.v;
			} else {
				String currString = curr.k; 
				for(int i=0; i < currString.length(); i++) {
					for(int j=0; j < 26; j++) {
						String next = currString.substring(0, i) + ('a' + j) + currString.substring(i+1);
						if(dict.contains(next)) {
							Tuple<String, Integer> tuple = new Tuple<String, Integer>(next, curr.v+1);
							q.enqueue(tuple);
							dict.remove(next);
						}
					}
				}
			}
		}
		return -1;
	}

	public static ArrayList<Integer> straightLine(int n) {
		ArrayList<Integer> curr = new ArrayList<Integer>();
		curr.add(1);
		MyQueue<ArrayList<Integer>> q = new MyQueue<ArrayList<Integer>>();
		while(!curr.contains(n)) {
			for(int i=0; i < curr.size(); i++) {
				for(int j=i; j < curr.size(); j++) {
					if(!curr.contains(curr.get(i)+curr.get(j))) {
						ArrayList<Integer> push = new ArrayList<Integer>(curr);
						push.add(curr.get(i)+curr.get(j));
						q.enqueue(push);
					}
				}
			}
			curr = q.dequeue();
		}
		return curr;
	}


	public static boolean differByOne(String s1, String s2) {
		if(s1.length() != s2.length()) {
			return false;
		}
		int counter = 0;
		for(int i=0; i < s1.length(); i++) {
			if(s1.substring(i, i+1) != s2.substring(i, i+1)) {
				counter++;
			}
		}
		return counter==1;
	}

	public static void main(String[] args) {
		ArrayList<Integer> line = straightLine(15);
		System.out.println(i);
	}
}
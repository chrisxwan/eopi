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
			

	public static void main(String[] args) {
		String[][] grid = new String[][]{{"B", "B", "B", "B"}, {"W", "B", "W", "B"},{"B", "W", "W", "B"}, {"B", "B", "B", "B"}};
		String[][] newGrid = enclosed(grid);
		for(int i=0; i < grid.length; i++) {
			for(int j=0; j < grid[i].length; j++) {
				System.out.print(newGrid[i][j]);
			}
			System.out.println();
		}
//		System.out.println(booleanMatrix(grid, 0, 0));
	}
}
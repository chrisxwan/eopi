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

	public static void main(String[] args) {
		int[][] grid = new int[][]{{0,1,0}, {0,0,1},{0,1,1}};
		int[][] newGrid = booleanMatrix(grid, 0, 0);
		for(int i=0; i < grid.length; i++) {
			for(int j=0; j < grid[i].length; j++) {
				System.out.print(newGrid[i][j]);
			}
			System.out.println();
		}
//		System.out.println(booleanMatrix(grid, 0, 0));
	}
}
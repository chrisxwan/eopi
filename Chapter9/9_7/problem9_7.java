

public class problem9_7 {
	public static int[] sunsetBuildings(int[] buildings) {
		int numBuildings = buildings.length;
		int[] sunset = new int[numBuildings];
		if(numBuildings == 0) {
			return sunset;
		}
		sunset[0] = buildings[0];
		int topIndex = 0;
		for(int i = 1; i < buildings.length; i++) {
			while(topIndex >= 0 && buildings[i] >= sunset[topIndex]) {
				sunset[topIndex] = 0;
				topIndex--;
			}
			topIndex++;
			sunset[topIndex] = buildings[i];
		}
		return sunset;
	}

	public static void main(String[] args) {
		int[] buildings = {10, 9, 8, 7, 9, 6, 5, 4, 5, 3, 1};
		int sunset[] = sunsetBuildings(buildings);
		for(int i=0; i < sunset.length; i++) {
			System.out.println(sunset[i]);
		}
	}
}
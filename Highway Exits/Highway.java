import java.util.*;

public class Highway {

	public static void main(String[] args) {
		int[] distances = {5, 3, 4, 2, 8, 6};
		int first = 0;
		int last = 6;
		System.out.println(findDistance(first, last, distances));
	}
	
	public static int findDistance(int initial, int target, int[] list) {
		if (initial < 0 || (target - 1) > list.length || initial > target) {
			throw new NoSuchElementException("Invalid Inputs");
		} else if (initial == target) {
			return 0;
		}
		
		int distance = 0;
		for (int i = initial; i < target; i++) {
			distance += list[i];
		}
		return distance;
	}

}

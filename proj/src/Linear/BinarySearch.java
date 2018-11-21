package Linear;

public class BinarySearch {
	
	public static int binarySearch(int[] A, int target) {
		int lo = 0;
		int hi = A.length - 1;
		while (lo <= hi) {
			int m = (lo+hi)/2;
			if (target == A[m]) {
				return m;				
			} else if (target < A[m]) {
				hi = m-1;
			} else {
				lo = m+1;
			}
		}
		return -1;
	}
}

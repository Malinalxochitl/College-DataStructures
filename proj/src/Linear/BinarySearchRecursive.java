package Linear;

public class BinarySearchRecursive {
	
	public static int binarySearch(int[] A, int lo, int hi, int target) {
		if (lo > hi) {
			return -1;
		}
		int m = (lo+hi)/2;
		if (target == A[m]) {
			return m;
		} else if (target < A[m]) {
			return binarySearch(A, lo, m-1, target);
		} else {
			return binarySearch(A, m+1, hi, target);
		}
	}
	public static int binarySearch(int[] A, int target) {
		return binarySearch(A, 0, A.length-1, target);
	}
	public static void main(String[] args) {
		int[] A = {3, 18, 20, 53, 70, 99};
		System.out.println(binarySearch(A, 53));
		System.out.println(binarySearch(A, 30));
	}
}

package tree;

public class HeapSort {

	public static void main(String[] args) {
		
		int[] n = {14, 3, 1, 15, 2, 10};
		Heap<Integer> heap = new Heap<Integer>(Heap.MIN_HEAP);
		
		// build phase nlogn
		for (int i = 0; i < n.length; i++) {
			heap.insert(n[i]);
		}
		
		// sort phase nlogn
		int count = 0;
		while (!heap.isEmpty()) {
			n[count++] = heap.delete();
		}
		
		for (int item : n) {
			System.out.println(item);
		}
	}

}

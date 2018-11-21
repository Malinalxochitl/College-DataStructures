package Linear;

public class IntLL {

	public static IntNode addToFront (IntNode front, int data) {
		return new IntNode(data, front);
	}
	
	public static void traverse (IntNode front) {
		IntNode ptr = front; // ptr points to the first node of the LL
		while (ptr != null) {
			System.out.print(ptr.data + " -> ");
			ptr = ptr.next;
		}
	}
	
	public static IntNode removeFront (IntNode front) {
		return front.next;
	}
	
	public static IntNode search (IntNode front, int target) {
		IntNode ptr = front;
		while (ptr != null) {
			if (ptr.data == target) {
				return ptr;
			}
		}
	return null;
	}
	
	public static IntNode delete (IntNode front, int target) {
		IntNode prev = null, ptr = front;
		while (ptr != null && ptr.data != target) {
			prev = ptr;
			ptr = ptr.next;
		}
		if (ptr == null) { //target not found
			return front;
		} else if (ptr == front) { //target is front
			return front.next;
		} else {
			prev.next = ptr.next;
			return front;
		}
	}
	
	public static boolean addAfter(IntNode front, int target, int data) {
		
		for (IntNode ptr = front; ptr != null; ptr = ptr.next) {
			if (ptr.data == target) {
				IntNode node = new IntNode(data, ptr.next);
				ptr.next = node;
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		IntNode L = null; //handle for the beginning of the LL
		L = addToFront(L, 26);
		L = addToFront(L, 24);
		L = addToFront(L, 19);
		L = addToFront(L, 17);
		traverse(L);
		System.out.println();
		L = removeFront(L);
		traverse(L);
		IntNode found = search(L, 26);
		if (found != null) {
			System.out.println("Found it");
		} else {
			System.out.println("Not Found");
		}
	}

}

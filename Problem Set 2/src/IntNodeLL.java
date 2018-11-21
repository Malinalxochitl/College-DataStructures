
public class IntNodeLL {
	
	public static IntNode addBefore(IntNode front, int target, int newItem) {
		IntNode ptr, previous = null;

		for (ptr = front; ptr != null && ptr.data != target; ptr = ptr.next) {
			previous = ptr;
		}
		
		if (ptr == front) {
			front = new IntNode (newItem, ptr);	
		} else if (ptr != null) {
			IntNode node = new IntNode(newItem, ptr);
			previous.next = node;
		}
		
		return front;
	}
	
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
	
	public static void main(String[] args) {
		IntNode L = null;
		L = addBefore(L, 3, 2);
		traverse(L);
		System.out.println();
		L = addToFront(L, 5);
		L = addToFront(L, 4);
		L = addToFront(L, 3);
		L = addToFront(L, 1);
		L = addToFront(L, 0);
		traverse(L);
		System.out.println();
		L = addBefore(L, 6, 99);
		traverse(L);
	}
}

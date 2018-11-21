package Linear;

public class DLL <T> {
	private DLLNode <T> front;
	private int size;
	public DLL () {
		front = null;
		size = 0;
	}
	public int getSize() {
		return size;
	}
	
	public void addToFront(T data) {
		DLLNode<T> node = new DLLNode<T>(data, null, null);
		if (front == null) {
			front = node;
		} else {
			node.next = front;
			front.previous = node;
			front = node;
		}
		size++;
	}
	
	public void addAfter (T data, T target) {
		DLLNode<T> ptr = front;
		while (ptr != null && ptr.data != target) {
			ptr = ptr.next;
		}
		if (ptr == null) { //target not found
			return;
		} else {
			DLLNode<T> node = new DLLNode<T>(data, ptr.next, ptr);
			ptr.next.previous = node;
			ptr.next = node;
		}
	}
	
	public void traverse () {
		DLLNode<T> ptr = front;
		while (ptr != null) {
			System.out.println(ptr.data);
			ptr = ptr.next;
		}
		System.out.println("");
	}
}

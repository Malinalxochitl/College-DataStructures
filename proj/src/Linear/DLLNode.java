package Linear;

public class DLLNode <T> {
	
	T data;
	DLLNode <T> previous;
	DLLNode <T> next;
	public DLLNode (T data, DLLNode<T> previous, DLLNode<T> next) {
		this.data = data;
		this.previous = previous;
		this.next = next;
	}
}

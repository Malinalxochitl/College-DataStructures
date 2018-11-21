package Linear;

import java.util.NoSuchElementException;

public class CLL <T> {
	private Node<T> tail;
	private int size;
	
	public CLL() {
		tail = null;
		size = 0;
	}
	
	public void addToFront (T data) {
		Node <T> node = new Node <T> (data, null);
		if (tail == null) {
			node.next = node;
			tail = node;
		} else {
			node.next = tail.next;
			tail.next = node;
		}
		size++;
	}
	
	public void traverse () {
		Node<T> ptr = tail.next;
		do {
			System.out.print(ptr.data + " -> ");
			ptr = ptr.next;
		} while (ptr != tail.next);
	}
	
	public T deleteFront() {
		if (tail == null) {
			throw new NoSuchElementException("Cll is empty");
		} else if (tail == tail.next) {
			T data = tail.data;
			tail = null;
			size--;
			return data;
		} else {
			T data = tail.next.data;
			tail.next = tail.next.next;
			size--;
			return data;
		}
	}
}

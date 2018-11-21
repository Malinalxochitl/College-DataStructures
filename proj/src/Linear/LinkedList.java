package Linear;

import java.util.NoSuchElementException;

public class LinkedList <T> {
	Node<T> front;
	int size;
	
	public LinkedList () {
		front = null;
		size = 0;
	}
	
	public void addToFront(T data) {
		front = new Node<T>(data, front);
		size++;
	}
	
	public void traverse() {
		Node<T> ptr = front;
		while (ptr != null) {
			System.out.print(ptr.data + " -> ");
			ptr = ptr.next;
		}
		System.out.println("\\");
	}
	
	public T deleteFront() {
		if (front ==  null) {
			throw new NoSuchElementException("List is Empty");	
		}
		T data = front.data;
		front = front.next;
		size--;
		return data;
	}
}

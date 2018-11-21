package Linear;

public class LinkedListApp {

	public static void main(String[] args) {
		
		LinkedList<String> lls = new LinkedList<String>();
		lls.addToFront("Sophia's World");
		lls.addToFront("Data Structures");
		lls.addToFront("Winnie the Pooh");
		lls.addToFront("The Republic");
		
		LinkedList<Integer> lli = new LinkedList<Integer>();
		lli.addToFront(8);
		lli.addToFront(5);
		lli.addToFront(3);
		lli.addToFront(2);
		lli.addToFront(1);
		lli.addToFront(0);
		
		lls.traverse();
		lli.traverse();
		lls.deleteFront();
		lls.deleteFront();
		lls.deleteFront();
		lls.traverse();
		lls.deleteFront();
		lls.deleteFront();
	}

}

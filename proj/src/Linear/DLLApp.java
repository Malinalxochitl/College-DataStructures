package Linear;

public class DLLApp {
	public static void main(String[] args) {
		DLL<Integer> dll = new DLL<Integer>();
		dll.addToFront(6);
		dll.addToFront(4);
		dll.addToFront(3);
		dll.addAfter(5, 4);
		dll.traverse();
	}
}

package Linear;

public class TestingGC {

	public static void main(String[] args) {
		
		int n = 2100000000;
		while (n-- > 0) {
			CLL<Integer> cll = new CLL<Integer>();
			cll.addToFront(n);
			cll.deleteFront();
		}
	}

}

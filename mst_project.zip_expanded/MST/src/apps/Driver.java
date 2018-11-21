package apps;

import java.io.IOException;
import java.util.Iterator;

import structures.*;
import apps.*;

public class Driver {

	public static void main(String[] args) throws IOException {
		
		Graph g = new Graph("graph1.txt");
		PartialTreeList ptl = MST.initialize(g);
		Iterator<PartialTree> iter = ptl.iterator();
		
		while(iter.hasNext()) {
			PartialTree pt = iter.next();
			System.out.println(pt);
		}
		
		System.out.println(MST.execute(ptl));
	}

}

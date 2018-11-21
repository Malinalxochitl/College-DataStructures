package apps;

import structures.*;
import java.util.ArrayList;

import apps.PartialTree.Arc;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	
		PartialTreeList result = new PartialTreeList();
		
		for (Vertex v : graph.vertices) {
			PartialTree T = new PartialTree(v);
			MinHeap<Arc> PQ = T.getArcs();
			Vertex.Neighbor n = v.neighbors;
			while (n != null) {
				PQ.insert(new Arc(v, n.vertex, n.weight));
				n = n.next;
			}
			result.append(T);
		}
		return result;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		/* COMPLETE THIS METHOD */
		ArrayList<PartialTree.Arc> result = new ArrayList<PartialTree.Arc>();
		while (ptlist.size() > 1) {
			PartialTree PTX = ptlist.remove();
			MinHeap<Arc> PQX = PTX.getArcs();
			Vertex v1;
			Vertex v2;
			Arc a;
			do {
				a = PQX.deleteMin();
				v1 = a.v1;
				v2 = a.v2;
			} while (v1.getRoot().equals(v2.getRoot()));
			result.add(a);
			PartialTree PTY = ptlist.removeTreeContaining(v2);
			MinHeap<Arc> PQY = PTY.getArcs();
			PQX.merge(PQY);
			PTX.merge(PTY);
			ptlist.append(PTX);
		}
		
		return result;
	}
}

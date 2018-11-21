package structures;

import java.util.ArrayList;

/**
 * Encapsulates an interval tree.
 * 
 * @author runb-cs112
 */
public class IntervalTree {
	
	/**
	 * The root of the interval tree
	 */
	IntervalTreeNode root;
	
	/**
	 * Constructs entire interval tree from set of input intervals. Constructing the tree
	 * means building the interval tree structure and mapping the intervals to the nodes.
	 * 
	 * @param intervals Array list of intervals for which the tree is constructed
	 */
	public IntervalTree(ArrayList<Interval> intervals) {
		
		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals) {
			intervalsRight.add(iv);
		}
		
		// rename input intervals for left sorting
		ArrayList<Interval> intervalsLeft = intervals;
		
		// sort intervals on left and right end points
		sortIntervals(intervalsLeft, 'l');
		sortIntervals(intervalsRight,'r');
		
		// get sorted list of end points without duplicates
		ArrayList<Integer> sortedEndPoints = 
							getSortedEndPoints(intervalsLeft, intervalsRight);
		
		// build the tree nodes
		root = buildTreeNodes(sortedEndPoints);
		
		// map intervals to the tree nodes
		mapIntervalsToTree(intervalsLeft, intervalsRight);
	}
	
	/**
	 * Returns the root of this interval tree.
	 * 
	 * @return Root of interval tree.
	 */
	public IntervalTreeNode getRoot() {
		return root;
	}
	
	/**
	 * Sorts a set of intervals in place, according to left or right endpoints.  
	 * At the end of the method, the parameter array list is a sorted list. 
	 * 
	 * @param intervals Array list of intervals to be sorted.
	 * @param lr If 'l', then sort is on left endpoints; if 'r', sort is on right endpoints
	 */
	public static void sortIntervals(ArrayList<Interval> intervals, char lr) {
		if (!intervals.isEmpty()) {
			
			ArrayList<Integer> points = new ArrayList<Integer>();
			
			if (lr == 'l') {
				for (int i = 0; i < intervals.size(); i++) {
					points.add(intervals.get(i).leftEndPoint);
				}
			} else {
				for (int i = 0; i< intervals.size(); i++) {
					points.add(intervals.get(i).rightEndPoint);
				}
			}
			
			points.sort(null);
			ArrayList<Interval> temp = new ArrayList<Interval>();
			
			if (lr == 'l') {
				for (int i = 0; i < points.size(); i++) {
					for (int j = 0; j < intervals.size(); j++) {
						if (points.get(i) == intervals.get(j).leftEndPoint && !temp.contains(intervals.get(j))) {
							temp.add(intervals.get(j));
							break;
						}
					}
				}
			} else {
				for (int i = 0; i < points.size(); i++) {
					for (int j = 0; j < intervals.size(); j++) {
						if (points.get(i) == intervals.get(j).rightEndPoint && !temp.contains(intervals.get(j))) {
							temp.add(intervals.get(j));
							break;
						}
					}
				}
			}
			
			intervals.clear();
			intervals.addAll(temp);
		}
	}
	
	/**
	 * Given a set of intervals (left sorted and right sorted), extracts the left and right end points,
	 * and returns a sorted list of the combined end points without duplicates.
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 * @return Sorted array list of all endpoints without duplicates
	 */
	public static ArrayList<Integer> getSortedEndPoints(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		if (leftSortedIntervals == null || rightSortedIntervals == null) {
			return null;
		}
		//System.out.println(leftSortedIntervals);
		//System.out.println(rightSortedIntervals);
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		int left = 0;
		int right = 0;
		while (left < leftSortedIntervals.size() && right < rightSortedIntervals.size()) {
			if (leftSortedIntervals.get(left).leftEndPoint < rightSortedIntervals.get(right).rightEndPoint) {
				if (!sorted.contains(leftSortedIntervals.get(left).leftEndPoint)) {
					sorted.add(leftSortedIntervals.get(left).leftEndPoint);
				}
				left++;
			} else if (leftSortedIntervals.get(left).leftEndPoint > rightSortedIntervals.get(right).rightEndPoint) {
				if (!sorted.contains(rightSortedIntervals.get(right).rightEndPoint)) {
					sorted.add(rightSortedIntervals.get(right).rightEndPoint);
				}
				right++;
			} else {
				if (!sorted.contains(leftSortedIntervals.get(left).leftEndPoint)) {
					sorted.add(leftSortedIntervals.get(left).leftEndPoint);
				}
				if (!sorted.contains(rightSortedIntervals.get(right).rightEndPoint)) {
					sorted.add(rightSortedIntervals.get(right).rightEndPoint);
				}
				left++;
				right++;
			}
		}
		while (left < leftSortedIntervals.size()) {
			if (!sorted.contains(leftSortedIntervals.get(left).leftEndPoint)) {
				sorted.add(leftSortedIntervals.get(left).leftEndPoint);
			}
		}
		while (right < rightSortedIntervals.size()) {
			if (!sorted.contains(rightSortedIntervals.get(right).rightEndPoint)) {
				sorted.add(rightSortedIntervals.get(right).rightEndPoint);
			}
			right++;
		}
		return sorted;
	}
	
	/**
	 * Builds the interval tree structure given a sorted array list of end points
	 * without duplicates.
	 * 
	 * @param endPoints Sorted array list of end points
	 * @return Root of the tree structure
	 */
	public static IntervalTreeNode buildTreeNodes(ArrayList<Integer> endPoints) {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
		if (endPoints == null) {
			System.out.println("NO ENDPOINTS AAAAAAAA");
			return null;
		}
		Queue<IntervalTreeNode> Q = new Queue<IntervalTreeNode>();
		IntervalTreeNode T;
		for (int i = 0; i < endPoints.size(); i++) {
			T = new IntervalTreeNode(endPoints.get(i), endPoints.get(i), endPoints.get(i));
			Q.enqueue(T);
		}
		while (true) {
			if (Q.size == 1) {
				T = Q.dequeue();
				break;
			}
			int temps = Q.size;
			while (temps > 1) {
				IntervalTreeNode T1 = Q.dequeue();
				IntervalTreeNode T2 = Q.dequeue();
				IntervalTreeNode node;
				for (node = T1; node.rightChild != null; node = node.rightChild);
				float v1 = node.splitValue;
				for (node = T2; node.leftChild != null; node = node.leftChild);
				float v2 = node.splitValue;
				IntervalTreeNode N = new IntervalTreeNode((v1+v2)/2, v1, v2);
				N.leftChild = T1;
				N.rightChild = T2;
				Q.enqueue(N);
				temps -= 2;
			}
			if (temps == 1) {
				IntervalTreeNode tem = Q.dequeue();
				Q.enqueue(tem);
			}
		}
		return T;
	}
	
	/**
	 * Maps a set of intervals to the nodes of this interval tree. 
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 */
	public void mapIntervalsToTree(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		for (int i = 0; i < leftSortedIntervals.size(); i++) {
			int left = leftSortedIntervals.get(i).leftEndPoint;
			int right = leftSortedIntervals.get(i).rightEndPoint;
			IntervalTreeNode node = root;
			while (node.splitValue < left || node.splitValue > right) {
				if (node.splitValue < left) {
					node = node.rightChild;
				} else if (node.splitValue > right) {
					node = node.leftChild;
				}
			}
			if (node.leftIntervals == null) {
				node.leftIntervals = new ArrayList<Interval>();
			}
			node.leftIntervals.add(leftSortedIntervals.get(i));
		}
		for (int i = 0; i < rightSortedIntervals.size(); i++) {
			int left = rightSortedIntervals.get(i).leftEndPoint;
			int right = rightSortedIntervals.get(i).rightEndPoint;
			IntervalTreeNode node = root;
			while (node.splitValue < left || node.splitValue > right) {
				if (node.splitValue < left) {
					node = node.rightChild;
				} else if (node.splitValue > right) {
					node = node.leftChild;
				}
			}
			if (node.rightIntervals == null) {
				node.rightIntervals = new ArrayList<Interval>();
			}
			node.rightIntervals.add(rightSortedIntervals.get(i));
		}
		
	}
	
	/**
	 * Gets all intervals in this interval tree that intersect with a given interval.
	 * 
	 * @param q The query interval for which intersections are to be found
	 * @return Array list of all intersecting intervals; size is 0 if there are no intersections
	 */
	public ArrayList<Interval> findIntersectingIntervals(Interval q) {
		if (root == null) {
			ArrayList<Interval> result = new ArrayList<Interval>();
			return result;
		}
		return findIntersectingIntervals(root, q);
	}
	private ArrayList<Interval> findIntersectingIntervals(IntervalTreeNode T, Interval I) {
		ArrayList<Interval> result = new ArrayList<Interval>();
		IntervalTreeNode R = T;
		float split = R.splitValue;
		ArrayList<Interval> LList = R.leftIntervals;
		ArrayList<Interval> RList = R.rightIntervals;
		IntervalTreeNode LSub = R.leftChild;
		IntervalTreeNode RSub = R.rightChild;
		
		if (LSub == null && RSub == null) {
			return result;
		} else if (split >= I.leftEndPoint && split <= I.rightEndPoint) {
			if (LList != null) {
				result.addAll(LList);
			}
			result.addAll(findIntersectingIntervals(R.leftChild, I));
			result.addAll(findIntersectingIntervals(R.rightChild, I));
		} else if (split < I.leftEndPoint) {
			if (RList != null) {
				for (int i = RList.size()-1; i >= 0 && ((I.leftEndPoint <= RList.get(i).rightEndPoint && I.rightEndPoint >= RList.get(i).rightEndPoint) || (I.leftEndPoint <= RList.get(i).leftEndPoint && I.rightEndPoint >= RList.get(i).leftEndPoint));i--) { 
					result.add(RList.get(i));
				}
			}
			result.addAll(findIntersectingIntervals(RSub, I));
		} else if (split > I.rightEndPoint) {
			if (RList != null) {
				for (int i = 0;i < LList.size() && ((I.leftEndPoint <= LList.get(i).rightEndPoint && I.rightEndPoint >= LList.get(i).rightEndPoint) || (I.rightEndPoint >= LList.get(i).leftEndPoint) && I.leftEndPoint <= LList.get(i).rightEndPoint);i++) {
					result.add(LList.get(i));
				}
			}
			result.addAll(findIntersectingIntervals(LSub, I));
		} else {
			System.out.println("Something went wrong");
		}
		return result;
	}

}


package tree;

public class BSTNode <T> {
	T key;
	BSTNode<T> left;
	BSTNode<T> right;
	
	public BSTNode (T key, BSTNode<T> left, BSTNode<T> right) {
		this.key = key;
		this.left = left;
		this.right = right;
	}
	
	public String toString() {
		return String.format("[%s, %s, %s]", key, (left != null) ? left.key: "null", (right != null) ? right.key : "null");
	}
}

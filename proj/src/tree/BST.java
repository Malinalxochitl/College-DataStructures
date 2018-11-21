package tree;

import java.util.*;

public class BST <T extends Comparable <T>> {

	BSTNode<T> root;
	int size;
	public BST () {
		root = null;
		size = 0;
	}
	public void insert (T key) {
		BSTNode<T> ptr = root;
		BSTNode<T> prev = null;
		int c = 0;
		while (ptr != null) {
			c = key.compareTo(ptr.key);
			prev = ptr;
			if (c == 0) {
				throw new IllegalArgumentException("Duplicate key");
			} else if (c < 0) {
				ptr = ptr.left;
			} else {
				ptr = ptr.right;
			}
		}
		
		BSTNode<T> node = new BSTNode<T>(key, null, null);
		if (prev == null) {
			// had an empty tree
			root = node;
		} else if (c < 0) {
			prev.left = node;
		} else {
			prev.right = node;
		}
		size++;
	}
	public static <T extends Comparable <T>> void inorderTraversal (BSTNode<T> root) {
		if (root == null) {
			return;
		}
		inorderTraversal(root.left);
		System.out.print(root.key + " ");
		inorderTraversal(root.right);
	}
	public void print() {
		inorderTraversal(root);
	}
	
	public void delete (T key) {
		// 1. find the node to delete (name it x)
		BSTNode<T> p = null;
		BSTNode<T> x = root;
		int c = 0;
		while (x != null) {
			c = key.compareTo(x.key);
			if (c == 0) {
				break;
			}
			p = x;
			x = (c < 0) ? x.left : x.right;
		}
		// 2. check if x is in the BST
		if (x == null) {
			//key not found
			throw new NoSuchElementException("Key not is BST");
		}
		// 3. x has two children
		BSTNode<T> y = null;
		if (x.left != null && x.right != null) {
			y = x.left;
			while (y.right != null) {
				p = y;
				y = y.right;
			}
			// 2. copy y's key into x's key
			x.key = y.key;
			
			//prepare to delete
			x = y;
		}
		// 4. check if x has one or no children
		BSTNode<T> tmp = (x.right != null) ? x.right : x.left;
		if (x == p.left) {
			p.left = tmp;
		} else {
			p.right = tmp;
		}
		size--;
	}
	public static void main(String args[]) {
		BST<Integer> tree = new BST<Integer>();
		tree.insert(53);
		tree.insert(65);
		tree.insert(60);
		tree.insert(42);
		tree.insert(30);
		tree.insert(48);
		tree.insert(49);
		tree.print();
	}
}

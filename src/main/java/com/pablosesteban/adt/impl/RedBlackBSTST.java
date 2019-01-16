/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

import com.pablosesteban.adt.SymbolTable;

/**
 * Left-Leaning Red-Black BST is a simple implementation of 2-3 trees where the nodes can hold more than one key.
 * The tree is ordered and perfectly balanced: the number of links on the path from the root to any null link is
 * the same.
 * TODO: finish deletion operations.
 * 
 * @param <K> the key
 * @param <V> the value
 */
public class RedBlackBSTST<K extends Comparable<K>, V> implements SymbolTable<K, V> {
	private Node<K, V> root;
	
	@Override
	public void put(K key, V value) {
		root = put(root, key, value);
		
		root.color = Node.BLACK;
	}

	private Node<K, V> put(Node<K, V> node, K key, V value) {
		if (node == null) {
			return new Node<K, V>(key, value, 1, Node.RED);
		}
		
		if (key.compareTo(node.key) < 0) {
			node.left = put(node.left, key, value);
		}else if (key.compareTo(node.key) > 0) {
			node.right = put(node.right, key, value);
		}else {
			node.value = value;
		}
		
		/*
		 * local transformations: provide near-perfect balance in the tree by maintaining a 1-1 correspondence 
		 * with 2-3 trees, on the way up the search path
		 */
		if (isRed(node.right) && !isRed(node.left)) {
			// rotates left any right-leaning 3-node (or a right-leaning red link at the bottom of a temporary 4-node)
			node = rotateLeft(node);
		}
		
		if (isRed(node.left) && isRed(node.left.left)) {
			// rotates right the top link in a temporary 4-node with two left-leaning red links
			node = rotateRight(node);
		}
		
		if (isRed(node.right) && isRed(node.left)) {
			// flips colors to pass a red link up the tree
			flipColors(node);
		}
		
		node.size = getNodeSize(node.left) + getNodeSize(node.right) + 1;
		
		return node;
	}
	
	@Override
	public V get(K key) {
		return get(key, root);
	}
	
	/*
	 * A recursive algorithm to search for a key in a BST follows immediately from the recursive structure.
	 * Search recursively in the appropriate subtree, moving left if the search key is smaller, right if it
	 * is larger. Just as the size of the interval in binary search shrinks by about half on each iteration,
	 * the size of the subtree rooted at the current node shrinks when we go down the tree (by about half,
	 * ideally, but at least by one). The procedure stops either when a node containing the search key is
	 * found (search hit) or when the current subtree becomes empty (search miss).
	 */
	private V get(K key, Node<K, V> node) {
		if (node == null) {
			return null;
		}
		
		if (key.compareTo(node.key) < 0) {
			return get(key, node.left);
		}else if (key.compareTo(node.key) > 0) {
			return get(key, node.right);
		}else {
			return node.value;
		}
	}

	@Override
	public void delete(K key) {
		root = delete(root, key);
		
		root.color = Node.BLACK;
	}
	
	private Node<K, V> delete(Node<K, V> node, K key) {
		if (key.compareTo(node.key) < 0) {
			/*
			 * adjust tree only when node.left and node.left.left are both BLACK, i.e. when the node is a 2-node on the way
			 * down the tree. Only left-leaning red links are allowed by invariant (a 4-node is node with two left-leaning
			 * red links consecutive)
			 */
			if (!isRed(node.left) && !isRed(node.left.left)) {
				node = moveRedLeft(node);
			}
			
			node.left = delete(node.left, key);
		}else {
			if (isRed(node.left)) {
				node = rotateRight(node);
			}
			
			// search hit at the bottom of the tree: delete the node
			if (key.compareTo(node.key) == 0 && node.right == null) {
				return null;
			}
			
			/*
			 * adjust tree only when node.right and node.right.left are both BLACK, i.e. when the node is a 2-node on the way
			 * down the tree. Only left-leaning red links are allowed by invariant (a 4-node is node with two left-leaning
			 * red links consecutive)
			 */
			if (!isRed(node.right) && !isRed(node.right.left)) {
				node = moveRedRight(node);
			}
			
			// search hit in the middle of the tree: exchange the node with its successor as in regular BSTs and delete the node
			if (key.compareTo(node.key) == 0) {
				node.key = min(node.right).key;
				
				node.value = get(node.key, node.right);
				
				node.right = deleteMin(node.right);
			}else {
				node.right = delete(node.right, key);
			}
		}
		
		/*
		 * local transformations: provide near-perfect balance in the tree by maintaining a 1-1 correspondence 
		 * with 2-3 trees, on the way up the search path
		 */
		if (isRed(node.right) && !isRed(node.left)) {
			// rotates left any right-leaning 3-node (or a right-leaning red link at the bottom of a temporary 4-node)
			node = rotateLeft(node);
		}
		
		if (isRed(node.left) && isRed(node.left.left)) {
			// rotates right the top link in a temporary 4-node with two left-leaning red links
			node = rotateRight(node);
		}
		
		if (isRed(node.right) && isRed(node.left)) {
			// flips colors to pass a red link up the tree
			flipColors(node);
		}
		
		node.size = getNodeSize(node.left) + getNodeSize(node.right) + 1;
		
		return node;
	}

	@Override
	public int size() {
		return getNodeSize(root);
	}

	@Override
	public Iterable<K> keys() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public K min() {
		if (root == null) {
			return null;
		}
		
		return min(root).key;
	}
	
	private Node<K, V> min(Node<K, V> node) {
		if (node.left == null) {
			return node;
		}
		
		return min(node.left);
	}

	@Override
	public K max() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public K floor(K key) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public K ceiling(K key) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int rank(K key) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public K select(int rank) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteMin() {
		root = deleteMin(root);
		
		root.color = Node.BLACK;
	}

	/*
	 * The basic idea is based on the observation that we can easily delete a key from a 3-node at the bottom of the tree,
	 * but not from a 2-node. Deleting the key from a 2-node would violate the perfect balance condition. So, to ensure
	 * that we do not end up on a 2-node, we perform appropriate transformations on the way down the tree to preserve the
	 * invariant that the current node is not a 2-node (it might be a 3-node or a temporary 4-node).
	 */
	private Node<K, V> deleteMin(Node<K, V> node) {
		if (node.left == null) {
			return null;
		}
		
		/*
		 * adjust tree only when node.left and node.left.left are both BLACK, i.e. when the node is a 2-node on the way
		 * down the tree. Only left-leaning red links are allowed by invariant (a 4-node is node with two left-leaning
		 * red links consecutive)
		 */
		if (!isRed(node.left) && !isRed(node.left.left)) {
			node = moveRedLeft(node);
		}
		
		node.left = deleteMin(node.left);
		
		/*
		 * local transformations: provide near-perfect balance in the tree by maintaining a 1-1 correspondence 
		 * with 2-3 trees, on the way up the search path
		 */
		if (isRed(node.right) && !isRed(node.left)) {
			// rotates left any right-leaning 3-node (or a right-leaning red link at the bottom of a temporary 4-node)
			node = rotateLeft(node);
		}
		
		if (isRed(node.left) && isRed(node.left.left)) {
			// rotates right the top link in a temporary 4-node with two left-leaning red links
			node = rotateRight(node);
		}
		
		if (isRed(node.right) && isRed(node.left)) {
			// flips colors to pass a red link up the tree
			flipColors(node);
		}
		
		node.size = getNodeSize(node.left) + getNodeSize(node.right) + 1;
		
		return node;
	}
	
	private Node<K, V> moveRedLeft(Node<K, V> node) {
		node.color = Node.BLACK;
		node.left.color = Node.RED;
		
		if (isRed(node.right.left)) {
			// if its immediate sibling is not a 2-node, move a key from the sibling to the left child
			node.right = rotateRight(node.right);
			
			node = rotateLeft(node);
		}else {
			/*
			 * if its immediate sibling is a 2-node, then combine them with the smallest key in the parent
			 * to make a 4-node, changing the parent from a 3-node to a 2-node or from a 4-node to a 3-node.
			 */
			node.right.color = Node.RED;
		}
		
		return node;
	}
	
	@Override
	public void deleteMax() {
		root = deleteMax(root);
		
		root.color = Node.BLACK;
	}
	
	/*
	 * The basic idea is based on the observation that we can easily delete a key from a 3-node at the bottom of the tree,
	 * but not from a 2-node. Deleting the key from a 2-node would violate the perfect balance condition. So, to ensure
	 * that we do not end up on a 2-node, we perform appropriate transformations on the way down the tree to preserve the
	 * invariant that the current node is not a 2-node (it might be a 3-node or a temporary 4-node).
	 */
	private Node<K, V> deleteMax(Node<K, V> node) {
		if (node.right == null) {
			if (isRed(node.left)) {
				node.left.color = Node.BLACK;
			}
			
			return node.left;
		}
		
		// if its immediate sibling is not a 2-node, move a key from the sibling to the right child
		if (isRed(node.left)) {
			node = rotateRight(node);
		}
		
		/*
		 * adjust tree only when node.right and node.right.left are both BLACK, i.e. when the node is a 2-node on the way
		 * down the tree. Only left-leaning red links are allowed by invariant (a 4-node is node with two left-leaning
		 * red links consecutive)
		 */
		if (!isRed(node.right) && !isRed(node.right.left)) {
			node = moveRedRight(node);
		}
		
		node.right = deleteMax(node.right);
		
		/*
		 * local transformations: provide near-perfect balance in the tree by maintaining a 1-1 correspondence 
		 * with 2-3 trees, on the way up the search path
		 */
		if (isRed(node.right) && !isRed(node.left)) {
			// rotates left any right-leaning 3-node (or a right-leaning red link at the bottom of a temporary 4-node)
			node = rotateLeft(node);
		}
		
		if (isRed(node.left) && isRed(node.left.left)) {
			// rotates right the top link in a temporary 4-node with two left-leaning red links
			node = rotateRight(node);
		}
		
		if (isRed(node.right) && isRed(node.left)) {
			// flips colors to pass a red link up the tree
			flipColors(node);
		}
		
		node.size = getNodeSize(node.left) + getNodeSize(node.right) + 1;
		
		return node;
	}
	
	private Node<K, V> moveRedRight(Node<K, V> node) {
		node.color = Node.BLACK;
		node.right.color = Node.RED;
		
		if (isRed(node.left.left)) {
			// if its immediate sibling is not a 2-node, move a key from the sibling to the right child
			node = rotateRight(node);
			
			node.color = Node.RED;
			
			node.left.color = Node.BLACK;
		}else {
			/*
			 * if its immediate sibling is a 2-node, then combine them with the smallest key in the parent
			 * to make a 4-node, changing the parent from a 3-node to a 2-node or from a 4-node to a 3-node.
			 */
			node.left.color = Node.RED;
		}
		
		return node;
	}

	@Override
	public int size(K lo, K hi) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<K> keys(K lo, K hi) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getClass().getSimpleName());
		sb.append(" {");
		
		toString(sb, root);
		
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.deleteCharAt(sb.lastIndexOf(" "));
		sb.append("}");
		
		return sb.toString();
	}

	/*
	 * To print all the keys in a BST in order, print all the keys in the left subtree, which are less than
	 * the key at the root by definition of BSTs, then print the key at the root, then print all the keys
	 * in the right subtree, which are greater than the key at the root by definition of BSTs.
	 */
	private void toString(StringBuilder sb, Node<K, V> node) {
		if (node == null) {
			return;
		}
		
		toString(sb, node.left);
		
		sb.append(node.toString());
		sb.append(", ");
		
		toString(sb, node.right);
	}
	
	private Node<K, V> rotateLeft(Node<K, V> node) {
		Node<K, V> nRight = node.right;
		
		// links of nodes
		node.right = nRight.left;
		nRight.left = node;
		
		// color of nodes
		nRight.color = node.color;
		node.color = Node.RED;
		
		// size of nodes
		nRight.size = node.size;
		node.size = getNodeSize(node.left) + getNodeSize(node.right) + 1;
		
		return nRight;
	}
	
	private Node<K, V> rotateRight(Node<K, V> node) {
		Node<K, V> nLeft = node.left;
		
		// links of nodes
		node.left = nLeft.right;
		nLeft.right = node;
		
		// color of nodes
		nLeft.color = node.color;
		node.color = Node.RED;
		
		// size of nodes
		nLeft.size = node.size;
		node.size = getNodeSize(node.left) + getNodeSize(node.right) + 1;
		
		return nLeft;
	}
	
	private void flipColors(Node<K, V> node) {
		node.right.color = Node.BLACK;
		node.left.color = Node.BLACK;
		
		node.color = Node.RED;
	}
	
	private int getNodeSize(Node<K, V> node) {
		if (node == null) {
			return 0;
		}
		
		return node.size;
	}
	
	private boolean isRed(Node<K, V> node) {
		if (node == null) {
			return false;
		}
		
		return node.color;
	}
	
	/*
	 * A private nested class to define nodes in Left-Leaning Red-Black BSTs.
	 * Each node contains a key, a value, a left link, a right link, a color and a node count.
	 * The left link points to a BST for nodes with smaller keys, and the right link points
	 * to a BST for nodes with larger keys.
	 * The size gives the node count in the subtree rooted at the node. The invariant size
	 * of node x = size of node x.left + size of node x.righ + 1, holds for every node x in
	 * the tree.
	 * Since each node is pointed to by precisely one link from its parent, we encode the
	 * color of links in nodes, by adding a boolean instance variable color which is true
	 * if the link from the parent is red and false if link is black. So when we refer to
	 * the color of a node, we are referring to the color of the link pointing to it.
	 */
	private class Node<K, V> {
		private static final boolean RED = true;
		private static final boolean BLACK = false;
		
		private K key;
		private V value;
		private boolean color;
		
		private Node<K, V> left;
		private Node<K, V> right;
		
		private int size;
		
		public Node(K key, V value, int size, boolean color) {
			this.key = key;
			this.value = value;
			this.size = size;
			this.color = color;
		}
		
		@Override
		public String toString() {
			return key + ": " + value + "(" + size + "," + (color ? "RED" : "BLACK") + ")";
		}
	}
	
	public static void main(String[] args) {
		SymbolTable<String, Integer> st = new RedBlackBSTST<>();
		
		String[] input = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
		
		for (int i = 0; i < input.length; i++) {
			st.put(input[i], i);
		}
		
		System.out.println(st);
		System.out.println("size: " + st.size());
//		System.out.println("keys between D and O: " + st.keys("D", "O"));
//		System.out.println("size between D and O: " + st.size("D", "O"));
//		System.out.println("get Z: " + st.get("Z"));
//		System.out.println("get B: " + st.get("B"));
//		System.out.println("get A: " + st.get("A"));
//		System.out.println("get C: " + st.get("C"));
//		System.out.println("get P: " + st.get("P"));
//		System.out.println("keys: " + st.keys());
//		System.out.println("min key: " + st.min());
//		System.out.println("max key: " + st.max());
//		System.out.println("floor A: " + st.floor("A"));
//		System.out.println("floor G: " + st.floor("G"));
//		System.out.println("ceiling G: " + st.ceiling("G"));
//		System.out.println("ceiling X: " + st.ceiling("X"));
//		System.out.println("rank of E: " + st.rank("E"));
//		System.out.println("key of rank 2: " + st.select(2));
//		System.out.println("rank of S: " + st.rank("S"));
//		System.out.println("key of rank 8: " + st.select(8));
//		System.out.println("rank of H: " + st.rank("H"));
//		System.out.println("key of rank 3: " + st.select(3));
//		System.out.println("rank of X: " + st.rank("X"));
//		System.out.println("key of rank 9: " + st.select(9));
//		System.out.println("delete min key");
//		st.deleteMin();
//		System.out.println(st);
//		System.out.println("size: " + st.size());
		
		System.out.println("delete max key");
		st.deleteMax();
		System.out.println(st);
		System.out.println("size: " + st.size());
		
//		System.out.println("delete L");
//		st.delete("L");
//		System.out.println(st);
//		System.out.println("size: " + st.size());
		
		System.out.println("insert J");
		st.put("J", 13);
		System.out.println(st);
		System.out.println("size: " + st.size());
	}
}

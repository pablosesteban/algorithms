/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

import com.pablosesteban.adt.SymbolTable;

/**
 * A symbol table implementation based on an Binary Search Tree (BST) data structure.
 * It combines the flexibility of insertion in a linked list with the efficiency of search
 * in an ordered array.
 * A BST is a binary tree where each node has a key and an associated value, and satisfies
 * the restriction that the key in any node is larger than the keys in all nodes in that
 * node’s left subtree and smaller than the keys in all nodes in that node’s right subtree. It
 * have the restriction that every node is pointed to by just one other node, which is called
 * its parent (except for one node, the root, which has no nodes pointing to it), and that each
 * node has exactly two links, which are called its left and right links, that point to nodes.
 * Although links point to nodes, we can view each link as pointing to a binary tree, the tree
 * whose root is the referenced node.
 * A BST represents a set of keys and associated values and there are many different BSTs that
 * represent the same set.
 * The running time of algorithms on BSTs depend on the shapes of the trees, which, in turn,
 * depend on the order in which keys are inserted. In the best case, a tree with N nodes could be
 * perfectly balanced, with ~ lgN nodes between the root and each null link (logarithmic time). In
 * the worst case there could be N nodes on the search path (linear time).
 * The balance in typical trees turns out to be much closer to the best case than the worst case.
 * BSTs works in fact like Quicksort, i.e. the node at the root of the tree corresponds to the first
 * partitioning item (no keys to the left are larger, and no keys to the right are smaller) and the
 * subtrees are built recursively, corresponding to quicksort’s recursive sub-array sorts.
 *
 * @param <K> the key
 * @param <V> the value
 */
public class BinarySearchTreeST<K extends Comparable<K>, V> implements SymbolTable<K, V> {
	private Node<K, V> root;

	@Override
	public void put(K key, V value) {
		root = put(key, value, root);
	}
	
	/*
	 * A recursive algorithm to search for a key in a BST follows immediately from the recursive structure.
	 * Using logic similar to the recursive search: if the tree is empty, we return a new node containing
	 * the key and value; if the search key is less than the key at current node, we set the left link to the
	 * result of inserting the key into the left subtree; if the search key is greater than the key at current
	 * node, we set the right link to the result of inserting the key into the right subtree; otherwise, the
	 * key is equals to the key at current node, so the value is overridden.
	 * The code maintains the invariant that no parts of the tree other than the subtree rooted at the current
	 * node can have a node whose key is equal to the search key.
	 */
	private Node<K, V> put(K key, V value, Node<K, V> node) {
		if (node == null) {
			return new Node<>(key, value, 1);
		}
		
		if (key.compareTo(node.key) < 0) {
			node.left = put(key, value, node.left);
		}else if (key.compareTo(node.key) > 0) {
			node.right = put(key, value, node.right);
		}else {
			node.value = value;
		}
		
		node.size = getNodeSize(node.left) + getNodeSize(node.right) + 1;
		
		return node;
	}
	
	private int getNodeSize(Node<K, V> node) {
		if (node == null) {
			return 0;
		}
		
		return node.size;
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
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
	public K select(int k) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteMin() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteMax() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
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
	
	private void toString(StringBuilder sb, Node<K, V> node) {
		if (node == null) {
			return;
		}
		
		sb.append(node.toString());
		sb.append(", ");
		
		toString(sb, node.left);
		
		toString(sb, node.right);
	}

	/*
	 * A private nested class to define nodes in BSTs.
	 * Each node contains a key, a value, a left link, a right link, and a node count.
	 * The left link points to a BST for nodes with smaller keys, and the right link points
	 * to a BST for nodes with larger keys.
	 * The size gives the node count in the subtree rooted at the node. The invariant size
	 * of node x = size of node x.left + size of node x.righ + 1, holds for every node x in
	 * the tree.
	 */
	private class Node<K, V> {
		private K key;
		private V value;
		
		private Node<K, V> left;
		private Node<K, V> right;
		
		private int size;
		
		public Node(K key, V value, int size) {
			this.key = key;
			this.value = value;
			this.size = size;
		}

		@Override
		public String toString() {
			return key + ": " + value + "(" + size + ")";
		}
	}
	
	public static void main(String[] args) {
		SymbolTable<String, Integer> st = new BinarySearchTreeST<>();
		
		String[] input = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
		
		for (int i = 0; i < input.length; i++) {
			st.put(input[i], i);
		}
		
		System.out.println(st);
		System.out.println("size: " + st.size());
		System.out.println("get Z: " + st.get("Z"));
		System.out.println("get B: " + st.get("B"));
		System.out.println("get A: " + st.get("A"));
		System.out.println("get C: " + st.get("C"));
		System.out.println("get P: " + st.get("P"));
		System.out.println("keys: " + st.keys());
		System.out.println("key of rank 4: " + st.select(4));
		System.out.println("min key: " + st.min());
		System.out.println("max key: " + st.max());
		System.out.println("floor A: " + st.floor("A"));
		System.out.println("floor G: " + st.floor("G"));
		System.out.println("ceiling G: " + st.ceiling("G"));
		System.out.println("ceiling X: " + st.ceiling("X"));
		System.out.println("size between D and O: " + st.size("D", "O"));
		System.out.println("keys between D and O: " + st.keys("D", "O"));
	}
}

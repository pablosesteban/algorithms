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
 *
 * @param <K> the key
 * @param <V> the value
 */
public class BinarySearchTreeST<K extends Comparable<K>, V> implements SymbolTable<K, V> {
	private Node<K, V> root;
	private int size;

	@Override
	public void put(K key, V value) {
		if (isEmpty()) {
			root = new Node<>(key, value, 0);
		}else {
			put(key, value, root);
		}
		
		size++;
	}
	
	private void put(K key, V value, Node<K, V> node) {
		if (node == null  || node.key.compareTo(key) == 0) {
			return;
		}
		
		if (key.compareTo(node.key) < 0) {
			put(key, value, node.left);
			
			if (node.left != null) {
				node.left.value = value;
			}else {
				node.left = new Node<>(key, value, 0);
			}
		}else {
			put(key, value, node.right);
			
			if (node.right != null) {
				node.right.value = value;
			}else {
				node.right = new Node<>(key, value, 0);
			}
		}
	}

	@Override
	public V get(K key) {
		Node<K, V> node = get(key,root);
		
		return node != null ? node.value : null;
	}
	
	private Node<K, V> get(K key, Node<K, V> node) {
		if (node == null || node.key.compareTo(key) == 0) {
			return node;
		}
		
		if (key.compareTo(node.key) < 0) {
			return get(key, node.left);
		}else {
			return get(key, node.right);
		}
	}

	@Override
	public void delete(K key) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return size;
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
	 * The size gives the node count in the subtree rooted at the node. The invariant (size
	 * of node x = size of node x.left + size of node x.righ + 1) holds for every node x in the tree.
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
			return key + ": " + value;
		}
	}
	
	public static void main(String[] args) {
		SymbolTable<String, Integer> st = new BinarySearchTreeST<>();
		
		String[] input = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
		
		for (int i = 0; i < input.length; i++) {
			st.put(input[i], i);
		}
		
		System.out.println(st);
	}
}

/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

/**
 * 
 * 
 * @param <K> the key
 * @param <V> the value
 */
public class RedBlackBSTST<K extends Comparable<K>, V> {
	/*
	 * A private nested class to define nodes in BSTs.
	 * Each node contains a key, a value, a left link, a right link, and a node count.
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
			return key + ": " + value + "(" + size + "-" + (color ? "RED" : "BLACK") + ")";
		}
	}
}

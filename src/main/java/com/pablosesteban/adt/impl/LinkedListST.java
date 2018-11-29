/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

import java.util.ArrayList;
import java.util.List;

import com.pablosesteban.adt.SymbolTable;

/**
 * A symbol table implementation based on an unordered linked list of nodes that contain keys and values.
 * To implement get(), we scan through the list to compare the search key with the key in each
 * node in the list. If we find the match, we return the associated value; if not, we return null.
 * To implement put(), we also scan through the list to compare the client key with the key in
 * each node in the list. If we find the match, we update the value associated with that key to be
 * the value given in the second argument; if not, we create a new node with the given key and
 * value and insert it at the beginning of the list. This method is known as sequential search.
 * Inserting N distinct keys into an initially empty linked-list symbol table uses ~ N^2/2 compares,
 * so the implementation with sequential search is too slow for it to be used to solve huge problems.
 *
 * @param <K> the key
 * @param <V> the value
 */
public class LinkedListST<K extends Comparable<K>, V> implements SymbolTable<K, V> {
	private Node<K, V> first;
	private int size;
	
	@Override
	public void put(K key, V value) {
		/* Because of our policy of disallowing duplicate keys, we need to do such a search before each insertion */
		for (Node<K, V> n = first; n != null; n = n.next) {
			if (n.key.compareTo(key) == 0) {
				n.value = value;

				return;
			}
		}

		first = new Node<>(key, value, first);

		size++;
	}

	@Override
	public V get(K key) {
		Node<K, V> n = first;
		
		while (n != null) {
			if (n.key.compareTo(key) == 0) {
				return n.value;
			}
			
			n = n.next;
		}
		
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterable<K> keys() {
		List<K> keys = new ArrayList<>();
		
		for (Node<K, V> n = first; n != null; n = n.next) {
			keys.add(n.key);
		}
		
		return keys;
	}
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(getClass().getSimpleName());
        sb.append(" {");
        
        Node<K, V> n = first;
        while (n != null && n.next != null) {
        	sb.append(n);
        	sb.append(", ");
        	
        	n = n.next;
        }
        
        if (n != null) {
        	sb.append(n);
        }
        
        sb.append("}");
        
        return sb.toString();
    }

	@Override
	public K min() {
		throw new UnsupportedOperationException();
	}

	@Override
	public K max() {
		throw new UnsupportedOperationException();
	}

	@Override
	public K floor(K key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public K ceiling(K key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int rank(K key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public K select(int k) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size(K lo, K hi) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<K> keys(K lo, K hi) {
		throw new UnsupportedOperationException();
	}
	
	private class Node<K, V> {
		private K key;
		private V value;
		private Node<K, V> next;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public Node(K key, V value, Node<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		@Override
	    public String toString() {
	        return key + ": " + value;
	    }
	}
	
	public static void main(String[] args) {
		SymbolTable<String, Integer> st = new LinkedListST<>();
		
		String[] input = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
		
		for (int i = 0; i < input.length; i++) {
			st.put(input[i], i);
		}
		
		System.out.println(st);
		System.out.println("size: " + st.size());
		System.out.println("get Z: " + st.get("Z"));
		System.out.println("get A: " + st.get("A"));
		
		System.out.println(st.keys());
	}
}

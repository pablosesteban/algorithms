/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

import com.pablosesteban.adt.Queue;
import com.pablosesteban.adt.SymbolTable;

/**
 * An unordered symbol table implementation based on hashing and separate chaining for collision resolution.
 * A straightforward and general approach to collision resolution is to build, for each of the M array indices,
 * a linked list of the key-value pairs whose keys hash to that index. Items that collide are chained together
 * in separate linked lists. The basic idea is to choose M to be sufficiently large that the lists are sufficiently
 * short to enable efficient search through a two-step process: hash to find the list that could contain the key,
 * then sequentially search through that list for the key.
 * If the hash function is not uniform and independent, the search and insert cost could be proportional to N.
 * The whole point of hashing is to uniformly disperse the keys, so any order in the keys is lost when hashing.
 * The performance of hashing with separate chaining depends on the ratio N/M. The load factor of a hash table
 * for separate chaining, is the average number of keys per list and is generally larger than 1. Choose the table
 * size M to be sufficiently small that we do not waste a huge area of contiguous memory with empty chains but
 * sufficiently large that we do not waste time searching through long chains. When space is not a critical resource,
 * M can be chosen sufficiently large that search time is constant. When space is a critical resource, we still
 * can get a factor of M improvement in performance by choosing M to be as large as we can afford. Another option is
 * to use array resizing to keep the lists short (same method as linear probing).
 * Hashing with separate chaining is easy to implement and probably the fastest (and most widely used) symbol-table
 * implementation for applications where key order is not important.
 * 
 * @param <K> the kind of keys
 * @param <V> the kind of values
 */
public class SeparateChainingHashST<K extends Comparable<K>, V> implements SymbolTable<K, V> {
	private static final int TABLE_SIZE = 17;
	
	private SequentialSearch[] table;
	
	public SeparateChainingHashST() {
		this.table = new SeparateChainingHashST.SequentialSearch[TABLE_SIZE];
	}

	/*
	 * Modular Hashing: choose the array size M to be prime and, for any positive integer key k,
	 * compute the remainder when dividing k by M. This function is effective in dispersing the
	 * keys evenly between 0 and M-1.
	 * Since hashCode method returns a 32-bit signed integer (can be negative) the code masks off
	 * the sign bit to turn the 32-bit integer into a 31-bit nonnegative integer using bitwise
	 * AND operator (&) with the maximum value an integer can hold, then combine it with modular
	 * hashing to produce integers between 0 and M – 1.
	 * This code expects that hashCode method from keys disperses them uniformly among the possible
	 * 32-bit result values. That is, for any object x, you can write x.hashCode() and, in principle,
	 * expect to get any one of the 2^32 possible 32-bit values with equal likelihood.
	 * A bad hash function is a classic example of a performance bug, everything will work properly,
	 * but much more slowly than expected. The easiest way to ensure uniformity is to make sure
	 * that all the bits of the key play an equal role in computing every hash value. The most 
	 * common mistake in implementing hash functions is to ignore significant numbers of the key bits.
	 */
	private int hash(K key) {
		return (key.hashCode() & Integer.MAX_VALUE) % TABLE_SIZE;
	}
	
	@Override
	public void put(K key, V value) {
		int index = hash(key);
		
		if (table[index] == null) {
			table[index] = new SequentialSearch();
		}
		
		table[index].put(key, value);
	}

	@Override
	public V get(K key) {
		int index = hash(key);
		
		return table[index] != null ? table[index].get(key) : null;
	}

	@Override
	public void delete(K key) {
		int index = hash(key);
		
		if (table[index] != null) {
			table[index].delete(key);
		}
	}

	@Override
	public int size() {
		int size = 0;
		
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				size += table[i].size;
			}
		}
		
		return size;
	}

	@Override
	public Iterable<K> keys() {
		Queue<K> queue = new LinkedQueue<>();
		
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				Node node = table[i].first;
				
				while (node != null) {
					queue.enqueue(node.key);
					
					node = node.next;
				}
			}
		}
		
		return queue;
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
	public K select(int rank) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteMin() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteMax() {
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" {\n");
		
		int i = 0;
		for (i = 0; i < table.length - 1; i++) {
			sb.append("\t");
			sb.append(i);
			sb.append(": [");
			
			if (table[i] != null) {
				sb.append(table[i]);
			}
			
			sb.append("],\n");
		}
		
		sb.append("\t");
		sb.append(i);
		sb.append(": [");
		
		if (table[i] != null) {
			sb.append(table[i]);
		}
		
		sb.append("]\n");
		
		sb.append("}");
		
		return sb.toString();
	}

	private class SequentialSearch {
		private Node first;
		private int size;
		
		public V get(K key) {
			Node node = get(first, key);
			
			return node != null ? node.value : null;
		}
		
		private Node get(Node node, K key) {
			if (node == null || node.key.equals(key)) {
				return node;
			}
			
			return get(node.next, key);
		}
		
		public void put(K key, V value) {
			first = put(first, key, value);
		}
		
		private Node put(Node node, K key, V value) {
			if (node == null) {
				size++;
				
				return new Node(key, value);
			}
			
			if (node.key.equals(key)) {
				node.value = value;
			}else {
				node.next = put(node.next, key, value);
			}
			
			return node;
		}
		
		public void delete(K key) {
			first = delete(first, key);
		}

		private Node delete(Node node, K key) {
			if (node == null) {
				return null;
			}else if (node.key.equals(key)) {
				size--;
				
				return null;
			}
			
			node.next = delete(node.next, key);
			
			return node;
		}
		
		@Override
		public String toString() {
			if (first == null) {
				return "";
			}
			
			StringBuilder sb = new StringBuilder();
			
			Node node = first;
			
			while (node.next != null) {
				sb.append(node);
				sb.append(", ");
				
				node = node.next;
			}
			
			sb.append(node);
			
			return sb.toString();
		}
	}
	
	private class Node {
		private K key;
		private V value;
		private Node next;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return key + ": " + value;
		}
	}
	
	public static void main(String[] args) {
		SymbolTable<String, Integer> st = new SeparateChainingHashST<>();
		
		String[] input = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
		
		for (int i = 0; i < input.length; i++) {
			st.put(input[i], i);
		}
		
		System.out.println(st);
		System.out.println("size: " + st.size());
		System.out.println("keys: " + st.keys());
		
		System.out.println("get Z: " + st.get("Z"));
		System.out.println("get B: " + st.get("B"));
		System.out.println("get A: " + st.get("A"));
		System.out.println("get C: " + st.get("C"));
		System.out.println("get P: " + st.get("P"));
		
		System.out.println("delete P");
		st.delete("P");
		System.out.println(st);
		System.out.println("size: " + st.size());
		System.out.println("keys: " + st.keys());
		
		System.out.println("delete R");
		st.delete("R");
		System.out.println(st);
		System.out.println("size: " + st.size());
		System.out.println("keys: " + st.keys());
		
		System.out.println("delete Z");
		st.delete("Z");
		System.out.println(st);
		System.out.println("size: " + st.size());
		System.out.println("keys: " + st.keys());
	}
}

/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

import com.pablosesteban.adt.SymbolTable;

public class SeparateChainingHashST<K extends Comparable<K>, V> implements SymbolTable<K, V> {
	private static final int TABLE_SIZE = 11;
	
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
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
		
		if (table[i] != null) {
			sb.append("\t");
			sb.append(i);
			sb.append(": [");
			sb.append(table[i]);
			sb.append("]\n");
		}
		
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
		
		System.out.println("get Z: " + st.get("Z"));
		System.out.println("get B: " + st.get("B"));
		System.out.println("get A: " + st.get("A"));
		System.out.println("get C: " + st.get("C"));
		System.out.println("get P: " + st.get("P"));
		
		System.out.println("delete P");
		st.delete("P");
		System.out.println(st);
		System.out.println("size: " + st.size());
		
		System.out.println("delete R");
		st.delete("R");
		System.out.println(st);
		System.out.println("size: " + st.size());
		
		System.out.println("delete Z");
		st.delete("Z");
		System.out.println(st);
		System.out.println("size: " + st.size());
	}
}

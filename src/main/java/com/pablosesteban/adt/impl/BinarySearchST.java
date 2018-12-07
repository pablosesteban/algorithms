/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

import java.util.ArrayList;
import java.util.List;

import com.pablosesteban.adt.SymbolTable;

/**
 * A symbol table implementation based on an ordered pair of parallel arrays, one for the keys
 * and one for the values.
 * The reason that we keep keys in an ordered array is so that we can use array indexing to
 * dramatically reduce the number of compares required for each search, using the classic
 * Binary Search Algorithm.
 * The heart of the implementation is the rank() method, which implements the binary search
 * algorithm.
 * Uses array resizing when both arrays are full.
 * Despite its guaranteed logarithmic search, the implementation still does not enable us to
 * solve huge problems, because the put method is too slow. Binary search reduces the number
 * of compares, but not the running time, because its use does not change the fact that the
 * number of array accesses required to build a symbol table in an ordered array is quadratic
 * in the size of the array when keys are randomly ordered.
 * 
 * @param <K> the key
 * @param <V> the value
 */
public class BinarySearchST<K extends Comparable<K>, V> implements SymbolTable<K, V> {
	private static final int INITIAL_CAPACITY = 5;
	
	private K[] keys;
	private V[] values;
	private int size;
	
	public BinarySearchST() {
		this.keys = (K[]) new Comparable[INITIAL_CAPACITY];
		this.values = (V[]) new Object[INITIAL_CAPACITY];
	}

	/*
	 * To implement put, the rank tells us precisely where to update the value when the key is in
	 * the table, and precisely where to put the key when the key is not in the table. We move all
	 * larger keys over one position to make room (working from back to front) and insert the given
	 * key and value into the proper positions in their respective arrays.
	 */
	@Override
	public void put(K key, V value) {
		int index = rank(key);
		
		if (keys[index] != null && keys[index].compareTo(key) == 0) {
			values[index] = value;
		}else {
			if (size == keys.length) {
				resize(size*2);
			}
			
			for (int i = size; i >= index; --i) {
				if (keys[i] == null) {
					continue;
				}
				
				keys[i+1] = keys[i];
				keys[i] = null;
				
				values[i+1] = values[i];
				values[i] = null;
			}
			
			keys[index] = key;
			values[index] = value;
			
			size++;
		}
	}
	
	/**
     * Resizes both arrays to the new size
     * 
     * @param size the new size
     */
    private void resize(int size) {
        K[] tmpKeys = (K[]) new Comparable[size];
        V[] tmpValues = (V[]) new Object[size];
        
        for (int i = 0; i < this.size; i++) {
            tmpKeys[i] = keys[i];
            
            tmpValues[i] = values[i];
        }
        
        keys = tmpKeys;
        values = tmpValues;
    }

    /*
     * To implement get, the rank tells us precisely where the key is to be found if it is in the
     * table (if it is not there, that it is not in the table).
     */
	@Override
	public V get(K key) {
		int index = rank(key);
		
		return index < size && keys[index].compareTo(key) == 0 ? values[index] : null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterable<K> keys() {
		List<K> l = new ArrayList<>();
		
		for (int i = 0; i < size; i++) {
			l.add(keys[i]);
		}
		
		return l;
	}

	@Override
	public K min() {
		return keys[0];
	}

	@Override
	public K max() {
		return keys[size - 1];
	}

	@Override
	public K floor(K key) {
		int rank = rank(key);
		
		return keys[rank].compareTo(key) > 0 ? keys[rank - 1] : keys[rank];
	}

	@Override
	public K ceiling(K key) {
		return keys[rank(key)];
	}

	@Override
	public int rank(K key) {
		return rank(key, 0, size - 1);
	}
	
	/**
	 * BINARY SEARCH ALGORITHM.
	 * Maintains indices into the sorted key array that delimit the sub-array that might contain
	 * the search key. To search, we compare the search key against the key in the middle of the
	 * sub-array. If the search key is less than the key in the middle, we search in the left half
	 * of the sub-array; if the search key is greater than the key in the middle, we search in the
	 * right half of the sub-array; otherwise the key in the middle is equal to the search key.
	 * If key is in the table, rank returns its index in the table, which is the same as the number
	 * of keys in the table that are smaller than key.
	 * If key is not in the table, rank also returns the number of keys in the table that are
	 * smaller than key.
	 * Binary search in an ordered array with N keys uses no more than lgN+1 compares for a
	 * search (successful or unsuccessful), i.e. achieves a logarithmic-time guarantee.
	 * 
	 * @param key the key
	 * @param lo low index of the sub-array
	 * @param hi high index of the sub-array
	 * @return the rank of the key
	 */
	private int rank(K key, int lo, int hi) {
		if (hi < lo) {
			return lo;
		}
		
		int middle = lo + (hi - lo)/2;
		
		if (keys[middle] != null && key.compareTo(keys[middle]) < 0) {
			return rank(key, lo, middle-1);
		} else if (keys[middle] != null && key.compareTo(keys[middle]) > 0) {
			return rank(key, middle+1, hi);
		}else {
			return middle;
		}
	}

	@Override
	public K select(int rank) {
		if (rank >= size || rank < 0) {
			throw new IllegalArgumentException(rank + " is not a valid rank");
		}
		
		return keys[rank];
	}

	@Override
	public Iterable<K> keys(K lo, K hi) {
		List<K> l = new ArrayList<>();
		
		for (int i = rank(lo); i < rank(hi); i++) {
			l.add(keys[i]);
		}
		
		return l;
	}

	@Override
	public int size(K lo, K hi) {
		return rank(hi) - rank(lo);
	}

	@Override
	public void delete(K key) {
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
	public String toString() {
		StringBuilder sb = new StringBuilder(getClass().getSimpleName());
		sb.append(" {");
		
		for (int i = 0; i < keys.length-1; i++) {
			sb.append(keys[i]);
			sb.append(": ");
			sb.append(values[i]);
			sb.append(", ");
		}
		
		sb.append(keys[keys.length-1]);
		sb.append(": ");
		sb.append(values[keys.length-1]);
		sb.append("}");
		
		return sb.toString();
	}

	public static void main(String[] args) {
		SymbolTable<String, Integer> st = new BinarySearchST<>();
		
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

/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt.impl;

import com.pablosesteban.adt.Queue;
import com.pablosesteban.adt.SymbolTable;

/**
 * An unordered symbol table implementation based on hashing and open addressing for collision resolution.
 * Open addressing methods search through alternate locations in the table (the probe sequence) until either
 * the target record is found, or an unused array slot is found, which indicates that there is no such key
 * in the table (relies on empty entries).
 * The simplest open addressing method is called linear probing. When there is a collision (when we hash to a
 * table index that is already occupied with a key different from the search key), then we just check the
 * next entry in the table (by incrementing the index) until the key is equal to searched key (search hit) or
 * it is an empty position (search miss), otherwise (key is not equal to the searched key) try next entry,
 * wrapping back to the beginning of the table if we reach the end.
 * Rather than using memory space for references in linked lists, we use it for the empty entries in the hash
 * table, which mark the ends of probe sequences.
 * It is implemented with two parallel arrays, one for the keys and one for the values.
 * The performance of hashing with open addressing depends on the ratio N/M. The load factor of a hash table
 * for linear probing, is the percentage of table entries that are occupied. We cannot let the load factor
 * reach 1 (completely full table) because a search miss would go into an infinite loop. Indeed, for the sake
 * of good performance, we use array resizing to guarantee that the load factor is between 1/8 and 1/2 (validated
 * by mathematical analysis). Furthermore, the average cost of linear probing depends on the way in which the
 * entries clump together into contiguous groups of occupied table entries, called clusters, when they are
 * inserted. Short clusters are certainly a requirement for efficient performance, but this can be problematic as
 * the table fills (the load factor approaches 1), because long clusters are common. Moreover, since all table
 * positions are equally likely to be the hash value of the next key to be inserted (under the uniform hashing
 * assumption), long clusters are more likely to increase in length than short ones, because a new key hashing to
 * any entry in the cluster will cause the cluster to increase in length by 1 (and possibly much more, if there is
 * just one table entry separating the cluster from the next one). To deal with that we use resizing the array.
 *
 * @param <K> the kind of keys
 * @param <V> the kind of values
 */
public class LinearProbingHashST<K extends Comparable<K>, V> implements SymbolTable<K, V> {
	private K[] keys;
	private V[] values;
	private int size;
	private int table_size = 5;
	
	public LinearProbingHashST() {
		keys = (K[]) new Comparable[table_size];
		values = (V[]) new Comparable[table_size];
	}

	private LinearProbingHashST(int table_size) {
		this.table_size = table_size;
		
		keys = (K[]) new Comparable[table_size];
		values = (V[]) new Comparable[table_size];
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
		return (key.hashCode() & Integer.MAX_VALUE) % table_size;
	}
    
	/*
	 * creates a new LinearProbingHashST with the new size table in order to rehash all the keys currently
	 * in the table into the new table.
	 * resizing ensures that the amount of memory used is always within a constant factor of the number of
	 * key-value pairs in the table, so that the load factor of the table is <= 1/2.
	 */
	private void resize(int size) {
		LinearProbingHashST<K, V> resized = new LinearProbingHashST<>(size);
		
		for(int i = 0; i < keys.length; i++) {
			if (keys[i] != null) {
				resized.put(keys[i], values[i]);
			}
		}

		table_size = size;
		
		keys = resized.keys;
		values = resized.values;
	}
	
	@Override
	public void put(K key, V value) {
		/*
		 * ensures that the table is at most one-half full.
		 * builds a hash table twice the size with the same keys, thus halving the value of the load factor
		 * of the table.
		 */
		if (size >= table_size/2) {
			resize(table_size*2);
		}
		
		int index = hash(key);
		
		while (keys[index] != null) {
			if (keys[index].equals(key)) {
				values[index] = value;
				
				return;
			}
			
			index++;
			
			if (index == keys.length) {
				index = 0;
			}
		}
		
		keys[index] = key;
		values[index] = value;
		
		size++;
	}

	@Override
	public V get(K key) {
		int index = hash(key);
		
		while (keys[index] != null) {
			if (keys[index].equals(key)) {
				return values[index];
			}
			
			index++;
			
			if (index == keys.length) {
				index = 0;
			}
		}
		
		return null;
	}

	@Override
	public void delete(K key) {
		int index = hash(key);
		
		while (keys[index] != null) {
			if (keys[index].equals(key)) {
				keys[index] = null;
				values[index] = null;
				
				reorganizingCluster(index);
				
				size--;
				
				break;
			}
			
			index++;
		}

		// ensure that the table is at least one-eighth full
		if (size > 0 && size == table_size/8) {
			resize(table_size/2);
		}
	}
	
	/*
	 * setting the key’s table position to null will not work, because that might prematurely terminate
	 * the search for a key that was inserted into the table later.
	 * When a cell index is emptied, it is necessary to search forward through the following cells of the
	 * table until finding either another empty cell or a key that can be moved to cell index, that is, a
	 * key whose hash value is equal to or earlier than index.
	 */
	private void reorganizingCluster(int index) {
		int i = index;
		
		while (keys[i + 1] != null) {
			if (hash(keys[i + 1]) <= index) {
				keys[i] = keys[i + 1];
				values[i] = values[i + 1];
				
				keys[i + 1] = null;
				values[i + 1] = null;
			}
			
			i++;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterable<K> keys() {
		Queue<K> queue = new LinkedQueue<>();
		
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] != null) {
				queue.enqueue(keys[i]);
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
		for (; i < keys.length; i++) {
			sb.append("\t");
			sb.append(i);
			sb.append(": ");
			
			if (keys[i] != null) {
				sb.append(keys[i]);
				sb.append("->");
				sb.append(values[i]);
			}else {
				sb.append("");
			}
			
			sb.append("\n");
		}
		
		sb.append("}");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		SymbolTable<String, Integer> st = new LinearProbingHashST<>();
		
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
		
		System.out.println("delete H");
		st.delete("H");
		System.out.println(st);
		System.out.println("size: " + st.size());
		System.out.println("keys: " + st.keys());

		System.out.println("get A: " + st.get("A"));
		System.out.println("get X: " + st.get("X"));
		
		System.out.println("delete L");
		st.delete("L");
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

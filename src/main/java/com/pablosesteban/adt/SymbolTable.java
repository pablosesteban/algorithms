/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.adt;

/**
 * A symbol table is a prototypical ADT (it represents a well-defined set of values and
 * operations on those values, enabling us to develop clients and implementations separately)
 * for key-value pairs that supports two operations: insert (put) a new pair into the table
 * and search for (get) the value associated.
 * Adopt the following conventions in all of the implementations:
 * <ul>
 * <li>Only one value is associated with each key (no duplicate keys in a table).</li>
 * <li>When a client puts a key-value pair into a table already containing that key and an
 * associated value (not null), the new value replaces the old one.</li>
 * <li>Keys must not be null, using a null key results in an exception at runtime.</li>
 * <li>No key can be associated with the value null, get() should return null for keys not in
 * the table, effectively associating the value null with every key not in the table.</li>
 * <li>It is an ordered symbol table, i.e. keys are Comparable objects.</li>
 * </ul>
 *  We can think of the symbol table as keeping the keys in order and consider a significantly
 *  numerous natural and useful operations involving relative key order.
 *  Those conventions make it possible to have default implementations for some methods.
 */
public interface SymbolTable<K extends Comparable<K>, V> {
	/**
	 * Puts key-value pair into the table
	 * 
	 * @param key the key
	 * @param value the value
	 */
	void put(K key, V value);
	
	/**
	 * Gets the value paired with key
	 * 
	 * @param key the key
	 * @return the value paired with key
	 */
	V get(K key);
	
	/**
	 * Removes the key and its value from table
	 * 
	 * @param key the key
	 */
	void delete(K key);
	
	/**
	 * Checks if there is a value paired with key
	 * 
	 * @param key the key
	 * @return true if there is a value paired with key, otherwise false
	 */
	default boolean contains(K key) {
		return get(key) != null;
	}
	
	/**
	 * Checks if the symbol table is empty
	 * 
	 * @return false if there are no key-value pairs in the symbol table, otherwise true
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Gets the number of key-value pairs in the symbol table
	 * 
	 * @return the number of key-value pairs in the symbol table
	 */
	int size();
	
	/**
	 * Gets all the keys in the symbol table
	 * 
	 * @return all the keys
	 */
	Iterable<K> keys();
	
	/* Methods related to ordered symbol tables */
	/**
	 * Gets the smallest key
	 *  
	 * @return the smallest key
	 */
	K min();
	
	/**
	 * Gets the largest key
	 * 
	 * @return the largest key
	 */
	K max();
	
	/**
	 * Gets the largest key less than or equal to the given key
	 * 
	 * @param key a key on the symbol table
	 * @return the largest key less than or equal to the given key
	 */
	K floor(K key);
	
	/**
	 * Gets the smallest key greater than or equal to the given key
	 * 
	 * @param key a key on the symbol table
	 * @return the smallest key greater than or equal to the given key
	 */
	K ceiling(K key);
	
	/* Methods for determining where a new key fits in the order */
	/**
	 * Gets the number of keys less than the given key
	 * 
	 * @param key a key
	 * @return the number of keys less than the given key
	 */
	int rank(K key);
	
	/**
	 * Gets the key of the given rank
	 * 
	 * @param k the rank
	 * @return the key of the given rank
	 */
	K select(int k);
	/* Methods for determining where a new key fits in the order */
	
	/**
	 * Delete smallest key
	 */
	void deleteMin();
	
	/**
	 * Delete largest key
	 */
	void deleteMax();
	
	/**
	 * Gets the number of keys in between the given keys
	 * 
	 * @param lo lowest key
	 * @param hi highest key
	 * @return the number of keys in between the given keys
	 */
	int size(K lo, K hi);
			
	/**
	 * Gets all keys in between the given keys, in sorted order
	 * 
	 * @param lo lowest key
	 * @param hi highest key
	 * @return all keys in between the given keys, in sorted order
	 */
	Iterable<K> keys(K lo, K hi);
	/* Methods related to ordered symbol tables */
}

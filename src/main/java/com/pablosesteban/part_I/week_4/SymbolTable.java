/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_4;

/**
 *
 * @author psantama
 */
/**
 * SYMBOL TABLE:
 *      is a data structure whose primary purpose is to associate a value with a key
 * 
 *      it is a Key-Value pair abstraction where you can insert a value with specified key and then given a key, search for the corresponding value
 * 
 * SYMBOL TABLE CONVENTIONS:
 *      values are not null
 *      
 *      get() return null if key not present
 * 
 *      put() overwrites old value with the new value
 * 
 * @param <K> 
 *      use IMMUTABLE types for keys
 *  
 *      keys can be:
 *          comparables i.e. use compareTo() method to compare (sorted symbol tables) and give the client more methods like floor(), ceiling()...
 * 
 *          not comparables, use (and overrides) equals() method to compare
 * @param <V> 
 */
public interface SymbolTable<K extends Comparable<K>, V> {
    /**
     * put key-value pair into the table (remove key from table if value is null)
     * 
     * @param key
     * @param value 
     */
    void put(K key, V value);
    
    /**
     * value paired with key (null if key is absent)
     * 
     * @param key
     * @return 
     */
    V get(K key);
    
    /**
     * remove key (and its value) from table
     * 
     * @param key 
     */
    void delete(K key);
    
    /**
     * is there a value paired with key?
     * 
     * @param key
     * @return 
     */
    boolean contains(K key);
    
    /**
     * is the table empty?
     * 
     * @return 
     */
    boolean isEmpty();
    
    /**
     * number of key-value pairs in the table
     * 
     * @return 
     */
    int size();
    
    /**
     * all the keys in the table
     * 
     * @return 
     */
    Iterable<K> keys();
    
    /**
     * if symbol table is ordered
     * 
     * @return the smallest key
     */
    K min();
    
    /**
     * if symbol table is ordered
     * 
     * @return the largest key
     */
    K max();
    
    /**
     * if symbol table is ordered
     * 
     * @param key
     * @return largest key less than or equal to key
     */
    K floor(K key);
    
    /**
     * if symbol table is ordered
     * 
     * @param k
     * @return smallest key greater than or equal to key
     */
    K ceiling(K key);
}

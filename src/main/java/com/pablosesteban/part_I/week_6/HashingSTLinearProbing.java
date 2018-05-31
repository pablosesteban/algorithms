/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.pablosesteban.part_I.week_6;

/**
 *
 * @author psantama
 */

/*
Linear Probing Hashing:
    is called OPEN ADDRESSING: when a new key collides, find next empty slot, and put it there

    it is also around the 50's

    instead of using space for the length in a linked list, I use that same space, and just, allocate an array

    the size of the array MUST be BIGGER than the number of keys that we expect:
        we use the empty slots in the array to essentially terminate the length of the list that we have to search through when we're doing a insertion/search

    the implementation needs to include array resizing, whenever the hash table gets too full:
        it is a good idea to make sure that the array is at least half empty

    the easiest way to implement DELETE operation:
        find and remove the key-value pair and then to re-insert all of the key-value pairs in the same cluster that appear after the deleted key-value pair

        if the hashtable doesn't get too full, the expected number of key-value pairs to reinsert will be a small constant

        an alternative way is to flag the deleted entry so that it is skipped over during a search but is used for an insertion
*/
public class HashingSTLinearProbing<K, V> {
    /*
    size of the array
    
    if M is too large: too many empty array entries
    
    if M is too small: search time blows up
    
    typical choice: α = N / M ~ 0.5, keep the array half full and you get constant time operations
    */
    private int m = 30001;
    
    // two arrays for storing the key-value pairs: they have to doubling and halving whenever they are necessary
    private V[] values = (V[]) new Object[m];
    private K[] keys = (K[]) new Object[m];
    
    // hash function (returns a positive int between 0 and m-1)
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }
    
    // search table index idx; if occupied but no match, try idx+1, idx+2, etc
    public V get(K  key) {
        for (int idx = hash(key); keys[idx] != null; idx = (idx+1) % m) {
            System.out.println("idx+1%m: " + ((idx+1) % m));
            System.out.println("idx+1: " + (idx+1));
            
            if (keys[idx].equals(key))
                return values[idx];
        }
        
        return null;
    }
    
    // put at table index idx if free; if not try idx+1, idx+2, etc
    public void put(K key, V value) {
        int idx;
        
        for (idx = hash(key); keys[idx] != null; idx = (idx+1) % m) {
            System.out.println("idx+1%m: " + ((idx+1) % m));
            System.out.println("idx+1: " + (idx+1));
            
            if (keys[idx].equals(key))
                break;
        }
        
        keys[idx] = key;
        values[idx] = value;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("HashingSTLinearProbing{");
        
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                sb.append(keys[i]);
                sb.append(": ");
                sb.append(values[i]);
                sb.append(", ");
            }
        }
        
        sb.append("}");
        
        return sb.toString();
    }
    
    public static void main(String[] args) {
        HashingSTLinearProbing<String, String> st = new HashingSTLinearProbing<>();
        
        st.put("pablo", "putoamo");
        st.put("prolo", "zumbao");
        
        System.out.println(st.get("pablo"));
        System.out.println(st.get("prolo"));
        System.out.println(st.get("hola"));
        
        System.out.println(st);
    }
}

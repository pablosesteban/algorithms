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
Separate Chaining Hashing:
    a collision red solution strategy that makes use of elementary link list to implement a ST using hashing

    is based on the Uniform Hashing Assumption:
        each key is equally likely to hash to an integer between 0 and M - 1

    the idea (1953) is just build a linked list for each of the table positions using hashing

    we'll have a table that's smaller than the number of keys that we have and the hash function will map each key to some integer (table index)

    we have to look through the whole list for search but you only have to look through one list out of all the lists

    if you have M entries in the hash table and N keys the linked list of list you're going to look at is about N^M because they're evenly distributed

    N^M under uniform hashing assumption is very close to 1 (constant time in average case)

    it has NO order at all
*/
public class HashingSTSeparateChaining<K, V> {
    // node class to implement a linked list
    private class Node<K, V> {
        private K key;
        private V value;
        private Node next;
        
        public Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    
    /*
    number of chains
    
    if you make M too large: you have too much space and you'll have empty chains or short chains
    
    if you make M too small: then they're too long, you have to search through them all
    
    typical choice of M: equal to the number of keys (N) divided by 5 and you  get constant time operations and not much extra space
    */
    private  int m = 97;
    
    //array of chains: it has to doubling and halving whenever it is necessary
    private Node<K, V>[] st = new Node[m];
    
    // hash function (returns a positive int between 0 and m-1)
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }
    
    public V get(K  key) {
        int idx = hash(key);
        
        // traversin the linked list
        for (Node n = st[idx]; n != null; n = n.next) {
            if (key.equals(n.key))
                return (V)n.value;
        }
        
        return null;
    }
    
    public void put(K key, V value) {
        int idx = hash(key);
        
        // traversing the linked list, if key exists update it with the new value
        for (Node n = st[idx]; n != null; n = n.next) {
            if (key.equals(n.key)) {
                n.value = value;
                
                return;
            }
        }
        
        // if not exists the key, associates this array entry to the new node created and add the node (first in the linked list), if any, to the new one as next
        st[idx] = new Node(key, value, st[idx]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("HashingSTSeparateChaining{");
        
        for (int i = 0; i < st.length; i++) {
            if (st[i] != null) {
                for (Node n = st[i]; n != null; n = n.next) {
                    sb.append(n.key);
                    sb.append(": ");
                    sb.append(n.value);
                    sb.append(", ");
                }
            }
        }
        
        sb.append("}");
        
        return sb.toString();
    }
    
    public static void main(String[] args) {
        HashingSTSeparateChaining<String, String> st = new HashingSTSeparateChaining<>();
        
        st.put("pablo", "putoamo");
        st.put("prolo", "zumbao");
        
        System.out.println(st.get("pablo"));
        System.out.println(st.get("prolo"));
        System.out.println(st.get("hola"));
        
        System.out.println(st);
    }
}

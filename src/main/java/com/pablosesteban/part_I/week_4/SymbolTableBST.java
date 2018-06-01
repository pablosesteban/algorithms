/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_4;

import edu.princeton.cs.algs4.Queue;

/**
 *
 * @author psantama
 */

/**
 * Binary Search Tree Data Structure:
 *      it is a binary tree like Heaps, but in SYMMETRIC ORDER, i.e. each node has a key, and every node's key is LARGER than all keys in its left subtree
 *      and SMALLER than all keys in its right subtree
 * 
 *      this is the data structure which allows us to implement an efficient symbol table
 * 
 *      to implement a BST we're going to extend our implementations of linked list structures to have two references instead of just one
 * 
 *      note that the tree shape depends on order of insertion and the number of compares is equal to one plus the depth of the node so:
 *          the number of compares is going to depend on the order in which the keys come in and that's a key feature of BST
 * 
 *      worst case tree shape is going to be like a linked list (all node to the right or to the left), but it could be avoid by randomizing the insertion of keys (like QuickSort)
 * 
 *      if randomizing the insertion the average case of insertion/search operations is logN and the tree shape is going to be almost balanced
 */
public class SymbolTableBST<K extends Comparable<K>, V> implements SymbolTable<K, V> {
    // the only instance variable is a link to the root node of the BST
    private Node root;
    
    // class representing a node in the linked list
    private class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        // every node's got references to the left subtree, that contains the smaller keys, and the right subtree that contains the larger keys
        private Node left;
        private Node right;
        // number of nodes in the subtree rooted at this node, in order to implement methods related to subtree counts (size, rank...)
        private int count;
        
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            
            // itself is included in the count
            count = 1;
        }
    }
    
    /*
     * INSERT OPERATION:
     *      start at the root and compare our key against the key at the root
     *  
     *      if less, go left; if greater, go right; if NULL, insert it
     * 
     *      implemented RECURSIVELY
     */
    @Override
    public void put(K key, V value) {
        // starting at root node
        root = put(root, key, value);
    }
    
    /*
    as it moves down the tree it'll return a link up higher in the tree
    
    when we insert a new node in this tree we go down that path, we create a new node and then return the link to that node higher up
    
    this allows us to put a node into an empty ST
    */
    private Node put(Node n, K key, V value) {
        // if the argument is null, return a reference to a new node that associates key with value and then that one has null links (empty ST)
        if (n == null)
            return new Node(key, value);
        
        int comparison = key.compareTo((K) n.key);
        
        if(comparison > 0) {
            n.right = put(n.right, key, value);
        }else if(comparison < 0) {
            n.left = put(n.left, key, value);
        }else {
            // update the value if exist the key
            n.value = value;
        }
        
        // track the number of nodes in the subtree
        n.count = 1 + nodeSize(n.left) + nodeSize(n.right);
        
        return n;
    }
    
    /**
     * SEARCH OPERATION:
     *      start at the root and compare our key against the key at the root
     *  
     *      if less, go left; if greater, go right; if EQUAL, search hit
     */
    @Override
    public V get(K key) {
        Node searched = root;
        
        while (searched != null) {
            int comparison = key.compareTo((K) searched.key);
            
            if(comparison > 0) {
                searched = searched.right;
            } else if(comparison < 0) {
                searched = searched.left;
            } else {
                break;
            }
        }
        
        System.out.println("size at " + searched.key + ": " + nodeSize(searched));
        
        return (V) searched.value;
    }

    /*
    HIBBARD DELETION:
        three cases:
            to delete a node that has no children just return null, and then go back up to update the counts as usual
    
            to delete a node that only has one child, just go ahead and return the link to that child, and then again update all the accounts after the recursive calls
    
            to delete a no that has two links pointing down from it:
                find the next smallest node in the right subtree of that tree
                
                put that node in the spot where the node to delete is
    
                then delete the minimum node in the right subtree of that tree
    
        there is a drawback: it is asymetric and the tree's becoming much less balanced than it was
    
        makes all operations sqrt(N) instead of logN (sqrt(N) > logN)
    */
    @Override
    public void delete(K key) {
        root = delete(root, key);
    }
    
    private Node delete(Node n, K key) {
        if (n == null)
            return null;
        
        int comparison = key.compareTo((K) n.key);
        
        // searching the node
        if (comparison > 0) {
            n.right = delete(n.right, key);
        }else if (comparison < 0) {
            n.left = delete(n.left, key);
        }else {
            // when it reaches the node searched
            if (n.right == null)
                return n.left;
            
            if  (n.left == null)
                return n.right;
            
            Node t = n;
            n = min(t.right);
            n.right = deleteMin(t.right);
            n.left = t.left;
        }
        
        n.count = 1 + nodeSize(n.left) + nodeSize(n.right);
        
        return n;
    }
    
    private Node min(Node n) {
        Node min = n.left;
        
        while (min != null) {
            min = min.left;
        }
        
        return min;
    }
    
    private Node deleteMin(Node n) {
        if (n.left == null)
            return n.right;
        
        n.left = deleteMin(n.left);
        
        n.count = 1 + nodeSize(n.left) + nodeSize(n.right);
        
        return n;
    }
    
    @Override
    public boolean contains(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return nodeSize(root);
    }
    
    private int nodeSize(Node n) {
        if (n == null)
            return 0;
        
        return n.count;
    }

    /*
    to iterate we're going to:
        maintain a queue of keys
    
        call this recursive in-order method
    
        just return that queue
    */
    @Override
    public Iterable<K> keys() {
        Queue<K> keys = new Queue<>();
        
        traverseInOrder(root, keys);
        
        return keys;
    }
    
    /*
    RECURSIVELY:
        traverse the left subtree
    
        enqueue the key
    
        traverse the right subtree
    
    this is going to add all the keys in the tree to the queue in order this way (like Merge Sort):
        put everybody to the left on the queue
        
        put the root on the queue
        
        put everybody to the right on the queue
    */
    private void traverseInOrder(Node n, Queue<K> keys) {
        if (n == null)
            return;
        
        traverseInOrder(n.left, keys);
        
        keys.enqueue((K)n.key);
        
        traverseInOrder(n.right, keys);
    }
    
    // traverse the BST only by the left side until reaches the last one
    @Override
    public K min() {
        Node n = root;
        
        while(n.left != null) {
            n = n.left;
        }
        
        return (K) n.key;
    }

    // traverse the BST only by the right side until reaches the last one
    @Override
    public K max() {
        Node n = root;
        
        while(n.right != null) {
            n = n.right;
        }
        
        return (K) n.key;
    }

    @Override
    public K floor(K key) {
        Node n = root;
        
        while (n != null) {
            int comparison = key.compareTo((K)n.key);
            
            if (comparison > 0) {
                if (n.right == null)
                    return (K)n.key;
                
                n = n.right;
            }else if(comparison < 0) {
                if (n.left == null)
                    return (K)n.key;
                
                n = n.left;
            }else {
                return (K)n.key;
            }
            
            if (n != null && key.compareTo((K)n.key) < 0) {
                return (K)n.key;
            }
        }
        
        return null;
    }

    @Override
    public K ceiling(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args) {
        SymbolTableBST<String, String> symbolTableBST = new SymbolTableBST<>();
        
        symbolTableBST.put("h", "1");
        symbolTableBST.put("c", "2");
        symbolTableBST.put("k", "3");
        symbolTableBST.put("a", "4");
        symbolTableBST.put("b", "5");
        symbolTableBST.put("r", "6");
        
        System.out.println("min: " + symbolTableBST.min());
        System.out.println("max: " + symbolTableBST.max());
        
        String key = "d";
        System.out.println("floor " + key + ": " + symbolTableBST.floor(key));
        
        System.out.println("size: " + symbolTableBST.size());
        symbolTableBST.get("c");
        
        for (String k : symbolTableBST.keys()) {
            System.out.println(k);
        }
        
        symbolTableBST.delete("b");
        
        for (String k : symbolTableBST.keys()) {
            System.out.println(k);
        }
    }
}

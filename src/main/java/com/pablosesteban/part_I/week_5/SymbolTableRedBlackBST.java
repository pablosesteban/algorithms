/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_5;

import java.util.*;
import com.pablosesteban.part_I.week_4.SymbolTable;
import edu.princeton.cs.algs4.Queue;

/**
 *
 * @author psantama
 */

/*
BALANCED BST:
    have near-perfect balance:
        every path from root to null link has same length
    
    the height is guaranteed to be no larger than 2 logN

    2-3 Search Trees:
        a pretty old algorithm which allow the nodes in our trees to hold more than one key
        
        is a way to generalize BST to provide the flexibility that we need to guarantee fast performance in all operations in a Symbol Table
        
        two properties which have to be maintained (invariants):
            perfect balance

            symmetric order: inorder traversal yields keys in ascending order

        two kind of nodes:
            2-node: one key, two children (less and greater)
            
            3-node: two keys, three children (less, between and greater)

        inserting a new key into a 3-node:
            1) compares the new key with the those on the 3-node (creates a temporary 4-node)
            
            2) choose the middle key (split it into a 3-node)

            3) put the middle key into the parent

            4) repeat those steps every time the parent is a 3-node

        the only time the height of a 2-3 tree changes is when the roots splits, i.e. all node in the path are 3-nodes

        the process of inserting a new key into a 3-node involves a CONSTANT number of operation, i.e. change a constant number of links

        guaranteed logarithmic performance for all operations
*/

/*
RED-BLACK BST (Left-Leaning):
    is a simple data structure that allows us to implement 2-3 search tree
    
    represent a 2–3 search tree as a BST using an internal left-leaning "red" links for representing a 3-node
    
    the larger of the two nodes in the 3-node will always be the root of a little BST of size two for the 2-node
    
    the link between the two nodes, the LEFT link that links the larger key to the smaller one we'll color RED
    
    properties:
        no node has two red links connected to it
            
        every path from root to null link has the same number of black links
        
        red links lean LEFT

        there is one-to-one correspondence between 2-3 Search Trees and Left-Leaning Red-Black Trees (horizontal red links):
            all of the operations that we're going to look at for red-black trees can be understood in terms of the corresponding operations on 2-3 search trees
    
    as red links are "internal" thing, methods from elementary BST will work here without reimplementing, and work better because of the perfect balanced tree
*/
public class SymbolTableRedBlackBST<K extends Comparable<K>, V> implements SymbolTable<K, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    
    private Node root;
    
    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private int count;
        // color of parent link property added to know if it is a red link, i.e. it is a 3-node
        private boolean color;
        
        public Node(K key, V value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
            
            count = 1;
        }
    }
    
    private boolean isRed(Node n) {
        if (n != null)
            return n.color == RED;
        
        return false;
    }
    
    /****************elementary operations that we have to perform on red-black trees****************/
    /*
    during the construction of a tree, or during an insertion operation, sometimes we wind up with red links that are leaning in the wrong direction (right leaning)
    
    it is a local operation that only changes a few links
    
    right children of parent node becomes the parent
    
    maintains the perfect balance of the tree
    */
    private Node roatateLeft(Node parent) {
        Node children = parent.right;
        
        parent.right = children.left;
        
        children.left = parent;
        // whatever color was in parent (must be black) is the color should have the children (which becomes parent)
        children.color = parent.color;
        
        parent.color = RED;
        
        // return the new parent to link further up the tree which happens during our standard recursive insertion
        return children;
    }
    
    /*
    to get the insertion done properly we sometimes need to take a left-leaning red link and temporarily make it lean right
    
    also maintains the perfect balance of the tree
    */
    private Node rotateRight(Node parent) {
        Node children = parent.left;
        
        parent.left = children.right;
        
        children.right = parent;
        children.color = parent.color;
        
        parent.color = RED;
        
        return children;
    }
    
    /*
    sometimes during the insertion, we might wind up with a node that's got two red links coming out of it
    
    that's corresponds precisely to our temporary 4-node when we're doing 2-3 trees
    
    we need to split the 4-node moving the middle element to the parent by flipping colors
    */
    private void flipColors(Node parent) {
        parent.color = RED;
        
        parent.left.color = BLACK;
        parent.right.color = BLACK;
    }
    /****************elementary operations that we have to perform on red-black trees****************/
    
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }
    
    /*
    the same code handles all of the cases and the way that it works is we are always reducing one case to another:
        right child red, left child black: ROTATE LEFT
        
        left child, left-left grandchild red: ROTATE RIGHT
        
        both children red: FLIP COLORS
    */
    private Node put(Node n, K key, V value) {
        /*
        if the argument is null, return a reference to a new node that associates key with value and then that one has null links (empty ST)
        
        insert at the bottom
        */
        if (n == null)
            // always create a Node with RED color (link)
            return new Node(key, value, RED);
        
        // searching
        int comparison = key.compareTo((K) n.key);
        if(comparison > 0) {
            n.right = put(n.right, key, value);
        }else if(comparison < 0) {
            n.left = put(n.left, key, value);
        }else {
            // update the value if exist the key
            n.value = value;
        }
        
        // applies the operations to maintain the invariants!
        if (isRed(n.right) && !isRed(n.left))
            n = roatateLeft(n);
        
        if (isRed(n.left) && isRed(n.left.left))
            n = rotateRight(n);
        
        if (isRed(n.left) && isRed(n.right))
            flipColors(n);
        
        // track the number of nodes in the subtree
        n.count = 1 + nodeSize(n.left) + nodeSize(n.right);
        
        return n;
    }
    
    private int nodeSize(Node n) {
        if (n == null)
            return 0;
        
        return n.count;
    }

    @Override
    public V get(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<K> keys() {
        Queue<K> keys = new Queue<>();
        
        traverseInOrder(root, keys);
        
        return keys;
    }

    private void traverseInOrder(Node n, Queue<K> keys) {
        if (n == null)
            return;
        
        traverseInOrder(n.left, keys);
        
        keys.enqueue((K)n.key);
        
        traverseInOrder(n.right, keys);
    }
    
    @Override
    public K min() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public K max() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public K floor(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public K ceiling(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        List<Node> nodeRoot = new LinkedList<>();
        nodeRoot.add(root);
        
        StringBuilder sb = new StringBuilder();
        
        traverseLevelOrder(nodeRoot, sb, 0);
        
        return sb.toString();
    }
    
    private void traverseLevelOrder(List<Node> nodes, StringBuilder sb, int level) {
        if (nodes.isEmpty())
            return;
        
        List<Node> nextLevelNodes = new LinkedList<>();
        
        sb.append("level " + level + ": ");
        for (Node node : nodes) {
            sb.append(node.key);
            sb.append(" ");
            
            if (node.left != null)
                nextLevelNodes.add(node.left);
            
            if (node.right != null)
                nextLevelNodes.add(node.right);
        }
        
        sb.append("\n");
        
        traverseLevelOrder(nextLevelNodes, sb, ++level);
    }
    
    public static void main(String[] args) {
        SymbolTableRedBlackBST<String, String> symbolTableRedBlackBST = new SymbolTableRedBlackBST<>();
        
        symbolTableRedBlackBST.put("p", "p");
        symbolTableRedBlackBST.put("a", "a");
        symbolTableRedBlackBST.put("h", "h");
        symbolTableRedBlackBST.put("v", "v");
        
        System.out.println(symbolTableRedBlackBST);
    }
}

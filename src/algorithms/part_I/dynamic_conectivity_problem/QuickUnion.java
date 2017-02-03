/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.dynamic_conectivity_problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author pabloS
 */

/*
Second implementation of an algorithm for solving the dynamic connectivity problem

This is a so called LAZY algorithm, i.e. avoid doing work until we have to

Can this code be effective for large problems? Well unfortunately Quick-union is faster but it's also too slow

Trees can get too tall, which would mean that the find operation would be too expensive
*/
public class QuickUnion implements UnionFind {
    /*
    Same data structure as QuickFind but with a different interpretation
    
    Array as representing a set of trees so, each entry in the array is going to contain a reference to its parent in the tree
    
    The root objects of the trees point to itself
    */
    protected int[] id;
    
    public QuickUnion(int n) {
        id = new int[n];
        
        /*
        Initially, we set up the id array, with each entry, equal to its index
        
        So all that says is that all the objects are independent
        */
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }
    
    /*
    Starting at any node, you just follow p equals id of p until they're equal and then you're at a root
    
    Could be very expensive, if it means going from the very bottom object to the very top object
    */
    protected int getRoot(int p) {
        //If it's not equal, we just move I up one level in the tree, set p equals id of p and so on
        while (p != id[p]) {
            p = id[p];
        }
        
        return p;
    }
    
    /*
    To merge two items that are in different components, all we do is set the id of p's root to the id of q's root, i.e make p's tree point to q
    
    A union operation only involves changing one entry in the array
    
    Quick implementation to connect two objects
    
    But it is limited by getting the root of both, which depends on how far the objects are from root
    */
    @Override
    public void union(int p, int q) {
        id[getRoot(p)] = getRoot(q);
    }
    
    /*
    Once we can calculate these roots, then we can implement the find operation just by checking whether the two items that we're supposed to check with are connective where they have the same root
    
    Takes time proportional to depth of both objects
    */
    @Override
    public boolean isConnected(int p, int q) {
        return getRoot(p) == getRoot(q);
    }
    
    @Override
    public Map<Integer, List<Integer>> getConnectedComponents() {
        Map<Integer, List<Integer>> connectedComponents = new HashMap<>();
        
        for (int i = 0; i < id.length; i++) {
            int root = getRoot(i);
            
            if (!connectedComponents.containsKey(root)) {
                connectedComponents.put(root, new ArrayList<>());
            }
            
            connectedComponents.get(root).add(i);
        }
        
        return connectedComponents;
    }

    @Override
    public int find(int i) {
        return new TreeSet<>(getConnectedComponents().get(getRoot(i))).last();
    }
    
    @Override
    public String toString() {
        StringBuilder index = new StringBuilder();
        StringBuilder val = new StringBuilder();
        
        index.append("elements: [");
        val.append("parents: [");
        
        for (int i = 0; i < id.length-1; i++) {
            index.append(i);
            index.append(", ");
            
            val.append(id[i]);
            val.append(", ");
        }
        index.append(id.length-1);
        index.append("]");
        
        val.append(id[id.length-1]);
        val.append("]");
        
        return index + "\n" + val;
    }
    
    public static void main(String[] args) {
        UnionFind uf = new QuickUnion(10);
        
        uf.union(1, 4);
        uf.union(4, 5);
        
        uf.union(6, 2);
        uf.union(2, 3);
        uf.union(3, 7);
        
        uf.union(7, 8);
        
        System.out.println(uf);
        System.out.println(uf.getConnectedComponents());
        
        System.out.println(uf.isConnected(4, 2));
        System.out.println(uf.isConnected(3, 3));
        System.out.println(uf.isConnected(7, 2));
        
        System.out.println(uf.find(3));
        System.out.println(uf.find(4));
    }
}

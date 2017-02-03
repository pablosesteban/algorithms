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

/**
 *
 * @author psantama
 */

/*
First implementation of an algorithm for solving the dynamic connectivity problem

This is a so called EAGER algorithm, i.e. does the work even though it is not necessary
*/
public class QuickFind implements UnionFind {
    /*
    Data structure is an integer array indexed by object
    
    The interpretation is the two objects, p and q are connected if and only if, their entries in the array are the same
    */
    private int[] id;
    
    public QuickFind(int n) {
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
    We have to change all the entries, whose id is equal to one of them to the other one
    
    Arbitrarily we choose to change the ones that are the same as p to the ones that are same as q
    
    This is an inefficient as it has to go through the entire array each time
    
    Both the initialized and union operations involved the for-loop that go through the entire array
    
    Is too expensive, it takes N^2 array accesses to process a sequence of N union commands on N objects
    
    We can't accept quadratic time algorithms for large problems as they don't scale, i.e. as computers get faster and bigger, quadratic algorithms actually get slower
    */
    @Override
    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }
    
    /*
    Two objects, p and q are connected if and only if, their entries in the array are the same
    
    Quick implementation of find if two objects are connected
    */
    @Override
    public boolean isConnected(int p, int q) {
        return id[p] == id[q];
    }
    
    @Override
    public Map<Integer, List<Integer>> getConnectedComponents() {
        Map<Integer, List<Integer>> connectedComponents = new HashMap<>();
        
        for (int i = 0; i < id.length; i++) {
            
            if (!connectedComponents.containsKey(id[i])) {
                connectedComponents.put(id[i], new ArrayList<>());
            }
            
            connectedComponents.get(id[i]).add(i);
        }
        
        return connectedComponents;
    }

    @Override
    public int find(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        StringBuilder index = new StringBuilder();
        StringBuilder val = new StringBuilder();
        
        index.append("elements: [");
        val.append("values: [");
        
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
        UnionFind uf = new QuickFind(10);
        
        uf.union(1, 4);
        uf.union(4, 5);
        
        uf.union(6, 2);
        uf.union(2, 3);
        uf.union(3, 7);
        
        System.out.println(uf);
        
        System.out.println(uf.getConnectedComponents());
        
        System.out.println(uf.isConnected(4, 2));
        System.out.println(uf.isConnected(3, 3));
        System.out.println(uf.isConnected(7, 2));
    }
}

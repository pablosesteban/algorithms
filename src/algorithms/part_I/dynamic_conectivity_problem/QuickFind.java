/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.dynamic_conectivity_problem;

/**
 *
 * @author psantama
 */

/*
First implementation of an algorithm for solving the dynamic connectivity problem

This is a so called eager algorithm, i.e. does the work even though it is not necessary
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
    
    Bth the initialized and union operations involved the for-loop that go through the entire array
    
    So they have to touch in a constant proportional to n times after touching array entry
    
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
}

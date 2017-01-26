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

This is a so called eager algorithm
*/
public class QuickFind implements UnionFind {
    //data structure is an integer array indexed by object
    private int[] id;
    
    public QuickFind(int n) {
        id = new int[n];
        
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    /*
    We have to change all the entries, whose id is equal to one of them to the other one
    
    Arbitrarily we choose to change the ones that are the same as p to the ones that are same as q
    */
    @Override
    public void union(int p, int q) {
        
    }
    
    //Two objects, p and q are connected if and only if, their entries in the array are the same
    @Override
    public boolean isConnected(int p, int q) {
        return id[p] == id[q];
    }
    
    
}

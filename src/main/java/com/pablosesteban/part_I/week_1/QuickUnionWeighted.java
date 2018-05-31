/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.pablosesteban.part_I.week_1;

/**
 *
 * @author psantama
 */

/*
Proved by Friedman and Sachs, that there is no linear time algorithm for the union find problem

But weighted quick union with path compression in practice is, is close enough that it's going to enable the solution of huge problems
*/
public class QuickUnionWeighted extends QuickUnion {
    /*
    IMPROVEMENT 1: WEIGHTING
        The idea is to when implementing the QuickUnion algorithm take steps to avoid having tall trees
        
        If you've got a large tree and a small tree (number of objects with the same root) to combine together what you want to try to do is avoid putting the large tree lower, that's going to lead to long tall trees
        
        What we'll do is we'll keep track of the number of objects in each tree and then, we'll maintain balance by always making sure that we link the root of the smaller tree to the root of the larger tree
        
        We wind up with a single tree representing all the objects, but this time, we have some guarantee that no item is too far from the root
    
        We used the same data structure as QuickUnion
        
        But now we need an extra array, that for each item, gives the number of objects in the tree root at that item
    */
    int[] treeSizes;
    
    public QuickUnionWeighted(int n) {
        super(n);
        
        treeSizes = new int[n];
        
        //All elements at the beginning are of size 1
        for (int i = 0; i < treeSizes.length; i++) {
            treeSizes[i] = 1;
        }
    }
    
    /*
    We're going to modify the code to check the sizes
    
    So link the root of the smaller tree to the root of the larger tree
    
    Then after changing the id link, we also change the size array by adding the size of the smallest tree to the bigger one
    
    Is not very much code but much, much better performance
    
    If we analyze the running time mathematically:
        It will take time proportional to how far down the nodes are in the tree
        
        But now we can show that it's guaranteed that the depth of any node in the tree is AT MOST the logarithm to the base two of N
        
        If N is 1000, that's going to be 10, if N is a 1000000 that's 20, if N is a 1000000000 that's 30...
        
        It's a very small number compared to N
        
        This means that the algorithm is SCALABLE, i.e. when N gets much bigger the depth of the objects in the tree increments slow
    
    It is more efficient, but could we improve it even further? Yes
    */
    @Override
    public void union(int p, int q) {
        int pRoot = getRoot(p);
        int qRoot = getRoot(q);
        
        if (treeSizes[pRoot] >= treeSizes[qRoot]) {
            id[qRoot] = pRoot;
            
            treeSizes[pRoot] += treeSizes[qRoot];
        }else {
           id[pRoot] = qRoot;
           
           treeSizes[qRoot] += treeSizes[pRoot];
        }
    }
    
    @Override
    protected int getRoot(int p) {
        while (p != id[p]) {
            /*
            IMPROVEMENT 2: PATH COMPRESSION
                When we're trying to find the root of the tree containing a given node we're touching all the nodes on the path from that node to the root
                
                While we're doing that we might as well make every other node in the path point to its grandparent on the way up the tree
                
                Doing that keeps trees almost completely flat
            */
            id[p] = id[id[p]];
            
            p = id[p];
        }
        
        return p;
    }

    @Override
    public String toString() {
        StringBuilder index = new StringBuilder();
        StringBuilder val = new StringBuilder();
        StringBuilder size = new StringBuilder();
        
        index.append("elements:      [");
        val.append("parent roots:  [");
        size.append("size:          [");
        
        for (int i = 0; i < id.length-1; i++) {
            index.append(i);
            index.append(", ");
            
            val.append(id[i]);
            val.append(", ");
            
            size.append(treeSizes[i]);
            size.append(", ");
        }
        index.append(id.length-1);
        index.append("]");
        
        val.append(id[id.length-1]);
        val.append("]");
        
        size.append(treeSizes[id.length-1]);
        size.append("]");
        
        return index + "\n" + val + "\n" + size;
    }
    
    public static void main(String[] args) {
        UnionFind uf = new QuickUnionWeighted(10);
        
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
    }
}

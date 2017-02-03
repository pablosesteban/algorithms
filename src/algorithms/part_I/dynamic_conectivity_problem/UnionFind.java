/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.dynamic_conectivity_problem;

import java.util.List;
import java.util.Map;

/**
 *
 * @author psantama
 */
/*
Union-Find Data Type

To resolve the Dynamic Conectivity Problem
*/
public interface UnionFind {
    public void union(int p, int q);
    
    public boolean isConnected(int p, int q);
    
    //CONNECTED COMPONENTS: Maximal set of objects that are mutually connected
    public Map<Integer, List<Integer>> getConnectedComponents();
    
    /*
    returns the largest element in the connected component containing i
    
    shouldtake logarithmic time or better
    */
    int find(int i);
}

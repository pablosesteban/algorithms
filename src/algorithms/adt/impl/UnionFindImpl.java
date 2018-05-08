 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package algorithms.adt.impl;

import algorithms.part_I.week_1.UnionFind;
import java.util.List;
import java.util.Map;

public class UnionFindImpl implements UnionFind {
    private int size;
    
    public UnionFindImpl(int capacity) {
        size = capacity;
    }

    @Override
    public void union(int p, int q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isConnected(int p, int q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, List<Integer>> getConnectedComponents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int find(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import com.pablosesteban.api.GraphCycles;
import java.util.List;

/**
 * A DFS implementation based on the fact that the recursive call stack
 * maintained by the system represents the "current" path under consideration.
 * If it ever finds an edge v-w to a vertex w that is on that stack, it has found
 * a cycle, since the stack is evidence of a path from w to v, and the edge v-w
 * completes the cycle.
 */
public class DFSCycles implements GraphCycles {
    private boolean[] marked;
    private int[] edgeTo;
    
    @Override
    public boolean hasCycle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfCycles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Iterable<Integer>> getCycles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

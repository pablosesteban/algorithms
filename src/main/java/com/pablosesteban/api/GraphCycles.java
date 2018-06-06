/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api;

import java.util.List;

/**
 * API to find cycles on a graph.
 * A cycle is a path with at least one edge whose first and last vertices are
 * the same.
 */
public interface GraphCycles {
    /**
     * Check if a graph has at least one cycle
     * 
     * @return true if, and only if, a graph has at least one cycle
     */
    boolean hasCycle();
    
    /**
     * Count the number of cycles in a graph
     * 
     * @return the number of cycles in a graph
     */
    int getNumberOfCycles();
    
    /**
     * Get all cycles in a graph.
     * 
     * @return null if a graph has no cycles, otherwise all cycles
     */
    List<Iterable<Integer>> getCycles();
}

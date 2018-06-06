/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api;

import java.util.List;

/**
 * API to find cycles on a graph
 */
public interface GraphCycle {
    /**
     * Check if a graph has at least one cycle
     * 
     * @return true if, and only if, a graph has at least one cycle
     */
    boolean hasCycle();
    
    /**
     * Count the number of cycles a graph has
     * 
     * @return the number of cycles in a graph
     */
    int getNumberOfCycles();
    
    /**
     * Get all cycles a graph has
     * 
     * @return null if a graph has no cycles, otherwise all cycles it has
     */
    List<Iterable<Integer>> getCycles();
}

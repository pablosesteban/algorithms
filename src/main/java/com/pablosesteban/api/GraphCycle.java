/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api;

/**
 * API to check if a graph has at least one cycle.
 * A cycle is a path with at least one edge whose first and last vertices are
 * the same.
 */
public interface GraphCycle {
    /**
     * Check if a graph has at least one cycle
     * 
     * @return true if, and only if, a graph has at least one cycle
     */
    boolean hasCycle();
    
    /**
     * Get the first cycle found in a graph.
     * 
     * @return null if a graph has no cycles, otherwise the first cycle found
     */
    Iterable<Integer> getCycle();
}

/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api;

/**
 * API to check if a directed graph has at least one cycle.
 * A cycle is a path with at least one edge whose first and last vertices are
 * the same.
 * Cycles are of particular importance in applications that involve processing
 * digraphs. For example, in scheduling problems, arranging for the completion
 * of a set of jobs, under a set of constraints, by specifying when and how the
 * jobs are to be performed, digraph models arise directly. The most important
 * type of constraints is precedence constraints, which specify that certain
 * tasks must be performed before certain others. In general, if a
 * precedence-constrained scheduling problem has a directed cycle, then there is
 * no feasible solution.
 */
public interface DigraphCycle {
	/**
     * Check if a digraph has at least one cycle
     * 
     * @return true if, and only if, a digraph has at least one cycle
     */
    boolean hasCycle();
    
    /**
     * Get the first cycle found in a digraph.
     * 
     * @return null if a digraph has no cycles, otherwise the first cycle found
     */
    Iterable<Integer> getCycle();
}

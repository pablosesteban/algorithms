/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api;

/**
 * An API to get vertices in a digraph in topological order, if possible.
 * Topological order puts the vertices in order such that all its directed
 * edges point from a vertex earlier in the order to a vertex later in the
 * order, or report that doing so is not possible.
 * In digraphs, precedence-constrained scheduling problems amounts to computing
 * topological order for its vertices.
 * A digraph has a topological order if and only if it is a Directed Acyclic
 * Graph (DAG), otherwise if it has a directed cycle, it has no topological order.
 */
public interface DigraphTopologicalOrder {
	/**
	 * Checks if the digraph is a Directed Acyclic Graph (DAG),
	 * i.e. a digraph with no directed cycles
	 * 
	 * @return true if, and only if, a digraph is a DAG
	 */
	boolean isDag();
	
	/**
	 * Gets the vertices in topological order if possible
	 * 
	 * @return the vertices in topological order if possible, otherwise null
	 */
	Iterable<Integer> topologicalOrder();
}

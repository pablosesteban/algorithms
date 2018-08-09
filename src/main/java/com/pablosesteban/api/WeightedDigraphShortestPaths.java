/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api;

import com.pablosesteban.adt.impl.Edge;

/**
 * API to find the shortest paths from a vertex source to every vertices reachable from it in the graph.
 * A shortest path from a source vertex to a vertex v in an edge-weighted digraph is a directed path
 * from source to v with the property that no other such path has a lower weight.
 */
public interface WeightedDigraphShortestPaths {
	/**
	 * Gets the distance from source to v vertex
	 * 
	 * @param v a vertex in the graph
	 * @return the distance from source to v vertex, infinity if not reachable
	 */
	double weightTo(int v);
	
	/**
	 * Check if there is a path from source to v vertex
	 * 
	 * @param v a vertex in the graph
	 * @return ture if, and only if, there is a path from source to v vertex
	 */
	boolean hasPathTo(int v);
	
	/**
	 * Gets the path from source to v vertex
	 * 
	 * @param v a vertex in the graph
	 * @return the path from source to v vertex, null if none
	 */
	Iterable<Edge> pathTo(int v);
}

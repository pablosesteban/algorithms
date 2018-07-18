package com.pablosesteban.adt;

/**
 * An Abstract Data Type (ADT) which defines the operations to identify the connected component containing
 * a vertex, determine whether two vertex are in the same connected component, and count the number of
 * connected components.
 * Assuming that “is connected to” is an equivalence relation, i.e:
 * <li>Reflexive: p is connected to p.</li>
 * <li>Symmetric: if p is connected to q, then q is connected to p.</li>
 * <li>Transitive: if p is connected to q and q is connected to r, then p is connected to r.</li>
 */
public interface UnionFind {
	/**
	 * Merges two connected components if the two vertices are
	 * in different connected components
	 * 
	 * @param v a vertex
	 * @param w another vertex
	 */
	void union(int v, int w);
	
	/**
	 * Gets the connected component identifier for a given vertex
	 * Think of each connected component as being represented by one of its vertex
	 * 
	 * @param v a vertex
	 * @return the connected component identifier
	 */
	int find(int v);
	
	/**
	 * Determines whether two vertices are in the same connected component
	 * 
	 * @param v a vertex
	 * @param w another vertex
	 * @return true if, and only if, v and w are in the same connected component
	 */
	boolean areConnected(int v, int w);
	
	/**
	 * Gets the number of connected components
	 * 
	 * @return the number of connected components
	 */
	int count();
}

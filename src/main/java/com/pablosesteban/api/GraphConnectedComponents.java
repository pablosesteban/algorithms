/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api;

/**
 * API to find connected components in a graph.
 * Connected components are maximal subsets of vertices that are strongly connected
 * to one another, with each vertex in exactly one subset.
 * A graph with V vertices has between 1 (all vertices connected) and V (no vertex
 * connected) connected components.
 */
public interface GraphConnectedComponents {
    /**
     * Determines if the two given vertices are connected
     * 
     * @param v a vertex
     * @param w another vertex
     * @return true if, and only if, both vertices are connected
     */
    boolean areConnected(int v, int w);
    
    /**
     * Returns the number of connected components in the graph
     * 
     * @return the number of connected components in the graph
     */
    int getCount();
    
    /**
     * Returns the component identifier for the given vertex (between 0 and
     * count()-1)
     * 
     * @param v a vertex
     * @return the component identifier for the given vertex
     */
    int getConnectedComponent(int v);
}

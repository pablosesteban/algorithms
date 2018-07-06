package com.pablosesteban.adt;

import com.pablosesteban.adt.impl.Edge;

/**
 * An Abstract Data Type (ADT) which defines the fundamental edge-weighted graph
 * operations.
 * An edge-weighted graph is a graph where is associated a weight or cost with each
 * edge.
 * Edge weights might represent anything, i.e. time or cost or an entirely different
 * variable, and do not need to be proportional to a distance at all.
 * Edge weights may be positive, zero or negative.
 */
public interface WeightedGraph {
	/**
     * Types of graphs:<br>
     * <ul>
     * <li>UNDIRECTED: A set of vertices and a collection of edges which connect
     * a pair of vertices. A vertex w is connected to a vertex v if exists a
     * path that contains both of them. The fact that w is connected to v indicates
     * that v is connected to w as well.</li>
     * <li>DIRECTED: A set of vertices and a collection of directed edges which
     * connects an ordered pair of vertices. A vertex w is reachable from a vertex
     * v if there is a directed path from v to w. The fact that w is reachable
     * from v in a indicates nothing about whether v is reachable from w.</li>
     * </ul>
     */
    enum Type {
        UNDIRECTED("A set of vertices and a collection of edges which connect a pair of vertices"),
        DIRECTED("A set of vertices and a collection of directed edges which connects an ordered pair of vertices");
    	
    	private String description;
    	
    	private Type(String description) {
    		this.description = description;
    	}
    	
    	public String getDescription() {
    		return description;
    	}
    }
	
	/**
	 * Add an edge to the graph
	 * 
	 * @param e a new edge
	 */
	void addEdge(Edge e);
	
	/**
	 * Get edges incident to a vertex. The order of iteration is not specified.
	 * 
	 * @param v a vertex in the graph
	 * @return all edges incident
	 */
	Iterable<Edge> getIncidentEdges(int v);
	
	/**
	 * Get all of the graph’s edges (ignoring any self-loops)
	 * 
	 * @return all edges in the graph
	 */
	Iterable<Edge> getEdges();
	
	/**
     * Gets the number of vertices in the graph
     * 
     * @return the number of vertices in the graph
     */
    int size();
}

 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package com.pablosesteban.adt;

import com.pablosesteban.adt.impl.DirectedGraph;

/**
 * An Abstract Data Type (ADT) which defines the fundamental graph operations.
 * A graph is a set of vertices and a collection of edges that each connect a
 * pair of vertices. When there is an edge connecting two vertices, we say that
 * the vertices are adjacent to one another and that the edge is incident to 
 * both vertices. The degree of a vertex is the number of edges incident to it.
 * A subgraph is a subset of a graph edges and associated vertices that
 * constitutes a graph.
 */
public interface Graph {
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
     * Add an edge between two vertices
     * 
     * @param v first vertex of the graph
     * @param w second vertex of the graph
     */
    void addEdge(int v, int w);
    
    /**
     * Get all adjacent vertices. The order of iteration is not specified.
     * In directed graphs it gives just vertices connected by edges that point
     * from each vertex.
     * 
     * @param v a vertex of the graph
     * @return all adjacent vertices
     */
    Iterable<Integer> getAdjacentVertices(int v);
    
    /**
     * Gets the number of vertices in the graph
     * 
     * @return the number of vertices in the graph
     */
    int size();
    
    /**
     * Gets a copy of the directed graph, with all edges reversed.
     * Is sometimes needed in directed graph processing because it allows to
     * find the edges that point to each vertex.
     * 
     * @return the reverse of a directed graph
     */
    DirectedGraph reverse();
}

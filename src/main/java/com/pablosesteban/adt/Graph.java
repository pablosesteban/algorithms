 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package com.pablosesteban.adt;

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
     * Types of graphs.
     */
    enum Type {
        UNDIRECTED,
        DIRECTED;
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
    Graph reverse();
}

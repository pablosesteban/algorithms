 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package algorithms.adt;

/**
 * An Abstract Data Type (ADT) which defines the fundamental graph operations.
 * A graph is a set of vertices and a collection of edges that each connect a
 * pair of vertices. When there is an edge connecting two vertices, we say that
 * the vertices are adjacent to one another and that the edge is incident to 
 * both vertices. The degree of a vertex is the number of edges incident to it.
 * A subgraph is a subset of a graph’s edges (and associated vertices) that
 * constitutes a graph.
 */
public interface Graph {
    /**
     * Add an edge between two vertices
     * 
     * @param v first vertice of the graph
     * @param w second vertice of the graph
     */
    void addEdge(int v, int w);
    
    /**
     * Get all adjacent vertices (the order of iteration is not specified)
     * 
     * @param v a vertice of the graph
     * @return all adjacent vertices
     */
    Iterable<Integer> getAdjacentVertices(int v);
}

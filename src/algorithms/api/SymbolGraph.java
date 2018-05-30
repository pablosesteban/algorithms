/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.api;

import algorithms.adt.Graph;

/**
 * API for processing graphs defined using strings, not integer indices, to
 * define and refer to vertices.
 */
public interface SymbolGraph {
    /**
     * Checks if the provided vertex name is a vertex in the graph
     * 
     * @param key vertex name
     * @return true if, and only if, the provided vertex name is a vertex in the
     * graph
     */
    boolean contains(String key);
    
    /**
     * Gets the vertex index associated with the provided vertex name
     * 
     * @param key vertex index
     * @return the vertex index associated with the provided vertex name if it
     * exists in the graph, otherwise -1
     */
    int index(String key);
    
    /**
     * Gets the vertex name associated with the provided vertex index
     * 
     * @param index vertex index
     * @return the vertex name associated with the provided vertex index if it
     * exists in the graph, otherwise null
     */
    String name(int index);
    
    /**
     * Gets the underlying Graph representation
     * 
     * @return the underlying Graph representation
     */
    Graph getGraph();
}

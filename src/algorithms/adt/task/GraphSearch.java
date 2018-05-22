 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package algorithms.adt.task;

/**
 * An interface to find vertices in the graph connected to a source vertex.
 * It finds paths from source vertex to other vertices in the graph.
 */
public interface GraphSearch {
    /**
     * Check if there is a path from source vertex to v vertex
     * 
     * @param v a vertex in the graph
     * @return true if, and only if, there is a path from source vertex to v
     * vertex
     */
    boolean hasPathTo(int v);
    
    /**
     * Search a path from source vertex to v vertex
     * 
     * @param v a vertex in the graph
     * @return a path from source vertex to v vertex, otherwise null
     */
    Iterable<Integer> pathTo(int v);
}

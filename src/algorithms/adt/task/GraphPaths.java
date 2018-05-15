/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.task;

/**
 * An interface to find paths from source vertex to other vertices in the graph.
 * Decouples the implementation from the graph representation, so that, clients
 * can create objects of this type to perform the task.
 */
public interface GraphPaths {
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

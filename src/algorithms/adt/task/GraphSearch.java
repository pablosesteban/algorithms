 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package algorithms.adt.task;

/**
 * An interface to find vertices in the graph connected to a source vertex.
 * Decouples the implementation from the graph representation, so that, clients
 * can create objects of this type to perform the task.
 */
public interface GraphSearch {
    /**
     * Checks if vertex v is connected to a vertex source
     * 
     * @param v a vertex in the graph
     * @return true if, and only if, v vertex is connected to vertex source
     */
    boolean marked(int v);
    
    /**
     * Counts how many vertices are connected to vertex source.
     * 
     * @return the vertices connected to vertex source
     */
    int count();
}

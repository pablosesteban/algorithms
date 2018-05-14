 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package algorithms.adt.task;

/**
 * 
 */
public interface GraphSearch {
    /**
     * Checks if vertex v is connected to a vertex source provided by the
     * constructor
     * 
     * @param v a vertex in the graph
     * @return true if, and only if, v is connected to source
     */
    boolean marked(int v);
    
    /**
     * Counts how many vertices are connected to vertex source provided by the
     * constructor
     * 
     * @return the vertices connected to source
     */
    int count();
}

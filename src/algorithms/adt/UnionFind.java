/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt;

/**
 * 
 */
public interface UnionFind {
    void union(int p, int q);
    
    int find(int p);
    
    boolean areConnected(int p, int q);
    
    int count();
}

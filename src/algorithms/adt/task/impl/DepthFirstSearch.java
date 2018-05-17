/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.task.impl;

import algorithms.adt.Graph;
import algorithms.adt.impl.UndirectedGraph;
import algorithms.adt.task.GraphSearch;
import java.util.Arrays;

/**
 * A fundamental recursive implementation that follows the graph’s edges to find
 * the vertices connected to the source vertex provided to the constructor.
 * It maintains an array of boolean values to mark all of the vertices that are 
 * connected to the source.
 * Depth First Search (DFS) approach to search a graph follows paths from the
 * source vertex to other vertices in the graph, marking each vertex encountered
 * by invoking a recursive method that visits vertices and mark them as having
 * been visited and visit (recursively) all the vertices that are adjacent to it
 * and that have not yet been marked.
 * DFS marks all the vertices connected to a given source in time proportional
 * to the sum of their degrees because marking ensures that each vertex is
 * visited once (taking time proportional to its degree to check marks).
 */
public class DepthFirstSearch implements GraphSearch {
    private boolean[] marked;
    private int count;
    
    /**
     * Find the vertices in the graph that are connected to the source vertex
     * 
     * @param g the graph
     * @param source a vertex in the graph
     */
    public DepthFirstSearch(Graph g, int source) {
        marked = new boolean[g.size()];
        
        dfs(g, source);
    }
    
    @Override
    public boolean marked(int v) {
        return marked[v];
    }

    @Override
    public int count() {
        return count;
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        
        count++;
        
        for (int w : g.getAdjacentVertices(v)) {
            if (!marked(w)) {
                dfs(g, w);
            }
        }
    }

    @Override
    public String toString() {
        return "DepthFirstSearch{" + "marked: " + Arrays.toString(marked) + ", count: " + count + '}';
    }
    
    public static void main(String[] args) {
        Graph g = new UndirectedGraph(13);
        
        g.addEdge(0, 5);
        g.addEdge(4, 3);
        g.addEdge(0, 1);
        g.addEdge(9, 12);
        g.addEdge(6, 4);
        g.addEdge(5, 4);
        g.addEdge(0, 2);
        g.addEdge(11, 12);
        g.addEdge(9, 10);
        g.addEdge(0, 6);
        g.addEdge(7, 8);
        g.addEdge(9, 11);
        g.addEdge(5, 3);
        
        System.out.println(g);
        
        DepthFirstSearch dfs = new DepthFirstSearch(g, 0);
        System.out.println(dfs);
        
        System.out.println("Is a connected graph? " + (dfs.count() == g.size()));
    }
}

/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.task.impl;

import algorithms.adt.Graph;
import algorithms.adt.Stack;
import algorithms.adt.impl.LinkedStack;
import algorithms.adt.impl.UndirectedGraph;
import algorithms.adt.task.GraphPaths;
import java.util.Arrays;

/**
 * A DFS-based implementation that extends the DFS by adding an array of integer
 * values (edgeTo) that gives a way to find a path back to source vertex provided to the
 * constructor for every vertex connected to it in the graph. It remembers a
 * path from each vertex to the source, such that edgeTo[w] = v means that v-w
 * was the edge used to access w for the first time. The array is a parent-link
 * representation of a tree rooted at source vertex that contains all the
 * vertices connected to it.
 * The implementation doesn't care about the path and choose it based on the
 * order of the adjacency list of each vertex if there are more than one.
 * DFS provides a way to search a path from a given source vertex to any marked
 * vertex in time proportional its length.
 */
public class DepthFirstPaths implements GraphPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int source;
    
    /**
     * Computes paths from source vertex to each vertex connected to it in the
     * graph
     * 
     * @param g a graph
     * @param source a vertex in the graph
     */
    public DepthFirstPaths(Graph g, int source) {
        this.source = source;
        
        marked = new boolean[g.size()];
        
        edgeTo = new int[g.size()];
        
        dfs(g, source);
    }
    
    @Override
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    @Override
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        
        Stack<Integer> path = new LinkedStack<>();
        
        for (int i = v; i != source; i = edgeTo[i]) {
            path.push(i);
        }
        path.push(source);
        
        return path;
    }

    @Override
    public String toString() {
        return "DepthFirstPaths{\nsource: " + source +"\nmarked: " + Arrays.toString(marked) + ",\nedgeTo: " + Arrays.toString(edgeTo) + "\n}";
    }
    
    private void dfs(Graph g, int v) {
        marked[v] = true;
        
        for (Integer w : g.getAdjacentVertices(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                
                dfs(g, w);
            }
        }
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
        
        DepthFirstPaths dfp = new DepthFirstPaths(g, 5);
        System.out.println(dfp);
        
        System.out.println("hasPathTo 2: " + dfp.hasPathTo(2));
        System.out.println("pathTo 2: " + dfp.pathTo(2));
        
        System.out.println("hasPathTo 4: " + dfp.hasPathTo(4));
        System.out.println("pathTo 4: " + dfp.pathTo(4));
        
        System.out.println("hasPathTo 3: " + dfp.hasPathTo(3));
        System.out.println("pathTo 3: " + dfp.pathTo(3));
        
        System.out.println("hasPathTo 9: " + dfp.hasPathTo(9));
        System.out.println("pathTo 9: " + dfp.pathTo(9));
    }
}

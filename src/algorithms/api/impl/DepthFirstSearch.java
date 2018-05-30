 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package algorithms.api.impl;

import algorithms.adt.Graph;
import algorithms.adt.Stack;
import algorithms.adt.impl.LinkedStack;
import algorithms.adt.impl.UndirectedGraph;
import algorithms.api.GraphSearch;
import java.util.Arrays;

/**
 * Depth First Search (DFS) is a recursive implementation that follows the
 * graph’s edges to find all the vertices connected to a source vertex giving
 * from each one a path to source.
 * This approach to search a graph follows paths from the source vertex to other
 * vertices in the graph, marking each vertex encountered by invoking a
 * recursive method that visits vertices and mark them as having been visited
 * and visit (recursively) all the vertices that are adjacent to it and that
 * have not yet been marked.
 * Marks all the vertices connected to a given source in time proportional to
 * the sum of their degrees because marking ensures that each vertex is visited
 * once (taking time proportional to its degree to check marks).
 * It maintains an array of boolean values to mark all of the vertices that are
 * connected to the source and another array of integer values that gives a way
 * to find a path back to source vertex provided to the constructor for every
 * vertex connected to it in the graph.
 * It remembers a path from each vertex to the source, such that integer[w] = v
 * means that v-w was the edge used to access w for the first time.
 * The implementation choose the path based on the order of the adjacency list
 * of each vertex. The path depend not just on the graph, but also on the
 * representation and the nature of the recursion (a pushdown stack (LIFO) that
 * is managed by the system to support the recursive search method), such that,
 * it choose of the edges yet to be explored, the one that was most recently
 * encountered (explores the vertices farthest from the source vertex first).
 * Provides a way to search a path from a given source vertex to any marked
 * vertex in time proportional its length, as the array of integer values is a
 * parent-link representation of a tree rooted at source vertex that contains
 * all the vertices connected to it, defining the paths (not the shortest) from
 * source to every vertex that is connected to it.
 * DFS explores the graph by looking for new vertices far away from the start
 * point, taking closer vertices only when dead ends are encountered, so DFS
 * paths tend to be long and winding.
 */
public class DepthFirstSearch implements GraphSearch {
    private boolean[] marked;
    private int[] edgeTo;
    private int source;
    private int count;
    
    /**
     * Computes the paths from source vertex to each vertex connected to it in
     * the graph
     *
     * @param g a graph
     * @param source a vertex in the graph to search paths
     */
    public DepthFirstSearch(Graph g, int source) {
        marked = new boolean[g.size()];
        edgeTo = new int[g.size()];
        this.source = source;
        
        dfs(g, source);
    }
    
    @Override
    public boolean isConnected(int v) {
        return marked[v];
    }
    
    @Override
    public Iterable<Integer> pathTo(int v) {
        if (!isConnected(v)) {
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
    public int getCount() {
        return count;
    }
    
    // the stack is implicitly managed by the recursion
    private void dfs(Graph g, int v) {
        marked[v] = true;
        
        for (Integer w : g.getAdjacentVertices(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                
                dfs(g, w);
            }
        }
        
        count++;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(getClass().getSimpleName());
        sb.append(" {");
        sb.append("\nsource: ");
        sb.append(source);
        sb.append("\nmarked: ");
        sb.append(Arrays.toString(marked));
        sb.append(",\nedgeTo: ");
        sb.append(Arrays.toString(edgeTo));
        sb.append("\n}");
        
        return  sb.toString();
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
        
        DepthFirstSearch dfs = new DepthFirstSearch(g, 5);
        System.out.println(dfs);
        
        System.out.println("Number of connected vertices: " + dfs.getCount());
        
        System.out.println("hasPathTo 2: " + dfs.isConnected(2));
        System.out.println("pathTo 2: " + dfs.pathTo(2));
        
        System.out.println("hasPathTo 4: " + dfs.isConnected(4));
        System.out.println("pathTo 4: " + dfs.pathTo(4));
        
        System.out.println("hasPathTo 3: " + dfs.isConnected(3));
        System.out.println("pathTo 3: " + dfs.pathTo(3));
        
        System.out.println("hasPathTo 9: " + dfs.isConnected(9));
        System.out.println("pathTo 9: " + dfs.pathTo(9));
    }
}

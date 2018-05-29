/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.api.impl;

import algorithms.adt.Graph;
import algorithms.adt.Queue;
import algorithms.adt.Stack;
import algorithms.adt.impl.LinkedQueue;
import algorithms.adt.impl.LinkedStack;
import algorithms.adt.impl.UndirectedGraph;
import algorithms.api.GraphSearch;
import java.util.Arrays;

/**
 * Breadth First Search (BFS) is a non-recursive implementation that follows the
 * graph’s edges to find all the vertices connected to a source vertex giving
 * from each one the shortest path to source.
 * This approach to search a graph follows paths from the source vertex to other
 * vertices in the graph, marking each vertex encountered by maintaining a queue
 * with all vertices to visit, which have not yet been marked, and mark them as
 * having been visited and removing them from the queue.
 * Marks all the vertices connected to a given source in time proportional to
 * the sum of their degrees because marking ensures that each vertex is visited
 * once (taking time proportional to its degree to check marks).
 * It maintains an array of boolean values to mark all of the vertices that are
 * connected to the source and another array of integer values that gives a way
 * to find a path back to source vertex provided to the constructor for every
 * vertex connected to it in the graph.
 * It uses a Queue (FIFO), such that, it choose of the edges yet to be explored,
 * the one that was least recently encountered (explores the vertices closest
 * from the source vertex first) to find paths in a graph with the fewest number
 * of edges from the source to each connected vertex.
 * Provides a way to search a path from a given source vertex to any marked
 * vertex in time proportional its length, as the array of integer values is a
 * parent-link representation of a tree rooted at source vertex that contains
 * all the vertices connected to it, defining the shortest paths from source to
 * every vertex that is connected to it.
 * BFS completely covers the area close to the starting point, moving farther
 * away only when everything nearby has been examined, so BFS paths are short
 * and direct.
 */
public class BreadthFirstSearch implements GraphSearch {
    private boolean[] marked;
    private int[] edgeTo;
    private int source;
    private int count;

    /**
     * Computes the shortest paths from source vertex to each vertex connected
     * to it in the graph
     *
     * @param g a graph
     * @param source a vertex in the graph to search paths
     */
    public BreadthFirstSearch(Graph g, int source) {
        marked = new boolean[g.size()];
        edgeTo = new int[g.size()];
        this.source = source;
        
        bfs(g, source);
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
        
        for(int w = v; w != source; w = edgeTo[w]) {
            path.push(w);
        }
        path.push(source);
        
        return path;
    }

    @Override
    public int getCount() {
        return count;
    }
    
    // the queue is explicitly managed by the code
    private void bfs(Graph g, int source) {
        marked[source] = true;
        
        count++;
        
        Queue<Integer> queue = new LinkedQueue<>();
        queue.enqueue(source);
        
        while(!queue.isEmpty()) {
            int v = queue.dequeue();
            
            for (Integer adjacentVertex : g.getAdjacentVertices(v)) {
                if (!marked[adjacentVertex]) {
                    marked[adjacentVertex] = true;
                    
                    queue.enqueue(adjacentVertex);
                
                    edgeTo[adjacentVertex] = v;
                    
                    count++;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(getClass().getSimpleName());
        sb.append(" {");
        sb.append("\n source: ");
        sb.append(source);
        sb.append(",\n marked: ");
        sb.append(Arrays.toString(marked));
        sb.append(",\n edgeTo: ");
        sb.append(Arrays.toString(edgeTo));
        sb.append("\n}");
        
        return sb.toString();
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
        
        BreadthFirstSearch bfp = new BreadthFirstSearch(g, 5);
        System.out.println(bfp);
        
        System.out.println("Number of connected vertices: " + bfp.getCount());
        
        System.out.println("hasPathTo 2: " + bfp.isConnected(2));
        System.out.println("pathTo 2: " + bfp.pathTo(2));
        
        System.out.println("hasPathTo 4: " + bfp.isConnected(4));
        System.out.println("pathTo 4: " + bfp.pathTo(4));
        
        System.out.println("hasPathTo 3: " + bfp.isConnected(3));
        System.out.println("pathTo 3: " + bfp.pathTo(3));
        
        System.out.println("hasPathTo 9: " + bfp.isConnected(9));
        System.out.println("pathTo 9: " + bfp.pathTo(9));
    }
}

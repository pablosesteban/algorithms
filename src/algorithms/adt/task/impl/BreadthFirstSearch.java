/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.task.impl;

import algorithms.adt.Graph;
import algorithms.adt.Queue;
import algorithms.adt.Stack;
import algorithms.adt.impl.LinkedQueue;
import algorithms.adt.impl.LinkedStack;
import algorithms.adt.impl.UndirectedGraph;
import algorithms.adt.task.GraphSearch;
import java.util.Arrays;

/**
 * Breadth First Search (BFS) is a
 */
public class BreadthFirstSearch implements GraphSearch {
    private boolean[] marked;
    private int[] edgeTo;
    private int source;

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
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    @Override
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        
        Stack<Integer> path = new LinkedStack<>();
        
        for(int w = v; w != source; w = edgeTo[w]) {
            path.push(w);
        }
        path.push(source);
        
        return path;
    }
    
    private void bfs(Graph g, int source) {
        marked[source] = true;
        
        Queue<Integer> queue = new LinkedQueue<>();
        queue.enqueue(source);
        
        while(!queue.isEmpty()) {
            int v = queue.dequeue();
            
            for (Integer adjacentVertex : g.getAdjacentVertices(v)) {
                if (!marked[adjacentVertex]) {
                    marked[adjacentVertex] = true;
                    
                    queue.enqueue(adjacentVertex);
                
                    edgeTo[adjacentVertex] = v;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(getClass().getSimpleName());
        sb.append(" {");
        sb.append("\nsource: ");
        sb.append(source);
        sb.append(",\nmarked: ");
        sb.append(Arrays.toString(marked));
        sb.append(",\nedgeTo: ");
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
        
        System.out.println("hasPathTo 2: " + bfp.hasPathTo(2));
        System.out.println("pathTo 2: " + bfp.pathTo(2));
        
        System.out.println("hasPathTo 4: " + bfp.hasPathTo(4));
        System.out.println("pathTo 4: " + bfp.pathTo(4));
        
        System.out.println("hasPathTo 3: " + bfp.hasPathTo(3));
        System.out.println("pathTo 3: " + bfp.pathTo(3));
        
        System.out.println("hasPathTo 9: " + bfp.hasPathTo(9));
        System.out.println("pathTo 9: " + bfp.pathTo(9));
    }
}

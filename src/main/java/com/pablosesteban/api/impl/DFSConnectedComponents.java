/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import com.pablosesteban.adt.Graph;
import com.pablosesteban.adt.impl.UndirectedGraph;
import com.pablosesteban.api.GraphConnectedComponents;
import java.util.Arrays;

/**
 * A DFS implementation that maintains a vertex-indexed array connectedTo[] that
 * associates the same integer value to every vertex in each component.
 * The implementation pre-process the Graph using DFS and waste time and space
 * proportional to the sum of the number of vertices and edges providing because
 * of that a constant-time guarantee connectivity queries in the Graph.
 */
public class DFSConnectedComponents implements GraphConnectedComponents {
    private boolean[] marked;
    private int[] connectedTo;
    private int count;
    
    /**
     * Finds an unmarked vertex and uses the recursive DFS to mark and identify 
     * all the vertices connected to it, continuing until all vertices have been
     * marked and identified.
     * 
     * @param g the Graph
     */
    public DFSConnectedComponents(Graph g) {
        marked = new boolean[g.size()];
        connectedTo = new int[g.size()];
        
        for (int i = 0; i < g.size(); i++) {
            if (!marked[i]) {
                dfs(g, i);
                
                count++;
            }
        }
    }
    
    @Override
    public boolean areConnected(int v, int w) {
        return connectedTo[v] == connectedTo[w];
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int getConnectedComponent(int v) {
        return connectedTo[v];
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" + "\nmarked: " + Arrays.toString(marked) + ",\nconnectedTo: " + Arrays.toString(connectedTo) + ",\ncount: " + count + "\n}";
    }
    
    private void dfs(Graph g, int v) {
        marked[v] = true;
        
        connectedTo[v] = count;
        
        for (Integer adjacentVertex : g.getAdjacentVertices(v)) {
            if (!marked[adjacentVertex]) {
                dfs(g, adjacentVertex);
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
        
        GraphConnectedComponents gcc = new DFSConnectedComponents(g);
        System.out.println(gcc);
    }
}

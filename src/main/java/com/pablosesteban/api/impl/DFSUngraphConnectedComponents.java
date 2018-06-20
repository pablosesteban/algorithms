/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.Arrays;

import com.pablosesteban.adt.Graph;
import com.pablosesteban.adt.impl.UndirectedGraph;
import com.pablosesteban.api.GraphConnectedComponents;

/**
 * A DFS implementation that maintains a vertex-indexed array connectedTo[] that
 * associates the same integer value to every vertex in each component.
 * The implementation pre-process the graph using DFS and waste time and space
 * proportional to the sum of the number of vertices and edges providing because
 * of that a constant-time guarantee connectivity queries in the graph.
 */
public class DFSUngraphConnectedComponents implements GraphConnectedComponents {
    private boolean[] marked;
    private int[] connectedTo;
    private int count;
    
    /**
     * Finds an unmarked vertex and uses the recursive DFS to mark and identify 
     * all the vertices connected to it, continuing until all vertices have been
     * marked and identified.
     * Preprocess the graph building the data structures that can efficiently
     * support client operations.
     * 
     * @param ug the undirected graph
     */
    public DFSUngraphConnectedComponents(UndirectedGraph ug) {
        marked = new boolean[ug.size()];
        connectedTo = new int[ug.size()];
        
        for (int i = 0; i < ug.size(); i++) {
            if (!marked[i]) {
                dfs(ug, i);
                
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
        
        if (g.getAdjacentVertices(v) != null) {
        	for (Integer adjacentVertex : g.getAdjacentVertices(v)) {
        		if (!marked[adjacentVertex]) {
        			dfs(g, adjacentVertex);
        		}
        	}
        }
    }
    
    public static void main(String[] args) {
    	UndirectedGraph ug = new UndirectedGraph(13);
        
        ug.addEdge(0, 5);
        ug.addEdge(4, 3);
        ug.addEdge(0, 1);
        ug.addEdge(9, 12);
        ug.addEdge(6, 4);
        ug.addEdge(5, 4);
        ug.addEdge(0, 2);
        ug.addEdge(11, 12);
        ug.addEdge(9, 10);
        ug.addEdge(0, 6);
        ug.addEdge(7, 8);
        ug.addEdge(9, 11);
        ug.addEdge(5, 3);
        
        System.out.println(ug);
        
        GraphConnectedComponents ugcc = new DFSUngraphConnectedComponents(ug);
        System.out.println(ugcc);
    }
}

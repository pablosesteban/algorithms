/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.Arrays;

import com.pablosesteban.adt.impl.DirectedGraph;
import com.pablosesteban.api.DigraphOrder.Order;
import com.pablosesteban.api.GraphConnectedComponents;

/**
 * A DFS implementation that maintains a vertex-indexed array connectedTo[] that
 * associates the same integer value to every vertex in each component.
 * The implementation is based on the Kosaraju’s algorithm:
 * <ul>
 * <li>Given a digraph, compute the reverse postorder of its reverse</li>
 * <li>Run standard DFS on the digraph, but consider the unmarked vertices in the
 * orderjust computed instead of the standard numerical order</li>
 * <li>All vertices reached on a call to the recursive dfs() are in the same
 * connected component</li>
 * Kosaraju’s algorithm uses pre-processing time and space proportional to the number
 * of vertices plus the number of edges to support constant-time guarantee
 * connectivity operations in the graph.
 * In a digraph, a vertex w is reachable from a vertex v if there is a directed
 * path from v to w, but there may or may not be a directed path back to v from w.
 * </ul>
 */
public class DigraphConnectedComponents implements GraphConnectedComponents {
	private boolean[] marked;
    private int[] connectedTo;
    private int count;
	
	public DigraphConnectedComponents(DirectedGraph dg) {
		marked = new boolean[dg.size()];
		connectedTo = new int[dg.size()];
		
		DFSOrder dfso = new DFSOrder(dg.reverse());
		
		for (Integer v : dfso.order(Order.REVERSE_POST_ORDER)) {
			if (!marked[v]) {
				dfs(v, dg);
				
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

	private void dfs(int v, DirectedGraph dg) {
		marked[v] = true;
		connectedTo[v] = count;
		
		if (dg.getAdjacentVertices(v) != null) {
			for (Integer w : dg.getAdjacentVertices(v)) {
				if (!marked[w]) {
					dfs(w, dg);
				}
			}
		}
	}
	
	public static void main(String[] args) {
    	DirectedGraph dg = new DirectedGraph(13);
        
        dg.addEdge(0, 1);
        dg.addEdge(0, 5);
        dg.addEdge(2, 0);
        dg.addEdge(2, 3);
        dg.addEdge(3, 2);
        dg.addEdge(3, 5);
        dg.addEdge(5, 4);
        dg.addEdge(4, 2);
        dg.addEdge(4, 3);
        dg.addEdge(6, 0);
        dg.addEdge(6, 4);
        dg.addEdge(6, 9);
        dg.addEdge(7, 6);
        dg.addEdge(7, 8);
        dg.addEdge(8, 7);
        dg.addEdge(8, 9);
        dg.addEdge(9, 11);
        dg.addEdge(9, 10);
        dg.addEdge(11, 12);
        dg.addEdge(11, 4);
        dg.addEdge(10, 12);
        dg.addEdge(12, 9);
        
        System.out.println(dg);
        
        GraphConnectedComponents gugcc = new DigraphConnectedComponents(dg);
        System.out.println(gugcc);
    }
}

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
 * In a digraph, a vertex w is reachable from a vertex v if there is a directed
 * path from v to w, but there may or may not be a directed path back to v from w.
 * Two vertices v and w are strongly connected if they are mutually reachable, i.e.
 * if and only if there exists a directed cycle that contains them both, i.e. if
 * there is a directed path from v to w and a different one directed path from w to v.
 * The implementation is based on the Kosaraju’s algorithm:
 * <ul>
 * <li>Given a digraph, compute the reverse postorder of its reverse</li>
 * <li>Run standard DFS on the digraph, but consider the unmarked vertices in the
 * order just computed instead of the standard numerical order</li>
 * </ul>
 * In the Kosaraju’s algorithm, all vertices reached on a call to the recursive DFS
 * are in the same connected component and are strongly connected.
 * Kosaraju’s algorithm uses pre-processing time and space proportional to the number
 * of vertices (V) plus the number of edges (E) to support constant-time guarantee
 * connectivity operations in the graph. Each of these three steps takes time
 * proportional to V+E and the reverse copy of the digraph uses space proportional
 * V+E.
 */
public class DigraphCC implements GraphConnectedComponents {
	private boolean[] marked;
    private int[] connectedTo;
    private int count;
	
	public DigraphCC(DirectedGraph dg) {
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
        
        GraphConnectedComponents gugcc = new DigraphCC(dg);
        System.out.println(gugcc);
    }
}

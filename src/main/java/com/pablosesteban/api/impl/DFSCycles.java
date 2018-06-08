/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pablosesteban.adt.Graph;
import com.pablosesteban.adt.Stack;
import com.pablosesteban.adt.impl.DirectedGraph;
import com.pablosesteban.adt.impl.LinkedStack;
import com.pablosesteban.api.GraphCycles;

/**
 * A DFS implementation based on the fact that the recursive call stack
 * maintained by the system represents the "current" path under consideration.
 * If it ever finds an edge v-w to a vertex w that is on that stack, it has found
 * a cycle, since the stack is evidence of a path from w to v, and the edge v-w
 * completes the cycle.
 */
public class DFSCycles implements GraphCycles {
	private boolean[] marked;
	private int[] edgeTo;
	private Graph g;
	private List<Stack<Integer>> cycles;
	private Stack<Integer> currentConnectedComponent;

	public DFSCycles(Graph g) {
		marked = new boolean[g.size()];
		edgeTo = new int[g.size()];
		this.g = g;
		cycles = new ArrayList<>();

		for (int v = 0; v < g.size(); v++) {
			if (!marked[v]) {
				currentConnectedComponent = new LinkedStack<>();
				
				dfs(g, v, v);
			}
		}
	}

	@Override
	public boolean hasCycle() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int getNumberOfCycles() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<Iterable<Integer>> getCycles() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getClass().getSimpleName());
		sb.append(" {\n");
		sb.append("marked: ");
		sb.append(Arrays.toString(marked));
		sb.append(",\nedgeTo: ");
		sb.append(Arrays.toString(edgeTo));
		sb.append(",\ngraph: ");
		sb.append(g);
		sb.append(",\ncycles: ");
		sb.append(cycles);
		sb.append("\n}");

		return sb.toString();
	}

	private void dfs(Graph g, int current, int source) {
		marked[current] = true;
		
		currentConnectedComponent.push(current);

		if (g.getAdjacentVertices(current) != null) {
			for (Integer adjacentVertex : g.getAdjacentVertices(current)) {
				if (!marked[adjacentVertex]) {
					edgeTo[adjacentVertex] = current;

					dfs(g, adjacentVertex, source);
				}
			}
		}
	}

	public static void main(String[] args) {
		Graph g = new DirectedGraph(13);

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
		g.addEdge(3, 0);

		DFSCycles dfsc = new DFSCycles(g);

		System.out.println(dfsc);
	}
}

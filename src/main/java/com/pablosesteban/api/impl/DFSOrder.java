/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.Arrays;

import com.pablosesteban.adt.Graph;
import com.pablosesteban.adt.Queue;
import com.pablosesteban.adt.Stack;
import com.pablosesteban.adt.impl.DirectedGraph;
import com.pablosesteban.adt.impl.LinkedQueue;
import com.pablosesteban.adt.impl.LinkedStack;
import com.pablosesteban.api.DigraphOrder;

/**
 * A DFS implementation based on the idea that DFS visits each vertex exactly once.
 * We save the current visited vertex in a data structure and then iterate through
 * that data structure in order determined by the nature of the data structure and 
 * by whether we do the save before or after the recursive calls.
 */
public class DFSOrder implements DigraphOrder {
	private boolean[] marked;
	private Graph g;

	private Queue<Integer> preOrder;
	private Queue<Integer> postOrder;
	private Stack<Integer> reversePostOrder;

	public DFSOrder(DirectedGraph dg) {
		marked = new boolean[dg.size()];
		this.g = dg;
		
		preOrder = new LinkedQueue<>();
		postOrder = new LinkedQueue<>();
		reversePostOrder = new LinkedStack<>();

		for (int v = 0; v < dg.size(); v++) {
			if (!marked[v]) {
				dfs(dg, v);
			}
		}
	}

	@Override
	public Iterable<Integer> order(Order order) {
		switch (order) {
		case PRE_ORDER:
			return preOrder;
		case POST_ORDER:
			return postOrder;
		case REVERSE_POST_ORDER:
			return reversePostOrder;
		default:
			throw new IllegalArgumentException("Order not allowed: " + order);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getClass().getSimpleName());
		sb.append(" {\n");
		sb.append("marked: ");
		sb.append(Arrays.toString(marked));
		sb.append(",\ngraph: ");
		sb.append(g);
		sb.append(",\npreOrder: ");
		sb.append(preOrder);
		sb.append(",\npostOrder: ");
		sb.append(postOrder);
		sb.append(",\nreversePostOrder: ");
		sb.append(reversePostOrder);
		sb.append("\n}");

		return sb.toString();
	}

	private void dfs(Graph g, int current) {
		marked[current] = true;
		
		preOrder.enqueue(current);

		if (g.getAdjacentVertices(current) != null) {
			for (Integer adjacentVertex : g.getAdjacentVertices(current)) {
				if (!marked[adjacentVertex]) {
					dfs(g, adjacentVertex);
				}
			}
		}
		
		postOrder.enqueue(current);
		reversePostOrder.push(current);
	}

	public static void main(String[] args) {
		DirectedGraph dg = new DirectedGraph(13);
		dg.addEdge(0, 5);
		dg.addEdge(0, 1);
		dg.addEdge(0, 6);
		dg.addEdge(2, 0);
		dg.addEdge(2, 3);
		dg.addEdge(3, 5);
		dg.addEdge(5, 4);
		dg.addEdge(6, 4);
		dg.addEdge(6, 9);
		dg.addEdge(7, 6);
		dg.addEdge(8, 7);
		dg.addEdge(9, 11);
		dg.addEdge(9, 12);
		dg.addEdge(9, 10);
		dg.addEdge(11, 12);
		
		DFSOrder dfso = new DFSOrder(dg);
		System.out.println(dfso);
	}
}

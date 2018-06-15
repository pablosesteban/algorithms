/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import com.pablosesteban.adt.impl.DirectedGraph;
import com.pablosesteban.api.DigraphCycle;
import com.pablosesteban.api.DigraphOrder;
import com.pablosesteban.api.DigraphOrder.Order;
import com.pablosesteban.api.DigraphTopologicalOrder;

/**
 * A DFS implementation which based on DFSCycle and DFSOrder implementations.
 * The implementation makes two DFS passes over the digraph in order to compute the
 * topological order of its vertices, one to ensure that the graph has no directed
 * cycles and the other to do the ordering, so it can topologically sort a DAG in
 * time proportional to the number of vertices plus the number of edges.
 * In this implementation the reverse postorder is a topological sort. Consider any
 * edge v->w, dfs(w) is done before dfs(v), if dfs(w) has been called and has not
 * yet returned when dfs(v) is called there will be a cycle, so w appears after v in
 * reverse postorder, thus each edge v->w points from a vertex earlier in the order
 * to a vertex later in the order.
 */
public class DFSTopologicalOrder implements DigraphTopologicalOrder {
	private Iterable<Integer> order;
	
	public DFSTopologicalOrder(DirectedGraph dg) {
		DigraphCycle dfsCycle = new DFSCycle(dg);
		
		if (!dfsCycle.hasCycle()) {
			DigraphOrder dfsOrder = new DFSOrder(dg);
			
			order = dfsOrder.order(Order.REVERSE_POST_ORDER);
		}
	}
	
	@Override
	public boolean isDag() {
		return order == null;
	}

	@Override
	public Iterable<Integer> topologicalOrder() {
		return order;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" {\n");
		sb.append("order: ");
		sb.append(order);
		sb.append("\n}");
		
		return sb.toString();
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
		System.out.println(dg);
		
		DigraphTopologicalOrder dgto = new DFSTopologicalOrder(dg);
		System.out.println(dgto);
		System.out.println("Is a DAG: " + dgto.isDag());
		System.out.println("Topological order: " + dgto.topologicalOrder());
	}
}

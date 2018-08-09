package com.pablosesteban.api.impl;

import java.io.IOException;
import java.util.Arrays;

import com.pablosesteban.adt.Queue;
import com.pablosesteban.adt.Stack;
import com.pablosesteban.adt.impl.DirectedWeightedGraph;
import com.pablosesteban.adt.impl.Edge;
import com.pablosesteban.adt.impl.LinkedQueue;
import com.pablosesteban.adt.impl.LinkedStack;
import com.pablosesteban.api.WeightedDigraphShortestPaths;

/**
 * A Shortest Paths implementation based on a Queue based Bellman-Ford's algorithm.
 * Solves the single source shortest paths problem from a given source vertex s for
 * any edge-weighted digraph with v vertices and NO NEGATIVE CYCLES reachable from
 * s, or finds a negative cycle reachable from s.
 * Takes time proportional to number of edges (E) * number of vertices (V) and extra
 * space proportional to V in the worst case, i.e the algorithm mimics the general
 * algorithm and relaxes all E edges in each of V passes.
 * If there is no negative cycle reachable from s, the algorithm terminates after
 * relaxations corresponding to the (V–1)st pass (since all shortest paths have fewer
 * than V–1 edges).
 * If there does exist a negative cycle reachable from s, the queue never empties, so
 * the algorithm never ends. If the queue is non empty after the Vth pass through all
 * the edges the subgraph of edges in our edgeTo[] array must contain a negative cycle.
 */
public class BellmanFordQueueSP implements WeightedDigraphShortestPaths {
	private double[] weightTo;
	private Edge[] edgeTo;
	private Queue<Integer> queue;
	private boolean[] onQueue;
	
	public BellmanFordQueueSP(DirectedWeightedGraph dwg, int source) {
		weightTo = new double[dwg.size()];
		edgeTo = new Edge[dwg.size()];
		// a vertex-indexed boolean array that indicates which vertices are on the queue, to avoid duplicates
		onQueue = new boolean[dwg.size()];
		// a queue of vertices to be relaxed
		queue = new LinkedQueue<>();
		
		for (int v = 0; v < weightTo.length; v++) {
			weightTo[v] = Double.POSITIVE_INFINITY;
		}
		
		weightTo[source] = 0.0;
		
		queue.enqueue(source);
		while (!queue.isEmpty()) {
			Integer vertex = queue.dequeue();
			
			onQueue[vertex] = true;
			
			relaxEdges(dwg, vertex);
		}
	}
	
	@Override
	public double weightTo(int v) {
		return weightTo[v];
	}

	@Override
	public boolean hasPathTo(int v) {
		return weightTo[v] != Double.POSITIVE_INFINITY;
	}

	@Override
	public Iterable<Edge> pathTo(int v) {
		if (!hasPathTo(v)) {
			return null;
		}
		
		Stack<Edge> path = new LinkedStack<>();
		
		Edge e = edgeTo[v];
		while (e != null) {
			path.push(e);
			
			e = edgeTo[e.getFrom()];
		}
		
		return path;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " {\nweightTo: " + Arrays.toString(weightTo) + ",\nedgeTo: " + Arrays.toString(edgeTo) + "\n}";
	}

	/**
	 * To relax an edge v->w means to test whether the best known way from source to w is to go from source to v,
	 * then take the edge from v to w, and, if so, update our data structures to indicate that to be the case.
	 * Two possible outcomes of an vertex relaxation operation:
	 * <li>The edge is ineligible and no changes are made on the data structures.</li>
	 * <li>The edge v->w leads to a shorter path to w and we update the data structures (which might render some
	 * other edges ineligible and might create some new eligible edges).</li>
	 * 
	 * @param dwg a weighted digraph
	 * @param v a vertex in the graph
	 */
	private void relaxEdges(DirectedWeightedGraph dwg, int v) {
		if (dwg.getIncidentEdges(v) != null) {
			for (Edge e : dwg.getIncidentEdges(v)) {
				double newWeight = e.getWeight() + weightTo[e.getFrom()];
				
				// every vertex whose edgeTo[] and weightTo[] values change in some pass is processed in the next pass
				if (newWeight < weightTo[e.getTo()]) {
					weightTo[e.getTo()] = newWeight;
					
					edgeTo[e.getTo()] = e;
					
					if (!onQueue[e.getTo()]) {
						queue.enqueue(e.getTo());
						
						onQueue[e.getTo()] = true;
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		DirectedWeightedGraph dwg = new DirectedWeightedGraph("weighted_digraph_tiny.txt");
		
		BellmanFordQueueSP bfq = new BellmanFordQueueSP(dwg, 1);
		System.out.println(bfq);
		
		System.out.println("pathTo 5: " + bfq.pathTo(5));
		System.out.println("pathTo 6: " + bfq.pathTo(6));
		
		DirectedWeightedGraph dwgNegativeEdges = new DirectedWeightedGraph("weighted_digraph_tiny_negative_edges.txt");
		
		BellmanFordQueueSP bfq2 = new BellmanFordQueueSP(dwgNegativeEdges, 0);
		System.out.println(bfq2);
		
		System.out.println("pathTo 5: " + bfq2.pathTo(5));
		System.out.println("pathTo 6: " + bfq2.pathTo(6));
		
		DirectedWeightedGraph dwgNegativeCycle = new DirectedWeightedGraph("weighted_digraph_tiny_negative_cycle.txt");
		
		BellmanFordQueueSP bfq3 = new BellmanFordQueueSP(dwgNegativeCycle, 0);
		System.out.println(bfq3);
		
		System.out.println("pathTo 5: " + bfq3.pathTo(5));
		System.out.println("pathTo 6: " + bfq3.pathTo(6));
	}
}

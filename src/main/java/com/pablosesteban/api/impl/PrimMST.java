package com.pablosesteban.api.impl;

import java.io.IOException;
import java.util.Arrays;

import com.pablosesteban.adt.Queue;
import com.pablosesteban.adt.WeightedGraph;
import com.pablosesteban.adt.impl.Edge;
import com.pablosesteban.adt.impl.LinkedQueue;
import com.pablosesteban.adt.impl.UndirectedWeightedGraph;
import com.pablosesteban.api.WeightedGraphMinimumSpanningTree;

import edu.princeton.cs.algs4.MinPQ;

/**
 * A MST implementation based on the Prim's Algorithm and assuming that the weighted graph is connected.
 * Consists on attaching a new edge to a single growing tree starting with any vertex as a single-vertex
 * tree, then add number of vertices - 1 edges to it, always taking next vertex the minimum weighted edge
 * that connects a vertex on the tree to a vertex not yet on the tree (crossing edge).
 * An ineligible edge connects two tree vertices.
 * The algorithm is backed by three data structures:<ul>
 * <li>A vertex-indexed boolean array, where each entry is true if the vertex is currently on the tree.</li>
 * <li>A queue to collect MST edges.</li>
 * <li>A priority queue, that compares edges by weight, to store crossing edges (perhaps also some
 * ineligible ones.)</li></ul>
 * The algorithm take an edge from the priority queue and, if it is not ineligible, add it to the tree and
 * updates the set of crossing edges with adjacent vertices of the vertex that this edge leads to.
 * Any edge connecting the vertex just added to a tree vertex that is already on the priority queue now becomes
 * ineligible.
 * The implementation uses space proportional to twice the number of edges (E) and time proportional to E logE,
 * in the worst case, to compute the MST.
 */
public class PrimMST implements WeightedGraphMinimumSpanningTree {
	private boolean[] marked;
	private Queue<Edge> edges;
	private MinPQ<Edge> crossingEdges;
	private double weight;

	/**
	 * Computes the MST for this weighted graph
	 * 
	 * @param wg a weighted graph
	 */
	public PrimMST(WeightedGraph wg) {
		marked = new boolean[wg.size()];
		edges = new LinkedQueue<>();
		crossingEdges = new MinPQ<>();

		addCrossingEdges(wg, 0);

		while (!crossingEdges.isEmpty() ) {
			Edge minWeightedEdge = crossingEdges.delMin();
			
			int to = minWeightedEdge.getTo();
			
			if (marked[to]) {
				continue;
			}
			
			edges.enqueue(minWeightedEdge);
			
			weight += minWeightedEdge.getWeight();
			
			addCrossingEdges(wg, to);
		}
	}

	@Override
	public Iterable<Edge> getEdges() {
		return edges;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " {\nmarked: " + Arrays.toString(marked) + ",\nedges: " + edges + ",\nweight: " + weight + ",\ncrossingEdges: " + crossingEdges + "\n}";
	}

	/**
	 * Marking the vertex as visited and then putting all of its incident edges that are
	 * not ineligible onto the priority queue, thus ensuring that the priority queue contains
	 * the crossing edges from tree vertices to non-tree vertices (perhaps also some ineligible edges)
	 * 
	 * @param wg a weighted graph
	 * @param v a vertex
	 */
	private void addCrossingEdges(WeightedGraph wg, int v) {
		marked[v] = true;
		
		for (Edge e : wg.getIncidentEdges(v)) {
			if (!marked[e.getTo()]) {
				crossingEdges.insert(e);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		UndirectedWeightedGraph uwg = new UndirectedWeightedGraph("weighted_graph_tiny.txt");
		
		PrimMST mst = new PrimMST(uwg);
		System.out.println(mst);
	}
}

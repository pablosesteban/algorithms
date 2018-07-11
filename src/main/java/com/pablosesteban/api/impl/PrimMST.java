package com.pablosesteban.api.impl;

import java.io.IOException;
import java.util.Arrays;

import com.pablosesteban.adt.Queue;
import com.pablosesteban.adt.WeightedGraph;
import com.pablosesteban.adt.impl.Edge;
import com.pablosesteban.adt.impl.LinkedQueue;
import com.pablosesteban.adt.impl.UndirectedWeightedGraph;
import com.pablosesteban.api.WeightedGraphMinimumSpanningTree;

import edu.princeton.cs.algs4.IndexMinPQ;

/**
 * A MST implementation based on the Prim's Algorithm and assuming that the weighted graph is connected.
 * Consists on attaching a new edge to a single growing tree starting with any vertex as a single-vertex
 * tree, then add number of vertices - 1 edges to it, always taking next vertex the minimum weighted edge
 * that connects a vertex on the tree to a vertex not yet on the tree (crossing edge).
 * An ineligible edge connects two tree vertices.
 * The algorithm is backed by these data structures:<ul>
 * <li>A vertex-indexed boolean array, where each entry is true if the vertex is currently on the tree.</li>
 * <li>An indexed priority queue, that compares edges by weight, to store only crossing edges.</li></ul>
 * <li>A vertex-indexed array of edges (edgeTo) and a vertex-indexed double array (weightTo) where if
 * vertex v is not on the tree but has at least one edge connecting it to the tree, then edgeTo[v] is the
 * shortest edge connecting v to the tree, and weightTo[v] is the weight of that edge. All such vertices
 * v are maintained on the indexed priority queue, as an index v associated with the weight of edgeTo[v].</li>
 * The algorithm maintains on the indexed priority queue just one edge for each non-tree vertex w, i.e.
 * the shortest edge that connects it to the tree. Any longer edge connecting w to the tree will become
 * ineligible at some point, so there is no need to keep it on the indexed priority queue.
 * 
 * The implementation uses space proportional to twice the number of edges (E) and time proportional to E logE,
 * in the worst case, to compute the MST.
 */
public class PrimMST implements WeightedGraphMinimumSpanningTree {
	private boolean[] marked;
	private Queue<Edge> edges;
	private IndexMinPQ<Double> crossingEdges;
	private Edge[] edgeTo;
	private double[] weightTo;
	private double weight;

	/**
	 * Computes the MST for this weighted graph
	 * 
	 * @param wg a weighted graph
	 */
	public PrimMST(WeightedGraph wg) {
		marked = new boolean[wg.size()];
		crossingEdges = new IndexMinPQ<>(wg.size());
		edgeTo = new Edge[wg.size()];
		weightTo = new double[wg.size()];
		edges = new LinkedQueue<>();

		for (int i = 0; i < weightTo.length; i++) {
			weightTo[i] = Double.POSITIVE_INFINITY;
		}

		weightTo[0] = 0.0;
		crossingEdges.insert(0, 0.0);

		while (!crossingEdges.isEmpty() ) {
			addCrossingEdges(wg, crossingEdges.delMin());
		}

		for (int i = 0; i < edgeTo.length; i++) {
			Edge e = edgeTo[i];

			if (e != null) {
				weight += e.getWeight();
				edges.enqueue(e);
			}
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
		return getClass().getSimpleName() + " {\nmarked: " + Arrays.toString(marked) + ",\nedges: " + getEdges() + ",\nweight: " + weight + ",\ncrossingEdges: " + crossingEdges + "\n}";
	}

	/**
	 * For a vertex v from the indexed priority queue, checks each edge v-w on its adjacency list.
	 * If w is marked, the edge is ineligible.
	 * If it is not on the priority queue or its weight is lower than the current best-known edgeTo[w],
	 * the code updates the data structures to establish v-w as the best-known way to connect v to the
	 * tree.
	 * 
	 * @param wg a weighted graph
	 * @param v a vertex
	 */
	private void addCrossingEdges(WeightedGraph wg, int v) {
		marked[v] = true;

		for (Edge e : wg.getIncidentEdges(v)) {
			int toVertex = e.getTo();

			if (marked[toVertex]) {
				continue;
			}

			if (e.getWeight() < weightTo[toVertex]) {
				edgeTo[toVertex] = e;
				weightTo[toVertex] = e.getWeight();

				if (crossingEdges.contains(toVertex)) {
					crossingEdges.changeKey(toVertex, weightTo[toVertex]);
				}else {
					crossingEdges.insert(toVertex, weightTo[toVertex]);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		UndirectedWeightedGraph uwg = new UndirectedWeightedGraph("weighted_graph_tiny.txt");

		PrimMST mst = new PrimMST(uwg);
		System.out.println(mst);

		IndexMinPQ<String> impq = new IndexMinPQ<>(10);
	}
}

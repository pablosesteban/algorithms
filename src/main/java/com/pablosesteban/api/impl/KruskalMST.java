package com.pablosesteban.api.impl;

import java.io.IOException;

import com.pablosesteban.adt.Queue;
import com.pablosesteban.adt.UnionFind;
import com.pablosesteban.adt.WeightedGraph;
import com.pablosesteban.adt.impl.Edge;
import com.pablosesteban.adt.impl.LinkedQueue;
import com.pablosesteban.adt.impl.UndirectedWeightedGraph;
import com.pablosesteban.adt.impl.WeightedQuickUnion;
import com.pablosesteban.api.WeightedUngraphMinimumSpanningTree;

import edu.princeton.cs.algs4.MinPQ;

/**
 * A MST implementation based on the Kruskal's Algorithm and assuming that the undirected weighted graph is connected.
 * Consists on processing the edges in order of their weight values, from the smallest to largest, taking for the MST
 * each edge that does not form a cycle with edges previously added, stopping after adding number of vertices - 1 edges
 * have been taken.
 * The algorithm builds the MST one edge at a time, but, by contrast to Prim's algorithm, it finds an edge that connects
 * two trees in a forest of growing trees, starting with a degenerate forest of the number of vertices single-vertex trees
 * and perform the operation of combining two trees, using the shortest edge possible, until there is just one tree left.
 * This implementation uses space proportional to the number of edges (E) and time proportional to E log E in the worst case,
 * to compute the MST of an weighted connected graph with E edges and V vertices. The key operation is the priority-queue
 * populated in the constructor with all the edges at a cost of at most E compares.
 * Despite this advantage, this algorithm is generally slower than Prim’s algorithm because it has to do a union operation
 * for each edge, in addition to the priority-queue operations that both algorithms do for each edge processed.
 */
public class KruskalMST implements WeightedUngraphMinimumSpanningTree {
	private Queue<Edge> mst;
	private double weight;
	
	public KruskalMST(WeightedGraph wg) {
		UnionFind uf = new WeightedQuickUnion(wg.size());
		
		MinPQ<Edge> edges = new MinPQ<>(wg.size());
		for (Edge edge : wg.getEdges()) {
			edges.insert(edge);
		}
		
		mst = new LinkedQueue<>();
		
		while (!edges.isEmpty() && mst.size() < wg.size() - 1) {
			Edge minEdge = edges.delMin();
			
			if (uf.areConnected(minEdge.getFrom(), minEdge.getTo())) {
				continue;
			}
			
			uf.union(minEdge.getFrom(), minEdge.getTo());
			
			mst.enqueue(minEdge);
			
			weight += minEdge.getWeight();
		}
	}

	@Override
	public Iterable<Edge> getEdges() {
		return mst;
	}

	@Override
	public double getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " {\nmst: " + mst + ",\nweigth: " + weight + "\n}";
	}

	public static void main(String[] args) throws IOException {
		UndirectedWeightedGraph uwg = new UndirectedWeightedGraph("weighted_graph_tiny.txt");
		System.out.println(uwg);
		
		KruskalMST kruskalMST = new KruskalMST(uwg);
		System.out.println(kruskalMST);
	}
}

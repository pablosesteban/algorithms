package com.pablosesteban.api.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import com.pablosesteban.adt.Queue;
import com.pablosesteban.adt.WeightedGraph;
import com.pablosesteban.adt.impl.Edge;
import com.pablosesteban.adt.impl.LinkedQueue;
import com.pablosesteban.adt.impl.UndirectedWeightedGraph;
import com.pablosesteban.api.WeightedUngraphMinimumSpanningTree;

import edu.princeton.cs.algs4.IndexMinPQ;

public class KruskalMST implements WeightedUngraphMinimumSpanningTree {
	private boolean[] marked;
	private double[] weightTo;
	private Edge[] edgeTo;
	private IndexMinPQ<Double> edges;
	private double weight;
	private Queue<Edge> mst;
	
	public KruskalMST(WeightedGraph wg) {
		marked = new boolean[wg.size()];
		weightTo = new double[wg.size()];
		edgeTo = new Edge[wg.size()];
		edges = new IndexMinPQ<>(wg.size());
		mst = new LinkedQueue<>();
		
		for (int i = 0; i < weightTo.length; i++) {
			weightTo[i] = Double.POSITIVE_INFINITY;
		}
		
		edges.insert(0, 0.0);
		for(int v = 0; v < wg.size(); v++) {
			addNextEdge(wg, v);
		}
		
		while (!edges.isEmpty()) {
			int index = edges.delMin();
			
			mst.enqueue(edgeTo[index]);
			
			weight += weightTo[index];
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
		return getClass().getSimpleName() + " {\nedgeTo: " + Arrays.toString(edgeTo) +  ",\nweightTo: " + Arrays.toString(weightTo) + ",\nmst: " + mst + ",\nweight: " + weight + "\n}";
	}

	private void addNextEdge(WeightedGraph wg, int v) {
		marked[v] = true;
		
		for (Edge e : wg.getIncidentEdges(v) ) {
			int toVertex = e.getTo();
			
			if (marked[toVertex]) {
				continue;
			}
			
			if (e.getWeight() < weightTo[v]) {
				edgeTo[v] = e;
				weightTo[v] = e.getWeight();

				if (edges.contains(v)) {
					edges.changeKey(v, weightTo[v]);
				}else {
					edges.insert(v, weightTo[v]);
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		UndirectedWeightedGraph uwg = new UndirectedWeightedGraph("weighted_graph_tiny.txt");
		System.out.println(uwg);
		
		KruskalMST kruskalMST = new KruskalMST(uwg);
		System.out.println(kruskalMST);
	}
}

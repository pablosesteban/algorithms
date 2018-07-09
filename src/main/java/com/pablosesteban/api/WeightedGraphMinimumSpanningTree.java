package com.pablosesteban.api;

import com.pablosesteban.adt.impl.Edge;

/**
 * API to find the Minimum Spanning Tree (MST) of a weighted graph.
 * A Spanning Tree of a graph is a connected subgraph with no cycles that includes
 * all the vertices. A MST of a weighted graph is a spanning tree whose weight
 * (the sum of the weights of its edges) is no larger than the weight of any other
 * spanning tree.
 */
public interface WeightedGraphMinimumSpanningTree {
	/**
	 * Get all of the MST edges
	 * 
	 * @return all of the MST edges
	 */
	Iterable<Edge> getEdges();
	
	/**
	 * Get the total weight of the MST
	 * 
	 * @return the sum of the weights of the MST edges
	 */
	double getWeight();
}

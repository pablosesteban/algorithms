package com.pablosesteban.api.impl;

import java.io.IOException;
import java.util.Arrays;

import com.pablosesteban.adt.Stack;
import com.pablosesteban.adt.impl.DirectedWeightedGraph;
import com.pablosesteban.adt.impl.Edge;
import com.pablosesteban.adt.impl.LinkedStack;
import com.pablosesteban.api.WeightedDigraphShortestPaths;

import edu.princeton.cs.algs4.IndexMinPQ;

/**
 * A Shortest Paths implementation based on Dijkstra's algorithm.
 * Focus on the single source shortest paths problem, where we are given a source vertex and the result of
 * the computation is a tree known as the Shortest Paths Tree (SPT), which gives a shortest path from source
 * to every vertex reachable from it.
 * Given an edge-weighted digraph and a designated source vertex s, a SPT for it is a subgraph containing s
 * and all the vertices reachable from s that forms a directed tree rooted at s such that every tree path is
 * a shortest path in the digraph.
 * The implementation is backed by two vertex-indexed arrays:
 * <li>edgeTo: edges on the SPT, which is a parent-edge representation of Edge objects where each entry is an edge that
 * connects that vertex to its parent in the tree (the last edge on a shortest path from source to that vertex).</li>
 * <li>weightTo: distance to the source, such that each entry is the weight of the shortest known path from source to that
 * vertex.
 * <li>an index priority queue to keep track of vertices that are candidates for being the next to be relaxed.</li>
 * The implementation is based on a simple operation known as vertex relaxation, i.e. as the algorithm proceeds,
 * it gathers information about the shortest paths that connect the source to each vertex encountered in both
 * data structures and by updating this information when we encounter edges, we can make new inferences about
 * shortest paths. Each vertex relaxation finds a shorter path than the best known so far to some vertex,
 * incrementally progressing toward the goal of finding shortest paths to every vertex.
 * Dijkstra’s algorithm solves the single source shortest paths problem in edge-weighted digraphs with
 * NON-NEGATIVE weights, i.e. if v is reachable from the source, every edge v->w is relaxed exactly once, when v
 * is relaxed, leaving weightTo[w] <= weightTo[v] + e.weight(), this inequality holds until the algorithm completes,
 * since weightTo[w] can only decrease (any relaxation can only decrease a distTo[] value) and distTo[v] never changes
 * (because edge weights are nonnegative and we choose the lowest distTo[] value at each step, no subsequent relaxation
 * can set any distTo[] entry to a lower value than distTo[v]).
 */
public class DijkstraSP implements WeightedDigraphShortestPaths {
	private double[] weightTo;
	private Edge[] edgeTo;
	private IndexMinPQ<Double> pq;
	
	/**
	 * Builds the SPT and computes shortest paths distances by initializing weightTo[source] to 0 and
	 * all other weightTo[] entries to positive infinity, then we relax and add to the tree a non-tree
	 * vertex with the lowest weightTo[] value, continuing until all vertices are on the tree or no
	 * non-tree vertex has a finite weightTo[] value.
	 * 
	 * @param dwg a weighted digraph
	 * @param source a vertex to compute the SPT
	 */
	public DijkstraSP(DirectedWeightedGraph dwg, int source) {
		weightTo = new double[dwg.size()];
		edgeTo = new Edge[dwg.size()];
		pq = new IndexMinPQ<>(dwg.size());
		
		for (int v = 0; v < weightTo.length; v++) {
			weightTo[v] = Double.POSITIVE_INFINITY;
		}
		
		weightTo[source] = 0.0;
		
		pq.insert(source, 0.0);
		while(!pq.isEmpty()) {
			relaxVertex(dwg, pq.delMin());
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
	private void relaxVertex(DirectedWeightedGraph dwg, int v) {
		if (dwg.getIncidentEdges(v) != null) {
			for (Edge e : dwg.getIncidentEdges(v)) {
				double newWeight = e.getWeight() + weightTo[e.getFrom()];
				
				if (newWeight < weightTo[e.getTo()]) {
					weightTo[e.getTo()] = newWeight;
					
					edgeTo[e.getTo()] = e;
					
					if (pq.contains(e.getTo())) {
						pq.changeKey(e.getTo(), newWeight);
					}else {
						pq.insert(e.getTo(), newWeight);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		DirectedWeightedGraph dwg = new DirectedWeightedGraph("weighted_digraph_tiny.txt");
		
		DijkstraSP dsp = new DijkstraSP(dwg, 0);
		System.out.println(dsp);
		
		System.out.println("pathTo 5: " + dsp.pathTo(5));
		System.out.println("pathTo 6: " + dsp.pathTo(6));
	}
}

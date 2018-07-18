package com.pablosesteban.adt.impl;

import java.util.Arrays;

import com.pablosesteban.adt.UnionFind;

/**
 * An UnionFind implementation which concentrates on speeding up the union operation and keeping the depth
 * of any node in the forest built N vertex at most lg N.
 * The implementation is backed by two vertex-indexed arrays:
 * <li>vertices: where each entry for each vertex is the index of another vertex in the same connected
 * component (possibly itself). This connection is called a link.</li>
 * <li>sizes: where each entry for each vertex is the size of the connected component to which the vertex
 * belongs to.</li>
 * The worst-case order of growth of the cost of all operations is log N, i.e. each operation does at most
 * a constant number of array accesses for each node on the path from a node to a root in the forest.
 */
public class WeigthedQuickUnion implements UnionFind {
	private int[] vertices;
	private int[] sizes;
	/**
	 * The number of connected components
	 */
	private int count;
	
	/**
	 * Initially, we start with numberOfVertices connected components, each vertex in its own connecte component
	 * 
	 * @param numberOfVertices the number of vertices
	 */
	public WeigthedQuickUnion(int numberOfVertices) {
		vertices = new int[numberOfVertices];
		sizes = new int[numberOfVertices];
		count = numberOfVertices;
		
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = i;
			sizes[i] = 1;
		}
	}
	
	/**
	 * To maintain the invariant that says vertex on the same connected component leads to the same root calling find(),
	 * it follow links to find the roots associated with v and w, then rename one of the connected components by linking
	 * one of these roots to the other (quick union) always keeping track of the size of each tree and always connect the
	 * smaller tree to the larger. This way, the height of a tree of 2^N nodes is N. Furthermore, when we merge two trees
	 * of 2^N nodes, we get a tree of 2^N+1 nodes, and we increase the height of the tree to N+1, guaranteeing logarithmic
	 * performance.
	 */
	@Override
	public void union(int v, int w) {
		int vRoot = find(v);
		int wRoot = find(w);
		
		if (vRoot == wRoot) {
			return;
		}
		
		if (sizes[vRoot] < sizes[wRoot]) {
			vertices[vRoot] = vertices[wRoot];
			
			sizes[wRoot] += sizes[vRoot];
		}else {
			vertices[wRoot] = vertices[vRoot];
			
			sizes[vRoot] += sizes[wRoot];
		}
		
		count--;
	}

	/**
	 * Follow its link to another vertex, follow that vertex’s link to yet another vertex, and so forth,
	 * following links until reaching a root, i.e. a vertex that has a link to itself.
	 */
	@Override
	public int find(int v) {
		while (v != vertices[v]) {
			v = vertices[v];
		}
		
		return v;
	}

	/**
	 * Two vertex are in the same connected component if, and only if, find() leads them to the same root.
	 */
	@Override
	public boolean areConnected(int v, int w) {
		return find(v) == find(w);
	}

	@Override
	public int count() {
		return count;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " {\nvertices: " + Arrays.toString(vertices) + ",\nsizes: " + Arrays.toString(sizes)
				+ ",\ncount: " + count + "\n}";
	}

	public static void main(String[] args) {
		WeigthedQuickUnion uf = new WeigthedQuickUnion(8);
		
		uf.union(0, 4);
		uf.union(0, 6);
		uf.union(0, 2);
		uf.union(0, 7);
		uf.union(1, 2);
		uf.union(1, 5);
		uf.union(1, 7);
		uf.union(1, 3);
		uf.union(2, 7);
		uf.union(2, 3);
		uf.union(2, 6);
		uf.union(3, 6);
		uf.union(4, 5);
		uf.union(4, 7);
		uf.union(4, 6);
		uf.union(5, 7);
		
		System.out.println(uf);
	}
}

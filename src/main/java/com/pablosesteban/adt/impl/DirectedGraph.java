 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package com.pablosesteban.adt.impl;

import com.pablosesteban.adt.Bag;
import com.pablosesteban.adt.Graph;

/**
 * A Graph implementation based on an array of adjacency lists data structure.
 * We maintain a vertex-indexed array of linked-lists of the vertices adjacent to
 * each vertex where each edge occurs just once.
 * In DIrected Graphs (digraphs), edges are one-way, i.e. the pair of vertices that
 * defines each edge is an ordered pair that specifies a one-way adjacency. A directed
 * edge points from the first vertex in the pair and points to the second vertex in
 * the pair.
 * Each vertex is reachable from itself.
 * The fact that w is reachable from v in a digraph indicates nothing about whether v
 * is reachable from w.
 * The outdegree of a vertex in a digraph is the number of edges going from it, the
 * indegree of a vertex is the number of edges going to it.
 * This implementation has a constraint: vertex names must be integer indices.
 * Parallel edges and self-loops are allowed.
 */
public class DirectedGraph implements Graph{
	private Bag<Integer>[] vertices;
	private int numberOfEdges;
	
	/**
     * Create a graph with no edges
     * 
     * @param numberOfVertices the number of vertices in the graph
     */
	public DirectedGraph(int numberOfVertices) {
		vertices = new Bag[numberOfVertices];
	}
	
	@Override
	public void addEdge(int v, int w) {
		if (vertices[v] == null) {
			vertices[v] = new LinkedBag<>();
		}
		
		vertices[v].add(w);
		
		numberOfEdges++;
	}

	@Override
	public Iterable<Integer> getAdjacentVertices(int v) {
		return vertices[v];
	}

	@Override
	public int size() {
		return vertices.length;
	}

	@Override
	public Graph reverse() {
		DirectedGraph dg = new DirectedGraph(vertices.length);
		
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i] != null) {
				for (Integer adjacentVertex : vertices[i]) {
					dg.addEdge(adjacentVertex, i);
				}
			}
		}
		
		return dg;
	}
	
	@Override
    public String toString() {
        StringBuilder verticesSb = new StringBuilder();
        
        verticesSb.append("{\n");
        
        int vertexLastIndex = vertices.length - 1;
        for (int i = 0; i < vertices.length; i++) {
            verticesSb.append(i);
            verticesSb.append(": ");
            
            if (vertices[i] != null) {
            	int verticesLastIndex = vertices[i].size() - 1;
            	int count = 0;
            	verticesSb.append("[");
            	for (Integer vertex : vertices[i]) {
            		verticesSb.append(vertex);

            		if (count == verticesLastIndex) {
            			if (i == vertexLastIndex) {
            				verticesSb.append("]\n");
            			}else {
            				verticesSb.append("],\n");
            			}
            		}else {
            			verticesSb.append(", ");
            		}

            		count++;
            	}
            }else {
            	if (i == vertexLastIndex) {
                	verticesSb.append("[]\n");
            	}else {
            		verticesSb.append("[],\n");
            	}
            }
        }
        
        verticesSb.append("}");
        
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" {");
        sb.append("\nnumberOfVertices: ");
        sb.append(vertices.length );
        sb.append(",\nnumberOfEdges: ");
        sb.append(numberOfEdges);
        sb.append(",\nvertices: ");
        sb.append(verticesSb);
        sb.append("\n}");
        
        return sb.toString();
    }
    
    public static void main(String[] args) {
        DirectedGraph dg = new DirectedGraph(13);
        
        dg.addEdge(0, 5);
        dg.addEdge(4, 3);
        dg.addEdge(0, 1);
        dg.addEdge(9, 12);
        dg.addEdge(6, 4);
        dg.addEdge(5, 4);
        dg.addEdge(0, 2);
        dg.addEdge(11, 12);
        dg.addEdge(9, 10);
        dg.addEdge(0, 6);
        dg.addEdge(7, 8);
        dg.addEdge(9, 11);
        dg.addEdge(5, 3);
        
        System.out.println(dg);
        System.out.println(dg.reverse());
    }
}

package com.pablosesteban.adt.impl;

import com.pablosesteban.adt.Bag;
import com.pablosesteban.adt.WeightedGraph;

/**
 * A Weighted Graph implementation based on an array of adjacency lists data
 * structure.
 * This data structure is the standard graph representation for graphs that are
 * not dense, where we maintain a vertex-indexed array of linked-lists of the
 * edges adjacent to each vertex.
 * Each vertex is reachable from itself.
 * This implementation has a constraint: vertex names must be integer indices.
 * To add an edge connecting v and w, we add the edge to v and w adjacency
 * lists, so that, each edge appears twice in the data structure as there is no
 * direction in edges, they are two-way.
 * The fact that w is reachable from v in a undirected graph indicates that also
 * v is reachable from w.
 * Parallel edges and self-loops are allowed.
 * It is important to realize that the order in which edges are added to the
 * graph determines the order in which vertices appear in the array of adjacency
 * lists built by graph, so that, many different arrays of adjacency lists can
 * represent the same graph.
 */
public class UndirectedWeightedGraph implements WeightedGraph {
	private Bag<Edge>[] vertices;
    private int numberOfEdges;
    
    
	public UndirectedWeightedGraph(int numberOfVertices) {
		vertices = new Bag[numberOfVertices];
	}

	@Override
	public void addEdge(Edge e) {
		if (vertices[e.getFrom()] == null) {
			vertices[e.getFrom()] = new LinkedBag<>();
		}
		
		if (vertices[e.getTo()] == null) {
			vertices[e.getTo()] = new LinkedBag<>();
		}
		
		vertices[e.getFrom()].add(e);
		vertices[e.getTo()].add(new Edge(e.getTo(), e.getFrom(), e.getWeight()));
		
		numberOfEdges++;
	}

	@Override
	public Iterable<Edge> getIncidentEdges(int v) {
		return vertices[v];
	}

	@Override
	public Iterable<Edge> getEdges() {
		Bag<Edge> edges = new LinkedBag<>();
		
		for (int v = 0; v < vertices.length; v++) {
			for (Edge edge : vertices[v]) {
				if (edge.getTo() > v) {
					edges.add(edge);
				}
			}
		}
		
		return edges;
	}

	@Override
	public int size() {
		return vertices.length;
	}
	
	@Override
    public String toString() {
        StringBuilder verticesSb = new StringBuilder();
        
        verticesSb.append("{\n");
        
        int vertexLastIndex = vertices.length - 1;
        for (int i = 0; i < vertices.length; i++) {
            verticesSb.append(i);
            verticesSb.append(": ");
            
            int verticesLastIndex = vertices[i].size() - 1;
            int count = 0;
            verticesSb.append("[");
            for (Edge vertex : vertices[i]) {
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
		UndirectedWeightedGraph uwg = new UndirectedWeightedGraph(13);
        
		uwg.addEdge(new Edge(0, 5, 0));
		uwg.addEdge(new Edge(4, 3, 0));
		uwg.addEdge(new Edge(0, 1, 0));
		uwg.addEdge(new Edge(9, 12, 0));
        uwg.addEdge(new Edge(6, 4, 0));
        uwg.addEdge(new Edge(5, 4, 0));
        uwg.addEdge(new Edge(0, 2, 0));
        uwg.addEdge(new Edge(11, 12, 0));
        uwg.addEdge(new Edge(9, 10, 0));
        uwg.addEdge(new Edge(0, 6, 0));
        uwg.addEdge(new Edge(7, 8, 0));
        uwg.addEdge(new Edge(9, 11, 0));
        uwg.addEdge(new Edge(5, 3, 0));
        
        System.out.println(uwg);
        
        System.out.println("Edges:");
        int count = 0;
        for (Edge e : uwg.getEdges()) {
        	System.out.println(e);
        	count++;
        }
        System.out.println(count);
	}
}

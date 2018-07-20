package com.pablosesteban.adt.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.pablosesteban.adt.Bag;
import com.pablosesteban.adt.WeightedGraph;

/**
 * A Weighted Directed Graph implementation based on an array of adjacency
 * lists data structure.
 * This data structure is the standard graph representation for graphs that are
 * not dense, where we maintain a vertex-indexed array of linked-lists of the
 * edges adjacent to each vertex.
 * Each vertex is reachable from itself.
 * This implementation has a constraint: vertex names must be integer indices.
 * To add an edge connecting v and w, we add the edge only to v adjacency list,
 * so that, each edge appears only once in the data structure as there is a
 * direction in edges, they are one-way.
 * The fact that w is reachable from v in a undirected graph indicates that also
 * v is reachable from w.
 * Parallel edges and self-loops are allowed.
 * It is important to realize that the order in which edges are added to the
 * graph determines the order in which vertices appear in the array of adjacency
 * lists built by graph, so that, many different arrays of adjacency lists can
 * represent the same graph.
 */
public class DirectedWeightedGraph implements WeightedGraph {
	private Bag<Edge>[] vertices;
    private int numberOfEdges;
    
    /**
     * Reads a graph from file
     * 
     * @param filename file to read the graph
     * @throws IOException if file does not exists
     */
    public DirectedWeightedGraph(String filename) throws IOException {
    	try(BufferedReader br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource(filename).getFile()))) {
    		String numberOfVertices = br.readLine();
        	String numberOfEdges = br.readLine();
        	
        	vertices = new Bag[Integer.parseInt(numberOfVertices)];
        	
        	String line = null;
        	while ((line = br.readLine()) != null) {
        		String[] edge = line.split(" ");
        		
        		addEdge(new Edge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]), Double.parseDouble(edge[2])));
        	}
    	}
    }
    
	/**
	 * Creates an empty graph with this number of vertices
	 */
    public DirectedWeightedGraph(int numberOfVertices, int source) {
		vertices = new Bag[numberOfVertices];
	}
    
	@Override
	public void addEdge(Edge e) {
		int from = e.getFrom();
		
		if (vertices[from] == null) {
			vertices[from] = new LinkedBag<>();
		}
		
		vertices[from].add(e);
		
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
			if (vertices[v] != null) {
				for (Edge edge : vertices[v]) {
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
            
            if (vertices[i] != null) {
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
	
	public static void main(String[] args) throws IOException {
		DirectedWeightedGraph dwg = new DirectedWeightedGraph("weighted_digraph_tiny.txt");
        
        System.out.println(dwg);
        
        System.out.println("Edges:");
        int count = 0;
        for (Edge e : dwg.getEdges()) {
        	System.out.println(e);
        	count++;
        }
        System.out.println(count);
	}
}

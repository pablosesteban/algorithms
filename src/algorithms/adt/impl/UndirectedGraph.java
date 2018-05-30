/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.impl;

import algorithms.adt.Bag;
import algorithms.adt.Graph;

/**
 * A Graph implementation based on an array of adjacency lists data structure.
 * This data structure is the standard graph representation for graphs that are
 * not dense, where we maintain a vertex-indexed array of linked-lists of the
 * vertices adjacent to each vertex.
 * This implementation has a constraint: vertex names must be integer indices.
 * To add an edge connecting v and w, we add w to v’s adjacency list and v to
 * w’s adjacency list, so that, each edge appears twice in the data structure.
 * Parallel edges and self-loops are allowed.
 * The implementation achieves the following performance characteristics:<br>
 * <ul>
 * <li>space usage proportional to number of vertices + number of edges</li>
 * <li>constant time to add an edge</li>
 * <li>time proportional to the degree of v to iterate through vertices adjacent
 * to v, i.e. constant time per adjacent vertex processed</li>
 * </ul>
 * It is important to realize that the order in which edges are added to the
 * graph determines the order in which vertices appear in the array of adjacency
 * lists built by graph, so that, many different arrays of adjacency lists can
 * represent the same graph.
 */
public class UndirectedGraph implements Graph {
    private Bag<Integer>[] vertices;
    private int numberOfEdges;
    
    /**
     * Create a graph with no edges
     * 
     * @param vertices the number of vertices in the graph
     */
    public UndirectedGraph(int vertices) {
        this.vertices = new Bag[vertices];
    }
    
    @Override
    public void addEdge(int v, int w) {
        if (vertices[v] == null) {
            vertices[v] = new LinkedBag<>();
        }
        
        if (vertices[w] == null) {
            vertices[w] = new LinkedBag<>();
        }
        
        vertices[v].add(w);
        vertices[w].add(v);
        
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
        UndirectedGraph ug = new UndirectedGraph(13);
        
        ug.addEdge(0, 5);
        ug.addEdge(4, 3);
        ug.addEdge(0, 1);
        ug.addEdge(9, 12);
        ug.addEdge(6, 4);
        ug.addEdge(5, 4);
        ug.addEdge(0, 2);
        ug.addEdge(11, 12);
        ug.addEdge(9, 10);
        ug.addEdge(0, 6);
        ug.addEdge(7, 8);
        ug.addEdge(9, 11);
        ug.addEdge(5, 3);
        
        System.out.println(ug);
    }
}

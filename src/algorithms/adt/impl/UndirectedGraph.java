/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.impl;

import algorithms.adt.Bag;
import algorithms.adt.Graph;

/**
 * 
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("{\n");
        
        for (int i = 0; i < vertices.length; i++) {
            sb.append("\t");
            sb.append(i);
            sb.append(": ");
            
            for (Integer vertex : vertices[i]) {
                sb.append(vertex);
                sb.append(", ");
            }
            
            int lastComma = sb.lastIndexOf(", ");
            if (lastComma != -1) {
                sb.replace(lastComma, lastComma+1, "\n");
            }
        }
        
        sb.append("}");
        
        return "UndirectedGraph{\n" + "numberOfVertices: " + vertices.length + ",\nnumberOfEdges: " + numberOfEdges + ",\nvertices: " + sb + '}';
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

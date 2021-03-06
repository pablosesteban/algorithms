/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package com.pablosesteban.api.impl;

import java.util.Arrays;
import java.util.TreeMap;

import com.pablosesteban.adt.Graph;
import com.pablosesteban.adt.Graph.Type;
import com.pablosesteban.adt.impl.DirectedGraph;
import com.pablosesteban.adt.impl.UndirectedGraph;
import com.pablosesteban.api.SymbolGraph;

import edu.princeton.cs.algs4.In;

/**
 * An implementation that uses two passes through the data to build the internal
 * data structures (like indexing), primarily because the number of vertices is
 * needed to build the graph.
 * The implementation preprocess the data building the data structures that can
 * efficiently support client operations.
 */
public class SymbolGraphImpl implements SymbolGraph {
    // direct index: given a name, returns its index
    private TreeMap<String, Integer> symbolTable;
    // inverted index: given an index, returns its name
    private String[] keys;
    private Graph graph;
    
    /**
     * Builds graph from the file specified by filename using delimiter to
     * separate vertex names.
     * 
     * @param filename a file where each line represents a set of edges,
     * connecting the first vertex name on the line to each of the other
     * vertices named on the line
     * @param delimiter a specified delimiter to separate vertex names
     */
    public SymbolGraphImpl(String filename, String delimiter, Type graphType) {
        In input = new In(filename);
        
        // building symbol table (direct index) data structure
        symbolTable = new TreeMap<>();
        while (input.hasNextLine()) {
            String[] vertices = input.readLine().split(delimiter);
            
            for (String vertex : vertices) {
                if (!symbolTable.containsKey(vertex)) {
                    symbolTable.put(vertex, symbolTable.size());
                }
            }
        }
        
        // building the inverted index data structure
        keys = new String[symbolTable.size()];
        for (String key : symbolTable.keySet()) {
            keys[symbolTable.get(key)] = key;
        }
        
        // building the graph data structure
        input = new In(filename);
        switch (graphType) {
        	case DIRECTED:
        		graph = new DirectedGraph(symbolTable.size());
        		break;
        	case UNDIRECTED:
        		graph = new UndirectedGraph(symbolTable.size());
        		break;
        	default:
        		throw new IllegalArgumentException("Supported types: " + Type.UNDIRECTED + " or " + Type.DIRECTED);
        }
        while(input.hasNextLine()) {
            String[] vertices = input.readLine().split(delimiter);
            
            for (int i = 1; i < vertices.length; i++) {
                graph.addEdge(symbolTable.get(vertices[0]), symbolTable.get(vertices[i]));
            }
        }
    }
    
    @Override
    public boolean contains(String key) {
        return symbolTable.containsKey(key);
    }

    @Override
    public int index(String key) {
        Integer index = symbolTable.get(key);
        
        if (index == null) {
            return -1;
        }
        
        return index; 
    }

    @Override
    public String name(int index) {
        if (index >= keys.length || index < 0) {
            return null;
        }
        
        return keys[index];
    }

    @Override
    public Graph getGraph() {
        return graph;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" + "\nsymbolTable: " + symbolTable + ",\nkeys: " + Arrays.toString(keys) + ",\ngraph: " + graph + "\n}";
    }
    
    public static void main(String[] args) {
        SymbolGraph symbolGraph = new SymbolGraphImpl("routes.txt", " ", Type.UNDIRECTED);
//        SymbolGraph symbolGraph = new SymbolGraphImpl("movies.txt", "/", Type.UNDIRECTED);
        
        System.out.println(symbolGraph);
        
        System.out.println("contains JFK: " + symbolGraph.contains("JFK"));
        System.out.println("contains PABLO: " + symbolGraph.contains("PABLO"));
        
        System.out.println("index of JFK: " + symbolGraph.index("JFK"));
        System.out.println("index of PABLO: " + symbolGraph.index("PABLO"));
        
        System.out.println("name of 0: " + symbolGraph.name(0));
        System.out.println("name of -4: " + symbolGraph.name(-4));
    }
}

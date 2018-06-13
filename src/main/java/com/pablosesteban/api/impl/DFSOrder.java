 /**
  * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
  */
package com.pablosesteban.api.impl;

import java.util.Arrays;

import com.pablosesteban.adt.Graph;
import com.pablosesteban.adt.Queue;
import com.pablosesteban.adt.Stack;
import com.pablosesteban.adt.impl.DirectedGraph;
import com.pablosesteban.adt.impl.LinkedStack;
import com.pablosesteban.api.DigraphOrder;

/**
 * A DFS implementation based on the fact that the recursive call stack
 * maintained by the system represents the "current" path under consideration.
 * If it ever finds an edge v-w to a vertex w that is on that stack, it has found
 * a cycle, since the stack is evidence of a path from w to v, and the edge v-w
 * completes the cycle.
 * The implementation maintains a boolean array onStack[] to keep track of the
 * vertices for which the recursive call has not completed (by setting onStack[v]
 * to true on entry and to false on exit), and when it finds an edge v->w to a
 * vertex w that is on the stack, it has discovered a directed cycle, which it can
 * recover by following edgeTo[] links.
 */
public class DFSOrder implements DigraphOrder {
    private boolean[] marked;
    private int[] edgeTo;
    private Graph g;
    private Stack<Integer> cycle;
    private boolean[] onStack;
    
    private Stack<Integer> topologicalOrder;
    private Queue<Integer> preOrder;
    private Queue<Integer> postOrder;
    
    public DFSOrder(DirectedGraph dg) {
        marked = new boolean[dg.size()];
        edgeTo = new int[dg.size()];
        this.g = dg;
        onStack = new boolean[dg.size()];
        
        for (int v = 0; v < dg.size(); v++) {
            if (!marked[v]) {
                dfs(dg, v);
            }
        }
    }
    
    @Override
    public boolean hasCycle() {
        return cycle != null;
    }
    
    @Override
    public Iterable<Integer> getCycle() {
        return cycle;
    }

	@Override
	public Iterable<Integer> order(Order order) {
		return null;
	}
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(getClass().getSimpleName());
        sb.append(" {\n");
        sb.append("marked: ");
        sb.append(Arrays.toString(marked));
        sb.append(",\nedgeTo: ");
        sb.append(Arrays.toString(edgeTo));
        sb.append(",\ngraph: ");
        sb.append(g);
        sb.append(",\nonStack: ");
        sb.append(Arrays.toString(onStack));
        sb.append(",\ncycle: ");
        sb.append(cycle);
        sb.append("\n}");
        
        return sb.toString();
    }
    
    private void dfs(Graph g, int current) {
        marked[current] = true;
        onStack[current] = true;
        
        if (g.getAdjacentVertices(current) != null) {
            for (Integer adjacentVertex : g.getAdjacentVertices(current)) {
                if (hasCycle()) {
                    return;
                }else if (!marked[adjacentVertex]) {
                    edgeTo[adjacentVertex] = current;
                    
                    dfs(g, adjacentVertex);
                }else if (onStack[adjacentVertex]) {
                    cycle = new LinkedStack<>();
                    
                    for (int v = current; v != adjacentVertex; v = edgeTo[v]) {
                        cycle.push(v);
                    }
                    
                    cycle.push(adjacentVertex);
                    cycle.push(current);
                }
            }
        }
        
        onStack[current] = false;
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
        dg.addEdge(3, 0);
        dg.addEdge(12, 9);
        
        DFSOrder dfso = new DFSOrder(dg);
        
        System.out.println(dfso);
        System.out.println("Has cycle: " + dfso.hasCycle());
        System.out.println();
        
        dg = new DirectedGraph(13);
        dg.addEdge(0, 5);
        dg.addEdge(4, 3);
        dg.addEdge(0, 1);
        dg.addEdge(9, 12);
        dg.addEdge(0, 2);
        dg.addEdge(11, 12);
        dg.addEdge(9, 10);
        dg.addEdge(0, 6);
        dg.addEdge(7, 8);
        dg.addEdge(9, 11);
        dg.addEdge(3, 0);
        
        dfso = new DFSOrder(dg);
        
        System.out.println(dfso);
        System.out.println("Has cycle: " + dfso.hasCycle());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.part_I.week_5.KdTree;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author psantama
 */
public class Algorithms {
    public static void printArray(Comparable[] arr, int lowIdx, int highIdx) {
        System.out.print("{");
        
        for (int i = lowIdx; i < highIdx; i++) {
            System.out.print(arr[i] + ", ");
        }
        
        System.out.println(arr[highIdx] + "}");
    }
    
    //swap elements at those index
    public static void swap(Object[] elements, int i, int j) {
        Object swap = elements[i];
        
        elements[i] = elements[j];
        
        elements[j] = swap;
    }
    
    // add the points that the user clicks in the standard draw window to a kd-tree and draw the resulting kd-tree
    private static void executeKdTreeVisualizer() {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.show();
                }
            }
            StdDraw.pause(50);
        }
    }
    
    public static void main(String[] args) {
        executeKdTreeVisualizer();
    }
}

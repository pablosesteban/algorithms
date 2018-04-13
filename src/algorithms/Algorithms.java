/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.part_I.week_5.KdTree;
import algorithms.part_I.week_5.PointSET;

import edu.princeton.cs.algs4.In;
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
    
    /*
    Read points from a file and draw to standard draw
    
    Highlight the closest point to the mouse
    
    The nearest neighbor according to the brute-force algorithm is drawn in red; the nearest neighbor using the kd-tree algorithm is drawn in blue
    */
    private static void executeNearestNeighborVisualizer() {
        String filename = "D:/Users/psantama/Downloads/kdtree/input10.txt";
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);
            System.out.println("mouse point: " + query);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            Point2D nearest = brute.nearest(query);
            nearest.draw();
            System.out.println("\tPointSet nearest: " + nearest);
            StdDraw.setPenRadius(0.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            nearest = kdtree.nearest(query);
            nearest.draw();
            System.out.println("\tKdTree nearest: " + nearest);
            StdDraw.show();
            StdDraw.pause(40);
        }
    }
    
    private static void printDecHexBit(int i) {
        System.out.println("decimal: " + i);
        System.out.println("hexadecimal: " + Integer.toHexString(i));
        System.out.println("bits: " + Integer.toBinaryString(i));
    }
    
    public static void main(String[] args) {
        int i = -4;
        printDecHexBit(i);
        
        System.out.println("");
        
        int d = 0x7FFFFFFF;
        printDecHexBit(d);
        
        System.out.println("");
        
        int j = i & 0x7FFFFFFF;
        printDecHexBit(j);
        
        System.out.println("");
        
        /**
         * PRIMITIVE TYPES OVERFLOW
         **/
        int p = 2147483647;
        int n = p + 1;
        System.out.println("p: " + p + ", p+1: " + n);
        n = p + 2;
        System.out.println("p: " + p + ", p+2: " + n);
        n = p + 3;
        System.out.println("p: " + p + ", p+3: " + n);
        long k = 9223372036854775807l;
        long u = k + 1;
        System.out.println("k: " + k + ", k+1: " + u);
        u = k + 2;
        System.out.println("k: " + k + ", k+2: " + u);
        u = k + 3;
        System.out.println("k: " + k + ", k+3: " + u);
    }
}

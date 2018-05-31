/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.pablosesteban.part_I.week_5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author psantama
 */

// mutable data type that represents a set of points in the unit square
public class PointSET {
    private static final double POINT_SIZE = 0.01;
    private static final double LINE_SIZE = 0.001;
    
    private final Set<Point2D> points;
    
    // construct an empty set of point
    public PointSET() {
        this.points = new TreeSet<>();
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return points.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException("null argument!");
        
        points.add(p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException("null argument!");
        
        return points.contains(p);
    }
    
    // draw all points to standard draw
    public void draw() {
        for (Point2D point : points) {
            StdDraw.setPenColor(StdDraw.BLACK);
            
            StdDraw.setPenRadius(0.01);
            
            StdDraw.point(point.x(), point.y());
        }
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException("null argument!");
        
        Stack<Point2D> rectPoints = new Stack<>();
        
        for (Point2D point : points) {
            if (rect.contains(point))
                rectPoints.push(point);
        }
        
        return rectPoints;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException("null argument!");
        
        if (points.isEmpty())
            return null;
        
        Point2D nearest = null;
        double minDistance = Double.MAX_VALUE;
        
        for (Point2D point : points) {
            double distanceTo = point.distanceTo(p);
            
            if (distanceTo < minDistance) {
                nearest = point;
                
                minDistance = distanceTo;
            }
        }
        
        return nearest;
    }
    
    public static void main(String[] args) throws IOException {
        PointSET pointSET = new PointSET();
        
        // sample data in specification
//        kdTree.insert(new Point2D(0.7, 0.2));
//        kdTree.insert(new Point2D(0.5, 0.4));
//        kdTree.insert(new Point2D(0.2, 0.3));
//        kdTree.insert(new Point2D(0.4, 0.7));
//        kdTree.insert(new Point2D(0.9, 0.6));
//        kdTree.insert(new Point2D(0.5, 0.4));
        
        // sample data from checklist
//        kdTree.insert(new Point2D(0.206107, 0.095492));
//        kdTree.insert(new Point2D(0.975528, 0.654508));
//        kdTree.insert(new Point2D(0.024472, 0.345492));
//        kdTree.insert(new Point2D(0.793893, 0.095492));
//        kdTree.insert(new Point2D(0.793893, 0.904508));
//        kdTree.insert(new Point2D(0.975528, 0.345492));
//        kdTree.insert(new Point2D(0.206107, 0.904508));
//        kdTree.insert(new Point2D(0.500000, 0.000000));
//        kdTree.insert(new Point2D(0.024472, 0.654508));
//        kdTree.insert(new Point2D(0.500000, 1.000000));

        String folder = "D:/Users/psantama/Downloads/kdtree/";
        BufferedReader br = new BufferedReader(new FileReader(folder + "vertical7.txt"));
        String line = null;
        while((line = br.readLine()) != null) {
            String[] coor = line.split(" ");
            
            pointSET.insert(new Point2D(Double.parseDouble(coor[0]), Double.parseDouble(coor[1])));
        }
        
        pointSET.draw();
        
        Point2D p = new Point2D(0.761250, 0.317125);
        StdDraw.setPenRadius(POINT_SIZE * 2);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        p.draw();
        Point2D nearest = pointSET.nearest(p);
        StdDraw.setPenRadius(POINT_SIZE);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        nearest.draw();
        System.out.println("nearest to " + p + ": " + nearest);
        
//        RectHV rect = new RectHV(0, 0.2, 0.3, 0.9);
//        StdDraw.setPenRadius(LINE_SIZE * 4);
//        StdDraw.setPenColor(StdDraw.GREEN);
//        rect.draw();
//        System.out.println("range " + rect + ": ");
//        StdDraw.setPenRadius(POINT_SIZE);
//        int count = 0;
//        StdDraw.setPenColor(StdDraw.MAGENTA);
//        for (Point2D point2D : pointSET.range(rect)) {
//            ++count;
//            System.out.println("\t" + point2D);
//            point2D.draw();
//        }
//        System.out.println(count);
    }
}

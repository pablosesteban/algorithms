/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author psantama
 */

// mutable data type that represents a set of points in the unit square
public class PointSET {
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
    
    public static void main(String[] args) {
        PointSET pointSET = new PointSET();
        
        pointSET.insert(new Point2D(0.3, 0.8));
        pointSET.insert(new Point2D(0.8, 0.8));
        pointSET.insert(new Point2D(0.5, 0.3));
        pointSET.insert(new Point2D(0.3, 0.1));
        pointSET.insert(new Point2D(0.9, 0.3));
        
        System.out.println(pointSET.nearest(new Point2D(0, 0)));
        
        pointSET.draw();
    }
}

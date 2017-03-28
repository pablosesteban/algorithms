/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author psantama
 */
public class FastCollinearPoints {
    private static final int MIN_SEGMENT_LENGTH = 4;
    private boolean debug;
    private List<LineSegment> segments;
    
    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points, boolean debug) {
        if (points == null) {
            throw new NullPointerException("null argument");
        }
        
        checkNullPoints(points);
        
        checkDuplicatePoints(points);
        
        segments = new ArrayList<>();
        this.debug = debug;
        
        Point[] toSort = Arrays.copyOf(points, points.length);
        
        for (Point point : points) {
            // points sorted by slope regarding to the each point
            Arrays.sort(toSort, point.slopeOrder());
            
            if (debug) {
                System.out.println("---------------------");
                System.out.println("toSort: " + Arrays.toString(toSort));
            }
            
            double slope = Double.NaN;
            
            List<Point> collinearPoints = new LinkedList<>();
            
            int lastIdx = 0;
            // check if 3 or more adjacent points in the sorted order have equal slopes regarding to each point
            for (int i = 1; i < toSort.length; i++) {
                if (debug) {
                    System.out.println("slopeTo {" + point + ", " + toSort[i] + "}: " + point.slopeTo(toSort[i]));
                }
                
                double slopeTo = point.slopeTo(toSort[i]);
                
                if (slope == slopeTo) {
                    // add the point before
                    collinearPoints.add(toSort[i-1]);
                    
                    lastIdx = i;
                }
                
                slope = slopeTo;
            }
            
            // add the last point
            collinearPoints.add(toSort[lastIdx]);
            
            // add the point by which the slopes are sorted
            collinearPoints.add(point);
            
            if (collinearPoints.size() >= MIN_SEGMENT_LENGTH) {
                Point[] tmp = collinearPoints.toArray(new Point[collinearPoints.size()]);
                
                Arrays.sort(tmp);
                
                LineSegment lineSegment = new LineSegment(tmp[0], tmp[tmp.length - 1]);
                
                boolean duplicated = false;
                
                // check if segment is duplicated
                for (LineSegment segment : segments) {
                    if (segment.toString().equals(lineSegment.toString())) {
                        duplicated = true;
                        
                        break;
                    }
                }
                
                if (!duplicated) {
                    segments.add(lineSegment);
                }
            }
            
            if (debug) {
                System.out.println("---------------------");
            }
        }
    }
    
    private void checkNullPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException("null point at index " + i);
            }
        }
    }
    
    private void checkDuplicatePoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i+1]) == 0)
                throw new IllegalArgumentException("repeated point at index " + i + ": " + points[i]);
        }
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }
    
    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:/Users/psantama/Downloads/collinear/input6.txt"));
        int n = Integer.parseInt(br.readLine());
        
        Point[] points = new Point[n];
        
        String line = null;
        int i = 0;
        while ((line = br.readLine()) != null) {
            String[] coors = line.trim().split("\\s+");
            
            points[i] = new Point(Integer.parseInt(coors[0]), Integer.parseInt(coors[1]));
            
            i++;
        }
        
        FastCollinearPoints bcp = new FastCollinearPoints(points, false);
        
        System.out.println("segments: ");
        
        for (LineSegment segment : bcp.segments) {
            System.out.println("\t" + segment);
        }
        
        System.out.println("# of segments: " + bcp.numberOfSegments());
    }
}

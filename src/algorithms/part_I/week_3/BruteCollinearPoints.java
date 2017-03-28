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
import java.util.List;

/**
 *
 * @author psantama
 */
public class BruteCollinearPoints {
    private List<LineSegment> segments;
    private static final int SEGMENT_LENGTH = 4;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("null argument");
        }
        
        // check it before sorting!
        checkNullPoints(points);
        
        segments = new ArrayList<>();
        
        // sort array by natural order
        Arrays.sort(points);
        
        for (int i = 0; i <= points.length - SEGMENT_LENGTH; i++) {
            for (int j = i+1; j <= points.length - (SEGMENT_LENGTH-1); j++) {
                // calculates slope between first and second points
                double slopeFS = points[i].slopeTo(points[j]);
                
                // by definition the slope of a point to itself is NEGATIVE_INFINITY
                if (slopeFS == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("repeated point at index " + i + ": " + points[i]);
                }
                
                for (int k = j+1; k <= points.length - (SEGMENT_LENGTH-2); k++) {
                    // calculates slope between first and third points
                    double slopeFT = points[i].slopeTo(points[k]);
                    
                    // if first two slopes are not equal there are no collinear
                    if (slopeFS == slopeFT) {
                        for (int l = k+1; l < points.length; l++) {
                            // calculates slope between first and fourth points
                            double slopeFF = points[i].slopeTo(points[l]);
                            
                            if (slopeFS == slopeFF) {
                                segments.add(new LineSegment(points[i], points[l]));
                            }
                        }
                    }
                }
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
    
    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }
    
    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:/Users/psantama/Downloads/collinear/input40.txt"));
        int n = Integer.parseInt(br.readLine());
        
        Point[] points = new Point[n];
        
        String line = null;
        int i = 0;
        while ((line = br.readLine()) != null) {
            String[] coors = line.trim().split("\\s+");
            
            points[i] = new Point(Integer.parseInt(coors[0]), Integer.parseInt(coors[1]));
            
            i++;
        }
        
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        
        System.out.println("segments: " + bcp.segments);
        System.out.println("# of segments: " + bcp.numberOfSegments());
    }
}

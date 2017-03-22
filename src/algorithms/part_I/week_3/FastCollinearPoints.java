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
import java.util.Arrays;

/**
 *
 * @author psantama
 */
public class FastCollinearPoints {
    private LineSegment[] segments;
    private static final int MIN_SEGMENT_LENGTH = 4;
    
    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("null argument");
        }
        
        checkNullPoints(points);
        
        checkDuplicatePoints(points);
        
        for (Point point : points) {
            Point[] sorted = Arrays.copyOf(points, points.length);
            
            // points sorted regarding "point"
            Arrays.sort(sorted, point.slopeOrder());
            
            for (int i = 1; i < sorted.length; i++) {
                if (point.compareTo(sorted[i]) == 0) {
                    
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
    
    private void checkDuplicatePoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i+1]) == 0)
                throw new IllegalArgumentException("repeated point at index " + i + ": " + points[i]);
        }
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }
    
    // the line segments
    public LineSegment[] segments() {
        return segments;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:/Users/psantama/Downloads/collinear/input8.txt"));
        int n = Integer.parseInt(br.readLine());
        
        Point[] points = new Point[n];
        
        String line = null;
        int i = 0;
        while ((line = br.readLine()) != null) {
            String[] coors = line.trim().split("\\s+");
            
            points[i] = new Point(Integer.parseInt(coors[0]), Integer.parseInt(coors[1]));
            
            i++;
        }
        
        FastCollinearPoints bcp = new FastCollinearPoints(points);
        
//        System.out.println("segments: " + Arrays.toString(bcp.segments));
//        System.out.println("# of segments: " + bcp.numberOfSegments());
    }
}

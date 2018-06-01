/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author psantama
 */
public class BruteCollinearPoints {
    private static final int SEGMENT_LENGTH = 4;
    private List<LineSegment> segments;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points, boolean debug) {
        if (points == null) {
            throw new NullPointerException("null argument");
        }
        
        // check it before sorting!
        checkNullPoints(points);
        
        checkDuplicatedPoints(points);
        
        segments = new ArrayList<>();
        
        for (int i = 0; i <= points.length - SEGMENT_LENGTH; i++) {
            Point minPoint = points[i];
            Point maxPoint = points[i];
            
            if (debug) {
                System.out.println("----------------------");
                
                System.out.println("First point: " + points[i]);
            }
            
            for (int j = i+1; j <= points.length - (SEGMENT_LENGTH - 1); j++) {
                if (debug) {
                    System.out.print("\tSecond point: " + points[j]);
                }
                
                // calculates slope between first and second points
                double slopeFS = points[i].slopeTo(points[j]);
                
                if (debug) {
                    System.out.println(", slopeFS: " + slopeFS);
                }
                
                for (int k = j+1; k <= points.length - (SEGMENT_LENGTH - 2); k++) {
                    if (debug) {
                        System.out.print("\t\tThird point: " + points[k]);
                    }
                    
                    // calculates slope between first and third points
                    double slopeFT = points[i].slopeTo(points[k]);
                    
                    if (debug) {
                        System.out.println(", slopeFT: " + slopeFT);
                    }
                    
                    // if first two slopes are not equal there are no collinear
                    if (slopeFS == slopeFT) {
                        for (int l = k+1; l < points.length; l++) {
                            if (debug) {
                                System.out.print("\t\t\tFourth point: " + points[l]);
                            }
                            
                            // calculates slope between first and fourth points
                            double slopeFF = points[i].slopeTo(points[l]);
                            
                            if (debug) {
                                System.out.println(", slopeFF: " + slopeFF);
                            }
                            
                            if (slopeFS == slopeFF) {
                                // check if second point is max or min point
                                if (minPoint.compareTo(points[j]) > 0) {
                                    minPoint = points[j];
                                } else {
                                    if (maxPoint.compareTo(points[j]) < 0)
                                        maxPoint = points[j];
                                }
                                
                                // check if third point is max or min point
                                if (minPoint.compareTo(points[k]) > 0) {
                                    minPoint = points[k];
                                } else {
                                    if (maxPoint.compareTo(points[k]) < 0)
                                        maxPoint = points[k];
                                }
                                
                                // check if fourth point is max or min point
                                if (minPoint.compareTo(points[l]) > 0) {
                                    minPoint = points[l];
                                } else {
                                    if (maxPoint.compareTo(points[l]) < 0)
                                        maxPoint = points[l];
                                }
                                
                                LineSegment lineSegment = new LineSegment(minPoint, maxPoint);
                                
                                if (debug) {
                                    System.out.println("\t\t\t\tsegment: " + points[i] + "->" + points[j] + "->" + points[k] + "->" + points[l]);
                                    
                                    System.out.println("\t\t\t\tlineSegment: " + lineSegment);
                                }
                                
                                segments.add(lineSegment);
                                
                                /*
                                reset min and max points:
                                    could be more than one segment starting at the same initial point points[i]
                                    
                                    if not, min or max points are "cached" from previous segments and next could be wrong endpoints
                                */
                                minPoint = points[i];
                                maxPoint = points[i];
                            }
                        }
                    }
                }
            }
            
            if (debug) {
                System.out.println("----------------------");
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
    
    // do better?
    private void checkDuplicatedPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i+1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("repeated point at index " + i + ": " + points[i]);
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
        
        BruteCollinearPoints bcp = new BruteCollinearPoints(points, true);
        
        System.out.println("segments: " + bcp.segments);
        System.out.println("# of segments: " + bcp.numberOfSegments());
    }
}

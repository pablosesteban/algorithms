/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablosesteban.part_I.week_5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author psantama
 */

/*
a mutable data type that uses a 2d-tree to implement the same API as PointSET

a 2d-tree is a generalization of a BST to two-dimensional keys
*/
public class KdTree {
    private static final double POINT_SIZE = 0.01;
    private static final double LINE_SIZE = 0.001;
    
    private Node root;
    private int size;
    
    /*
    is static because it does not refer to a generic Key or Value type that depends on the object associated with the outer class
    
    this saves the 8-byte inner class object overhead
    */
    private static class Node {
        // the point
        private Point2D p;
        
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;
        
        // parent node
        private Node parent;
        
        // the left/bottom subtree
        private Node lb;
        
        // the right/top subtree
        private Node rt;
        
        public Node(Point2D p, Node parent) {
            this.p = p;
            this.parent = parent;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            
            sb.append("Node{" + "point: " + p + ", rectangle: " + rect + ", parent: ");
            
            if (parent != null) {
                sb.append(parent.p);
            } else {
                sb.append("null");
            }
            
            sb.append(", lb: ");
            
            if (lb != null) {
                sb.append(lb.p);
            } else {
                sb.append("null");
            }
            
            sb.append(", rt: ");
            
            if (rt != null) {
                sb.append(rt.p);
            } else {
                sb.append("null");
            }
            
            sb.append("}");
            
            return sb.toString();
        }
    }
    
    // construct an empty set of point
    public KdTree() {
        
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }
    
    // number of points in the set
    public int size() {
        return size;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException("null argument!");
        
        root = insert(root, 0, p, null);
    }
    
    private Node insert(Node n, int level, Point2D p, Node parent) {
        if (n == null) {
            Node node = new Node(p, parent);
            
            /********************** CREATES THE RECTANGLE WHICH POINT BELONGS TO **********************/
            RectHV rectangle = null;
            // root node
            if (parent == null) {
                // xmin, ymin, xmax, ymax
                rectangle = new RectHV(0.0, 0.0, 1.0, 1.0);
            } else {
                // even level (compare to its parent which is splitted by y-coor)
                if ((level & 1) == 0) {
                    // bottom children
                    if (Double.compare(p.y(), parent.p.y()) < 0) { // if the point to be inserted has a SMALLER X/Y-COOR than the point at the root, go LEFT; OTHERWISE go RIGHT
                        rectangle = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.p.y());
                        
                    } else { // top children
                        rectangle = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(), parent.rect.ymax());
                    }
                } else { // odd level (compare to its parent which is splitted by x-coor)
                    // left children
                    if (Double.compare(p.x(), parent.p.x()) < 0) { // if the point to be inserted has a SMALLER X/Y-COOR than the point at the root, go LEFT; OTHERWISE go RIGHT
                        rectangle = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(), parent.rect.ymax());
                    } else { // right children
                        rectangle = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
                    }
                }
            }
            /********************** CREATES THE RECTANGLE WHICH POINT BELONGS TO **********************/
            
            node.rect = rectangle;
            
            size++;
            
            return node;
        }
        
        // check duplicates
        if (n.p.compareTo(p) == 0)
            return n;
        
        // searching
        int comparison = 0;
        
        // check if level is even or odd (instead of using the % operator)
        if ((level & 1) == 0) {
            // even level: if the point to be inserted has a smaller x-coordinate than the point at the root, go left; otherwise go right
            comparison = Double.compare(p.x(), n.p.x());
        } else {
            // odd level: if the point to be inserted has a smaller y-coordinate than the point in the node, go left; otherwise go right
            comparison = Double.compare(p.y(), n.p.y());
        }
        
        // if the point to be inserted has a SMALLER X/Y-COOR than the point at the root, go LEFT; OTHERWISE go RIGHT
        if (comparison >= 0) {
            n.rt = insert(n.rt, ++level, p, n);
        } else { // if < goes LEFT/BOTTOM
            n.lb = insert(n.lb, ++level, p, n);
        }
        
        return n;
    }
    
    private List<Node> getNodeTrace(Node n) {
        LinkedList<Node> nodes = new LinkedList<>();
        
        getNodeTrace(n, nodes);
        
        return nodes;
    }
    
    private void getNodeTrace(Node n, LinkedList<Node> nodes) {
        if (n == null)
            return;
        
        getNodeTrace(n.parent, nodes);
        
        nodes.add(nodes.size(), n);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException("null argument!");
        
        Node n = root;
        int level = 0;
        while (n != null) {
            if (p.compareTo(n.p) == 0)
                return true;
            
            int comparison = 0;
            
            if ((level & 1) == 0) {
                comparison = Double.compare(p.x(), n.p.x());
            } else {
                comparison = Double.compare(p.y(), n.p.y());
            }
            
            if (comparison < 0) {
                n = n.lb;
            } else {
                n = n.rt;
            }
            
            level++;
        }
        
        return false;
    }
    
    // draw all points to standard draw
    public void draw() {
        List<Node> nodeRoot = new ArrayList<>();
        nodeRoot.add(root);
        
        draw(nodeRoot, 0);
    }
    
    private void draw(List<Node> nodes, int level) {
        if (nodes.isEmpty())
            return;
        
        List<Node> nextLevelNodes = new ArrayList<>();
        
        for (Node node : nodes) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(POINT_SIZE);
            StdDraw.point(node.p.x(), node.p.y());
            
            // root level (split y-coor)
            if (level == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                
                StdDraw.setPenRadius(LINE_SIZE);
                
                StdDraw.line(node.p.x(), 0, node.p.x(), 1);
            } else if ((level & 1) == 0) { // even levels (split y-coor)
                StdDraw.setPenColor(StdDraw.RED);
                
                StdDraw.setPenRadius(LINE_SIZE);
                
                if (Double.compare(node.p.y(), node.parent.p.y()) <= 0) {
                    StdDraw.line(node.p.x(), 0, node.p.x(), node.parent.p.y());
                } else {
                    StdDraw.line(node.p.x(), 1, node.p.x(), node.parent.p.y());
                }
            } else { // odd levels (split x-coor)
                StdDraw.setPenColor(StdDraw.BLUE);
                
                StdDraw.setPenRadius(LINE_SIZE);
                
                if (Double.compare(node.p.x(), node.parent.p.x()) <= 0) {
                    StdDraw.line(0, node.p.y(), node.parent.p.x(), node.p.y());
                } else {
                    StdDraw.line(1, node.p.y(), node.parent.p.x(), node.p.y());
                }
            }
            
            if (node.lb != null) {
                nextLevelNodes.add(node.lb);
            }
            
            if (node.rt != null) {
                nextLevelNodes.add(node.rt);
            }
        }
        
        draw(nextLevelNodes, ++level);
    }
    
    @Override
    public String toString() {
        List<Node> nodeRoot = new LinkedList<>();
        nodeRoot.add(root);
        
        StringBuilder sb = new StringBuilder();
        
        levelOrder(nodeRoot, sb, 0, new ArrayList<>());
        
        return sb.toString();
    }
    
    private List<Node> levelOrder(List<Node> nodes, StringBuilder sb,int level, List<Node> output) {
        if (nodes.isEmpty())
            return null;
        
        List<Node> nextLevelNodes = new LinkedList<>();
        
        output.addAll(nodes);
        
        sb.append("level " + level + ": ");
        for (Node node : nodes) {
            if (node != null) {
                sb.append(node.p);
                sb.append(" ");
                
                if (node.lb != null) {
                    nextLevelNodes.add(node.lb);
                } else {
                    nextLevelNodes.add(null);
                }
                
                if (node.rt != null) {
                    nextLevelNodes.add(node.rt);
                } else {
                    nextLevelNodes.add(null);
                }
            } else {
                sb.append("null");
                sb.append(" ");
            }
        }
        
        sb.append("\n");
        
        levelOrder(nextLevelNodes, sb, ++level, output);
        
        return output;
    }
    
    /*
    all points that are inside the rectangle
    
    RANGE SEARCH:
        start at the root and recursively search for points in both subtrees using the following pruning rule:
            if the query rectangle does not intersect the rectangle corresponding to a node, there is no need to explore that node (or its subtrees)
            
            a subtree is searched only if it might contain a point contained in the query rectangle
    */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException("null argument!");
        
        Stack<Point2D> points = new Stack<>();
        
        range(root, rect, 0, points);
        
        return points;
    }
    
    private void range(Node n, RectHV rect, int level, Stack<Point2D> points) {
        if (n == null)
            return;
        
        if (!rect.intersects(n.rect))
            return;
        
        // splitted by x-coor
        if ((level & 1) == 0) {
            if (Double.compare(rect.xmax(), n.p.x()) <= 0) {
                range(n.lb, rect, ++level, points);

                // right children MUST be visited as comparison can be EQUALS (when inserting node goes right if comparison is 0)
                range(n.rt, rect, level, points);
            } else if (Double.compare(rect.xmin(), n.p.x()) < 0) { // if the point lies inside the rectangle search both subtrees
                range(n.lb, rect, ++level, points);
                
                // ++level above!
                range(n.rt, rect, level, points);
            } else {
                range(n.rt, rect, ++level, points);
            }
            
            if (rect.contains(n.p))
                points.push(n.p);
        } else { // splitted by y-coor
            if (Double.compare(rect.ymax(), n.p.y()) <= 0) {
                range(n.lb, rect, ++level, points);
                
                // right children MUST be visited as comparison can be EQUALS (when inserting node goes right if comparison is 0)
                range(n.rt, rect, level, points);
            } else if (Double.compare(rect.ymin(), n.p.y()) < 0) { // if the point lies inside the rectangle search both subtrees
                range(n.lb, rect, ++level, points);
                
                // ++level above!
                range(n.rt, rect, level, points);
            } else {
                range(n.rt, rect, ++level, points);
            }
            
            if (rect.contains(n.p))
                points.push(n.p);
        }
    }
    
    /*
    a nearest neighbor in the set to point p; null if the set is empty
    
    NEAREST NEIGHBOR SEARCH:
        start at the root and recursively search in both subtrees using the following pruning rule:
            if the closest point discovered so far is closer than the distance between the QUERY POINT and the RECTANGLE corresponding to a node, there is no need to explore that node (or its subtrees)
    
            a node is searched only if it might contain a point that is closer than the best one found so far
            
            organize your recursive method so that when there are two possible subtrees to go down, you always choose the subtree that is on the same side of the splitting line as the query point as the first subtree to explore—the closest point found while exploring the first subtree may enable pruning of the second subtree
    */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException("null argument!");
        
        if (isEmpty())
            return null;
        
        return nearest(p, root, 0, root.p);
    }
    
    private Point2D nearest(Point2D p, Node n, int level, Point2D minP) {
        if (n == null)
            return minP;
        
        if (p.distanceTo(n.p) < p.distanceTo(minP))
            minP = n.p;
        
        // splitted by x-coor
        if ((level & 1) == 0) {
            if (Double.compare(p.x(), n.p.x()) < 0) {
                minP = nearest(p, n.lb, ++level, minP);
                
                // only visit the right node if it is not null and the distance is less than the distance to the left children (there is a possibility of reaching a nearer node)
                if (n.rt != null && (Double.compare(minP.distanceTo(p), n.rt.rect.distanceTo(p)) >= 0))
                    minP = nearest(p, n.rt, ++level, minP);
            } else {
                minP = nearest(p, n.rt, ++level, minP);
                
                // only visit the left node if it is not null and the distance is less than the distance to the right children (there is a possibility of reaching a nearer node)
                if (n.lb != null && (Double.compare(minP.distanceTo(p), n.lb.rect.distanceTo(p)) >= 0))
                    minP = nearest(p, n.lb, ++level, minP);
            }
        } else { // splitted by y-coor
            if (Double.compare(p.y(), n.p.y()) < 0) {
                minP = nearest(p, n.lb, ++level, minP);
                
                // only visit the right node if it is not null and the distance is less than the distance to the left children (there is a possibility of reaching a nearer node)
                if (n.rt != null && (Double.compare(minP.distanceTo(p), n.rt.rect.distanceTo(p)) >= 0))
                    minP = nearest(p, n.rt, ++level, minP);
            } else {
                minP = nearest(p, n.rt, ++level, minP);
                
                // only visit the left node if it is not null and the distance is less than the distance to the right children (there is a possibility of reaching a nearer node)
                if (n.lb != null && (Double.compare(minP.distanceTo(p), n.lb.rect.distanceTo(p)) >= 0))
                    minP = nearest(p, n.lb, ++level, minP);
            }
        }
        
        return minP;
    }
    
    public static void main(String[] args) throws IOException {
        KdTree kdTree = new KdTree();
        
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
        BufferedReader br = new BufferedReader(new FileReader(folder + "input10.txt"));
        String line = null;
        while((line = br.readLine()) != null) {
            String[] coor = line.split(" ");
            
            kdTree.insert(new Point2D(Double.parseDouble(coor[0]), Double.parseDouble(coor[1])));
        }
        
        kdTree.draw();
        
        Point2D p = new Point2D(0.5078125, 0.482421875);
        StdDraw.setPenRadius(POINT_SIZE * 2);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        p.draw();
        Point2D nearest = kdTree.nearest(p);
        StdDraw.setPenRadius(POINT_SIZE);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        nearest.draw();
        System.out.println("nearest to " + p + ": " + nearest);
        
//        RectHV rect = new RectHV(0, 0.5, 0.9, 0.9);
//        StdDraw.setPenRadius(LINE_SIZE * 4);
//        StdDraw.setPenColor(StdDraw.GREEN);
//        rect.draw();
//        System.out.println("range " + rect + ": ");
//        StdDraw.setPenRadius(POINT_SIZE);
//        int count = 0;
//        StdDraw.setPenColor(StdDraw.MAGENTA);
//        for (Point2D point2D : kdTree.range(rect)) {
//            ++count;
//            System.out.println("\t" + point2D);
//            point2D.draw();
//        }
//        System.out.println(count);
        
        System.out.println(kdTree);
    }
}

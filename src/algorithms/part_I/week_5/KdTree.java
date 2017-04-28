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
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
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
    private static final double pointSize = 0.01;
    private static final double lineSize = 0.001;
    
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
            }else {
                sb.append("null");
            }
            
            sb.append(", lb: ");
            
            if (lb != null) {
                sb.append(lb.p);
            }else {
                sb.append("null");
            }
            
            sb.append(", rt: ");
            
            if (rt != null) {
                sb.append(rt.p);
            }else {
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
        
        size++;
    }
    
    private Node insert(Node n, int level, Point2D p, Node parent) {
        if (n == null) {
            Node node = new Node(p, parent);
            
            /********************** CREATES THE RECTANGLE WHICH POINT BELONGS TO **********************/
            RectHV rectangle = null;
            // root node
            if (parent == null) {
                //xmin, ymin, xmas, ymax
                rectangle = new RectHV(0, 0, 1, 1);
            }else {
                // even level (compare to its parent which is splitted by y-coor)
                if ((level & 1) == 0) {
                    // bottom children
                    if (Double.compare(p.y(), parent.p.y()) <= 0) {
                        rectangle = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.p.y());
                    }else { // top children
                        rectangle = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(), parent.rect.ymax());
                    }
                }else { // odd level (compare to its parent which is splitted by x-coor)
                    // left children
                    if (Double.compare(p.x(), parent.p.x()) <= 0) {
                        rectangle = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(), parent.rect.ymax());
                    }else { // right children
                        rectangle = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
                    }
                }
            }
            /********************** CREATES THE RECTANGLE WHICH POINT BELONGS TO **********************/
            
            node.rect = rectangle;
            
            return node;
        }
        
        // searching
        int comparison = 0;
        
        // check if level is even or odd (instead of using the % operator)
        if ((level & 1) == 0) {
            // even level: if the point to be inserted has a smaller x-coordinate than the point at the root, go left; otherwise go right
            comparison = Double.compare(p.x(), n.p.x());
        }else {
            // odd level: if the point to be inserted has a smaller y-coordinate than the point in the node, go left; otherwise go right
            comparison = Double.compare(p.y(), n.p.y());
        }
        
        if(comparison > 0) {
            n.rt = insert(n.rt, ++level, p, n);
        }else if(comparison <= 0) {
            n.lb = insert(n.lb, ++level, p, n);
        }else {
            // update the value if exist the key
            n.p = p;
        }
        
        return n;
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
            }else {
                comparison = Double.compare(p.y(), n.p.y());
            }
            
            if (comparison <= 0){
                n = n.lb;
            }else {
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
            StdDraw.setPenRadius(pointSize);
            StdDraw.point(node.p.x(), node.p.y());
            
            // root level (split y-coor)
            if (level == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                
                StdDraw.setPenRadius(lineSize);
                
                StdDraw.line(node.p.x(), 0, node.p.x(), 1);
            }else if ((level & 1) == 0) { // even levels (split y-coor)
                StdDraw.setPenColor(StdDraw.RED);
                
                StdDraw.setPenRadius(lineSize);
                
                if(Double.compare(node.p.y(), node.parent.p.y()) <= 0) {
                    StdDraw.line(node.p.x(), 0, node.p.x(), node.parent.p.y());
                }else {
                    StdDraw.line(node.p.x(), 1, node.p.x(), node.parent.p.y());
                }
            }else { //odd levels (split x-coor)
                StdDraw.setPenColor(StdDraw.BLUE);
                
                StdDraw.setPenRadius(lineSize);
                
                if(Double.compare(node.p.x(), node.parent.p.x()) <= 0) {
                    StdDraw.line(0, node.p.y(), node.parent.p.x(), node.p.y());
                }else {
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
    
    private List<Node> levelOrder(List<Node> nodes, StringBuilder sb, int level, List<Node> output) {
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
                }else {
                    nextLevelNodes.add(null);
                }
                
                if (node.rt != null) {
                    nextLevelNodes.add(node.rt);
                }else {
                    nextLevelNodes.add(null);
                }
            }else {
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
        
        if ((level & 1) == 0) {
            if (Double.compare(rect.xmax(), n.p.x()) <= 0) {
                range(n.lb, rect, ++level, points);
            }else {
                range(n.rt, rect, ++level, points);
            }
            
            if (rect.contains(n.p))
                points.push(n.p);
        }else {
            if (Double.compare(rect.ymax(), n.p.y()) <= 0) {
                range(n.lb, rect, ++level, points);
            }else {
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
            if the closest point discovered so far is closer than the distance between the query point and the rectangle corresponding to a node, there is no need to explore that node (or its subtrees)
    
            a node is searched only if it might contain a point that is closer than the best one found so far
            
            organize your recursive method so that when there are two possible subtrees to go down, you always choose the subtree that is on the same side of the splitting line as the query point as the first subtree to explore—the closest point found while exploring the first subtree may enable pruning of the second subtree
    */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException("null argument!");
        
        throw new UnsupportedOperationException("Not implemented yet!");
    }
    
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        
        kdTree.draw();
        
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(kdTree.root);
        List<Node> output = new ArrayList<>();
        kdTree.levelOrder(nodes, new StringBuilder(), 0, output);
        for (Node node : output) {
            System.out.println(node);
        }
        
        RectHV rectHV = new RectHV(0.1, 0.1, 0.55, 0.45);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        rectHV.draw();
        
        Iterable<Point2D> range = kdTree.range(rectHV);
        System.out.println(range);
        
//        StdDraw.setPenRadius(0.01);
//        
//        StdDraw.setPenColor(StdDraw.GRAY);
//        output.get(0).rect.draw();
//        
//        StdDraw.setPenColor(StdDraw.CYAN);
//        output.get(1).rect.draw();
//        
//        StdDraw.setPenColor(StdDraw.PINK);
//        output.get(2).rect.draw();
//        
//        StdDraw.setPenColor(StdDraw.ORANGE);
//        output.get(3).rect.draw();
//        
//        StdDraw.setPenColor(StdDraw.MAGENTA);
//        output.get(4).rect.draw();
    }
}

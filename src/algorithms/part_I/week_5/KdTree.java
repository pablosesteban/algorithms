/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
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
            
//            if (parent == null) {
//                // xmin, ymin, xmax, ymax
//                node.rect = new RectHV(0, 0, 1, 1);
//            }else {
//                // check if level is even or odd (instead of using the % operator)
//                if ((level & 1) == 0) {
//                    // even level: if the point to be inserted has a smaller x-coordinate than the point at the root, go left; otherwise go right
//                    int comparison = Double.compare(p.x(), n.p.x());
//                    
//                    // left children
//                    if(comparison <= 0) {
//                        node.rect = new RectHV(node.parent.rect.xmin(), node.parent.rect.ymin(), node.parent.p.x(), node.parent.rect.ymin());
//                    }else { // right children
//                        node.rect = new RectHV(node.parent.p.x(), node.parent.rect.xmax(), node.parent.rect.xmax(), node.parent.rect.ymax());
//                    }
//                }else {
//                    // odd level: if the point to be inserted has a smaller y-coordinate than the point in the node, go left; otherwise go right
//                    int comparison = Double.compare(p.y(), n.p.y());
//                    
//                    
//                }
//            }
            
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
        
        levelOrder(nodeRoot, sb, 0);
        
        return sb.toString();
    }
    
    private void levelOrder(List<Node> nodes, StringBuilder sb, int level) {
        if (nodes.isEmpty())
            return;
        
        List<Node> nextLevelNodes = new LinkedList<>();
        
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
        
        levelOrder(nextLevelNodes, sb, ++level);
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException("null argument!");
        
        throw new UnsupportedOperationException("Not implemented yet!");
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException("null argument!");
        
        throw new UnsupportedOperationException("Not implemented yet!");
    }
    
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        
        System.out.println("isEmpty: " + kdTree.isEmpty());
        
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        
        System.out.println("isEmpty: " + kdTree.isEmpty());
        
        System.out.println("size: " + kdTree.size());
        
        System.out.println(kdTree);
        
        kdTree.draw();
        
        System.out.println(kdTree.contains(new Point2D(0.4, 0.7)));
        System.out.println(kdTree.contains(new Point2D(0.4, 0.4)));
    }
}

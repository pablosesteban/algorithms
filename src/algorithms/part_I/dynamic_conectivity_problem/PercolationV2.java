/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms.part_I.dynamic_conectivity_problem;

//import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author psantama
 */
public class PercolationV2 {
    private static final byte CONNECTED_TOP = 1;
    private static final byte CONNECTED_BOTTOM = 2;
    private static final byte CONNECTED_BOTH = 3;
    
    //array holding open status of each site
    private boolean[] openStatus;
    
    //array holding connected status of each site
    private byte[] connectStatus;
    
    //data structure to hold connections between sites
    private WeightedQuickUnionUF connections;
    
    private int n;
    
    private int openSites;
    
    private boolean percolates;
    
    //create n-by-n grid, with all sites blocked
    public PercolationV2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n: " + n);
        }
        
        //NO VIRTUAL POINTS!!!
        openStatus = new boolean[n * n];
        
        connectStatus = new byte[n * n];
        
        connections = new WeightedQuickUnionUF(n * n);
        
        this.n = n;
    }
    
    //open site (row, col) if it is not already open
    public void open(int row, int col) {
        int siteIndex = getSiteIndex(row, col);
        
        if (openStatus[siteIndex]){
            return;
        }
        
        System.out.println("open {row: " + row + ", col: " + col + "}");
        
        openStatus[siteIndex] = true;
        
        openSites++;
        
        boolean top = false;
        boolean bottom = false;
        
        //check left site and if is open connect to it
        if (col > 1 && isOpen(row, col - 1)) {
            System.out.println("Left Union: {" + siteIndex + ", " + (siteIndex - 1) + "}");
            
            //check if the site's root or its left neighbor's root is connected to top
            top = isConnectedTop(siteIndex, siteIndex - 1);
            
            //check if the site's root or its left neighbor's root is connected to bottom
            bottom = isConnectedBottom(siteIndex, siteIndex - 1);
            
            connections.union(siteIndex, siteIndex - 1);
        }
        
        //check right site and if is open connect to it
        if (col < n && isOpen(row, col + 1)) {
            System.out.println("Right Union: {" + siteIndex + ", " + (siteIndex + 1) + "}");
            
            //check if the site's root or its right neighbor's root is connected to top
            top = isConnectedTop(siteIndex, siteIndex + 1);
            
            //check if the site's root or its right neighbor's root is connected to bottom
            bottom = isConnectedBottom(siteIndex, siteIndex + 1);
            
            connections.union(siteIndex, siteIndex + 1);
        }
        
        //check upper site and if is open connect to it
        if (row > 1 && isOpen(row - 1, col)) {
            System.out.println("Top Union: {" + siteIndex + ", " + (siteIndex - n) + "}");
            
            //check if the site's root or its upper neighbor's root is connected to top
            top = isConnectedTop(siteIndex, siteIndex - n);
            
            //check if the site's root or its upper neighbor's root is connected to bottom
            bottom = isConnectedBottom(siteIndex, siteIndex - n);
            
            connections.union(siteIndex, siteIndex - n);
        }
        
        //check bottom site and if is open connect to it
        if (row < n && isOpen(row + 1, col)) {
            System.out.println("Bottom Union: {" + siteIndex + ", " + (siteIndex + n) + "}");
            
            //check if the site's root or its bottom neighbor's root is connected to top
            top = isConnectedTop(siteIndex, siteIndex + n);
            
            //check if the site's root or its bottom neighbor's root is connected to bottom
            bottom = isConnectedBottom(siteIndex, siteIndex + n);
            
            connections.union(siteIndex, siteIndex + n);
        }
        
        if (row == 1 || top) {
            connectStatus[connections.find(siteIndex)] |= CONNECTED_TOP;
        }
        
        if (row == n || bottom) {
            connectStatus[connections.find(siteIndex)] |= CONNECTED_BOTTOM;
        }
        
        if ((connectStatus[connections.find(siteIndex)] & CONNECTED_BOTH) == CONNECTED_BOTH) {
            percolates = true;
        }
    }
    
    private boolean isConnectedTop(int site, int neighbor) {
        if ((connectStatus[connections.find(site)] & CONNECTED_TOP) == CONNECTED_TOP || (connectStatus[connections.find(neighbor)] & CONNECTED_TOP) == CONNECTED_TOP) {
            return true;
        }
        
        return false;
    }
    
    private boolean isConnectedBottom(int site, int neighbor) {
        if ((connectStatus[connections.find(site)] & CONNECTED_BOTTOM) == CONNECTED_BOTTOM || (connectStatus[connections.find(neighbor)] & CONNECTED_BOTTOM) == CONNECTED_BOTTOM) {
            return true;
        }
        
        return false;
    }
    
    //is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openStatus[getSiteIndex(row, col)];
    }
    
    /*
    transform two-dimension coor to one-dimension coor
    
    takes into account the 1-based index
    */
    private int getSiteIndex(int row, int col) {
        if (row < 1 || row > n) {
            throw new IndexOutOfBoundsException("row: " + row);
        }
        
        if (col < 1 || col > n) {
            throw new IndexOutOfBoundsException("col: " + col);
        }
        
        return (col - 1) + (row - 1) * n;
    }
    
    /*
    is site (row, col) full (blocked)?
    
    a full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites
    */
    public boolean isFull(int row, int col) {
        if (row == 1) {
            return openStatus[getSiteIndex(row, col)];
        }
        
        return (connectStatus[connections.find(getSiteIndex(row, col))] & CONNECTED_TOP) == CONNECTED_TOP;
    }
    
    //number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }
    
    /*
    does the system percolate?
    
    the system percolates if there is a full site in the bottom row
    */
    public boolean percolates() {
        return percolates;
    }
    
    @Override
    public String toString() {
        StringBuilder cStatus = new StringBuilder();
        StringBuilder oStatus = new StringBuilder();
        StringBuilder c = new StringBuilder();
        
        cStatus.append("connectStatus:\n");
        oStatus.append("openStatus:\n");
        c.append("connections:\n");
        
        int j = 0;
        for (int i = 0; i < n * n; i++) {
            cStatus.append("[" + i + "] ");
            cStatus.append(connectStatus[i]);
            
            oStatus.append("[" + i + "] ");
            oStatus.append(openStatus[i]);
            
            c.append("[" + i + "] ");
            c.append(connections.parent[i]);
            
            if (i == n + j - 1) {
                cStatus.append("\n");
                oStatus.append("\n");
                c.append("\n");
                
                j += n;
            }else {
                cStatus.append(", ");
                oStatus.append(", ");
                c.append(", ");
            }
        }
        
        return c.toString() + "\n" + oStatus.toString() + "\n" + cStatus.toString();
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:/Users/psantama/Downloads/percolation/input10-no.txt"));
        
        PercolationV2 p = new PercolationV2(Integer.parseInt(br.readLine()));

        Set<String[]> coors = new HashSet<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (p.openSites == 41) {
                break;
            }
            
            if (!"".equals(line)) {
                String[] sites = line.trim().split("\\s+");
                
                System.out.println("----------");
                
                coors.add(sites);
                
                p.open(Integer.parseInt(sites[0]), Integer.parseInt(sites[1]));
                
                System.out.println("\tis open? {" + Integer.parseInt(sites[0]) + ", " + Integer.parseInt(sites[1]) + "} " + p.isOpen(Integer.parseInt(sites[0]), Integer.parseInt(sites[1])));
                System.out.println("\tpercolates? " + p.percolates());
                System.out.println("\t# opens sites: " + p.numberOfOpenSites());
                System.out.println("\tis full? {" + Integer.parseInt(sites[0]) + ", " + Integer.parseInt(sites[1]) + "} " + p.isFull(Integer.parseInt(sites[0]), Integer.parseInt(sites[1])));
                
                System.out.println(p);
                
                System.out.println("----------");
            }
        }
        
        System.out.println(p.connections.count());
        
        Map<Integer, List<Integer>> connectedComponents = new HashMap<>();
        
        for (int i = 0; i < p.connections.parent.length; i++) {
            int root = p.connections.find(i);
            
            if (!connectedComponents.containsKey(root)) {
                connectedComponents.put(root, new ArrayList<>());
            }
            
            connectedComponents.get(root).add(i);
        }
        
        for (Map.Entry<Integer, List<Integer>> entry : connectedComponents.entrySet()) {
            System.out.println(entry);
        }
    }
}

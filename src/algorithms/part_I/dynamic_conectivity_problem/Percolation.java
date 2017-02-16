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
public class Percolation {
    //array to hold open status of each site
    private boolean[] openStatus;
    
    //data structure to hold connections between sites
    private WeightedQuickUnionUF connections;
    
    //data structure to hold connections without the bottom virtual point to avoid backwash problem
    private WeightedQuickUnionUF backwash;
    
    //hold a reference to the number of objects by row
    private int n;
    
    private int openSites;
    
    //create n-by-n grid with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n: " + n);
        }
        
        //add virtual points at the beginning/end
        openStatus = new boolean[n * n + 2];
        openStatus[0] = openStatus[n * n + 1] = true;
        
        //add virtual points at the beginning/end
        connections = new WeightedQuickUnionUF(n * n + 2);
        
        //add only one virtual point at the beginning
        backwash = new WeightedQuickUnionUF(n * n + 1);
        
        this.n = n;
        
        for (int i = 1; i <= n; i++) {
            connections.union(0, i);
            
            connections.union(n * n + 1, n * n - i + 1);
            
            backwash.union(0, i);
        }
    }
    
    //open site (row, col) if it is not open already
    public void open(int row, int col) {
        int siteIndex = getSiteIndex(row, col);
        
        if (openStatus[siteIndex]){
            return;
        }
        
        System.out.println("open {row: " + row + ", col: " + col + "}");
        
        openStatus[siteIndex] = true;
        
        openSites++;
        
        //check left site and if is open connect to it
        if (col > 1 && isOpen(row, col - 1)) {
            System.out.println("Left Union: {" + siteIndex + ", " + (siteIndex - 1) + "}");
            
            connections.union(siteIndex, siteIndex - 1);
            
            backwash.union(siteIndex, siteIndex - 1);
        }
        
        //check right site and if is open connect to it
        if (col < n && isOpen(row, col + 1)) {
            System.out.println("Right Union: {" + siteIndex + ", " + (siteIndex + 1) + "}");
            
            connections.union(siteIndex, siteIndex + 1);
            
            backwash.union(siteIndex, siteIndex + 1);
        }
        
        //check upper site and if is open connect to it
        if (row > 1 && isOpen(row - 1, col)) {
            System.out.println("Top Union: {" + siteIndex + ", " + (siteIndex - n) + "}");
            
            connections.union(siteIndex, siteIndex - n);
            
            backwash.union(siteIndex, siteIndex - n);
        }
        
        //check bottom site and if is open connect to it
        if (row < n && isOpen(row + 1, col)) {
            System.out.println("Bottom Union: {" + siteIndex + ", " + (siteIndex + n) + "}");
            
            connections.union(siteIndex, siteIndex + n);
            
            backwash.union(siteIndex, siteIndex + n);
        }
    }
    
    //is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openStatus[getSiteIndex(row, col)];
    }
    
    /*
    is site (row, col) full (blocked)?
    
    a full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites
    */
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }
        
        return backwash.connected(0, getSiteIndex(row, col));
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
        //when there is only one site the system percolates if it is open
        if (n == 1) {
            return isOpen(1, 1);
        }
        
        return connections.connected(0, n * n + 1);
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
        
        return ((col - 1) + (row - 1) * n) + 1;
    }
    
    @Override
    public String toString() {
        StringBuilder oStatus = new StringBuilder();
        StringBuilder c = new StringBuilder();
        
        oStatus.append("openStatus:\n");
        oStatus.append("[0] ");
        oStatus.append(openStatus[0]);
        oStatus.append("\n");
        
        c.append("connections:\n");
        c.append("[0] ");
        c.append(connections.parent[0]);
        c.append("\n");
        
        int j = 0;
        for (int i = 1; i <= n * n; i++) {
            oStatus.append("[" + i + "] ");
            oStatus.append(openStatus[i]);
            
            c.append("[" + i + "] ");
            c.append(connections.parent[i]);
            
            if (i == n + j) {
                oStatus.append("\n");
                c.append("\n");
                
                j += n;
            }else {
                oStatus.append(", ");
                c.append(", ");
            }
        }
        
        oStatus.append("[" + (n * n + 1) + "] ");
        oStatus.append(openStatus[n * n + 1]);
        
        c.append("[" + (n * n + 1) + "] ");
        c.append(connections.parent[n * n + 1]);
        
        return c.toString() + "\n" + oStatus.toString();
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:/Users/psantama/Downloads/percolation/input10-no.txt"));
        
        Percolation p = new Percolation(Integer.parseInt(br.readLine()));
        
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

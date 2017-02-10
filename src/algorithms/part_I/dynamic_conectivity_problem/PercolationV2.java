/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms.part_I.dynamic_conectivity_problem;

import java.util.Arrays;
//import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author psantama
 */
public class PercolationV2 {
    //grid n by n to hold the state of sites (open or blocked)
    private boolean[][] grid;
    
    //array to track if a site is connected to top
    private boolean[] topConnections;
    
    //data structure to hold connections between grid sites
    private WeightedQuickUnionUF connections;
    
    //data structure to hold connections without the bottom virtual poing to avoid backwash problem
    private WeightedQuickUnionUF backwash;
    
    //hold a reference to the number of objects by row
    private int n;
    
    private int openSites;
    
    //create n-by-n grid, with all sites blocked
    public PercolationV2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        
        //add extra array of size 1 at the beginning to create the TOP virtual point
        grid = new boolean[n + 2][];
        topConnections = new boolean[n * n + 2];
        topConnections[0] = true;
        //add virtual points at the beginning/end
        connections = new WeightedQuickUnionUF(n * n + 2);
        backwash = new WeightedQuickUnionUF(n * n + 1);
        this.n = n;
        
        //initialize TOP virtual point array
        grid[0] = new boolean[1];
        grid[0][0] = true;
        grid[n + 1] = new boolean[1];
        grid[n + 1][0] = true;
        
        for (int i = 1; i <= n; i++) {
            grid[i] = new boolean[n];
            
            for (int j = 0; j < n; j++) {
                if (i == 1) {
                    connections.union(0, j + 1);
                    backwash.union(0, j + 1);
                }
                
                if (i == n) {
                    connections.union(n * n + 1, n * n - j);
                }
                
                grid[i][j] = false;
            }
        }
    }
    
    //open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > n) {
            throw new IndexOutOfBoundsException("row: " + row);
        }
        
        //col starts at 1 NOT 0
        int colIdx = col - 1;
        System.out.println("open {row, colIdx}: {" + row + ", " + colIdx + "}");
        if (grid[row][colIdx]){
            return;
        }
        
        grid[row][colIdx] = true;
        
        openSites++;
        
        //check left site and if is open connect to it
        //isOpen() substract 1 from col!!
        if (colIdx - 1 >= 0 && isOpen(row, colIdx)) {
            System.out.println("Left Union: {" + (row * n - (n - col + 1)) + ", " + (row * n - (n - col)) + "}");
            
            connections.union(row * n - (n - col + 1), row * n - (n - col));
            backwash.union(row * n - (n - col + 1), row * n - (n - col));
        }
        
        //check right site and if is open connect to it
        //isOpen() substract 1 from col!!
        if (colIdx + 1 < n && isOpen(row, colIdx + 2)) {
            System.out.println("Right Union: {" + (row * n - (n - col - 1)) + ", " + (row * n - (n - col)) + "}");
            
            connections.union(row * n - (n - col - 1), row * n - (n - col));
            backwash.union(row * n - (n - col - 1), row * n - (n - col));
        }
        
        //check upper site and if is open connect to it
        //isOpen() substract 1 from col!!
        if (row - 1 >= 1 && isOpen(row - 1, col)) {
            System.out.println("Top Union: {" + ((row - 1) * n - (n - col)) + ", " + (row * n - (n - col)) + "}");
            
            connections.union((row - 1) * n - (n - col), row * n - (n - col));
            backwash.union((row - 1) * n - (n - col), row * n - (n - col));
        }
        
        //check bottom site and if is open connect to it
        //isOpen() substract 1 from col!!
        if (row + 1 <= n && isOpen(row + 1, col)) {
            System.out.println("Bottom Union: {" + ((row + 1) * n - (n - col)) + ", " + (row * n - (n - col)) + "}");
            
            connections.union((row + 1) * n - (n - col), row * n - (n - col));
            backwash.union((row + 1) * n - (n - col), row * n - (n - col));
        }
        
        //check if is connected to top
//        connectedToTop(row, col);
    }
    
    private void connectedToTop(int row, int col) {
        int colIdx = col - 1;
        
        if (topConnections[row * n - (n - col)] = connections.connected(0, row * n - (n - col))) {
            //check top node to connect to top if it is open!
            if (row - 1 > 1 && grid[row - 1][colIdx] == true) {
                topConnections[(row - 1) * n - (n - col)] = true;
            }
            
            //check bottom node to connect to top if it is open!
            if (row + 1 < n && grid[row + 1][colIdx] == true) {
                topConnections[(row + 1) * n - (n - col)] = true;
            }
            
            //check right node to connect to top if it is open!
            if (colIdx + 1 < n && grid[row][colIdx + 1] == true) {
                topConnections[row * n - (n - col - 1)] = true;
            }
            
            //check left node to connect to top if it is open!
            if (colIdx - 1 >= 0 && grid[row][colIdx - 1] == true) {
                topConnections[row * n - (n - col + 1)] = true;
            }
        }
    }
    
    //is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > n) {
            throw new IndexOutOfBoundsException("row: " + row);
        }
        
        col -= 1;
        
        return grid[row][col];
    }
    
    /*
    is site (row, col) full (blocked)?
    
    a full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites
    */
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > n) {
            throw new IndexOutOfBoundsException("row: " + row);
        }
        
        if (!isOpen(row, col)) {
            return false;
        }
        
        int colIdx = col - 1;
        
        return backwash.connected(0, (row * n) - ((n - 1) - colIdx));
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
        if (n <= 1) {
            return isOpen(1, 1);
        }
        
        return connections.connected(0, (n * n) + 1);
    }
    
    private String printParent() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("[0] ");
        sb.append(connections.parent[0]);
        sb.append("\n");
        int j = 0;
        for (int i = 1; i <= n * n; i++) {
            sb.append("[" + i + "] ");
            sb.append(connections.parent[i]);
            
            if (i == n + j) {
                sb.append("\n");
                
                j += n;
            }else {
                sb.append(", ");
            }
        }
        sb.append("[" + (n * n + 1) + "] ");
        sb.append(connections.parent[n * n + 1]);
        
        return sb.toString();
    }
    
    private String printTopConnections() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("[0] ");
        sb.append(topConnections[0]);
        sb.append("\n");
        int j = 0;
        for (int i = 1; i <= n * n; i++) {
            sb.append("[" + i + "] ");
            sb.append(topConnections[i]);
            
            if (i == n + j) {
                sb.append("\n");
                
                j += n;
            }else {
                sb.append(", ");
            }
        }
        sb.append("[" + (n * n + 1) + "] ");
        sb.append(topConnections[n * n + 1]);
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        
        for (int i = 0; i < grid.length - 1; i++) {
            sb.append(Arrays.toString(grid[i]));
            sb.append("\n");
            
            sb1.append(Arrays.toString(grid[i]));
            sb1.append("\n");
        }
        sb.append(Arrays.toString(grid[grid.length - 1]));
        
        return sb.toString() + "\n" + sb1.toString();
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:/Users/psantama/Downloads/percolation/input6.txt"));
        
        PercolationV2 p = new PercolationV2(Integer.parseInt(br.readLine()));
        
        System.out.println(p);
        System.out.println(p.printParent());
        System.out.println(p.printTopConnections());
        
//        p.open(1, 4);
//        p.open(3, 4);
//        p.open(2, 4);
//        
//        System.out.println(p.isFull(1, 4));
//        System.out.println(p.isFull(3, 4));
//        System.out.println(p.isFull(2, 4));
//        
//        System.out.println(p);
//        System.out.println(p.printParent());
//        System.out.println(p.printTopConnections());
        
        String line;
        while ((line = br.readLine()) != null) {
            if (!"".equals(line)) {
                String[] sites = line.trim().split("\\s+");
                
                System.out.println("----------");
                
                System.out.println("open {" + Integer.parseInt(sites[0]) + ", " + Integer.parseInt(sites[1]) + "} ");
                p.open(Integer.parseInt(sites[0]), Integer.parseInt(sites[1]));
                
                System.out.println("\tis open? {" + Integer.parseInt(sites[0]) + ", " + Integer.parseInt(sites[1]) + "} " + p.isOpen(Integer.parseInt(sites[0]), Integer.parseInt(sites[1])));
                System.out.println("\tpercolates? " + p.percolates());
                System.out.println("\t# opens sites: " + p.numberOfOpenSites());
                System.out.println("\tis full? {" + Integer.parseInt(sites[0]) + ", " + Integer.parseInt(sites[1]) + "} " + p.isFull(Integer.parseInt(sites[0]), Integer.parseInt(sites[1])));
                
                System.out.println(p);
                System.out.println(p.printParent());
                System.out.println(p.printTopConnections());
                
                System.out.println("----------");
            }
        }
        
        System.out.println(p.isFull(2, 1));
    }
}

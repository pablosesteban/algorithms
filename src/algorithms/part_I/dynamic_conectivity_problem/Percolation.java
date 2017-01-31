/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.dynamic_conectivity_problem;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author psantama
 */
public class Percolation {
    //grid n by n to hold the state of sites (open or blocked)
    private boolean[][] grid;
    
    //data structure to hold connections between grid sites
    private UnionFind connections;
    
    //hold a reference to the number of objects by row
    private int n;
    
    private int openSites;
    
    private Random r = new Random();
    
    //create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        
        //add extra arrays of size 1 at the beginning/end to create the virtual points
        grid = new boolean[n + 2][];
        //add virtual points at the beginning/end
        connections = new QuickUnionWeighted(n * n + 2);
        this.n = n;
        
        //initialize virtual point arrays
        grid[0] = new boolean[1];
        grid[0][0] = true;
        grid[n + 1] = new boolean[1];
        grid[n + 1][0] = true;
        
        for (int i = 1; i <= n; i++) {
            grid[i] = new boolean[n];
            
            for (int j = 0; j < n; j++) {
                if(i == 1) {
                    connections.union(0, j + 1);
                }
                
                if(i == n) {
                    connections.union(n * n + 1, n * n - j);
                }
                
                grid[i][j] = false;
            }
        }
    }
    
    //open site (row, col) if it is not open already
    private void open(int row, int col) {
        if (row <= 0 || row > n) {
            throw new IndexOutOfBoundsException();
        }
        
        if (grid[row][col] == true){
            return;
        }
        
        grid[row][col] = true;
        
        openSites++;
        
        //check left site and if is open connect to it
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            connections.union(row * n - (col - 1), row * n - col);
        }
        
        //check right site and if is open connect to it
        if (col + 1 < n && isOpen(row, col + 1)) {
            connections.union(row * n - (col + 1), row * n - col);
        }
        
        //check upper site and if is open connect to it
        if (row - 1 >= 1 && isOpen(row - 1, col)) {
            connections.union((row - 1) * n - col, row * n - col);
        }
        
        //check bottom site and if is open connect to it
        if (row + 1 < n && isOpen(row + 1, col)) {
            connections.union((row + 1) * n + col, row * n + col);
        }
    }
    
    //is site (row, col) open?
    private boolean isOpen(int row, int col) {
        if (row <= 0 || row > n) {
            throw new IndexOutOfBoundsException();
        }
        
        return grid[row][col];
    }
    
    /*
    is site (row, col) full (blocked)?
    
    a full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites
    */
    private boolean isFull(int row, int col) {
        /*
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IndexOutOfBoundsException();
        }
        
        for (int i = 0; i < n; i++) {
            if (connections.isConnected(i, row * n + col)) {
                return true;
            }
        }
        
        return false;
        */
        if (row == n + 1) {
            return connections.isConnected(0, (n * n) + 1);
        }
        
        System.out.println((row * n) - ((n - 1) - col));
        return connections.isConnected(0, (row * n) - ((n - 1) - col));
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
        /*
        for (int i = 0; i < n; i++) {
            if (isFull(n - 1, i)) {
                return true;
            }
        }
        
        return false;
        */
        
        return isFull(n + 1, 0);
    }
    
    public void randomOpen() {
        int row = r.nextInt(n + 1);
        if (row == 0) {
            row = 1;
        }
        
        int col = r.nextInt(n);
        
        open(row, col);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < grid.length - 1; i++) {
            sb.append(Arrays.toString(grid[i]));
            sb.append("\n");
        }
        sb.append(Arrays.toString(grid[grid.length - 1]));
        
        return sb.toString();
    }
    
    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        
        while (p.percolates() == false) {
            p.randomOpen();
        }
        
        System.out.println(p);
        System.out.println(p.connections);
        System.out.println(p.connections.getConnectedComponents());
        System.out.println("Open Sites: " + p.numberOfOpenSites());
        System.out.println("Is Full Site (3, 3): " + p.isFull(3, 3));
        System.out.println("Is Full Site (3, 4): " + p.isFull(3, 4));
        System.out.println("Is Full Site (4, 1): " + p.isFull(4, 1));
        System.out.println("Is Full Site (2, 4): " + p.isFull(2, 4));
    }
}
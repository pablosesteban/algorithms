/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.dynamic_conectivity_problem;

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
        
        grid = new boolean[n][n];
        connections = new QuickUnionWeighted(n * n);
        this.n = n;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
    }
    
    //open site (row, col) if it is not open already
    private void open(int row, int col) {
        if (grid[row][col] == true){
            return;
        }
        
        grid[row][col] = true;
        
        openSites++;
        
        //check left site and if is open connect to it
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            connections.union(row * n + (col - 1), row * n + col);
        }
        
        //check right site and if is open connect to it
        if (col + 1 < n && isOpen(row, col + 1)) {
            connections.union(row * n + (col + 1), row * n + col);
        }
        
        //check upper site and if is open connect to it
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            connections.union((row - 1) * n + col, row * n + col);
        }
        
        //check bottom site and if is open connect to it
        if (row + 1 < n && isOpen(row + 1, col)) {
            connections.union((row + 1) * n + col, row * n + col);
        }
    }
    
    //is site (row, col) open?
    private boolean isOpen(int row, int col) {
        return grid[row][col];
    }
    
    /*
    is site (row, col) full (blocked)?
    
    a full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites
    */
    private boolean isFull(int row, int col) {
        for (int i = 0; i < n; i++) {
            if (connections.isConnected(i, row * n + col)) {
                return true;
            }
        }
        
        return false;
    }
    
    //number of open sites
    public int getNumberOfOpenSites() {
        return openSites;
    }
    
    /*
    does the system percolate?
    
    the system percolates if there is a full site in the bottom row
    */
    public boolean percolates() {
        for (int i = 0; i < n; i++) {
            if (isFull(n - 1, i)) {
                return true;
            }
        }
        
        return false;
        
    }
    
    public void randomOpen() {
        open(r.nextInt(n), r.nextInt(n));
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
        System.out.println("Open Sites: " + p.getNumberOfOpenSites());
        System.out.println("Percolates: " + p.percolates());
        System.out.println("Is Full Site (3, 3): " + p.isFull(3, 3));
        System.out.println("Is Full Site (4, 1): " + p.isFull(4, 1));
        System.out.println("Is Full Site (2, 4): " + p.isFull(2, 4));
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_4;

/**
 *
 * @author psantama
 */
public class Board {
    private int[] blocks;
    private int n;
    
    // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
    // receives an n-by-n array containing the n2 integers between 0 and n2 − 1, where 0 represents the blank square
    public Board(int[][] blocks) {
        if (blocks == null)
            throw new NullPointerException("null argument");
        
        n = blocks.length;
        this.blocks = new int[n * n];
        
        int i = 0;
        for (int[] block : blocks) {
            for (int value : block) {
                this.blocks[i] = value;
                
                i++;
            }
        }
    }
    
    // board dimension n
    public int dimension() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        
        int i = 1;
        for (int value : blocks) {
            System.out.println("\tvalue: " + value);
            System.out.println("\ti: " + i);
            
            if (value == 0) {
                i++;
                
                continue;
            }
            
            if (value != i) {
                ++hamming;
            }
            
            i++;
        }
        
        return hamming;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        
        int i = 1;
        for (int value : blocks) {
            if (value != i) {
                manhattan += Math.round(Math.abs(value - i) / n);
            }
        }
        
        return manhattan;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // string representation of this board (in the output format specified below)
    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // does this board equal obj?
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static void main(String[] args) {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        
        Board board = new Board(blocks);
        
        System.out.println("hamming: " + board.hamming());
        
        System.out.println("manhattan: " + board.manhattan());
    }
}

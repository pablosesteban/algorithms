/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.pablosesteban.part_I.week_4;

import java.util.Stack;

/**
 *
 * @author psantama
 */

// immutable data type
public class Board {
    private int[][] blocks;
    private int n;
    
    private boolean debug = false;
    
    // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
    // receives an n-by-n array containing the n2 integers between 0 and n2 − 1, where 0 represents the blank square
    public Board(int[][] blocks) {
        if (blocks == null)
            throw new NullPointerException("null argument");
        
        n = blocks.length;
        this.blocks = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }
    
    // board dimension n
    public int dimension() {
        return n;
    }
    
    // number of blocks out of place
    public int hamming() {
        if (debug)
            System.out.println("hamming:");
        
        int hamming = 0;
        int expected = 1;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = blocks[i][j];
                
                if (debug) {
                    System.out.print("\tvalue: " + value);
                    System.out.println(", expected: " + expected);
                }
                
                if (value != 0 &&expected != value) {
                    hamming++;
                }
                
                expected++;
            }
        }
        
        return hamming;
    }
    
    // sum of the vertical and horizontal moves from their initial positions to their goal positions of blocks out of place
    public int manhattan() {
        if (debug)
            System.out.println("manhattan:");
        
        int manhattan = 0;
        int expected = 1;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = blocks[i][j];
                
                if (debug) {
                    System.out.print("\tvalue: " + value);
                    System.out.println(", expected: " + expected);
                }
                
                if (value != 0 && expected != value) {
                    // to know the expected col and row where the item should be
                    int expectedRow = (value - 1) / n;
                    int expectedCol = (value - 1) % n;
                    
                    // to know the number of moves you should make
                    int moves = Math.abs(i - expectedRow) + Math.abs(j - expectedCol);
                    
                    if (debug)
                        System.out.println("\tmoves: " + moves);
                    
                    manhattan += moves;
                }
                
                expected++;
            }
        }
        
        return manhattan;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        // you can use hamming() or manhattan()
        return hamming() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks: to know if a board is unsolvable
    public Board twin() {
        Board twin = cloneBoard(this);
        
        int j = 0;
        int i = 0;
        
        if (twin.blocks[i][j] != 0 && twin.blocks[i][j+1] != 0) {
            swap(twin.blocks, i, j, i, j+1);
        }else {
            swap(twin.blocks, i+1, j, i+1, j+1);
        }
        
        return twin;
    }
    
    // all neighboring boards: at most four boards which differ from original in only one move of the blank block
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        
        outer: for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    // left
                    if (j > 0) {
                        Board left = cloneBoard(this);
                        
                        swap(left.blocks, i, j, i, j-1);
                        
                        neighbors.add(left);
                    }
                    
                    // right
                    if (j < n-1) {
                        Board right = cloneBoard(this);
                        
                        swap(right.blocks, i, j, i, j+1);
                        
                        neighbors.add(right);
                    }
                    
                    // up
                    if (i > 0) {
                        Board up = cloneBoard(this);
                        
                        swap(up.blocks, i, j, i-1, j);
                        
                        neighbors.add(up);
                    }
                    
                    // bottom
                    if (i < n-1) {
                        Board bottom = cloneBoard(this);
                        
                        swap(bottom.blocks, i, j, i+1, j);
                        
                        neighbors.add(bottom);
                    }
                    
                    break outer;
                }
            }
        }
        
        return neighbors;
    }
    
    private Board cloneBoard(Board b) {
        int[][] blocks = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = this.blocks[i][j];
            }
        }
        
        return new Board(blocks);
    }
    
    private void swap(int[][] blocks, int r1, int c1, int r2, int c2) {
        int value = blocks[r1][c1];
        
        blocks[r1][c1] = blocks[r2][c2];
        
        blocks[r2][c2] = value;
    }
    
    // string representation in JSON of this board
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        s.append("{");
        
        for (int i = 0; i < n - 1; i++) {
            s.append(i);
            s.append(": [");
            
            for (int j = 0; j < n-1; j++) {
                s.append(String.format("%2d, ", blocks[i][j]));
            }
            
            s.append(String.format("%2d", blocks[i][n-1]));
            
            s.append("], ");
        }
        s.append((n-1));
        
        s.append(": [");
        for (int j = 0; j < n-1; j++) {
            s.append(String.format("%2d, ", blocks[n-1][j]));
        }
        s.append(String.format("%2d", blocks[n-1][n-1]));
        
        s.append("]}");
        
        return s.toString();
        
        // needed for coursera
//        StringBuilder s = new StringBuilder();
//        
//        s.append(n + "\n");
//        
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                s.append(String.format("%2d ", blocks[i][j]));
//            }
//            
//            s.append("\n");
//        }
//        
//        return s.toString();
    }
    
    // does this board equal obj?
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Board) {
            if (((Board) obj).dimension() != this.dimension())
                return false;
            
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (blocks[i][j] != ((Board) obj).blocks[i][j])
                        return false;
                }
            }
            
            return true;
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        
        Board board = new Board(blocks);
        
        System.out.println("hamming: " + board.hamming());
        
        System.out.println("manhattan: " + board.manhattan());
        
        System.out.println(board);
        
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }
    }
}

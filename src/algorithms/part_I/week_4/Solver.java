/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_4;

import edu.princeton.cs.algs4.MinPQ;
import java.util.Stack;

/**
 *
 * @author psantama
 */
public class Solver {
    private Node goal;
    private Stack<Board> boardsSequence = new Stack<>();
    private boolean solvable = true;
    
    private boolean debug = true;
    
    // class representing the search nodes stored in the priority queue
    private class Node implements Comparable<Node> {
        private Node previous;
        private Board board;
        private int numMoves;

        public Node(Board board) {
            this.board = board;
        }

        public Node(Board board, Node previous) {
            this.board = board;
            this.previous = previous;
            this.numMoves = previous.numMoves + 1;
        }

        @Override
        public int compareTo(Node move) {
            // the priority function must take into account the moves made to reach the board
            return (this.board.manhattan() - move.board.manhattan()) + (this.numMoves - move.numMoves);
        }

        // string representation in JSON format
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            
            sb.append("{");
            sb.append("previous: " + previous + ", ");
            sb.append("board: " + board + ", ");
            sb.append("numMoves: " + numMoves);
            sb.append("}");
            
            return sb.toString();
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (debug)
            System.out.println("initial node: " + initial);
        
        MinPQ<Node> pq = new MinPQ<>();
        /**
         * Not all initial boards can lead to the goal board by a sequence of legal moves
         * 
         * To detect such situations, use the fact that boards are divided into two equivalence classes with respect to reachability:
         *      those that lead to the goal board
         * 
         *      those that lead to the goal board if we modify the initial board by swapping any pair of blocks
         */
        MinPQ<Node> pqTwin = new MinPQ<>();
    
        pq.insert(new Node(initial));
        pqTwin.insert(new Node(initial.twin()));
        
        Node min = null;
        Node minTwin = null;
        do {
            min = pq.delMin();
            minTwin = pqTwin.delMin();
            
            checkNeighbors(pq, min, false);
            
            checkNeighbors(pqTwin, minTwin, true);
            
            if (minTwin.board.isGoal()) {
                // if twin board reaches the goal, the original board is unsolvable!
                solvable = false;
                break;
            }
        } while (!min.board.isGoal());
        
        boardsSequence.add(min.board);
        
        goal = min;
    }
    
    private void checkNeighbors(MinPQ<Node> pq, Node min, boolean isTwin) {
        if (debug)
            System.out.println("min node: " + min);
        
        for (Board neighbor : min.board.neighbors()) {
            Node neighborNode = new Node(neighbor, min);
            
            if (debug)
                System.out.println("\tneighbor node: " + neighborNode);
            
            if (min.previous == null) {
                pq.insert(neighborNode);
            }else {
                if (!min.previous.board.equals(neighborNode.board))
                    pq.insert(neighborNode);
            }
        }
        
        if (min.previous != null && !isTwin)
            boardsSequence.add(min.previous.board);
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        
        return goal.numMoves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        
        return boardsSequence;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
//        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        
        // not solvable
        int[][] blocks = {{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};
        
//        int[][] blocks = {{1, 0}, {3, 2}};
        
        Board board = new Board(blocks);
        
        Solver s = new Solver(board);
        
        System.out.println("moves: " + s.moves());
        
        if (s.solution() != null) {
            System.out.println("boards sequence:");
            for (Board b : s.solution()) {
                System.out.println("\t" + b);
            }
        }
    }
    
}

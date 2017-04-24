/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_4;

import edu.princeton.cs.algs4.MinPQ;
import java.util.LinkedList;

/**
 * Best-First Search:
 *      is a search algorithm which explores a graph by expanding the most promising node chosen according to a specified rule
 * 
 *      was described as estimating the promise of node n by a "heuristic" evaluation function which, in general, may depend on:
 *          the description of n
 *          
 *          the description of the goal
 * 
 *          the information gathered by the search up to that point
 * 
 *          and most important, on any extra knowledge about the problem domain
 * 
 *      efficient selection of the current best candidate for extension is typically implemented using a Priority Queue
 * 
 *      The "A* Search Algorithm" is an example of Best-First Search
 */

/**
 * A* Algorithm:
 *      is a computer algorithm that is widely used in pathfinding and graph traversal, the process of plotting an efficiently directed path between multiple points, called nodes
 * 
 *      it enjoys widespread use due to its performance and accuracy
 * 
 *      achieves better performance by using heuristics to guide its search
 */

/**
 * HEURISTIC:
 *      is a technique designed for solving a problem more quickly when classic methods are too slow, or for finding an approximate solution when classic methods fail to find any exact solution
 *  
 *      is achieved by trading optimality, completeness, accuracy, or precision for speed
 *      
 *      heuristic function (heuristic):
 *          is a function that ranks alternatives in search algorithms at each branching step based on available information to decide which branch to follow
 *          
 *          it may approximate the exact solution
 */
public class Solver {
    private Node goal;
    private LinkedList<Board> boardsSequence = new LinkedList<>();
    private boolean solvable = true;
    
    private boolean debug = true;
    
    // class representing a search node stored in the priority queue
    private class Node implements Comparable<Node> {
        // parent node which this node comes from by only one move
        private Node previous;
        // board
        private Board board;
        // number of moves to reach this board from the initial board
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
            /**
             * the priority function:
             *      must take into account the moves made to reach this board
             * 
             *      can use either hamming() or manhattan() methods
             * 
             *      intuitively, a search node with a small number of blocks in the wrong (hamming) position is close to the goal
             * 
             *      moreover, we prefer a search node that have been reached using a small number of moves
             */
            return (this.board.hamming()- move.board.hamming()) + (this.numMoves - move.numMoves);
        }

        // string representation in JSON format
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            
            sb.append("{");
            
            sb.append("previous: ");
            sb.append(previous);
            sb.append(", ");
            
            sb.append("board: ");
            sb.append(board);
            sb.append(", ");
            
            sb.append("numMoves: ");
            sb.append(numMoves);
            
            sb.append("}");
            
            return sb.toString();
        }
    }
    
    /**
     * find a solution to the initial board using the "A* Search Algorithm":
     *      1) insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue
     * 
     *      2) delete from the priority queue the search node with the minimum priority
     * 
     *      3) insert onto the priority queue all neighboring search nodes (those that can be reached in one move from the dequeued search node)
     * 
     *      4) repeat this procedure until the search node dequeued corresponds to a goal board
     * 
     *      the success of this approach hinges on the choice of priority function for a search node
     */
    public Solver(Board initial) {
        if (debug)
            System.out.println("initial node: " + initial);
        
        MinPQ<Node> pq = new MinPQ<>();
        /**
         * not all initial boards can lead to the goal board by a sequence of legal moves
         * 
         * to detect such situations, use the fact that boards are divided into two equivalence classes with respect to reachability:
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
            
            addNeighbors(pq, min, false);
            
            addNeighbors(pqTwin, minTwin, true);
            
            if (minTwin.board.isGoal()) {
                // if twin board reaches the goal, the original board is unsolvable!
                solvable = false;
                
                break;
            }
        } while (!min.board.isGoal());
        
        goal = min;
        
        // create boards sequence
        while (min != null) {
            boardsSequence.addFirst(min.board);
            
            min = min.previous;
        }
    }
    
    private void addNeighbors(MinPQ<Node> pq, Node min, boolean isTwin) {
        if (debug && !isTwin)
            System.out.println("min node: " + min);
        
        for (Board neighbor : min.board.neighbors()) {
            Node neighborNode = new Node(neighbor, min);
            
            if (debug && !isTwin)
                System.out.println("\tneighbor node: " + neighborNode);
            
            if (min.previous == null) {
                pq.insert(neighborNode);
            }else {
                /**
                 * Best-First Search has one annoying feature:
                 *      search nodes corresponding to the same board are enqueued on the priority queue many times
                 * 
                 *      to reduce unnecessary exploration of useless search nodes, when considering the neighbors of a search node, don't enqueue a neighbor if its board is the same as the board of the previous search node
                 */
                if (!min.previous.board.equals(neighborNode.board))
                    pq.insert(neighborNode);
            }
        }
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
        
        int[][] blocks = {{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};
        
//        int[][] blocks = {{1, 0}, {3, 2}};
        
        Board board = new Board(blocks);
        
        Solver s = new Solver(board);
        
        System.out.println("moves: " + s.moves());
        
        if (s.solution() != null) {
            System.out.println("boards sequence: " + s.boardsSequence.size());
            
            for (Board b : s.solution()) {
                System.out.println("\t" + b);
            }
        }
    }
}

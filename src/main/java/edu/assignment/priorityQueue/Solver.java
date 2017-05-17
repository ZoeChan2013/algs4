package edu.assignment.priorityQueue;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

/**
 * Created by shuyi_chen on 4/5/17.
 */
public class Solver {
    private MinPQ<Node> pq = new MinPQ<Node>();
    private int minMoves = -1;
    private Node bestNode;
    private boolean solved = false;

    private class Node implements Comparable<Node> {
        private Board board;
        private int moves, dist;
        private Node prev;

        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            dist = board.manhattan();
        }

        @Override
        public int compareTo(Node that) {
            return this.moves + this.dist - that.moves - that.dist;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        pq.insert(new Node(initial, 0, null));
        pq.insert(new Node(initial.twin(), 0, null));
        while (!pq.isEmpty()) {
            Node current = pq.delMin();
            if (current.board.isGoal()) {
                Node root = root(current);
                if (!root.board.equals(initial)) {
                    break;
                }
                solved = true;
                if (minMoves == -1 || current.moves < minMoves) {
                    minMoves = current.moves;
                    bestNode = current;
                }
            }
            if (minMoves == -1 || current.moves + current.dist < minMoves) {
                Iterable<Board> it = current.board.neighbors();
                for (Board b : it) {
                    if (current.prev == null || !b.equals(current.prev.board)) {
                        pq.insert(new Node(b, current.moves + 1, current));
                    }
                }
            } else {
                break;
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable()  {
        return solved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return minMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solved) {
            Stack<Board> sol = new Stack<>();
            Node current = bestNode;
            while (current != null) {
                sol.push(current.board);
                current = current.prev;
            }
            return sol;
        } else {
            return null;
        }
    }

    private Node root(Node node) {
        Node current = node;
        while (current.prev != null) {
            current = current.prev;
        }
        return current;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In("/puzzle02.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}

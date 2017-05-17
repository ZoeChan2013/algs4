package edu.assignment.priorityQueue;

import edu.princeton.cs.algs4.Stack;

/**
 * Created by shuyi_chen on 4/3/17.
 */
public class Board {
    private final int n;
    private final int[][] board;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)  {
        n = blocks.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(blocks[i], 0, board[i], 0, blocks[i].length);
        }
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != goalValueAt(i, j)) {
                    hamming += 1;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = board[i][j];
                if (value != 0 && value != goalValueAt(i, j)) {
                    int targetX = (value - 1) / n;
                    int targetY = (value - 1) % n;
                    manhattan += Math.abs(targetX - i) + Math.abs(targetY - j);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return isBoardsEqual(this.board, getTargetBoard());
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board newBoard = new Board(board);
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                if(board[i][j] != 0 && board[i][j+1] != 0) {
                    newBoard.swap(i, j, i, j+1);
                    return newBoard;
                }
            }
        }
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;
        return this.n == that.n && isBoardsEqual(this.board, that.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (n < 2) {
            return null;
        }

        int i0 = 0;
        int j0 = 0;
        boolean isFound = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                    isFound = true;
                    break;
                }
            }
            if (isFound) {
                break;
            }
        }

        Stack<Board> neighbors = new Stack<>();
        Board boards = new Board(board);
        if (boards.swap(i0, j0, i0 - 1, j0)) {
            neighbors.push(boards);
        }

        boards = new Board(board);  // Reset the status of boards
        if (boards.swap(i0, j0, i0 + 1, j0)) {
            neighbors.push(boards);
        }

        boards = new Board(board);  // Reset the status of boards
        if (boards.swap(i0, j0, i0, j0 - 1)) {
            neighbors.push(boards);
        }

        boards = new Board(board);  // Reset the status of boards
        if (boards.swap(i0, j0, i0, j0 + 1)) {
            neighbors.push(boards);
        }

        return neighbors;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // Check whether current value is in correct place
    private int goalValueAt(int i, int j) {
        if (isEnd(i, j)) {
            return 0;
        }
        return 1 + i * n + j;
    }

    // Check whether is the last block
    private boolean isEnd(int i, int j) {
        return i == n - 1 && j == n - 1;
    }

    // Compare two board
    private boolean isBoardsEqual(int[][] x, int[][] y) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (x[i][j] != y[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] getTargetBoard() {
        int[][] targetBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(!isEnd(i, j)) {
                    targetBoard[i][j] = i * n + (j + 1);
                }
                else {
                    targetBoard[i][j] = 0;
                }
            }
        }
        return targetBoard;
    }

    private boolean swap (int i, int j, int iNext, int jNext) {
        if (iNext < 0 || iNext >= n || jNext < 0 || jNext >= n) {
            return false;
        }
        int temp = board[i][j];
        board[i][j] = board[iNext][jNext];
        board[iNext][jNext] = temp;
        return true;
    }

    // unit tests (not graded)
    public static void main(String[] args) { }
}

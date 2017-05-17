package edu.assignment.percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] isOpen; // block false, open true
    private boolean[] isConnectedTop;
    private boolean[] isConnectedBottom;
    private int n; // create N-by-N grid
    private WeightedQuickUnionUF uf;
    private boolean isPercolate;
    private int numOfOpenSites = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0!");
        }
        this.n = n;
        isOpen = new boolean[n * n];
        isConnectedTop = new boolean[n * n];
        isConnectedBottom = new boolean[n * n];
        uf = new WeightedQuickUnionUF(n * n);

        for (int i = 0; i < n * n; i++) {
            isOpen[i] = false;
            isConnectedTop[i] = false;
            isConnectedBottom[i] = false;
        }
        isPercolate = false;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRowCol(row, col);
        int index = getIndex(row, col);
        boolean connectTop = false;
        boolean connectBottom = false;

        if (!isOpen[index]) {
            isOpen[index] = true;    // set open status
            numOfOpenSites += 1;    // count open sites number
        }

        if (row > 1 && isOpen[index - n]) {
            if (isConnectedTop[uf.find(index - n)] || isConnectedTop[uf.find(index)]) {
                connectTop = true;
            }
            if (isConnectedBottom[uf.find(index - n)] || isConnectedBottom[uf.find(index)]) {
                connectBottom = true;
            }
            uf.union(index, index - n);
        }
        if (row < n && isOpen[index + n]) {
            if (isConnectedTop[uf.find(index + n)] || isConnectedTop[uf.find(index)]) {
                connectTop = true;
            }
            if (isConnectedBottom[uf.find(index + n)] || isConnectedBottom[uf.find(index)]) {
                connectBottom = true;
            }
            uf.union(index, index + n);
        }
        if (col > 1 && isOpen[index - 1]) {
            if (isConnectedTop[uf.find(index - 1)] || isConnectedTop[uf.find(index)]) {
                connectTop = true;
            }
            if (isConnectedBottom[uf.find(index - 1)] || isConnectedBottom[uf.find(index)]) {
                connectBottom = true;
            }
            uf.union(index, index - 1);
        }
        if (col < n && isOpen[index + 1]) {
            if (isConnectedTop[uf.find(index + 1)] || isConnectedTop[uf.find(index)]) {
                connectTop = true;
            }
            if (isConnectedBottom[uf.find(index + 1)] || isConnectedBottom[uf.find(index)]) {
                connectBottom = true;
            }
            uf.union(index, index + 1);
        }
        if (row == 1) {
            connectTop = true;
        }
        if (row == n) {
            connectBottom = true;
        }

        isConnectedTop[uf.find(index)] = connectTop;
        isConnectedBottom[uf.find(index)] = connectBottom;

        if (connectTop && connectBottom) {
            isPercolate = true;
        }
    }

    private int getIndex(int row, int col) {
        validateRowCol(row, col);
        return col + (row - 1) * n - 1;
    }

    private void validateRowCol(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IndexOutOfBoundsException("Row/Column is out of range!");
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        return isOpen[getIndex(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRowCol(row, col);
        return isConnectedTop[uf.find(getIndex(row, col))];
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolate;
    }

    // test client (optional)
    public static void main(String[] args) { }
}

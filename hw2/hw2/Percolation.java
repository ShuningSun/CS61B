package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;

public class Percolation {
    int openSiteNumber;

    int sideNumber;

    boolean[] openList;

    int waterSide;

    int landSide;

    WeightedQuickUnionUF percolationLogic;

    WeightedQuickUnionUF visual;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        sideNumber = N;
        openSiteNumber = 0;
        openList = new boolean[N * N];
        waterSide = N * N;
        landSide = N * N + 1;
        percolationLogic = new WeightedQuickUnionUF(N * N + 2);
        visual = new WeightedQuickUnionUF(N * N + 1);
        for (int i = 0; i < N; i++) {
            percolationLogic.union(waterSide, i);
            visual.union(waterSide, i);
        }

        for (int j = N * N -1 ; j >= N * (N - 1); j--) {
            percolationLogic.union(landSide, j);
        }


    }

    public void open(int row, int col) {
        int oneD = xyTo1D(row, col);
        checkNeighbors(row, col);
        openSiteNumber++;
        openList[xyTo1D(row, col)] = true;
    }

    public boolean isOpen(int row, int col) {
        if (isOutBound(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return openList[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (isOutBound(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return visual.connected(waterSide, xyTo1D(row, col)) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return openSiteNumber;
    }

    public boolean percolates() {
        return percolationLogic.connected(waterSide, landSide);
    }

    private int xyTo1D(int row, int col) {
        return row * sideNumber + col % sideNumber;
    }

    private boolean isOutBound(int row, int col) {
        return row < 0 || row > sideNumber - 1 || col < 0 || col > sideNumber - 1;
    }

    private void checkNeighbors(int row, int col) {
        int nx;
        int ny;
        // up, down, left right
        int[] horizontal =  {0, 0, -1, 1};
        int[] vertical  = {-1, 1, 0, 0};
        for (int i = 0; i < 4; i++) {
            nx = row + horizontal[i];
            ny = col + vertical[i];
            if (!isOutBound(nx, ny) && isOpen(nx, ny)) {
                int old  = xyTo1D(row, col);
                int neighbor = xyTo1D(nx, ny);
                percolationLogic.union(old, neighbor);
                visual.union(old, neighbor);
            }
        }
    }

}

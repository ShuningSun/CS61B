package hw2;

import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    int[] results;

    int T;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        results = new int[T];
        int times;
        int x;
        int y;
        for (int i = 0; i < T; i++) {
            times = 0;
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                x = StdRandom.uniform(N);
                y = StdRandom.uniform(N);
                if (!p.isOpen(x, y)) {
                    p.open(x, y);
                    times++;
                }
            }
            results[i] = times;
        }

    }
    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLow() {
        double mean = mean();
        double stddev = stddev();
        return mean - (1.96 * stddev) / T;
    }

    public double confidenceHigh() {
        double mean = mean();
        double stddev = stddev();
        return mean + (1.96 * stddev) / T;
    }


}

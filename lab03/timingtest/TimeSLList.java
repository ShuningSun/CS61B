package timingtest;

import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        int twoBase;
        int base = 1000;
        int upperBound;
        int M = 10000;
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        for (int i = 0; i < 8; i++){
            int j;
            twoBase = (int)Math.pow(2, i);
            upperBound = twoBase * base;
            SLList<Integer> temp = new SLList<>();
            Ns.addLast(upperBound);

            for (j = 0; j < upperBound; j++){
                temp.addLast(1);
            }
            Stopwatch sw = new Stopwatch();
            for (int k = 0; k < M; k++){
                int h = temp.getLast();
            }
            times.addLast(sw.elapsedTime());
            opCounts.addLast(M);

        }
        printTimingTable(Ns, times, opCounts);
    }


}

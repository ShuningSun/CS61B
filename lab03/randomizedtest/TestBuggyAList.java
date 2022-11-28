package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        // Arrange
        AListNoResizing<Integer> expected = new AListNoResizing<>();
        BuggyAList <Integer> actual = new BuggyAList<>();

        // Action
        expected.addLast(4);
        actual.addLast(4);

        expected.addLast(5);
        actual.addLast(5);

        expected.addLast(6);
        actual.addLast(6);

        // Assert
        assertEquals(expected.size(), actual.size());

        assertEquals(expected.getLast(), actual.getLast());

        assertEquals(expected.getLast(), actual.getLast());

        assertEquals(expected.getLast(), actual.getLast());
    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList <Integer> B = new BuggyAList<>();
        int N = 500000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int expected = L.size();
                int actual = B.size();
                assertEquals(expected, actual);
            } else if (operationNumber == 2 && L.size() > 2 && B.size() > 2) {
                // get last
                int expected = L.getLast();
                int actual = B.getLast();
                assertEquals(expected, actual);
            } else if (operationNumber == 3 && L.size() > 2 && B.size() > 2) {
                // get last
                int expected = L.removeLast();
                int actual = B.removeLast();
                assertEquals(expected, actual);
            }

        }
    }
}

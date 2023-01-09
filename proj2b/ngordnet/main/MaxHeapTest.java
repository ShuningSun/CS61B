package ngordnet.main;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class MaxHeapTest {

    @Test
    public void  test_MaxHeap_1() {
        // Arrange
        List<Integer> numbers = new ArrayList<>();
        List<Integer> actual = new ArrayList<>();
        Random rand = new Random();
        for (int j = 0; j < 2000000; j++)
        {
            int pick = rand.nextInt(2000000);
            numbers.add(pick);
            if (pick % (j + 1) == 0) {
                numbers.add(pick);
            }
        }
        MaxHeap mh =  new MaxHeap();

        // Action
        for (int i : numbers) {
            mh.add(i);
        }
        Collections.sort(numbers, Collections.reverseOrder());
        int iterLimit = numbers.size();
        for (int j = 0; j < iterLimit; j++) {
            actual.add(mh.remove());
        }

        // Assert
        assertEquals(numbers, actual);
    }

}

package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        int i = 1;
        int arraySize = size();
        while (i < arraySize) {
            T iter = get(i);
            max = comparator.compare(max, iter) < 0 ? iter : max;
            i++;
        }
        return max;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        int i = 1;
        int arraySize = size();
        while (i < arraySize) {
            T iter = get(i);
            max = c.compare(max, iter) < 0 ? iter : max;
            i++;
        }
        return max;
    }
}

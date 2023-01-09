package ngordnet.main;

import java.util.ArrayList;

public class MaxHeap {
    ArrayList<Integer> heap;
    public MaxHeap() {
        heap = new ArrayList<>();
        heap.add(Integer.MAX_VALUE);
    }

    public void add(int number) {
        heap.add(number);
        int index = heap.size() - 1;
        if (heap.get(index) > heap.get(getParent(index))) {
            buttomUp(index);
        }
    }

    public int remove() {
        int lastIndex = heap.size() - 1;
        int result =  heap.get(1);
        heap.set(1, heap.get(lastIndex));
        heap.remove(lastIndex);
        topDown(1);
        return result;
    }

    public boolean isEmpty() {
        return heap.size() <= 1;
    }

    private int getParent(int index) {
        return index / 2;
    }

    private int getLeft(int index) {
        return index * 2;
    }

    private int getRight(int index) {
        return index * 2 + 1;
    }

    private void buttomUp(int index) {
        int parent = getParent(index);
        if (index == 1 || heap.get(index) <= heap.get(parent)) {
            return;
        }
        int temp = heap.get(index);
        heap.set(index, heap.get(parent));
        heap.set(parent, temp);
        buttomUp(parent);
    }

    private void topDown(int index) {
        int left = getLeft(index);
        int right = getRight(index);
        int boundary = heap.size() - 1;
        if (index > boundary) {
            return;
        }
        int temp = heap.get(index);
        if (right <= boundary) {
            if (temp >= heap.get(left) && temp >= heap.get(right)) {
                return;
            }
            if (heap.get(left) > heap.get(right)) {
                heap.set(index, heap.get(left));
                heap.set(left, temp);
                topDown(left);
            }
            else {
                heap.set(index, heap.get(right));
                heap.set(right, temp);
                topDown(right);
            }
        }
        else if (left <= boundary && temp < heap.get(left)) {
            heap.set(index, heap.get(left));
            heap.set(left, temp);
            topDown(left);
        }
    }

}

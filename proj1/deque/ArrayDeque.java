package deque;

import java.util.Iterator;


public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
     int size;
     int head;

     int tail;
     T[] array;

     @Override
    public void addFirst(T item) {
        if (isEmpty()) {
            this.array[0] = item;
            this.head = 0;
            this.tail = 0;
            this.size++;
            return;
        }
        array = reSize();
        if (head == 0) {
            this.head = array.length - 1;
            this.array[head] = item;
        } else {
            this.head--;
            this.array[head] = item;
        }
        this.size++;
    }

    @Override
    public void addLast(T item) {
        if (isEmpty()) {
            this.array[0] = item;
            this.head = 0;
            this.tail = 0;
            this.size++;
            return;
        }
        array = reSize();
        if (tail == array.length - 1) {
            this.tail = 0;
            this.array[tail] = item;
        }
        else {
            this.tail++;
            this.array[tail] = item;
        }
        this.size++;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i)+" ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T temp = array[head];
        array[head] = null;
        this.head = (head + 1 == array.length) ? 0 : head + 1;
        this.size--;
        return temp;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T temp = array[tail];
        array[tail] = null;
        this.tail = (tail == 0) ? array.length - 1 : tail - 1;
        this.size--;
        return temp;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }

        int iterator = head + index;
        if (iterator > array.length - 1) {
            iterator -= (array.length);
        }
        return array[iterator];
    }

    @Override
    public boolean equals(Object o) {
        return true;
    }


    public ArrayDeque() {
        array = (T[]) new Object[8];
    }

    private T[] reSize() {
        if (array.length > 16 && size + 1 > array.length) {
            int newSize = (int) (array.length * 1.25);
            T[] newArray = (T[]) new Object[newSize];
            if (head > tail) {
                int headSize = array.length -  head;
                System.arraycopy(array, head, newArray, 0, headSize);
                System.arraycopy(array, 0, newArray, headSize, tail + 1);
            } else {
                System.arraycopy(array, head, newArray, 0, size);
            }
            this.head = 0;
            this.tail = size - 1;
            return newArray;
        }
        else if (size + 1 > array.length)  {
            T[] newArray = (T[]) new Object[size + 1];
            if (head > tail) {
                int headSize = array.length - head;
                System.arraycopy(array, head, newArray, 0, headSize);
                System.arraycopy(array, 0, newArray, headSize, tail + 1);
            } else {
                System.arraycopy(array, head, newArray, 0, size);
            }
            this.head = 0;
            this.tail = size - 1;
            return newArray;
        }
        else if (size + 1 < array.length * 0.75 && array.length > 12) {
            int newSize = (int) (array.length * 0.75);
            T[] newArray = (T[]) new Object[newSize];
            if (head > tail) {
                int headSize = array.length -  head;
                System.arraycopy(array, head, newArray, 0, headSize);
                System.arraycopy(array, 0, newArray, headSize, tail + 1);
            } else {
                System.arraycopy(array, head, newArray, 0, size);
            }
            this.head = 0;
            this.tail = size - 1;
            return newArray;
        }
        return this.array;

        }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int iter;

        public ArrayDequeIterator() {
            iter = 0;
        }
        @Override
        public boolean hasNext() {
            return iter  < size;
        }

        @Override
        public T next() {
            T returnItem = get(iter);
            iter++;
            return returnItem;
        }
    }


}


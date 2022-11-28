package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private ListNode sentinel;
    public int size = 0;

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private int iter;

        public LinkedListIterator() {
            iter = 0;
        }

        @Override
        public boolean hasNext() {
            return iter < size;
        }

        @Override
        public T next() {
            T returnItem = get(iter);
            iter++;
            return returnItem;
        }
    }
    public class ListNode {
        public T item;
        public ListNode next;
        public ListNode previous;

        public ListNode(T item, ListNode previous, ListNode next) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    public LinkedListDeque() {
        sentinel = new ListNode(null, null, null);
        sentinel.previous = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T item) {
        ListNode temp = sentinel.previous;
        sentinel.previous = new ListNode(item, sentinel, temp);
        temp.previous = sentinel.previous;
        if (temp == sentinel) {
            sentinel.previous.next = sentinel.next;
            sentinel.next.previous = sentinel.previous;
        }
        size++;
    }

    @Override
    public void addLast(T item) {
        ListNode temp = sentinel.next;
        sentinel.next = new ListNode(item, temp, sentinel);
        temp.next = sentinel.next;
        if (temp == sentinel) {
            sentinel.next.previous = sentinel.previous;
            sentinel.previous.next = sentinel.next;
        }
        size++;
    }

    @Override
    public void printDeque() {
        ListNode starter  = sentinel.previous;
        while (starter != sentinel) {
            System.out.print(starter.item + " ");
            starter = starter.next;
        }
        System.out.println();
    }



    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        ListNode temp = sentinel.previous;
        sentinel.previous = sentinel.previous.next;
        sentinel.previous.previous = sentinel;
        T holder = temp.item;
        temp = null;
        size--;
        return holder;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        ListNode temp = sentinel.next;
        sentinel.next = sentinel.next.previous;
        sentinel.next.next = sentinel;
        T holder = temp.item;
        temp = null;
        size--;
        return holder;
    }

    @Override
    public T get(int index) {
        ListNode starter = sentinel.previous;
        for (int i = 1; i < index; i++){
            starter = starter.next;
            if (starter == sentinel) {
                return null;
            }
        }
        return starter.item;
    }

    public ListNode getter(int index) {
        ListNode starter = sentinel.previous;
        for (int i = 1; i < index; i++){
            starter = starter.next;
            if (starter == sentinel) {
                return null;
            }
        }
        return starter;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LinkedListDeque) {
            return checkEqual((LinkedListDeque) o);
        }
        return false;
    }

    public T getRecursive(int index) {
        return recurser(index, sentinel.previous);
    }

    public T recurser(int index, ListNode l1) {
        if (l1 == sentinel) {
            return null;
        }
        if (index == 0) {
            return  l1.item;
        }
        return recurser(index - 1, l1.next);
    }

    private boolean checkEqual(LinkedListDeque l1) {
        if (l1.getSize() == size) {
            ListNode LN1 = l1.getter(0);
            ListNode LN2 = sentinel.previous;
            while (LN1 != l1.getSentinel() || LN2 != sentinel) {
                if (LN1.item != LN2.item) {
                    return false;
                }
                LN1 = LN1.next;
                LN2 = LN2.next;
            }
            return true;
        }

        return false;
    }

    public ListNode getSentinel() {
        return sentinel;
    }


    public int getSize() {
        return size;
    }
}

package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HyponymHandler extends NgordnetQueryHandler{

    WordNet wn;

    NGramMap ngm;

     class Node {
        String word;
        Double Occurance;
    }

    public HyponymHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> result = wn.handle(q.words());
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        minHeap heap = new minHeap();
        List<String> str = new ArrayList<>();
        result.forEach(s -> {
            Double occurance =
                    ngm.countHistory(s.trim(), startYear, endYear).data().stream().mapToDouble(Double::doubleValue).sum();
            if (heap.peek() < occurance && heap.size() > k) {
                heap.remove();
                heap.add(new HeapNode(occurance, s));
            }
        });
        if (k == 0 || k > heap.size() && !heap.isEmpty()) {
            while (!heap.isEmpty()) {
                str.add(heap.remove());
            }
        }
        else if (k > 0 && !heap.isEmpty()) {
            for (int i = 0; i < k; i++) {
                str.add(heap.remove());
            }
        }

        return "[" + String.join(", ", str) + "]";
    }

    class HeapNode {
        Double counts;
        String word;
        public HeapNode(Double counts, String word) {
            this.counts = -1 * counts;
            this.word = word;
        }
    }

    class minHeap implements Comparator<HeapNode> {
        ArrayList<HeapNode> heap;

        public minHeap() {
            heap = new ArrayList<>();
            HeapNode dummy = new HeapNode(Double.MIN_VALUE,"Dummy");
            heap.add(dummy);
        }

        public Double peek() {
            return size() <= 1 ? Double.MIN_VALUE : heap.get(1).counts * (-1);
        }
        public void add(HeapNode newNode) {
            heap.add(newNode);
            int index = heap.size() - 1;
            if (this.compare(newNode, heap.get(getParent(index))) > 0) {
                buttomUp(index);
            }
        }

        public String remove() {
            int lastIndex = heap.size() - 1;
            HeapNode result = heap.get(1);
            heap.set(1, heap.get(lastIndex));
            heap.remove(lastIndex);
            topDown(1);
            return result.word;
        }

        private void buttomUp(int index) {
            int parent = getParent(index);
            if (index == 1 || this.compare(heap.get(index), heap.get(parent)) <= 0) {
                return;
            }
            HeapNode temp = heap.get(index);
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
            HeapNode temp = heap.get(index);
            if (right <= boundary) {
                if (this.compare(temp, heap.get(left)) >= 0 && this.compare(temp, heap.get(right)) >= 0) {
                    return;
                }
                if (this.compare(heap.get(left), heap.get(right)) > 0) {
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
            else if (left <= boundary && this.compare(temp, heap.get(left)) < 0) {
                heap.set(index, heap.get(left));
                heap.set(left, temp);
                topDown(left);
            }
        }

        public boolean isEmpty() {
            return heap.size() <= 1;
        }

        public int size() {
            return heap.size();
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

        @Override
        public int compare(HeapNode h1, HeapNode h2) {
            return h1.counts.compareTo(h2.counts);
        }

    }

}

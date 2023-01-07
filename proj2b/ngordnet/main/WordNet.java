package ngordnet.main;

import java.util.*;
import java.util.stream.Collectors;

public class WordNet {
    private Graph wordGraph;
    public WordNet(String synsetFile, String hyponymFile) {
        wordGraph = new Graph(synsetFile, hyponymFile);
    }


    public List<String> handle(List<String> words) {
        Set<String> hyponymSets = null;
        for (String s : words) {
            if (hyponymSets == null) {
                hyponymSets = getAllHyponyms(s);
            }
            else if (hyponymSets.isEmpty()){
                return new ArrayList<>(hyponymSets);
            }
            else {
                hyponymSets = innerJoins(hyponymSets, getAllHyponyms(s));
            }
        }
        return hyponymSets == null ? null : hyponymSets.stream().sorted().collect(Collectors.toList());
    }

    public Set<String> innerJoins(Set<String> firstWord, Set<String>  secondWord) {
        /*
        return firstWord.stream().filter(
                f -> secondWord.stream().anyMatch(
                        f::equals)).collect(Collectors.toSet());
         */
        return firstWord.stream().filter(secondWord::contains).collect(Collectors.toSet());
    }
    public Set<String> getAllHyponyms(String word) {
        List<Integer> initial = wordToID(word);
        if (initial == null) {
            return null;
        }
        Set<Integer> existing = new HashSet<>(initial);
        Queue<Integer> hyponoymsQueue = new PriorityQueue<>(initial);
        while (!hyponoymsQueue.isEmpty()) {
            List<Integer> childHyponoyms = getHyponyms(hyponoymsQueue.remove());
            if (childHyponoyms != null) {
                childHyponoyms.stream().filter(id -> !existing.contains(id)).forEach(id ->{
                    existing.add(id);
                    hyponoymsQueue.add(id);
                });
            }
        }
        return translation(existing);
    }

    /*
    public List<String> translation(Set<Integer> graphSet) {
        return graphSet == null ? null : graphSet.isEmpty()
                ? Collections.emptyList() : graphSet.stream().map(this::idToWord).flatMap(Collection::stream).distinct()
                .sorted().collect(Collectors.toList());
    }*/

    public Set<String> translation(Set<Integer> graphSet) {
        return graphSet == null ? null : graphSet.isEmpty() ? Collections.emptySet() :
                graphSet.stream().map(this::idToWord).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    private List<Integer> wordToID(String word) {
        return wordGraph.wordSet.get(word);
    }

    private List<String> idToWord(int id) {
        return wordGraph.synSet.get(id);
    }

    private List<Integer> getHyponyms(int id) {
        return wordGraph.hyponySet.get(id);
    }

    class HeapNode {
        Double counts;
        String word;
        public HeapNode(Double counts, String word) {
            this.counts = counts;
            this.word = word;
        }
    }

    class MaxHeap implements Comparator<HeapNode> {
        ArrayList<HeapNode> heap;

        public MaxHeap() {
            heap = new ArrayList<>();
            HeapNode dummy = new HeapNode(Double.MAX_VALUE,"Dummy");
            heap.add(dummy);
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

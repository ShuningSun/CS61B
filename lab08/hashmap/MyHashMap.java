package hashmap;

import java.util.*;
import java.lang.Math;


/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    int size;

    int capacity;

    int iteration;

    double maxLoad;

    Collection<Node>[] table;

    private Iterator<Node> current;

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this.size = 0;
        this.capacity = 16;
        this.table = createTable(capacity);
        this.maxLoad = 0.75;
        this.iteration = 0;
    }

    public MyHashMap(int initialSize) {
        this.size = 0;
        this.capacity = initialSize;
        this.table = createTable(capacity);
        this.maxLoad = 0.75;
        this.iteration = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.size = 0;
        this.capacity = initialSize;
        this.table = createTable(capacity);
        this.maxLoad = maxLoad;
        this.iteration = 0;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>() ;
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        this.capacity = tableSize;
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void clear() {
        size = 0;
        table = createTable(capacity);
    }

    @Override
    public boolean containsKey(K key) {
        int index = hashTableLocation(key);
        Collection<Node> nodeCluster = table[index];
        if (nodeCluster != null) {
            for(Node n : nodeCluster)  {
                if (n.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = hashTableLocation(key);
        Node temp = getFromHashMap(key, index);
        return temp != null ? temp.value : null;
    }

    public Node getNode(K key) {
        int index = hashTableLocation(key);
        Node temp = getFromHashMap(key, index);
        return temp;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int index = hashTableLocation(key);
        insertValue(key, value, index);
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new hashMapIterator();
    }

    private int hashTableLocation(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    protected Node getFromHashMap(K key, int index) {
        Collection<Node> nodeCluster = table[index];
        if (nodeCluster != null) {
            for(Node n : nodeCluster)  {
                if (n.key.equals(key)) {
                    return n;
                }
            }

        }
        return null;
    }

    protected void reSize() {
        List<Node> holder = new LinkedList<>();
        for (K key : this) {
            holder.add(this.getNode(key));
        }
        this.size = 0;
        this.capacity = this.capacity * 2;
        this.table = createTable(capacity);
        for (Node n : holder) {
            put(n.key, n.value);
        }
    }

    protected void insertValue(K key, V value, int  index) {
        Collection<Node> nodeCluster = table[index];
        if (nodeCluster != null) {
            for(Node n : nodeCluster)  {
                if (n.key.equals(key)) {
                    n.value = value;
                    return;
                }
            }
            nodeCluster.add(new Node(key, value));
            size++;
        }
        else {
            nodeCluster = createBucket();
            nodeCluster.add(new Node(key, value));
            table[index] = nodeCluster;
            size++;
        }
        if (size >= capacity *  maxLoad) {
            reSize();
        }
    }

    private class hashMapIterator implements Iterator<K> {
        private int index;
        public hashMapIterator() {
            index = 0;
            for (; index < capacity; index++) {
                if (table[index] == null || table[index].size() == 0) {
                    continue;
                }
                current = table[index].iterator();
                break;

            }

        }
        @Override
        public boolean hasNext() {
            if (current != null) {
                if (current.hasNext()) {
                    return true;
                }
                else if (index < capacity - 1) {
                    return jumpBucket();
                }
            }
            else if (index < capacity - 1) {
                return jumpBucket();
            }
            return false;
        }

        private boolean jumpBucket() {
            index++;
            current = table[index] != null && table[index].size() != 0 ? table[index].iterator() : null;
            return hasNext();
        }
        @Override
        public K next() {
            return current.next().key;
        }

        public int getIndex() {
            return index;
        }
    }

}

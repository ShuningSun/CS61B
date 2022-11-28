package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.lang.UnsupportedOperationException;
import java.lang.Comparable;


public class BSTMap<K extends Comparable, V> implements Map61B<K, V>{

    private int size;

    private BSTNode root;

    public BSTMap() {
        size = 0;
    }

    @Override
    public void clear() {
        recursiveRemove(root);
    }

    @Override
    public boolean containsKey(K key) {
        if (size == 0) {
            return false;
        }
        return checkKey(root, key);
    }

    @Override
    public V get(K key) {
        if (size == 0) {
            return null;
        }
        return recursiveGet(root, key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (size == 0) {
            root = new BSTNode(key, value);
            size++;
        }
        else {
            recursivePut(root, key, value);
        }
    }

    private void recursivePut(BSTNode node, K key, V value) {
        int comparator = node.getKey().compareTo(key);
        if (comparator > 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTNode(key, value));
                size++;
                return;
            }
            else {
                recursivePut(node.getLeft(), key, value);
                return;
            }
        }
        else if(comparator < 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTNode(key, value));
                size++;
            }
            else {
                recursivePut(node.getRight(), key, value);
            }

        }
        else {
            node.setValue(value);
        }

    }

    private boolean checkKey(BSTNode node, K key) {
        if (node == null) {
            return false;
        }
        int comparator = node.getKey().compareTo(key);

        if (comparator > 0) {
            return checkKey(node.getLeft(), key);
        }

        if(comparator < 0) {
            return checkKey(node.getRight(), key);
        }

        return true;
    }

    private V recursiveGet(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        int comparator = node.getKey().compareTo(key);

        if (comparator > 0) {
            return recursiveGet(node.getLeft(), key);
        }

        if(comparator < 0) {
            return recursiveGet(node.getRight(), key);
        }

        return node.getValue();
    }

    private void recursiveRemove(BSTNode Node) {
        if (Node == null) {
            return;
        }
        recursiveRemove(Node.getLeft());
        recursiveRemove(Node.getRight());
        Node = null;
        size--;
    }

    public void printInOrder() {

    }

    private class BSTNode {
        private K key;
        private V value;

        BSTNode left;
        BSTNode right;
        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public BSTNode getLeft() {
            return left;
        }

        public void setLeft(BSTNode left) {
            this.left = left;
        }

        public BSTNode getRight() {
            return right;
        }

        public void setRight(BSTNode right) {
            this.right = right;
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}

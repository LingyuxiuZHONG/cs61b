package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
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
    int capacity = 16;
    double factor = 0.75;
    int size = 0;
    Set<K> keySet = new HashSet<>();

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(capacity);
    }

    public MyHashMap(int initialCapacity) {
        capacity = initialCapacity;
        buckets = createTable(capacity);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        capacity = initialCapacity;
        factor = loadFactor;
        buckets = createTable(capacity);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        if(size * 1.0 / capacity > factor){
            resize();
        }
        Node node = new Node(key,value);
        int index = Math.floorMod(node.key.hashCode(),capacity);
        if(buckets[index] == null){
            buckets[index] = createBucket();
        }
        if(keySet.contains(key)){
            for(Node a : buckets[index]){
                if (a.key.equals(key)){
                    a.value = value;
                    return node;
                }
            }
        }
        keySet.add(key);
        buckets[index].add(node);
        size++;
        return node;
    }

    private void resize(){
        capacity *= 2;
        Collection<Node>[] newBuckets = new Collection[capacity];
        for(int i = 0;i < capacity/2;i++){
            if(buckets[i] != null){
                for(Node node : buckets[i]){
                    int index = Math.floorMod(node.key.hashCode(),capacity);
                    if(newBuckets[index] == null){
                        newBuckets[index] = createBucket();
                    }
                    newBuckets[index].add(node);
                }
            }

        }
        buckets = newBuckets;
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
        return new LinkedList<>();
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
        Collection<Node>[] table = new Collection[tableSize];
        return table;
    }


    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        createNode(key,value);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(),capacity);
        if(buckets[index] == null || !containsKey(key)) {
            return null;
        }else{
            for(Node node : buckets[index]){
                if(node.key.equals(key)){
                    return node.value;
                }
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        if(keySet.contains(key)){
            return true;
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        buckets = createTable(capacity);
        keySet = new HashSet<>();
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 8.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        int index = Math.floorMod(key.hashCode(),capacity);
        V a = null;
        if(buckets[index] == null){
            return a;
        }
        for(Node node : buckets[index]){
            if(node.key.equals(key)){
                a = node.value;
                buckets[index].remove(node);
                keySet.remove(key);
                break;
            }
        }
        return a;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */

    private class myHashMapIterator<T> implements Iterator<T>{
        Object[] keyset = keySet.stream().toArray();

        int point = 0;
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            if(point < keyset.length){
                return true;
            }
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements to iterate.");
            }
            T a = (T)keyset[point];
            point++;
            return a;
        }
    }
    @Override
    public Iterator<K> iterator() {
        return new myHashMapIterator<>();
    }


}

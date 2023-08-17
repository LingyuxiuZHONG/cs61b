import java.util.*;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V>{

    Node root;
    Set<K> keySet = new TreeSet<>();
    private class Node{
        K key;
        V value;
        int size;
        Node left;
        Node right;
        Node(K key,V value,int size){
            this.key = key;
            this.value = value;
            this.size = size;
        }
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
        root = put(key,value,root);
    }

    private Node put(K key,V value,Node node){
        if(node == null){
            keySet.add(key);
            return new Node(key,value,1);
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            node.left = put(key,value,node.left);
        }else if(cmp > 0){
            node.right = put(key,value,node.right);
        }else{
            node.value = value;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private int size(Node node) {
        if (node == null) return 0;
        else return node.size;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        return get(key,root);
    }

    private V get(K key,Node node){
        if(node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            return get(key,node.left);
        }else if(cmp > 0){
            return get(key,node.right);
        }else{
            return node.value;
        }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        if(containsKey(key,root) != null){
            return true;
        }
        return false;
    }

    private K containsKey(K key,Node node){
        if(node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            return containsKey(key,node.left);
        }else if(cmp > 0){
            return containsKey(key,node.right);
        }else{
            return node.key;
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size(root);
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
    }



    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        return keySet;
    }



    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    V value = null;
    @Override
    public V remove(K key) {
        value = null;
        root = remove(key,root);
        if(value != null){
            keySet.remove(key);
        }
        return value;
    }


    private Node remove(K key,Node node){
        if(node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            node.left = remove(key,node.left);
        }else if(cmp > 0){
            node.right = remove(key,node.right);
        }else{
            value = node.value;
            if(node.left == null){
                return node.right;
            }else if(node.right == null){
                return node.left;
            }
            Node LMR = findLeftMostRight(node.left);
            remove(LMR.key,node);
            node.key = LMR.key;
            node.value = LMR.value;

        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;

    }

    private Node findLeftMostRight(Node node){
        if(node.right == null){
            return node;
        }else{
            return findLeftMostRight(node.right);
        }

    }


    private class BSTMapIterator<K> implements Iterator<K>{
        Object[] arr = new Object[root.size];
        int point = 0;
        int index = 0;
        BSTMapIterator(){
            BSTArray(root);
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        private void BSTArray(Node node){
            if(node == null){
                return;
            }
            arr[point] = node;
            BSTArray(node.left);
            BSTArray(node.right);
        }
        @Override
        public boolean hasNext() {
            if(index < root.size){
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
        public K next() {
            if(!hasNext()){
                throw new NoSuchElementException("No more elements");
            }
            K a = (K)arr[index];
            index++;
            return a;
        }
    }
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator<>();
    }


}

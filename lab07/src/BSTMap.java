import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V>{

    Node root;
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
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return null;
    }


}

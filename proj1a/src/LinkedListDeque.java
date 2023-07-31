import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    Node<T> sentinel;
    int size;
    private class Node<T>{
        T item;
        Node<T> prev;
        Node<T> next;
        Node(T item,Node<T> prev,Node<T> next){
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
        Node(T item){
            this.item = item;
        }
        public T getItem(){
            return item;
        }
        public Node<T> getprev(){
            return prev;
        }
        public Node<T> getnext(){
            return next;
        }
    }
    public LinkedListDeque() {
        sentinel = new Node<>(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Node<T> a = new Node<>(x,sentinel,sentinel.next);
        sentinel.next.prev = a;
        sentinel.next = a;
        if(size == 0){
            sentinel.prev = a;
        }
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node<T> a = new Node<>(x,sentinel.prev,sentinel);
        sentinel.prev.next = a;
        sentinel.prev = a;
        if(size == 0){
            sentinel.next = a;
        }
        size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter tne deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        toList(sentinel,list);
        return list;
    }

    private void toList(Node<T> node,List<T> list){
        if(node.next == sentinel){
            return ;
        }
        list.add(node.next.item);
        toList(node.next,list);
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        if(size == 0){
            return true;
        }
        return false;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        Node<T> first = sentinel.next;
        if(first == sentinel){
            return null;
        }
        sentinel.next = first.next;
        first.next.prev = sentinel;
        return first.item;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        Node<T> last = sentinel.prev;
        if(last == sentinel){
            return null;
        }
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        return last.item;
    }

    /**
     * The Deque abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively.Does
     * not alter the deque.
     *
     * @param index index to get, assumes valid index
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        Node<T> a = sentinel.next;
        for(int i = 0;i < index;i++){
            a = a.next;
        }
        return a.item;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively.
     *
     * @param index index to get, assumes valid index
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {

        return getRecursive(index,0,sentinel.next);
    }

    private T getRecursive(int index,int position,Node<T> node){
        if(index == position){
            return node.item;
        }
        return getRecursive(index,++position,node.next);

    }


    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addLast(6);
        lld.addLast(10);
        List<Integer> a = lld.toList();
    }
}

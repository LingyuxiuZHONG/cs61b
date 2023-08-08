import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T>{
    int size;
    T[] items = (T[]) new Object[8];
    int nextFirst = 4;
    int nextLast = 5;
    public ArrayDeque(){

    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if(size == items.length){
            resize();
        }
        items[nextFirst] = x;
        size++;
        nextFirst = nextFirst - 1 < 0 ? items.length - 1 : nextFirst - 1;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if(size == items.length){
            resize();
        }
        items[nextLast] = x;
        size++;
        nextLast = nextLast + 1 > items.length - 1 ? 0 : nextLast + 1;
    }

    private void resize(){
        T[] temp = (T[]) new Object[size * 2];
        size *= 2;
        nextFirst = size / 4 - 1;
        nextLast = (nextFirst + 1) + size / 2 + 1;
        int index = 0;
        for(int i = nextFirst + 1;i < nextLast;i++){
            temp[i] = get(index);
            index++;
        }
        items = temp;
    }

    /**
     * Returns a List copy of the deque. Does not alter tne deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for(int i = 0;i < size;i++){
            returnList.add(get(i));
        }
        return returnList;
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
        int a = nextFirst+1 > items.length - 1 ? 0 : nextFirst+1;
        if(items[a] != null){
            nextFirst = a;
        }
        size--;
        return items[a];
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        int a = nextLast-1 < 0 ? items.length - 1 : nextLast-1;
        if(items[a] != null){
            nextLast = a;
        }
        size--;
        return items[a];
    }

    /**
     * The Deque abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element. Does
     * not alter the deque.
     *
     * @param index index to get, assumes valid index
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        int realIndex = nextFirst + 1 + index > items.length - 1 ? nextFirst + 1 + index - items.length: nextFirst + 1 + index;
        return items[realIndex];
    }

    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();
    }
}

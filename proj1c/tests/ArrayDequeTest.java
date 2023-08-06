import deque.ArrayDeque;
import deque.Deque;
import deque.LinkedListDeque;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDequeTest {
    @Test
    public void IteratorTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);


        for(int x : lld1){

        }


    }

    @Test
    public void equalTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        Deque<Integer> lld2 = new ArrayDeque<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);

        lld2.addLast(0);
        lld2.addLast(1);
        lld2.addLast(2);
        lld2.addLast(3);

        assertThat(lld1.equals(lld2)).isEqualTo(true);

    }

    @Test
    public void toStringTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);


        assertThat(lld1.toString()).isEqualTo("[0, 1, 2, 3]");

    }
}

import deque.MaxArrayDeque;
import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {

    private class innerComparator<Integer> implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return (int) o1 - (int) o2;
        }
    }
    @Test
    public void maxInnerTest() {
        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<>(new innerComparator());
        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);


        assertThat(lld1.max()).isEqualTo(3);

    }
    @Test
    public void maxOuterTest(){
        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<>();
        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);


        assertThat(lld1.max(new innerComparator<>())).isEqualTo(3);
    }
}

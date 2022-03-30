import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestLinkedListRemover {
    private final LinkedListRemover linkedListRemover;

    TestLinkedListRemover() {
        linkedListRemover = new LinkedListRemover();
    }

    @Test
    public void TestEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> new ListNode(new int[]{}));
    }

    @Test
    public void TestRemover() {
        var list = new ListNode(new int[]{1, 2, 3, 4, 5});
        var head = linkedListRemover.removeNthFromEnd(list, 2);
        compare(head, new int[]{1, 2, 3, 5});
    }

    private void compare(ListNode list, int[] expected) {
        ArrayList<Integer> actual = new ArrayList<>();
        while (list != null) {
            actual.add(list.val);
            list = list.next;
        }
        int[] array = actual.stream().mapToInt(i -> i).toArray();
        Assertions.assertArrayEquals(expected, array);
    }

    @Test
    public void TestSingleElem() {
        var list = new ListNode(new int[]{1});
        var head = linkedListRemover.removeNthFromEnd(list, 1);
        compare(head, new int[]{});

    }
}


import java.util.LinkedList;

//You are given the head of a linked list.
//You are also given an integer n.
//Remove the nth node from the end of the linked list and return it’s head.
//Example
//Input: head = [1, 2, 3, 4, 5], n = 2
//Output: [1, 2, 3, 5]
public class LinkedListRemover {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int length = getLength(head);
        var index = length - n;

        ListNode curr = head;
        ListNode newHead = new ListNode(0);
        var rv = newHead;

        for (var i = 0; i < length; i++) {
            if (i != index) {
                newHead.next = curr;
                newHead = newHead.next;
            }
            curr = curr.next;
            newHead.next = null;
        }

        return rv.next;
    }

    private int getLength(ListNode head) {
        var length = 0;
        var current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }
}

public class Main {
    public static void main(String[] args) {
        ListNode head = new ListNode(new int[]{1, 2, 3, 4, 5});
//        var x = new ListNode(null);

        var rv = new LinkedListRemover().removeNthFromEnd(head, 2);

        System.out.println(rv);
    }
}

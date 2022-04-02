public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public ListNode(int[] nums) {
        if (nums.length == 0) {
            throw new IllegalArgumentException("nums is empty");
        }
        else {
            this.val = nums[0];
            ListNode head = this;
            for (int i = 1; i < nums.length; i++) {
                head.next = new ListNode(nums[i]);
                head = head.next;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode head = this;
        while (head != null) {
            sb.append(head.val);
            sb.append("->");
            head = head.next;
        }
        sb.append("null");
        return sb.toString();
    }
}

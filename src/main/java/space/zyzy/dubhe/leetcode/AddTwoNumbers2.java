package space.zyzy.dubhe.leetcode;

import java.math.BigInteger;

public class AddTwoNumbers2 {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return reverseAndCreate(reverseAndAdd(listNodeToString(l1), listNodeToString(l2)));
    }

    private static StringBuilder listNodeToString(ListNode node) {
        StringBuilder sb = new StringBuilder();
        while (node != null) {
            sb.append(node.val);
            node = node.next;
        }
        return sb;
    }

    private static String reverseAndAdd(StringBuilder val1, StringBuilder val2) {
        return (new BigInteger(val1.reverse().toString()).add(new BigInteger(val2.reverse().toString())).toString());
    }

    private static ListNode reverseAndCreate(String valStr) {
        ListNode dummy = new ListNode(0);
        ListNode p = dummy;
        int lastIndex = valStr.length() - 1;
        for (int i = lastIndex; i >= 0; i--) {
            p.next = new ListNode(Character.getNumericValue(valStr.charAt(i)));
            p = p.next;
        }
        return dummy.next;
    }

    /**
     * 这个的算法精髓在于没有反转字符串在相加在反转,而是将进位带到下一个循环里面
     */
    private static ListNode __addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode p, dummy = new ListNode(0);
        p = dummy;
        while (l1 != null || l2 != null || carry != 0) {
            if (l1 != null) {
                carry += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                carry += l2.val;
                l2 = l2.next;
            }
            p.next = new ListNode(carry % 10);
            carry /= 10;
            p = p.next;
        }
        return dummy.next;
    }


    public static void main(String[] args) {
        ListNode l1 = reverseAndCreate("342");
        ListNode l2 = reverseAndCreate("465");
        System.out.println(listNodeToString(__addTwoNumbers(l1, l2)));
    }

    public static class ListNode {

        int val;

        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


}

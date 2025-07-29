/**
 * @author nakhoonchoi
 * @date 2025/07/29
 * @see https://leetcode.com/problems/add-two-numbers-ii/description/
 * @mem 44.8MB
 * @time 2ms
 * @caution
 * [고려사항]
 * 스택을 이용해서 문제를 해결했다.
 * 이 로직을 이용하면 BigInteger 객체의 덧셈을 구현할 수 있을 것이라고 생각했다.
 *
 * l1 연결 리스트와 l2 연결 리스트를 ArrayDeque 스택으로 옮겨 담았고,
 * 두 스택 크기 중 작은 크기에 대해서 합을 구하면서
 * 오버플로우(10이 넘어가는 경우)가 발생하면 상위 자리에 1을 추가하면서 result 스택에 담아주었다.
 *
 * 마지막으로 result 스택에 남은 자리수를 처리한 뒤 (예를 들어 7243과 564 였다면 남은 자리수는 7)
 * result 스택에서 합산된 값을 pop 하면서 ListNode 객체에 연결시켜주었다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Add Two Numbers II'

public class LeetCode445{
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Deque<ListNode> firstStack = new ArrayDeque<>();
        Deque<ListNode> secondStack = new ArrayDeque<>();
        Deque<ListNode> resultStack = new ArrayDeque<>();

        ListNode temp;

        temp = l1;
        while(true){
            firstStack.add(temp);

            if(Objects.isNull(temp.next)){
                break;
            }

            temp = temp.next;
        }

        temp = l2;
        while(true){
            secondStack.add(temp);

            if(Objects.isNull(temp.next)){
                break;
            }

            temp = temp.next;
        }

        ListNode resultHead;
        boolean isUpper = false;

        int minSize = Math.min(firstStack.size(), secondStack.size());

        for(int i=0;i<minSize;i++){
            ListNode listNode1 = firstStack.pollLast();
            ListNode listNode2 = secondStack.pollLast();

            int sum = listNode1.val + listNode2.val;

            if(isUpper){
                sum++;
            }

            isUpper = sum >= 10;

            listNode1.val = sum % 10;

            resultStack.add(listNode1);
        }

        if(firstStack.isEmpty()){
            while(!secondStack.isEmpty()){
                ListNode listNode = secondStack.pollLast();

                int value = listNode.val;
                if(isUpper){
                    value++;
                }

                isUpper = value >= 10;

                listNode.val = value % 10;

                resultStack.add(listNode);
            }

            if(isUpper){
                resultStack.add(new ListNode(1));
            }
        }else{
            while(!firstStack.isEmpty()){
                ListNode listNode = firstStack.pollLast();

                int value = listNode.val;
                if(isUpper){
                    value++;
                }

                isUpper = value >= 10;

                listNode.val = value % 10;

                resultStack.add(listNode);
            }

            if(isUpper){
                resultStack.add(new ListNode(1));
            }
        }

        resultHead = resultStack.pollLast();
        ListNode connectListNode = resultHead;
        while(!resultStack.isEmpty()){
            connectListNode.next = resultStack.peekLast();

            connectListNode = resultStack.pollLast();
        }

        return resultHead;
    }
    public void testCase() {
        /* 첫 번째 TC */
        ListNode listNode3 = new ListNode(3);
        ListNode listNode2 = new ListNode(4, listNode3);
        ListNode listNode1 = new ListNode(2, listNode2);
        ListNode head = new ListNode(7, listNode1);

        ListNode listNode5 = new ListNode(4);
        ListNode listNode4 = new ListNode(6, listNode5);
        ListNode head2 = new ListNode(5, listNode4);

        ListNode result = addTwoNumbers(head, head2);

        StringBuilder sb = new StringBuilder();
        while(true){
            sb.append(result.val);

            if(Objects.nonNull(result.next)){
                result = result.next;
            }else{
                break;
            }
        }

        System.out.println(sb);
    }
    public class ListNode{
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
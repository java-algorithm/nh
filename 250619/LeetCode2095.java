/**
 * @author nakhoonchoi
 * @date 2025/06/10
 * @see https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/
 * @mem 68.59MB
 * @time 15ms
 * @caution
 * [고려사항]
 * LinkedList가 있을 때 전체 Depth 중 중간의 노드를 지우면 되는 문제였다.
 *
 * 💡 문제를 풀 때 사용했던 테스트케이스는 main 메소드에 3가지 공유하겠다.
 *
 * 로직의 순서를 단순하게 생각해보면
 * 1. 지워야할 중간(의 앞)의 노드를 찾는다.
 * 2. 중간의 앞 노드의 next 포인터를 다다음 노드(.next.next)로 바꿔준다.
 *
 * 문제의 힌트1을 읽고 두번 건너뛰는 cur 포인터와 하나씩 건너뛰는 halfNode 포인터 두 개를 이용해서 지워야할 중간 노드를 찾았다.
 * (⚠️ cur.next = cur.next.next와 같이 멤버 접근 연산자를 사용할 수 없는 줄 알고 풀었다.)
 * 중간 노드를 찾으면서 생성자로만 next 멤버 변수를 업데이트 할 수 있는 줄 알고 스택용 덱(deque)에 중간까지의 값을 저장해놓았다.
 *
 * 중간 노드를 찾았다면 deque에는 중간 노드 이전 값(val)이 저장되어있을 것이기 때문에
 * new ListNode(중간 노드 이전 값, 중간 노드.다음); 으로 next 포인터를 변경 시킨뒤에
 * deque이 빌 때까지 스택처럼 val을 뽑으며 새로 값을 전부 덮어씌운 후 새로운 head를 만들어서 반환했다.
 *
 * ⚠️ 문제를 제출해서 AC를 맞고 보니 최적의 답이 아니었음을 알 수 있었다.
 * 위에서 언급한 내용처럼 ListNode의 next 멤버 변수에 연산자로 접근할 수 있는 줄 모르고 풀었기 때문에 스택을 사용해서 덮어씌운 부분이 비효율적이었다.
 * while 문으로 ListNode의 depth를 탐색하면서 중간 위치의 이전 노드 prev, 중간 노드 halfNode, 현재 노드 cur을 이용해서
 * cur이 끝에 도달했을 때의 prev 기준으로 prev.next = halfNode.next로 수정해주면 스택이 필요없이 최적의 답을 구할 수 있었다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode 75> 'Delete the Middle Node of a Linked List'

public class LeetCode2095{
    public static void main(String[] args) {
        /* 첫 번째 TC */
        ListNode end = new ListNode(6);
        ListNode listNode5 = new ListNode(2, end);
        ListNode listNode4 = new ListNode(1, listNode5);
        ListNode listNode3 = new ListNode(7, listNode4);
        ListNode listNode2 = new ListNode(4, listNode3);
        ListNode listNode1 = new ListNode(3, listNode2);
        ListNode head = new ListNode(1, listNode1);

        /* 두 번째 TC */
//        ListNode end = new ListNode(1);
//        ListNode head = new ListNode(2, end);

        /* 세 번째 TC */
//        ListNode head = new ListNode(2);

        //중간의 앞에 있는 노드를 찾아서 그 next 포인터만 다다음의 포인터로 바꿔주면 될듯
        ListNode newHead = deleteMiddle(head);

        System.out.println(newHead);
    }
    public static class ListNode{
        int val;
        ListNode next;
        ListNode(){}
        ListNode(int val){ this.val = val; }
        ListNode(int val, ListNode next){ this.val = val; this.next = next; }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }

    public static ListNode deleteMiddle(ListNode head){
        ListNode cur = head;
        ListNode halfNode = head;

        Deque<Integer> deque = new ArrayDeque<>();

        while(cur.next != null){
            if(cur.next.next != null) {
                cur = cur.next.next;
            }else{
                cur = cur.next;
            }
            deque.offer(halfNode.val);
            halfNode = halfNode.next;
        }

        ListNode newHead;

        if(deque.isEmpty()){
            newHead = null;
        }else{
            newHead = new ListNode(deque.pollLast(), halfNode.next);
        }

        while(!deque.isEmpty()){
            int val = deque.pollLast();
            newHead = new ListNode(val, newHead);
        }

        return newHead;
    }

    public static ListNode deleteMiddle2(ListNode head){
        if(head == null || head.next == null) return null;

        ListNode prev = null;
        ListNode halfNode = head;
        ListNode cur = head;
        while(cur != null && cur.next != null){
            prev = halfNode;
            halfNode = halfNode.next;
            cur = cur.next.next;
        }
        prev.next = halfNode.next;
        return head;
    }
}
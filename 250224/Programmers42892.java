/**
 * @author nakhoonchoi
 * @date 2025/02/19
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/42892
 * @caution
 * [고려사항]
 * 2차원 배열 형태의 노드 정보를 Node class로 변환한 뒤에 List에 저장하고,
 * 노드의 x와 y 좌표 기반으로 y 내림차순, x 오름차순으로 정렬했다.
 *
 * 각 노드를 이진 트리로 변환하기 위해 루트 노드를 변수에 넣고
 * 재귀 방식으로 부모의 x와 자식의 x를 비교하며 부모를 변경하며 적절한 위치에 자식 노드를 배치 시켰다.
 *
 * 전위 순회와 후위 순회도 마찬가지로 재귀를 이용해서 결과값을 넣었는데,
 * 전위 순회와 후위 순회는 항상 헷갈리는데
 * 부모 노드 -> 왼 자식 노드 -> 오른 자식 노드 방향으로 탐색한다고 하면
 * 이를 앞 -> 중간 -> 뒤로 생각해서 '전','중','후'로 판단하여 그 위치의 값을 결과로 저장한다고 생각하면 됐다.
 *
 * 전위 순회는 부모 노드 (저장할 노드) -> 왼쪽 자식 노드 -> 오른 자식 노드 탐색
 * 후위 순회는 부모 노드 -> 왼쪽 자식 노드 -> 오른 자식 노드 (저장할 노드) 탐색
 *
 * 으로 구현했다.
 *
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2019 Kakao Blind Recruitment> '길 찾기 게임'

public class Programmers42892 {
    List<Integer> preOrderResult;
    List<Integer> postOrderResult;

    public int[][] solution(int[][] nodeinfo) {
        List<Node> nodeList = new ArrayList<>();
        Node rootNode;

        for(int i=0;i<nodeinfo.length;i++){
            Node node = new Node(nodeinfo[i][0], nodeinfo[i][1], i+1);
            nodeList.add(node);
        }
        Collections.sort(nodeList);

        rootNode = nodeList.get(0);

        for(int i=1;i<nodeList.size();i++){
            insertNode(rootNode, nodeList.get(i));
        }

        preOrderResult = new ArrayList<>();
        postOrderResult = new ArrayList<>();

        preOrder(rootNode);
        postOrder(rootNode);

        int [][] answer = new int[2][];

        answer[0] = preOrderResult.stream().mapToInt(num -> num).toArray();
        answer[1] = postOrderResult.stream().mapToInt(num -> num).toArray();

        return answer;
    }

    public void insertNode(Node parent, Node child) {
        if (child.x < parent.x) {
            if (parent.left == null) {
                parent.left = child;
            } else {
                insertNode(parent.left, child);
            }
        } else {
            if (parent.right == null) {
                parent.right = child;
            } else {
                insertNode(parent.right, child);
            }
        }
    }

    public void preOrder(Node curNode){
        if(curNode == null){
            return;
        }
        preOrderResult.add(curNode.num);
        preOrder(curNode.left);
        preOrder(curNode.right);
    }

    public void postOrder(Node curNode){
        if(curNode == null){
            return;
        }
        postOrder(curNode.left);
        postOrder(curNode.right);
        postOrderResult.add(curNode.num);
    }

    class Node implements Comparable<Node>{
        int x;
        int y;
        int num;

        Node left;
        Node right;

        Node(int x, int y, int num){
            this.x = x;
            this.y = y;
            this.num = num;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        @Override
        public int compareTo(Node o) {
            if(this.y == o.y){
                return Integer.compare(this.x, o.x);
            }
            return Integer.compare(this.y, o.y) * -1;
        }
    }
}
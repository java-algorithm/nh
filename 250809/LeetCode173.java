/**
 * @author nakhoonchoi
 * @date 2025/08/09
 * @see https://leetcode.com/problems/binary-search-tree-iterator/description/
 * @mem 48.6MB
 * @time 17ms
 * @caution
 * [고려사항]
 * Binary Search Tree를 in-order traversal(중위순회)로 순회해서
 * 다음 값이 존재하는 지 or 값이 있다면 값을 반환해야했다.
 * public void inOrderSearch(TreeNode root){
 *     if(Objects.nonNull(root.left)) {
 *         inOrderSearch(root.left);
 *     }
 *     list.add(root.val);
 *     if(Objects.nonNull(root.right)) {
 *         inOrderSearch(root.right);
 *     }
 * }
 *
 * 아래와 같이 최대한 treeNode의 왼쪽을 먼저 list에 담고, 현재 값을 담은 뒤에 treeNode의 오른쪽을 탐색했다.
 * 그리고 list의 현재 인덱스를 기반으로 반환한다.
 * 
 * ArrayList외에 스택으로 구현했으면 더 효율적이었을 것 같다.
 *
 * 맨 아래에 사용한 테스트 코드를 주석으로 달아놓았다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Binary Search Tree Iterator'

public class LeetCode173 {
    class BSTIterator {
        int curIndex = 0;
        List<Integer> list = new ArrayList<>();

        public BSTIterator(TreeNode root) {
            inOrderSearch(root);
        }

        public void inOrderSearch(TreeNode root){
            if(Objects.nonNull(root.left)) {
                inOrderSearch(root.left);
            }
            list.add(root.val);
            if(Objects.nonNull(root.right)) {
                inOrderSearch(root.right);
            }
        }

        public int next() {
            return list.get(curIndex++);
        }

        public boolean hasNext() {
            return curIndex < list.size();
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

//    public static void main(String[] args) {
//        TreeNode treeNode7 = new TreeNode(7);
//        TreeNode treeNode3 = new TreeNode(3);
//        TreeNode treeNode15 = new TreeNode(15);
//        TreeNode treeNode9 = new TreeNode(9);
//        TreeNode treeNode20 = new TreeNode(20);
//
//        treeNode7.left = treeNode3;
//        treeNode7.right = treeNode15;
//        treeNode15.left = treeNode9;
//        treeNode15.right = treeNode20;
//
//        BSTIterator bst = new BSTIterator(treeNode7);
//    }
}
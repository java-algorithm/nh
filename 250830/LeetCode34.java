/**
 * @author nakhoonchoi
 * @date 2025/08/26
 * @see https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 * @mem 46.18MB
 * @time 0ms
 * @caution
 * [고려사항]
 * 릿코드에서 실행 시간 기준으로 100점을 받은 것이 처음이었다.
 *
 * 일반적으로 이분 탐색은 요소의 중복이 없고, 오름차순 정렬되어있는 상태에서 진행하는데
 * 이 문제는 요소의 중복이 있어서 중복된 target 요소들 중 가장 왼쪽 인덱스와 오른쪽 인덱스를 반환해야한다.
 *
 * 각 왼쪽 인덱스와 오른쪽 인덱스를 추출하기 위해 왼쪽 용과 오른쪽 용으로 두 번 이분 탐색을 했다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Find First and Last Position of Element in Sorted Array'

public class LeetCode34 {
    public static void main(String[] args) {
        int [] nums = {5,7,7,8,8,10};
        int target = 8;

        System.out.println(Arrays.toString(searchRange(nums, target)));
    }
    public static int[] searchRange(int[] nums, int target) {
        int [] answer = new int[2];

        answer[0] = binarySearchLeft(nums, target);
        answer[1] = binarySearchRight(nums, target);

        return answer;
    }

    public static int binarySearchLeft(int [] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        int index = -1;

        while(left <= right){
            int mid = left + (right - left) / 2;

            if(nums[mid] > target){
                right = mid - 1;
            }else if(nums[mid] < target){
                left = mid + 1;
            }else{
                index = mid;
                right = mid - 1;
            }
        }

        return index;
    }

    public static int binarySearchRight(int [] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        int index = -1;

        while(left <= right){
            int mid = left + (right - left) / 2;

            if(nums[mid] > target){
                right = mid - 1;
            }else if(nums[mid] < target){
                left = mid + 1;
            }else{
                index = mid;
                left = mid + 1;
            }
        }

        return index;
    }
}
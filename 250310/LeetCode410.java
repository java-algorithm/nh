/**
 * @author nakhoonchoi
 * @date 2025/03/05
 * @see https://leetcode.com/problems/split-array-largest-sum/description/
 * @mem 41.31MB
 * @time 2ms
 * @caution
 * [고려사항]
 * 부분 배열을 만든다는 게 감이 안잡혀서 힌트를 봤다.
 * 맨처음에는 자르는 위치를 기준으로 이분탐색을 해야하나 했는데
 * 합으로 제한을 걸어야하는 '값'을 기준으로 이분탐색을 해서 부분 배열을 만들었을 때 
 * 부분 배열읠 개수가 k개 이하라면 답을 갱신하고 mid의 왼쪽을 탐색한다.
 * 그리고 k개 초과라면 기준치를 올려서 mid의 오른쪽을 탐색한다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Split Array Largest Sum'

public class LeetCode410 {
    public int splitArray(int[] nums, int k) {
        int left = Arrays.stream(nums).max().getAsInt();
        int right = Arrays.stream(nums).sum();
        int answer = Integer.MAX_VALUE;

        while(left <= right){
            int mid = left + (right - left) / 2;

            int result = getSubArrayCount(nums, mid);
            if(result <= k){
                answer = mid;
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }

        return answer;
    }

    public int getSubArrayCount(int[] nums, int target) {
        int count = 0;
        int sum = 0;
        int n = nums.length;

        for(int i=0;i<n;i++){
            if(sum + nums[i] > target){
                count++;
                sum = nums[i];
            }else{
                sum += nums[i];
            }
        }
        count++;

        return count;
    }
}
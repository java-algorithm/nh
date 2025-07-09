/**
 * @author nakhoonchoi
 * @date 2025/07/09
 * @see https://leetcode.com/problems/distinct-prime-factors-of-product-of-array/description/
 * @mem 45.21MB
 * @time 29ms
 * @caution
 * [고려사항]
 * 소수 판별 알고리즘(에라토스테네스의 체)과 Set을 이용해서 문제를 해결했다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Distinct Prime Factors of Product of Array'

public class LeetCode2521 {
    public int distinctPrimeFactors(int[] nums) {
        Set<Integer> primeSet = new HashSet<>();

        int maxValue = -1;

        for(int num : nums){
            maxValue = Integer.max(maxValue, num);
        }

        maxValue++;

        boolean [] isNotPrime = new boolean[maxValue];

        for(int i=2;i<=Math.sqrt(maxValue);i++){
            for(int j=2;i*j<maxValue;j++){
                isNotPrime[i*j] = true;
            }
        }

        for(int i=0;i<nums.length;i++){
            int value = nums[i];

            int remain = 2;

            while(value != 1){
                if(value % remain == 0){
                    value /= remain;
                    primeSet.add(remain);
                }else{
                    remain = nextPrimeInt(isNotPrime, remain);
                }
            }
        }

        return primeSet.size();
    }

    public int nextPrimeInt(boolean [] isNotPrime, int remain){
        for(int i=remain+1;i<isNotPrime.length;i++){
            if(!isNotPrime[i]){
                return i;
            }
        }

        return -1; //cannot reach this value.
    }
}
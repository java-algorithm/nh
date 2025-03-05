/**
 * @author nakhoonchoi
 * @date 2025/03/05
 * @see https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/description/
 * @mem 60.26MB
 * @time 1,576ms
 * @caution
 * [고려사항]
 * 이분탐색을 이용해서 O(NlogN) 시간 복잡도로 풀었다. 그럼에도 시간이 많이 소요됐다.
 * 타 코드보다 소요 시간이 오래 걸린 것은 stream 때문인 듯 싶다.
 *
 * 우선 bloomDay 배열의 길이가 m * k보다 작다면 -1을 반환하고 시작했다.
 * left를 bloomDay의 최솟값, right를 bloomDay의 최댓값으로 정하고 이분탐색을 진행했다.
 *
 * mid 기준으로 순회하며 k 크기의 바구니에 인접한 꽃을 m개 이상 채울 수 있는지 판별하는 함수를 만들어서 반복문으로 순회해서 탐색했다.
 *
 * lower_bound 문제로 조건을 만족하는 최솟값을 찾아 반환했다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Minimum Number of Days to Make m Bouquets'

public class LeetCode1482 {
    public static void main(String[] args) {
        int [] bloomDay = {7,7,7,7,12,7,7};
        int m = 2;
        int k = 3;

        System.out.println(minDays(bloomDay, m, k));
    }
    public static int minDays(int[] bloomDay, int m, int k) {
        int left = Arrays.stream(bloomDay).min().getAsInt();
        int right = Arrays.stream(bloomDay).max().getAsInt();

        int answer = -1;

        if(bloomDay.length < m * k){
            return answer;
        }

        while(left <= right){
            int mid = left + (right - left) / 2;

            if(isCompleteBouquets(bloomDay, m, k, mid)){
                answer = mid;
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }

        return answer;
    }

    private static boolean isCompleteBouquets(int[] bloomDay, int m, int k, int target) {
        int n = bloomDay.length;
        int count = 0;

        for(int i=0;i<n-k+1;i++){
            if(isBloomOk(bloomDay, i, k, target)){
                count++;
                if(count == m){
                    return true;
                }
                i+=(k-1);
            }
        }

        return false;
    }

    private static boolean isBloomOk(int[] bloomDay, int index, int k, int target) {
        for(int i=index;i<index+k;i++){
            if(bloomDay[i] > target){
                return false;
            }
        }

        return true;
    }
}
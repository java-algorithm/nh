/**
 * @author nakhoonchoi
 * @date 2025/03/05
 * @see https://leetcode.com/problems/koko-eating-bananas/description/
 * @mem 45.26MB
 * @time 13ms
 * @caution
 * [고려사항]
 * 이분탐색 문제였다.
 * piles 배열의 최솟값을 left로, 최댓값을 right로 두고 이분탐색을 진행했고,
 * k를 기준으로 H를 구했을 때 구한 H가 h보다 작다면 k를 최솟값으로 갱신해주었다.
 *
 * getH 과정에서 원래는 아래와 같이 나머지와 몫을 기준으로 분기처리해서 더해줬는데,
 * for(int pile : piles){
 *     int quotient = pile / k; //몫
 *     int remain = pile % k; //나머지
 *     count += (remain == 0 ? quotient : quotient + 1);
 * }
 *
 * 아래와 같이 MOD 연산을 통해 코드를 줄였다.
 * for(int pile : piles){
 *     count += (pile + k - 1 / k);
 * }
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Koko Eating Banana'

public class LeetCode875 {
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = Arrays.stream(piles).max().getAsInt();
        int answer = -1;

        while(left <= right){
            int mid = left + (right - left) / 2;

            long result = getH(piles, mid);
            if(result <= h){
                answer = mid;
                right = mid - 1;
            }else {
                left = mid + 1;
            }
        }

        return answer;
    }

    public long getH(int [] piles, int k){
        long count = 0;
        for(int pile : piles){
            count += (pile + k - 1) / k; //올림 연산
        }

        return count;
    }
}
/**
 * @author nakhoonchoi
 * @date 2025/03/04
 * @see https://leetcode.com/problems/first-bad-version/
 * @mem 40.97MB
 * @time 25ms
 * @caution
 * [고려사항]
 * 간단한 이분 탐색 문제였다.(lower_bound)
 * 특정 숫자 이후 버전은 모두 bad version으로, bad version 중 가장 최솟값을 구하면 되는 문제였다.
 *
 * 초기 left를 1, right를 n으로 둔 뒤에
 * mid 값이 isBadVersion이 true라면 answer 값을 갱신하고 왼쪽 탐색,
 * false라면 오른쪽을 탐색해서 bad version의 최솟값을 찾는다.
 *
 * 실제 isBadVersion은 LeetCode에 내장되어있다.
 * [입력사항]
 * [출력사항]
 */
//LeetCode <LeetCode> 'First Bad Version'

public class LeetCode278 {
    public int firstBadVersion(int n) {
        int left = 1;
        int right = n;
        int answer = n;

        while(left <= right){
            int mid = left + (right - left) / 2;

            if(isBadVersion(mid)){
                answer = mid;
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }

        return answer;
    }

    public boolean isBadVersion(int n){
        return false;
    }
}

/**
 * @author nakhoonchoi
 * @date 2025/06/18
 * @see https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 * @mem 61.83MB
 * @time 2ms
 * @caution
 * [고려사항]
 * 현장 코테에서 받았던 문제와 똑같다.(회사와 플랫폼은 X)
 * 당시에 이 문제를 시간 내에 못 풀어서 다시 풀어봤다.
 *
 * 슬라이딩 윈도우나 투 포인터도 생각했지만 조건이 너무 복잡해서 아닐 것 같았다.
 * 엄청 단순한 문제일 것 같고 실제로 문제 난이도도 Easy인데 나에게는 Easy하지 못했다.
 *
 * 아마 다시 문제를 풀더라도 시간 안에는 못 풀지 않았을까 싶다. 엄청 오래 걸렸다.
 * 배열을 O(N)으로 i 변수로 한 번 탐색하면서
 * 현재까지 최솟값(min)과 prices[i]의 차이 중 최댓값을 갱신해줘서
 * 마지막에 차이 중 최댓값을 return 하면 되는 문제였다.
 *
 * 쉬운 문제를 너무 어렵게 생각했다... 쉬운 문제이지만 누군가에게는 어려운 문제일지도...?
 * [입력사항]
 * [출력사항]
 */
//LeetCode <LeetCode> 'Best Time to Buy and Sell Stock'

public class LeetCode121 {
    public int maxProfit(int[] prices) {
        int answer = 0;
        int min = Integer.MAX_VALUE;
        for(int i=0;i<prices.length;i++){
            if(prices[i] < min){
                min = prices[i];
            }
            answer = Math.max(answer, prices[i] - min);
        }

        return answer;
    }
}
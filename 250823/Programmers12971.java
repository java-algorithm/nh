/**
 * @author nakhoonchoi
 * @date 2025/08/19
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/12971
 * @caution
 * [고려사항]
 * 풀이에 대한 힌트를 참고해서 푼 문제이다. DP라고 생각되어지지 않아서 어렵게 느껴졌다.
 *
 * 처음에 떠올린 아이디어를 이야기하고, DP 로직에 대해 설명하려고 한다.
 * <처음 생각한 아이디어>
 * 1. sticker의 길이가 홀수인지 짝수인지에 따라 규칙이 있을 줄 알았지만 실패.
 * 2. 각 시작점에 따라 합을 저장할 배열을 선언하고,
 *    스티커의 인덱스가 포함될 수 있는 시작점의 합에 값을 더해주었다.
 *    (이 방식은 O(N^2)이므로 N이 최대 10만인 문제에 대해서 시간 초과가 발생할 수 있었다.)
 *    (로직을 잘못 지정했는 지 정확성 테스트도 통과하지 못했다.)
 * 결론. DP
 *
 * <DP 로직>
 * 현재 인덱스의 스티커를 최적의 합에 포함할 것인지 아닌지를 생각해야한다.
 * 그리고 0번 스티커를 선택할 경우와 1번 스티커를 선택할 경우에 탐색해야하는 범위가 달라진다.
 *
 * 예를 들어 [1,2,3,4,5,6,7,8,9,10]의 스티커 배열이 입력으로 주어졌다고 하면,
 * 0번 스티커를 선택할 경우에는 마지막 인덱스의 스티커를 선택할 수 없기 때문에
 * [1,2,3,4,5,6,7,8,9]의 범위에서 최적을 구할 수 밖에 없다.
 * 그리고 1번 스티커를 선택할 경우에는 0번 인덱스의 스티커를 선택할 수 없기 때문에
 * [2,3,4,5,6,7,8,9,10]의 범위에서 최적을 구하면 된다.
 *
 * 현재 스티커를 선택한다면
 * 이전 인덱스 중 선택되지 않은 최적의 값에서 현재 스티커 값을 더하면 된다.
 * 현재 스티커를 선택하지 않을 것이라면
 * 이전 인덱스의 스티커가 선택된 최적의 값과 선택되지 않은 최적의 값 중 큰 값을 넣어준다.
 *
 * dp 배열을 3차원 배열로 다음과 같이 인덱스의 정보를 지정했다.
 * [스티커 길이][현재 인덱스 선택 여부][0번 인덱스부터 시작인지 아닌지]
 *
 * 모든 경우의 수를 최적으로 저장한 뒤에 아래의 값들 중 최댓값을 반환하면 된다.
 *
 * 위 두 개가 sticker.length - 2인 이유는 위에서 말한대로 0번 스티커를 선택할 때에는 마지막 인덱스는 탐색하지 않기 때문
 * dp[sticker.length - 2][0][0] (-> dp[sticker.length - 1][0][0]에 대입함)
 * dp[sticker.length - 2][1][0] (-> dp[sticker.length - 1][1][0]에 대입함)
 * dp[sticker.length - 1][0][1]
 * dp[sticker.length - 1][1][1]
 * [입력사항]
 * [출력사항]
 */
//프로그래머스 <Summer/Winter Coding(~2018)> '스티커 모으기(2)'

public class Programmers12971 {
    public int solution(int [] sticker) {
        if(sticker.length == 1){
            return sticker[0];
        }

        if(sticker.length == 2){
            return Math.max(sticker[0], sticker[1]);
        }

        //[스티커 길이][현재 인덱스 선택 여부][0번 인덱스부터 시작인지 아닌지]
        int [][][] dp = new int[sticker.length][2][2];

        dp[0][1][0] = sticker[0];
        dp[1][1][1] = sticker[1];

        for(int i=1;i<sticker.length - 1;i++){
            dp[i][0][0] = Math.max(dp[i-1][0][0], dp[i-1][1][0]);
            dp[i][1][0] = dp[i-1][0][0] + sticker[i];
        }

        dp[sticker.length - 1][0][0] = dp[sticker.length - 2][0][0];
        dp[sticker.length - 1][1][0] = dp[sticker.length - 2][1][0];

        for(int i=2;i<sticker.length;i++){
            dp[i][0][1] = Math.max(dp[i-1][0][1], dp[i-1][1][1]);
            dp[i][1][1] = dp[i-1][0][1] + sticker[i];
        }

        int answer = 0;
        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                answer = Math.max(answer, dp[sticker.length - 1][i][j]);
            }
        }
        return answer;
    }
}
/**
 * @author nakhoonchoi
 * @date 2025/07/30
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/161988
 * @caution
 * [고려사항]
 * 누적합 + 메모이제이션(DP) 문제였다.
 * sequence 배열의 각 값은 규칙에 따라 -1과 곱해져서 더할 때도 있고, 1과 곱한 그 수를 곱할 때도 있는데
 * 그 규칙을 dp 배열의 두번째 인자(?)에서 나눠서 계산했다.
 *
 * 우선 sequence 배열의 0번 인덱스에 대한 규칙을 설정하면서 max 연산을 해줬다.
 * 그리고 배열을 끝까지 순회하면서
 * 1. dp[i][0]과 dp[i][1]에 각각 규칙에 따라 -1을 곱해야하면 곱하고, 아니라면 1을 곱한 수를 구한다.
 * 2. 이전 dp 값을 더해서 현재 dp 값을 깎는다면 과감히 버리고 현재 규칙으로 얻은 값만 채택했다.(max 연산 이용)
 * 3. 이후에 각 dp 값을 구했다면 answer에 max 연산을 해서 갱신했다.
 *
 * 마지막으로 answer를 반환하면 정답이었다.
 * [입력사항]
 * [출력사항]
 */
//프로그래머스 <연습문제> '연속 펄스 부분 수열의 합'

public class Programmers161988 {
    public static void main(String[] args) {
        int [] sequence = {2, 3, -6, 1, 3, -1, 2, 4};

        System.out.println(solution(sequence));
    }
    public static long solution(int [] sequence) {
        long [][] dp = new long[sequence.length][2];

        dp[0][0] = sequence[0]; //1 -1 1 -1...
        dp[0][1] = sequence[0] * -1; //-1 1 -1 1...

        long answer = Math.max(dp[0][0], dp[0][1]);

        for(int i=1;i<sequence.length;i++){
            int target = sequence[i];

            dp[i][0] = Math.max(dp[i-1][0] + ((i % 2 == 0) ? 1 : -1) * target, ((i % 2 == 0) ? 1 : -1) * target);
            dp[i][1] = Math.max(dp[i-1][1] + ((i % 2 == 1) ? 1 : -1) * target, ((i % 2 == 1) ? 1 : -1) * target);

            answer = Math.max(answer, dp[i][0]);
            answer = Math.max(answer, dp[i][1]);
        }

        return answer;
    }
}
/**
 * @author nakhoonchoi
 * @date 2025/03/30
 * @see https://boj.ma/13398
 * @mem 28,352kb
 * @time 208ms
 * @caution
 * [고려사항]
 * DP로 문제를 해결했다.
 * dp 메모이제이션 배열을 2차원 배열로 선언하고, [N][2] 크기로 선언했다.
 * 2번째 원소의 0과 1은 다음과 같이 나타냈다.
 * 0은 현재 기준으로 연속된 합 중 제거하지 않은 가장 큰 수를 저장할 인덱스
 * 1은 현재 기준으로 연속된 합 중 제거를 1번 한 가장 큰 수를 저장할 인덱스
 *
 * 점화식은 현재 i번째 수를 arr[i]이라고 한다면
 * dp[i][0] = Math.max(dp[i-1][0] + arr[i], arr[i]);
 * dp[i][1] = Math.max(dp[i-1][1] + arr[i], dp[i-1][0]);
 * 로 점화식을 세우고 i를 순회하며
 * answer에 dp[i][0]와 dp[i][1], answer 중 최대를 갱신해주었다.
 *
 * N이 1일 때를 고려하지 못해서 WA를 받았었다.
 *
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP> '연속합 2'

public class BOJ13398_2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int [] arr = new int[N];
        int [][] dp = new int[N][2];
        int answer = Integer.MIN_VALUE;
        //마지막 인덱스 중 0은 현재 위치를 고려한 현황.
        //1은 현재 위치를 고려하지 않고 생각할 현황.

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        dp[0][0] = arr[0];
        answer = Math.max(answer, dp[0][0]);

        for(int i=1;i<N;i++){
            dp[i][0] = Math.max(dp[i-1][0] + arr[i], arr[i]);
            dp[i][1] = Math.max(dp[i-1][0], dp[i-1][1] + arr[i]);

            answer = Math.max(answer, Math.max(dp[i][0], dp[i][1]));
        }

        System.out.println(answer);
    }
}
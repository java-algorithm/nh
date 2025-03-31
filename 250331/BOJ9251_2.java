/**
 * @author nakhoonchoi
 * @date 2025/03/31
 * @see https://boj.ma/9251
 * @mem 16,080kb
 * @time 100ms
 * @caution
 * [고려사항]
 * DP 문제였다.
 * LCS 문제로 Longest Common Subsequence, 최장 공통 부분 수열을 나타낸다.
 * 문자열의 길이가 최대 1000이므로, O(N^2)의 알고리즘도 통과되겠다고 생각했다.
 * 문자열의 길이가 더 길다면 O(NlogN)의 알고리즘으로 탐색할 수 있는 방법을 공부해야겠다.
 *
 * 우선 LIS와 마찬가지로 2차원 배열로 dp 배열을 선언한다.
 * dp[i][j]는 str1의 i 인덱스 문자까지와 str2의 j 인덱스 문자까지의 최장 공통 부분 수열을 저장할 값이다.
 * 점화식은 str1.charAt(i)과 str2.charAt(j)가 같은 경우와 다른 경우로 생각했다.
 * - 만약 같다면, str1의 i-1 인덱스 문자까지와 str2의 j-1 인덱스까지
 *   고려했을 때의 메모이제이션 값 (dp[i-1][j-1]) + 1을 담는다.
 * - 그리고 다르다면, Math.max(dp[i-1][j], dp[i][j-1])를 담는다.
 *   왜냐하면 str1과 str2 기준으로 하나씩만 앞 문자까지의 현황 중에 큰 값을 담아야
 *   현재까지 최대값이 보장될 수 있다.
 *
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
//백준 <DP> 'LCS'

public class BOJ9251_2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str1 = br.readLine();
        String str2 = br.readLine();

        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i=1;i<=str1.length();i++) {
            for (int j=1;j<=str2.length();j++) {
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        System.out.println(dp[str1.length()][str2.length()]);
    }
}
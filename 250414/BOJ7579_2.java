/**
 * @author nakhoonchoi
 * @date 2025/04/12
 * @see https://boj.ma/7579
 * @mem 18,596kb
 * @time 128ms
 * @caution
 * [고려사항]
 * DP, 냅색 문제였다.
 * 다른 문제와 똑같이 2차원 dp 배열을 선언했고, dp 배열에 대해서는
 * dp[몇 번째 프로그램까지 고려됐는 지][비용] = 메모리로 생각해서 점화식을 세웠다.
 * 마지막 프로그램까지 고려한 이후에 기준 메모리 이상이 되는 가장 최소의 비용 값을 출력했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP> '앱'

public class BOJ7579_2 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int [] memory = new int[N+1];
        int [] cost = new int[N+1];

        st = new StringTokenizer(br.readLine());
        for(int i=1;i<=N;i++) {
            memory[i] = Integer.parseInt(st.nextToken());
        }

        int size = 0;
        st = new StringTokenizer(br.readLine());
        for(int i=1;i<=N;i++) {
            cost[i] = Integer.parseInt(st.nextToken());
            size += cost[i];
        }

        int [][] dp = new int[N+1][size+1];

        for(int i=1;i<=N;i++) {
            for (int j=0;j<=size;j++) {
                if(j>=cost[i]){
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j - cost[i]] + memory[i]);
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        int answer = Integer.MAX_VALUE;
        for (int i=0;i<=size;i++) {
            if (dp[N][i]>=M) {
                answer = i;
                break;
            }
        }
        System.out.println(answer);
    }
}
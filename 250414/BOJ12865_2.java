/**
 * @author nakhoonchoi
 * @date 2025/04/09
 * @see https://boj.ma/12865
 * @mem 52,228kb
 * @time 160ms
 * @caution
 * [고려사항]
 * 일반적인 0-1 냅색 문제였다.
 * 2차원 배열 dp는 dp[현재까지 고려된 물건의 수][배낭의 무게]
 * 로 정해서 문제를 해결하였다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP> '평범한 배낭'

public class BOJ12865_2 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int [][] dp = new int[N+1][K+1];

        for(int i=1;i<=N;i++){
            st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            for(int j=0;j<=K;j++){
                if(j >= w) {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-w] + v);
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        System.out.println(dp[N][K]);
    }
}
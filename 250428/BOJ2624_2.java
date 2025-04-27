/**
 * @author nakhoonchoi
 * @date 2025/04/27
 * @see https://boj.ma/2624
 * @mem 17,296kb
 * @time 144ms
 * @caution
 * [고려사항]
 * coin 배열을 동전 금액에 따라 정렬해서 DP 배낭 문제처럼 풀어봤다.
 * dp[i-1] 행을 가져오는 시점을 정하는 것이 어려웠다.
 * 한 번 가져와서 dp[i][T]에 dp[i-1][T-(동전금액*개수)] 만큼 계속 더해주었고
 * 모든 경우의 수만큼 점화식을 진행한 뒤에 dp[k][T] 값을 출력하였다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP> '동전 바꿔주기'

public class BOJ2624_2 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());
        int [][] coins = new int[k][2];
        int [][] dp = new int[k+1][T+1];
        StringTokenizer st;

        for(int i=0;i<k;i++){
            st = new StringTokenizer(br.readLine());
            coins[i][0] = Integer.parseInt(st.nextToken());
            coins[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(coins, Comparator.comparing(o -> o[0]));
        dp[0][0] = 1;

        for(int i=1;i<=k;i++){
            int price = coins[i-1][0];
            int count = coins[i-1][1];

            for(int j=0;j<=T;j++){
                dp[i][j] = dp[i-1][j];
            }

            for(int j=count;j>=1;j--){
                int curValue = price * j;

                if(curValue > T){
                    continue;
                }

                for(int m=T;m>=0;m--){
                    if(m >= curValue){
                        dp[i][m] += dp[i-1][m - curValue];
                    }
                }
            }
        }

        System.out.println(dp[k][T]);
    }
}
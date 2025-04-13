/**
 * @author nakhoonchoi
 * @date 2025/04/13
 * @see https://boj.ma/2629
 * @mem 16,228kb
 * @time 104ms
 * @caution
 * [고려사항]
 * DP, 냅색 문제였다.
 * 2차원 배열을 dp[지금까지 고려된 추의 개수][무게] = 1(가능), 0(불가능) 으로 표현했다.
 * 점화식이 다른 냅색 문제와 달랐다.
 * j를 0부터 total까지 순회하면서
 * 현재 추의 무게는 무조건 측정 가능하므로 dp[i][현재 추 무게] = 1로 세팅한다.
 * 그리고 순회하면서 이전 추의 무게가 측정 가능한 추(j)였을 경우 (dp[i-1][j] == 1)
 * dp[i][Math.abs(j - weight[i])], dp[i][j + weight[i]]를 1로 세팅한다.
 * (이전 추의 무게를 현재 추와 반대로 보낼 경우, 그리고 함께 측정할 경우이다.)
 *
 * 대신에 절댓값과 합의 범위를 total 이하로 조건문을 걸어야한다.
 *
 * 그리고 Y와 N을 판별할 때 입력값이 total보다 큰 무게가 들어올 수 있기 때문에
 * 조심해야했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP> '양팔저울'

public class BOJ2629 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int [] weight = new int[N+1];
        int total = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1;i<=N;i++){
            weight[i] = Integer.parseInt(st.nextToken());
            total += weight[i];
        }

        int [][] dp = new int[N+1][total+1];

        for(int i=1;i<=N;i++){
            for(int j=0;j<=total;j++){
                if(j == weight[i]){
                    dp[i][j] = 1;
                }
                if(dp[i-1][j] == 1){
                    dp[i][j] = dp[i-1][j];
                    if(Math.abs(j - weight[i]) <= total) {
                        dp[i][Math.abs(j - weight[i])] = 1;
                    }
                    if(j + weight[i] <= total) {
                        dp[i][j + weight[i]] = 1;
                    }
                }
            }
        }

        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<M;i++){
            int target = Integer.parseInt(st.nextToken());

            if(target > total || dp[N][target] == 0) {
                sb.append("N");
            }else{
                sb.append("Y");
            }
            sb.append(" ");
        }

        System.out.println(sb.substring(0, sb.length() - 1));
    }
}
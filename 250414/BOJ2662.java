/**
 * @author nakhoonchoi
 * @date 2025/04/11
 * @see https://boj.ma/2662
 * @mem 15,236kb
 * @time 152ms
 * @caution
 * [고려사항]
 * 일반적인 배낭 문제와 다르게 각 기업 별로 무게마다 가치를 다르게 측정하고 있었다.
 * dp 배열은 [기업의 개수][현재까지 가치(투자받은 액수)] = 최적의 이익 으로 구성했다.
 *
 * 최적의 이익을 구하는 동시에 어떤 기업에 얼마의 금액을 투자했는 지 기록을 해야하기 때문에 route 배열을 따로 만들었다.
 * 점화식으로 최적의 이익을 구하면서 최적의 경우에는 route 배열 현재 기업의 투자 액수에 투자 금액을 기록해놓았다.
 *
 * 그리고 가장 많은 이익을 구한 뒤에 거꾸로 route 배열을 기반으로 마지막 기업에서 첫 번째 기업까지 순회하면서
 * result 배열에 각 기업마다 최적일 때의 투자 금액을 기록해서 문자열 형태로 출력하였다.
 *
 * 경로를 구하는 과정에서 DFS를 이용해서 백트래킹을 하려고 하니 시간 초과와 메모리 초과가 발생했다.
 * dp 배열에 최적의 값을 대입하면서 경로를 기록하는 부분에서 함수 호출 수가 많이 줄었을 거라고 생각한다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP> '기업투자'

public class BOJ2662 {
    static int N, M;
    static int [][] investmentInfo;
    static int [][] dp;
    static int [][] route;
    static int [] result;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        investmentInfo = new int[M+1][N+1];
        dp = new int[M+1][N+1];
        result = new int[M];
        route = new int[M+1][N+1];

        for(int i=1;i<=N;i++){
            st = new StringTokenizer(br.readLine());
            st.nextToken(); // 첫 번째 값은 pass

            for(int j=1;j<=M;j++){
                investmentInfo[j][i] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=1;i<=M;i++){
            for(int j=1;j<=N;j++){
                dp[i][j] = dp[i-1][j];

                for(int k=0;k<=j;k++){
                    int temp = dp[i-1][j-k] + investmentInfo[i][k];
                    if(temp > dp[i][j]){
                        dp[i][j] = temp;
                        route[i][j] = k;
                    }
                }
            }
        }

        System.out.println(dp[M][N]);

        int[] result = new int[M+1];
        int money = N;

        for(int i=M;i>=1;i--){
            result[i] = route[i][money];
            money -= result[i];
        }

        StringBuilder sb = new StringBuilder();
        for(int i=1;i<=M;i++){
            sb.append(result[i]).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
}
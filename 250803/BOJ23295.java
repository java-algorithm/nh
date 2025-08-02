/**
 * @author nakhoonchoi
 * @date 2025/08/03
 * @see https://boj.ma/23295
 * @mem 80,204kb
 * @time 432ms
 * @caution
 * [고려사항]
 * 누적합과 슬라이딩 윈도우를 이용해서 풀 수 있는 문제였다.
 * 예전에 풀었던 이모스 법을 기반으로 빠르게 누적합을 하는 부분이 시간 초과를 해결하는 방법이었다.
 * https://velog.io/@luckyprice1103/%EC%9D%B4%EB%AA%A8%EC%8A%A4-%EB%B2%95
 *
 * 간단하게 이모스 법에 대해서 설명하면, dp[s]에 1을 추가하고, dp[e]에 -1을 해서 누적합을 해보려고 한다.
 * {1,0,0,0,-1}을 -> 방향으로 누적합 한다고 했을 때 결과는 {1,1,1,1.0}이 된다.
 *
 * 이는 2차원 배열에서도 적용할 수 있다.
 *
 * 그리고 스터디 참여 수는 int 형을 벗어날 수 있다는 점을 주의해야했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <누적합> '스터디 시간 정하기 1'

public class BOJ23295 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());

        int start = 0;
        int end = 0;
        int maxEnd = 0;
        long maxEvaluation = 0;
        long curEvaluation = 0;

        long [] dp = new long[100001];

        for(int i=0;i<N;i++){
            int K = Integer.parseInt(br.readLine());

            for(int j=0;j<K;j++){
                st = new StringTokenizer(br.readLine());

                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());

                maxEnd = Math.max(maxEnd, e);

                dp[s]++;
                dp[e]--;
            }
        }

        for(int i=1;i<=maxEnd;i++){
            dp[i] += dp[i-1];
        }

        for(int i=0;i<T;i++){
            curEvaluation += dp[i];
        }

        if(curEvaluation > 0){
            maxEvaluation = curEvaluation;
            end = T;
        }

        for(int i=1;i<maxEnd - T;i++){
            curEvaluation -= dp[i-1];
            curEvaluation += dp[i+T-1];

            if(curEvaluation > maxEvaluation){
                maxEvaluation = curEvaluation;
                start = i;
                end = i+T;
            }
        }

        System.out.println(start + " " + end);
    }
}
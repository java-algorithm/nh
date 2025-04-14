/**
 * @author nakhoonchoi
 * @date 2025/04/14
 * @see https://boj.ma/1943
 * @mem 19,860kb
 * @time 268ms
 * @caution
 * [고려사항]
 * 2629번과 비슷한 문제라고 생각해서 풀었는데 체감상 백준 12920번 플래티넘 4 '평범한 배낭 2'보다 어려웠다.
 * dp[동전의 총 개수][금액] = 1(가능), 0(불가능) 으로 풀으려고 했는데 동전의 총 개수를 있는 그대로 넣으려고 하면 메모리 초과가 발생했고,
 * 12920번에서 사용했던 2진 카운팅을 넣어서 2차원 배열을 선언해도 메모리 초과가 발생했다.
 * 그래서 dp를 1차원 배열로 선언해야했다. 하지만 점화식을 세우는 과정이 2차원 배열일 때보다 어려워서 한참 헤맸다.
 * 체감상 12920번 보다 많이 어려웠다...
 *
 * dp 1차원 배열을 선언할 때 dp[금액]인데 dp 값을 갱신하러 for문으로 순회할 때 금액을 0부터 total 까지 순회하는 것이 아니라, total에서 0으로 --로 순회해야했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP/냅색> '동전분배'

public class BOJ1943 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        for(int i=0;i<3;i++){
            int N = Integer.parseInt(br.readLine());
            int total = 0;
            List<Integer> coins = new ArrayList<>();

            for(int j=0;j<N;j++){
                st = new StringTokenizer(br.readLine());
                int value = Integer.parseInt(st.nextToken());
                int count = Integer.parseInt(st.nextToken());

                total += (value * count);

                int k = 1;
                while(k <= count){
                    coins.add(value * k);
                    count -= k;
                    k *= 2;
                }
                if(count != 0){
                    coins.add(value * count);
                }
            }

            if(total % 2 == 1){
                System.out.println(0);
                continue;
            }

            int [] dp = new int[total / 2 + 1];
            dp[0] = 1;

            for(int j=1;j<=coins.size();j++){
                int coin = coins.get(j-1);
                for(int k=total / 2;k>=coin;k--){
                    if(dp[k - coin] == 1){
                        dp[k] = 1;
                    }
                }
            }

            System.out.println(dp[total / 2]);
        }
    }
}
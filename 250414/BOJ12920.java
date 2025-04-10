/**
 * @author nakhoonchoi
 * @date 2025/04/10
 * @see https://boj.ma/12920
 * @mem 69,520kb
 * @time 196ms
 * @caution
 * [고려사항]
 * 물건의 개수만큼 전부 다 비교했을 때 시간초과가 발생했다.
 * O(N^2) 만큼, 물건의 개수만큼 완전 탐색을 하면 시간 초과가 발생하는 듯 했다.
 *
 * 완전 탐색을 했을 때 시간 복잡도는 O(물건의 종류 * 가방의 최대 무게 * 각 물건의 개수)로 나타낼 수 있다.
 * 이전의 백준 12865 '평범한 배낭' 문제에서는 O(물건의 종류 * 가방의 최대 무게)로 문제가 풀렸기에
 * 완전 탐색이 아닌 각 물건의 개수를 줄여서 표현하는 법이 필요했다.
 *
 * 각 물건의 개수를 줄이는 아이디어가 떠오르지 않아 참고를 했다.
 * 이진수 분할이라는 내용인데, 예를 들어 무게가 2인 물건이 13개 있다고 하면
 * 물건의 개수를 1개, 2개, 4개, 6개의 물건을 각각 하나의 물건으로 생각해서 문제를 푸는 것이다.
 *
 * 그래서 물건을 담을 products에 물건의 개수를 이진수 분할해서
 * 1개, 2개, 4개, 8개 순으로 넣다가 나머지가 발생하면 나머지를 하나의 물건으로
 * 넣어주고 12865번 '평범한 배낭'처럼 0-1 냅색을 진행하면 된다.
 *
 * 이진수 분할이라는 개념을 알게 되어서 좋은 문제였고, 이게 플래티넘 4 문제라고?
 * 쉽게 봤는데 절대 쉬운 문제가 아니었다...
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP> '평범한 배낭 2'

public class BOJ12920 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<int []> products = new ArrayList<>();
        products.add(new int[]{-1, -1}); // index를 1부터 하기 위해 쓰레기값 하나 추가
        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            int W = Integer.parseInt(st.nextToken());
            int V = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());

            int temp = 1;
            while(temp <= count) {
                products.add(new int[]{temp * W, temp * V});
                count -= temp;
                temp *= 2;
            }
            if(count != 0) {
                products.add(new int[]{count * W, count * V});
            }
        }

        int[][] dp = new int[products.size() + 1][M + 1];

        for(int i=1;i<products.size();i++){
            int weight = products.get(i)[0];
            int value = products.get(i)[1];

            for(int j=0;j<=M;j++){
                if(j >= weight){
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weight] + value);
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        System.out.println(dp[products.size()-1][M]);
    }
}
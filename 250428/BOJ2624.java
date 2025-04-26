/**
 * @author nakhoonchoi
 * @date 2025/04/26
 * @see https://boj.ma/2624
 * @mem 68,600kb
 * @time 468ms
 * @caution
 * [고려사항]
 * DP를 생각 못하고 현재 동전 금액 수에 대한 경우의 수를 Map에 저장했다.
 * 배열이 아닌 Map을 이용한 메모이제이션을 했다.
 * 보통 배낭은 dp[i-1][T-(금액*동전개수)] 처럼 빼는 방식으로 체크를 했다면,
 * Map을 이용한 메모이제이션에는 Map에 저장된 가능한 금액들에 (금액*동전개수)를 더한 금액은 가능하다고 생각해서 새 Map에 경우의 수를 넣어주었다.
 *
 * Map으로 관리하면 배열로 관리하는 것이 아니기 때문에
 * 동전 금액의 경우의 수가 T가 넘어가더라도 확인을 굳이 안해줘도 된다는 점이 편했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP> '동전 바꿔주기'

public class BOJ2624 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());
        int [][] coins = new int[k][2];
        StringTokenizer st;

        for(int i=0;i<k;i++){
            st = new StringTokenizer(br.readLine());
            coins[i][0] = Integer.parseInt(st.nextToken());
            coins[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(coins, Comparator.comparing(o -> o[0]));

        Map<Integer, Integer> prev = new HashMap<>();
        prev.put(0, 1);

        for (int i=0;i<k;i++) {
            int price = coins[i][0];
            int count = coins[i][1];
            Map<Integer, Integer> next = new HashMap<>(prev);

            for (int possiblePrice : prev.keySet()) {
                int possibleCount = prev.get(possiblePrice);

                for (int j=1;j<=count;j++) {
                    int newPrice = possiblePrice + j * price;
                    if (newPrice > T) break;
                    next.put(newPrice, next.getOrDefault(newPrice, 0) + possibleCount);
                }
            }
            prev = next;
        }
        System.out.println(prev.getOrDefault(T, 0));
    }
}
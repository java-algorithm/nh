/**
 * @author nakhoonchoi
 * @date 2025/07/05
 * @see https://boj.ma/2533
 * @mem 451,328kb
 * @time 2,368ms
 * @caution
 * [고려사항]
 * DFS를 이용해서 메모이제이션을 해야하는 문제였다.
 * 우선 문제에서 루트 노드에 대한 정보를 주지 않았기에 루트가 어느 곳이든지 상관없이 결과가 같겠구나 싶었다.
 * 대신에 N이 2부터 100만이기 때문에 1번 노드는 무조건 있을 거라고 생각해서 DFS 탐색은 무조건 1번 노드부터 시작했다.
 *
 * 얼리어답터가 아닌 친구는 연속으로 연결되어있으면 안된다.
 * 그래서 dp를 2차원 배열로 나타내서 'dp[노드의 인덱스][얼리어답터인지 아닌지] = 경우의 수'로 표현했다.
 * 현재 노드가 얼리어답터가 아니라면 자식(다음) 노드는 무조건 얼리어답터여야하기 때문에
 * dp[cur][0] += dp[child][1]; 로 얼리어답터인 경우만 합산했고,
 * 현재 노드가 얼리어답터라면 자식(다음) 노드는 얼리어답터이든 아니든 상관 없기 때문에
 * dp[cur][0] += (Integer.min(dp[child][0], dp[child][1])); 으로 두 경우의 수 중 최솟값을 합산했다.
 *
 * 마지막으로 1번 노드 기준으로 DFS 탐색을 한 뒤, dp[1][0]과 dp[1][1] 중 최솟값을 출력해주면 된다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DFS DP> '사회망 서비스(SNS)'

public class BOJ2533 {
    static Map<Integer, List<Integer>> treeMap;
    static boolean [] visited;
    static int [][] dp;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        treeMap = new HashMap<>();
        visited = new boolean[N+1];
        dp = new int[N+1][2];

        for(int i=0;i<N-1;i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            treeMap.putIfAbsent(a, new ArrayList<>());
            treeMap.putIfAbsent(b, new ArrayList<>());
            treeMap.get(a).add(b);
            treeMap.get(b).add(a);
        }

        dfs(1);
        System.out.println(Integer.min(dp[1][0], dp[1][1]));
    }

    static void dfs(int node){
        visited[node] = true;
        dp[node][0] = 0;
        dp[node][1] = 1;

        for(int child : treeMap.get(node)){
            if(!visited[child]){
                dfs(child);
                dp[node][0] += dp[child][1];
                dp[node][1] += (Integer.min(dp[child][0], dp[child][1]));
            }
        }
    }
}
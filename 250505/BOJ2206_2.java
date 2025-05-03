/**
 * @author nakhoonchoi
 * @date 2025/05/04
 * @see https://boj.ma/2206
 * @mem 94,884kb
 * @time 640ms
 * @caution
 * [고려사항]
 * 예전에 풀었던 문제다.
 * 예전에는 어떻게 풀었는 지 기억이 나지 않았는데 요즘 DP(메모이제이션)로 푼 문제들이 많아서 그런가
 * 메모이제이션을 이용해서 문제를 해결했다.
 *
 * 'dp[행][열][현재까지 벽을 몇 개 부쉈는지] = 경우의 수' 로 dp 3차원 배열을 선언했고

 *
 * 점화식을 잘 지정해주어야했는데 우선 map의 값이 빈 공간인지 벽인지에 따라 분기처리 했고,
 * 빈 공간이라면 dp[현재좌표][1] + 1 < dp[다음좌표][1] 일 경우와
 * dp[현재좌표][0] + 1 < dp[다음좌표][0]에 같은 상태에 대해서 길이를 1 늘려서 최소값을 dp에 반영하려고 했다.
 *
 * 그리고 벽이라면
 * dp[현재좌표][0] + 1 < dp[다음좌표][1]로
 * 이전에 벽을 한 번도 부순 적이 없었던 경우의 수만 벽의 좌표로 넘어올 수 있다는 점을 계산해주었다.
 *
 * 마지막에 dp[N-1][M-1][0]과 dp[N-1][M-1][1]이 INF가 아니라면
 * Math.min(dp[N-1][M-1][0], dp[N-1][M-1][1])을 출력해주었다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <BFS + DP> '벽 부수고 이동하기'

public class BOJ2206_2 {
    static int N, M;
    static char [][] map;
    static int [][][] dp;
    static int [] dx = {-1, 0, 1, 0};
    static int [] dy = {0, -1, 0, 1};
    static final int INF = Integer.MAX_VALUE - 10_000_000;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        dp = new int[N][M][2];

        for(int i=0;i<N;i++){
            map[i] = br.readLine().toCharArray();
            for(int j=0;j<M;j++){
                dp[i][j][0] = INF;
                dp[i][j][1] = INF;
            }
        }

        bfs();

        System.out.println(dp[N-1][M-1][0] == INF && dp[N-1][M-1][1] == INF
                    ? -1 : Math.min(dp[N-1][M-1][0], dp[N-1][M-1][1]));
    }

    public static void bfs(){
        Queue<int []> q = new ArrayDeque<>();
        q.offer(new int[]{0, 0});
        dp[0][0][0] = 1;

        while(!q.isEmpty()){
            int [] cur = q.poll();

            for(int i=0;i<4;i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if(isIn(nx, ny)){
                    if(map[nx][ny] == '0'){
                        if(dp[cur[0]][cur[1]][0] + 1 < dp[nx][ny][0]) {
                            q.offer(new int[]{nx, ny});
                            dp[nx][ny][0] = dp[cur[0]][cur[1]][0] + 1;
                        }

                        if(dp[cur[0]][cur[1]][1] + 1 < dp[nx][ny][1]){
                            q.offer(new int[]{nx, ny});
                            dp[nx][ny][1] = dp[cur[0]][cur[1]][1] + 1;
                        }
                    }else if(dp[cur[0]][cur[1]][0] + 1 < dp[nx][ny][1]){
                        q.offer(new int[]{nx, ny});
                        dp[nx][ny][1] = dp[cur[0]][cur[1]][0] + 1;
                    }
                }
            }
        }
    }

    public static boolean isIn(int x, int y){
        return x>=0 && x<N && y>=0 && y<M;
    }
}
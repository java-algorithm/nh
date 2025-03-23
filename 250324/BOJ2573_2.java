/**
 * @author nakhoonchoi
 * @date 2025/03/23
 * @see https://boj.ma/2573
 * @mem 21,996kb
 * @time 404ms
 * @caution
 * [고려사항]
 * 빙산을 체크했을 때 bfs로 했다가 시간초과가 나서 dfs로 바꿨더니 해결할 수 있었다.
 * 문제를 풀 때 집중해서 봐야겠다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DFS> '빙산'

public class BOJ2573_2 {
    static int N;
    static int M;
    static int [][] map;
    static boolean [][] visited;
    static int [][] melt;
    static int [] dx = { 1, -1, 0, 0 };
    static int [] dy = { 0, 0, 1, -1 };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        visited = new boolean[N][M];
        melt = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        solution();
    }

    public static void solution() {
        int year = 0;

        while (true) {
            // dfs 로 빙산 덩어리 세기
            int count = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (!visited[i][j] && map[i][j] != 0) {
                        dfs(i, j);
                        count++;
                    }
                }
            }

            if (count == 0) {
                System.out.println(0);
                break;
            } else if (count >= 2) {
                System.out.println(year);
                break;
            }

            melt();
            year++;
        }
    }

    //dfs 한번에 melt와 덩어리 체크.
    public static void dfs(int x, int y) {
        visited[x][y] = true;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (isIn(nx, ny)) {
                // 1년 후에 녹는 빙산의 양 구하기
                if (map[nx][ny] == 0) {
                    melt[x][y]++;
                }

                // dfs 재귀
                if (!visited[nx][ny] && map[nx][ny] != 0) {
                    dfs(nx, ny);
                }
            }
        }
    }

    public static void melt() {
        /**
         * 1. 빙산 녹이기
         * 2. 만약 녹인 높이가 음수가 되면 0으로 바꿔주기
         * 3. visited 초기화
         * 4. melt 초기화
         */
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] -= melt[i][j];

                if (map[i][j] < 0)
                    map[i][j] = 0;

                visited[i][j] = false;
                melt[i][j] = 0;
            }
        }
    }

    public static boolean isIn(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}
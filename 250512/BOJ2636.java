/**
 * @author choi
 * @date Oct 29, 2020
 * @see https://www.acmicpc.net/problem/2636
 * @mem 14,392kb
 * @time 128ms
 * @caution
 * [고려사항]
 * bfs를 이용해 바깥 공기를 찾고, 바깥 공기를 기준으로 치즈의 가장자리부터 녹인다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
import java.io.*;
//백준 <구현/BFS> '치즈'
public class BOJ2636 {
    private static int N;
    private static int M;
    private static int[][] board;
    private static boolean[][] visited;
    private static Queue<Point> meltingQueue = new LinkedList<>();
    private static int day = 0;
    private static int cheese = 0;
    private static int[] dx = {1, 0, -1, 0};
    private static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N + 1][M + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while (true) {
            visited = new boolean[N + 1][M + 1];

            // 바깥 공기를 -1로 바꾼다.
            checkOutsideAir();

            // 치즈의 가장자리 구역을 찾고, meltingQueue에 저장
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= M; j++) {
                    if (board[i][j] == 1 && isEdge(i, j)) {
                        bfs(i, j);
                    }
                }
            }

            // 녹을 치즈가 더이상 없는 경우 종료
            if (meltingQueue.isEmpty()) break;

            // 모든 치즈가 녹기 한 시간 전 남은 치즈적 면 == 마지막으로 녹아야할 치즈 면
            cheese = meltingQueue.size();

            // meltingQueue에 있는 치즈 녹이기
            while (!meltingQueue.isEmpty()) {
                Point point = meltingQueue.poll();
                board[point.x][point.y] = -1;
            }

            // 날짜++
            day++;
        }

        System.out.println(day);
        System.out.println(cheese);
    }

    private static void bfs(int x, int y) {
        Queue<Point> q = new LinkedList<>();

        q.offer(new Point(x, y));
        visited[x][y] = true;

        while (!q.isEmpty()) {
            Point point = q.poll();

            if (isEdge(point.x, point.y)) {
                board[point.x][point.y] = 2;
                meltingQueue.offer(new Point(point.x, point.y));
            }

            for (int i = 0; i < 4; i++) {
                int newX = point.x + dx[i];
                int newY = point.y + dy[i];

                if (isIn(newX, newY) && board[newX][newY] == 1 && !visited[newX][newY]) {
                    q.offer(new Point(newX, newY));
                    visited[newX][newY] = true;
                }
            }
        }
    }

    private static boolean isEdge(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (board[newX][newY] == -1) {
                return true;
            }
        }
        return false;
    }

    private static void checkOutsideAir() {
        Queue<Point> q = new LinkedList<>();
        boolean[][] visitedAir = new boolean[N + 1][M + 1];

        q.offer(new Point(1, 1));
        board[1][1] = -1;
        visitedAir[1][1] = true;

        while (!q.isEmpty()) {
            Point point = q.poll();

            for (int i = 0; i < 4; i++) {
                int newX = point.x + dx[i];
                int newY = point.y + dy[i];

                if (isIn(newX, newY) && !visitedAir[newX][newY] && board[newX][newY] <= 0) {
                    board[newX][newY] = -1;
                    visitedAir[newX][newY] = true;
                    q.offer(new Point(newX, newY));
                }
            }
        }
    }

    private static boolean isIn(int x, int y) {
        return x > 0 && x <= N && y > 0 && y <= M;
    }

    static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
/**
 * @author nakhoonchoi
 * @date 2025/04/05
 * @see https://boj.ma/17135
 * @mem 31,648kb
 * @time 184ms
 * @caution
 * [고려사항]
 * 우선 2차원 배열을 문제와 다르게 0,0부터 N-1, N-1로 생각했고
 * 적이 게임에서 제외된다와 제거된다는 다른 의미이고 제거되는 적의 수만 count해야했다.
 * N번째 행에 성을 놓아야하기 때문에 map은 [N+1][M]으로 선언했다.
 *
 * 조합으로 궁수의 위치를 세 곳 골라서 게임을 진행해서 적을 kill한 횟수를 최대 연산 처리했다.
 * 2차원 배열을 copy하기 위해 deepcopy 메소드를 선언했고 System.arraycopy를 이용했다.
 * 궁수의 위치에서 BFS로 거리 D만큼 탐색해서 적이 있으면 상태를 DEAD_ENEMY로 치환하고
 * bfs 메소드를 끝낸다. 이 때 궁수의 이동 위치 순위는 왼쪽이 우선이 되어야한다.
 * 궁수는 될 수 있는 왼쪽의 적을 먼저 제거해야하기 때문이다.
 *
 * 종합적으로 조합과 BFS를 이용해서 문제를 해결할 수 있었다.
 * 턴마다 적을 한 칸씩 아래로 내리는 것은 배열을 순회해서 대입했고,
 * 게임이 끝났는 지는 2차원 배열을 0~N-1행까지 순회해서 전부 0이면 끝나는 것으로 확인했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <구현> '캐슬 디펜스'

public class BOJ17135_2 {
    static int N;
    static int M;
    static int D;
    static int answer;
    static int kill;
    //입력받는 게임 맵.
    static int [][] game;
    //카피해서 쓸 temp 맵.
    static int [][] copyMap;
    static int [] dx = {0,-1,0};
    static int [] dy = {-1,0,1};
    static final int ENEMY = 1;
    static final int BLANK = 0;
    static final int DEAD_ENEMY = 3;
    static final int SHOOTER = 2;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        game = new int[N+1][M];
        copyMap = new int[N+1][M];
        answer = Integer.MIN_VALUE;
        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++) {
                game[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        comb(0,0);

        System.out.println(answer);
    }

    //궁수 3명을 놓는 경우의 수 조합
    public static void comb(int cnt, int start) {
        if(cnt == 3) {
            copyMap = deepcopy(game);
            doGame();
            return;
        }

        for(int i=start;i<M;i++) {
            game[N][i] = SHOOTER;
            comb(cnt+1,i+1);
            game[N][i] = BLANK;
        }
    }

    //매 조합마다 게임을 진행한다.
    public static void doGame() {
        kill = 0;
        while(!isFinish()) {
            for(int i=0;i<M;i++) {
                if(copyMap[N][i] == SHOOTER) {
                    bfs(N, i, 0);
                }
            }
            //적이 있으면 내려온다.
            for(int i=N-1;i>=0;i--) {
                for(int j=0;j<M;j++) {
                    if(copyMap[i][j] == ENEMY) {
                        if(isIn(i+1,j)) {
                            copyMap[i+1][j] = ENEMY;
                        }
                    }
                    copyMap[i][j] = BLANK;
                }
            }
        }
        answer = Math.max(kill, answer);
    }

    //궁수가 범위 안에 있는 우선순위가 가장 높은 적을 찾는 bfs(왼쪽 우선)
    public static void bfs(int x, int y, int d) {
        Queue<Status> q = new LinkedList<>();
        q.offer(new Status(x, y, d));

        while(!q.isEmpty()) {
            Status temp = q.poll();
            if(copyMap[temp.x][temp.y] == DEAD_ENEMY) {
                return;
            }
            else if(copyMap[temp.x][temp.y] == ENEMY) {
                copyMap[temp.x][temp.y] = DEAD_ENEMY;
                kill++;
                return;
            }
            if(temp.d == D) {
                continue;
            }
            for(int i=0;i<3;i++) {
                int nx = temp.x + dx[i];
                int ny = temp.y + dy[i];
                if(isIn(nx,ny)) {
                    q.offer(new Status(nx,ny,temp.d+1));
                }
            }
        }
    }

    //전부 0인 지 아닌 지(게임이 끝났는 지 아닌 지)
    public static boolean isFinish() {
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                if(copyMap[i][j] != BLANK)
                    return false;
            }
        }
        return true;
    }

    //2차원 배열을 복사하는 deepcopy 코드
    public static int [][] deepcopy(int [][] original){
        if(original == null) {
            return null;
        }

        int [][] result = new int[original.length][original[0].length];

        for(int i=0;i<original.length;i++) {
            System.arraycopy(original[i], 0, result[i], 0, original[0].length);
        }
        return result;
    }

    //구역이 게임 구역에 포함되는 지 안 되는 지
    public static boolean isIn(int x, int y) {
        return x>=0 && x<N && y>=0 && y<M;
    }

    //현재 위치에 대한 클래스
    static class Status{
        int x;
        int y;
        int d;

        public Status(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }
}
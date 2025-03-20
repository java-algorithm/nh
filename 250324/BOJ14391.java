/**
 * @author nakhoonchoi
 * @date 2025/03/21
 * @see https://boj.ma/14391
 * @mem 11,936kb
 * @time 72ms
 * @caution
 * [고려사항]
 * 재귀(DFS)를 이용해서 완전 탐색을 해야하는 문제였다.
 * 평소에 재귀 설계에 많이 약한 편이라 오래 걸렸다.
 * 
 * 재귀 함수의 인자에는 다음(현재 좌표 X,Y,현재까지 합계)과 같이 담아주었다.
 * 현재 좌표 기준으로 오른쪽 방향과 아래방향으로 최대한 만들 수 있는 길이의 종이 조각 만큼 만들어서 방문 처리를 하고
 * 합계를 계산한 뒤에 다음 DFS(permutation)를 진행한다.
 * 좌표는 2차원 배열 순회하듯이 열 기준으로 먼저 탐색하고 행 기준으로 탐색했다.
 *
 * 다음 좌표를 편하게 알기 위해서 모듈러 연산을 이용했다.
 * int nextTarget = startX * M + startY + 1;
 * int nextX = nextTarget / M;
 * int nextY = nextTarget % M;
 * 
 * 다음 DFS(permutation)에 들어갈 좌표가 이미 방문 처리 되어있다면 합계 처리를 하지 않고 바로 다음 좌표로 진행했고,
 * 마지막 좌표에 도달했을 때의 합계를 max 연산 처리해서 값을 도출해냈다.
 *
 * (재귀 설계가 어려워서 map 크기가 1 1인 경우를 dfs 밖에서 예외처리 했는데 깔끔하게 재귀처리하는 방법이 궁금하다.)
 * (그리고 해당 종이 조각 방문 처리를 원복 시키는 과정에 list를 이용해서 일괄로 원복시켰는데, 91번째 줄,,, 약간 아쉬운 부분이다.)
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DFS> '종이 조각'

public class BOJ14391 {
    static int [][] map;
    static boolean [][] visited;
    static int answer, N, M;
    static int [] dx = {0, 1};
    static int [] dy = {1, 0};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];

        for(int i=0;i<N;i++) {
            String str = br.readLine();
            for(int j=0;j<M;j++) {
                map[i][j] = str.charAt(j) - '0';
            }
        }

        if(N == 1 && M == 1){
            System.out.println(map[0][0]);
            return;
        }

        dfs(0, 0, 0);

        System.out.println(answer);
    }

    public static void dfs(int startX, int startY, int sum){
        if(startX == N-1 && startY == M-1){
            answer = Math.max(sum, answer);
            return;
        }

        int nextTarget = startX * M + startY + 1;
        int nextX = nextTarget / M;
        int nextY = nextTarget % M;

        if(visited[startX][startY]){
            dfs(nextX, nextY, sum);
            return;
        }

        visited[startX][startY] = true;
        sum += map[startX][startY];

        int cur = map[startX][startY];
        
        for(int i=0;i<2;i++){//아래방향과 윗 방향
            int nx = startX;
            int ny = startY;
            int nCur = cur;
            //방문 처리를 dfs 탐색 하고 난 뒤에 일괄적으로 FALSE 시키기 위한 List
            List<int []> updateList = new ArrayList<>();

            while(true){
                nx += dx[i];
                ny += dy[i];

                if(!isIn(nx, ny) || visited[nx][ny]){
                    break;
                }

                updateList.add(new int[]{nx, ny});

                if(!visited[nx][ny]){
                    visited[nx][ny] = true;
                    nCur = nCur * 10 + map[nx][ny];
                    dfs(nextX, nextY, sum + nCur - cur);
                }
            }

            for(int [] point : updateList){
                visited[point[0]][point[1]] = false;
            }
        }

        visited[startX][startY] = false;
    }

    static boolean isIn(int x, int y){
        return x>=0 && x<N && y>=0 && y<M;
    }
}
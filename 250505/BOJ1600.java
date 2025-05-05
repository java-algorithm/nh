/**
 * @author choi
 * @date Jan 4, 2021
 * @see https://www.acmicpc.net/problem/1600
 * @mem 86,292kb
 * @time 464ms
 * @caution
 * [고려사항]
 * 백준 '움직이는 미로 탈출'과 문제가 비슷했다. 다만 장애물이 움직이지 않기 때문에 visited 처리를 해주어야 했고,
 * 맨 오른쪽 아래 지점에 도달해야 최소 길이를 count하고 return할 수 있었다.
 * 일반 원숭이의 이동(4방향)과 말의 이동(특이 case)을 따로 처리해주었고, 우선순위를 말의 이동이 더 앞으로 오도록 했다.
 * 여기서 visited를 2차원 배열로 주었을 때 틀릴 수 있다는 것을 알게 되었다. 말의 이동으로 한번 도달할 수 있는 지점을 visited처리 해버리면
 * 일반 원숭이는 그 지점을 도달할 수 없기 때문에 최적의 해를 찾을 수 없을 것 같다는 생각을 했고,
 * visited를 3차원 배열로 주어 [말의 이동 사용 횟수][H][W] 순으로 인덱스를 정했을 때 답을 도출해낼 수 있었다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
import java.io.*;
//백준 <BFS> '말이 되고픈 원숭이'
public class BOJ1600 {
    static int [][] map;
    static boolean [][][] visited;
    static int K,H,W;
    static int [] dx = {-1,0,1,0};
    static int [] dy = {0,1,0,-1};
    static int [] hx = {-2,2,-2,2,1,1,-1,-1};
    static int [] hy = {1,1,-1,-1,-2,2,-2,2};
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        K = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        map = new int[H][W];
        visited = new boolean[K+1][H][W];

        for(int i=0;i<H;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<W;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(bfs());
    }

    public static int bfs() {
        Queue<Monkey> q = new LinkedList<>();
        q.offer(new Monkey(0, 0, 0, 0));
        visited[0][0][0] = true;

        while(!q.isEmpty()) {
            Monkey temp = q.poll();

            if(temp.x == H-1 && temp.y == W-1){
                return temp.length;
            }

            if(temp.horseCount < K) {
                for(int i=0;i<8;i++) {
                    int nx = temp.x + hx[i];
                    int ny = temp.y + hy[i];

                    if(isIn(nx,ny) && !visited[temp.horseCount+1][nx][ny] && map[nx][ny] != 1) {
                        visited[temp.horseCount+1][nx][ny] = true;
                        q.offer(new Monkey(nx,ny,temp.horseCount+1,temp.length+1));
                    }
                }
            }

            for(int i=0;i<4;i++) {
                int nx = temp.x + dx[i];
                int ny = temp.y + dy[i];

                if(isIn(nx,ny) && !visited[temp.horseCount][nx][ny] && map[nx][ny] != 1) {
                    visited[temp.horseCount][nx][ny] = true;
                    q.offer(new Monkey(nx,ny,temp.horseCount,temp.length+1));
                }
            }
        }
        return -1;
    }

    public static boolean isIn(int x, int y) {
        return x>=0 && x<H && y>=0 && y<W;
    }

    static class Monkey{
        int x;
        int y;
        int horseCount;
        int length;
        Monkey(int x, int y, int horseCount, int length){
            this.x = x;
            this.y = y;
            this.horseCount = horseCount;
            this.length = length;
        }
    }
}
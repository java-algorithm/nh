/**
 * @author choi
 * @date Jan 9, 2021
 * @see https://www.acmicpc.net/problem/3055
 * @mem 11,832kb
 * @time 88ms
 * @caution
 * [고려사항]
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
import java.io.*;
//백준 <BFS> '탈출'
public class BOJ3055 {
    static int R,C,answer;
    static char [][] map;
    static int [] dx = {-1,0,1,0};
    static int [] dy = {0,-1,0,1};
    static Queue<Point> waterQ = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];

        int startX = -1;
        int startY = -1;
        answer = -1;
        for(int i=0;i<R;i++) {
            String str = br.readLine();
            for(int j=0;j<C;j++) {
                map[i][j] = str.charAt(j);
                if(map[i][j] == 'S') {
                    startX = i;
                    startY = j;
                    map[i][j] = '.';
                }else if(map[i][j] == '*') {
                    waterQ.offer(new Point(i,j));
                }
            }
        }

        bfs(startX, startY);

        System.out.println(answer == -1 ? "KAKTUS" : answer);
    }

    public static void bfs(int x, int y) {
        Queue<Point> q = new LinkedList<>();
        boolean [][] visited = new boolean[R][C];
        q.offer(new Point(x,y,0));
        visited[x][y] = true;

        int level = 0;
        while(!q.isEmpty()) {
            Point temp = q.poll();

            if(level != temp.depth) {
                level++;
                spreadWater();
            }

            if(map[temp.x][temp.y] == 'D') {
                answer = temp.depth;
                return;
            }

            if(map[temp.x][temp.y] == '*') continue;

            for(int i=0;i<4;i++) {
                int nx = temp.x + dx[i];
                int ny = temp.y + dy[i];

                if(isIn(nx,ny) && !visited[nx][ny] && map[nx][ny] != 'X' && map[nx][ny] != '*') {
                    visited[nx][ny] = true;
                    q.offer(new Point(nx,ny,temp.depth+1));
                }
            }
        }
    }

    public static void spreadWater() {
        int size = waterQ.size();

        for(int i=0;i<size;i++) {
            Point temp = waterQ.poll();

            for(int j=0;j<4;j++) {
                int nx = temp.x + dx[j];
                int ny = temp.y + dy[j];

                if(isIn(nx,ny) && map[nx][ny] == '.') {
                    map[nx][ny] = '*';
                    waterQ.offer(new Point(nx,ny));
                }
            }
        }
    }

    public static boolean isIn(int x, int y) {
        return x>=0 && x<R && y>=0 && y<C;
    }

    static class Point{
        int x;
        int y;
        int depth;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        Point(int x, int y, int depth){
            this.x = x;
            this.y = y;
            this.depth = depth;
        }
    }
}
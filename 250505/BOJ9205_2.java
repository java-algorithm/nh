/**
 * @author nakhoonchoi
 * @date 2025/05/03
 * @see https://boj.ma/9205
 * @mem 12,476kb
 * @time 84ms
 * @caution
 * [고려사항]
 * BFS로 문제를 해결했지만 다른 사람들의 풀이를 보니 플로이드 와샬, union-find로도 문제를 해결할 수 있는듯하다.
 *
 * BFS를 구현할 때 큐에 집의 좌표를 넣고, 큐에 있는 좌표들을 순회하면서 맨해튼 거리가 1000 이하인 편의점 좌표들을
 * 최대한 큐에 넣고 페스티벌의 위치에 도달할 수 있는 편의점 좌표들인지 판별해주었다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <BFS> '맥주 마시면서 걸어가기'

public class BOJ9205_2 {
    static int [][] convenience;
    static boolean [] visited;
    static int n;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;

        while(t-->0){
            n = Integer.parseInt(br.readLine());

            int [] home = new int[2];
            int [] festival = new int[2];
            convenience = new int[n][2];
            visited = new boolean[n];

            st = new StringTokenizer(br.readLine());
            home[0] = Integer.parseInt(st.nextToken());
            home[1] = Integer.parseInt(st.nextToken());

            for(int i=0;i<n;i++){
                st = new StringTokenizer(br.readLine());
                convenience[i][0] = Integer.parseInt(st.nextToken());
                convenience[i][1] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            festival[0] = Integer.parseInt(st.nextToken());
            festival[1] = Integer.parseInt(st.nextToken());

            System.out.println(bfs(home, festival) ? "happy" : "sad");
        }
    }

    public static boolean bfs(int [] home, int [] festival){
        Queue<int []> q = new ArrayDeque<>();
        q.offer(home);

        while(!q.isEmpty()){
            int [] cur = q.poll();

            if(getDistance(cur[0], cur[1], festival[0], festival[1]) <= 1000){
                return true;
            }

            for(int i=0;i<n;i++){
                if(!visited[i] && getDistance(cur[0], cur[1], convenience[i][0], convenience[i][1]) <= 1000){
                    visited[i] = true;
                    q.offer(convenience[i]);
                }
            }
        }
        return false;
    }

    public static int getDistance(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
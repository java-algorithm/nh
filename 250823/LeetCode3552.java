/**
 * @author nakhoonchoi
 * @date 2025/08/20
 * @see https://leetcode.com/problems/grid-teleportation-traversal/
 * @mem 76.68MB
 * @time 183ms
 * @caution
 * [고려사항]
 * 릿코드 문제가 영어이다보니(?) 단순한 BFS일줄 알았지만 헷갈린 점이 몇 있었다.
 * 각 알파벳이 map에 무조건 두 개씩 있을 줄 알았으나 2개 이상으로 여러 개가 있을 수 있다.
 * 
 * Map을 통해 각 알파벳 별로 좌표와 현재까지의 거리를 담은 Point 객체를 저장해주었고,
 * visited처리를 boolean 배열을 이용해서 BFS를 구현했다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Grid Teleportation Traversal'

public class LeetCode3552 {
    int [] dx = {1, 0, -1, 0};
    int [] dy = {0, 1, 0, -1};
    char [][] map;
    boolean [][] visited;
    int N;
    int M;
    int answer;
    Map<Character, List<Point>> characterPointMap;

    public int minMoves(String [] matrix) {
        characterPointMap = new HashMap<>();

        N = matrix.length;
        M = matrix[0].length();
        map = new char[N][M];
        visited = new boolean[N][M];

        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                map[i][j] = matrix[i].charAt(j);

                if(isUpperAlphabet(map[i][j])){
                    characterPointMap.putIfAbsent(map[i][j], new ArrayList<>());
                    characterPointMap.get(map[i][j]).add(new Point(i, j));
                }
            }
        }

        answer = Integer.MAX_VALUE;

        bfs();

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    public void bfs(){
        Queue<Point> q = new ArrayDeque<>();
        q.offer(new Point(0, 0, 0));
        visited[0][0] = true;

        if(isUpperAlphabet(map[0][0])){
            for(Point nextPoint : characterPointMap.get(map[0][0])){
                if(!visited[nextPoint.x][nextPoint.y]) {
                    nextPoint.distance = 0;
                    q.offer(nextPoint);
                    visited[nextPoint.x][nextPoint.y] = true;
                }
            }
        }

        while(!q.isEmpty()){
            Point cur = q.poll();

            if(cur.x == N-1 && cur.y == M-1){
                answer = cur.distance;
                return;
            }

            for(int i=0;i<4;i++){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if(isIn(nx, ny) && !visited[nx][ny] && map[nx][ny] != '#'){
                    if(isUpperAlphabet(map[nx][ny])){
                        for(Point nextPoint : characterPointMap.get(map[nx][ny])){
                            if(!visited[nextPoint.x][nextPoint.y]) {
                                nextPoint.distance = cur.distance + 1;
                                q.offer(nextPoint);
                                visited[nextPoint.x][nextPoint.y] = true;
                            }
                        }
                    }
                    q.offer(new Point(nx, ny, cur.distance + 1));
                    visited[nx][ny] = true;
                }
            }
        }
    }

    public boolean isUpperAlphabet(char c){
        return c>='A' && c<='Z';
    }

    public boolean isIn(int x, int y){
        return x>=0 && x<N && y>=0 && y<M;
    }

    class Point implements Comparable<Point>{
        int x;
        int y;
        int distance;

        Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        Point(int x, int y, int distance){
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public int compareTo(Point o) {
            return Integer.compare(this.distance, o.distance);
        }
    }
}
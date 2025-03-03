/**
 * @author nakhoonchoi
 * @date 2025/02/28
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/150365
 * @caution
 * [고려사항]
 * BFS에 그리디로 효율적인 해답을 찾아야하는 문제였다.
 * 좌표 압축? 도 포함되어야했다.(맨해튼 거리는 힌트를 얻었다.)
 * 우선 4방향 탐색은 d, l, r, u 순서로 하좌우상 순서로 탐색하는 것이 유리하다.
 * 그리고 4방향을 탐색한다음 nx, ny값이 (0,0) ~ (N-1, M-1) 사이에 속해있으면서
 * 맨해튼 거리가 (K - 지금까지의 거리) 사이인 최적의 값만 큐에 추가한다.
 *
 * 큐에는 무조건 정렬되어진 최적의 값만 있기 때문에 end point 에 도착했을 때의 경로 값이 정답이고
 * 갱신이 안되었다면 "impossible"을 출력하면 되었다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2023 Kakao Blind Recruitment> '미로 탈출 명령어'

public class Programmers150365 {
    int N, M, startX, startY, endX, endY, K;
    int [] dx = {1, 0, 0, -1};
    int [] dy = {0, -1, 1, 0};
    char [] dir = {'d','l','r','u'};
    String answer;

    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        answer = "impossible";
        N = n;
        M = m;
        startX = x-1;
        startY = y-1;
        endX = r-1;
        endY = c-1;
        K = k;

        if(getDistance(startX, startY, endX, endY) > K){
            return answer;
        }

        bfs();

        return answer;
    }

    public int getDistance(int x, int y, int r, int c) {
        return Math.abs(x - r) + Math.abs(y - c);
    }

    public void bfs(){
        Queue<Status> q = new LinkedList<>();
        q.offer(new Status(startX, startY));

        while(!q.isEmpty()){
            Status cur = q.poll();

            if(cur.sb.length() == K && cur.x == endX && cur.y == endY){
                answer = cur.sb.toString();
                break;
            }

            for(int i=0;i<4;i++){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                StringBuilder sb = new StringBuilder(cur.sb);

                if(isIn(nx, ny) && getDistance(nx, ny, endX, endY) <= (K - sb.length())){
                    q.offer(new Status(nx, ny, sb.append(dir[i])));
                    break;
                }
            }
        }
    }

    public boolean isIn(int x, int y){
        return x>=0 && x<N && y>=0 && y<M;
    }

    class Status{
        int x;
        int y;
        StringBuilder sb;

        Status(int x, int y){
            this.x = x;
            this.y = y;
            this.sb = new StringBuilder();
        }

        Status(int x, int y, StringBuilder sb){
            this.x = x;
            this.y = y;
            this.sb = sb;
        }
    }
}
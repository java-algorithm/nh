/**
 * @author nakhoonchoi
 * @date 2025/09/25
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/72415
 * @caution
 * [고려사항]
 * 💡 IDE를 이용하지 않고 문제를 풀었다.(프로그래머스 웹 버전에서)
 *
 * 뭔가 삼성의 구현과 BFS, 순열이 들어간 완전탐색 느낌의 문제였다.
 *
 * 먼저 map을 순회하면서 찾아야하는 짝이 몇 개인지 확인했다.
 * 그 이후에는 순열을 진행하면서 어떤 순서대로 짝을 찾아 처리할 것인지 순서를 지정했다.
 * (짝은 최대 6개이기 때문에 순열을 진행해도 괜찮을 것이라고 생각했다.)
 *
 * 그리고 최단 거리는 BFS를 이용해서 찾았는데,
 * BFS는 현재 좌표를 기반으로 특정 타겟 숫자의 카드를 찾기 위한 최소 이동 count를 반환한다.
 *
 * (현재 좌표 - (BFS) - 첫 번째 타겟 숫자 카드 탐색 완료(좌표 이동 반영) - (BFS) - 두 번째 타겟 숫자 카드 탐색 완료(좌표 이동 반영)
 * 위 flow를 모든 짝을 찾을 때까지 반복하면 현재 순열에 대한 최단 이동 거리를 찾을 수 있다.
 * 이를 순열 경우의 수 마다 answer에 min 연산 해주었다.
 *
 * BFS에서 최단 거리를 탐색할 때 4방향으로 1칸씩 이동하는 것 뿐만 아니라
 * 해당 방향 중 가까운 0이 아닌 카드나 해당 방향의 끝으로 점프하는 로직을 잘 작성해야했다.
 *
 * 과거에 풀었는데, 실제 시험에서도 0.95%라는 괴랄한(?) 통과율을 자랑한 구현 문제이다...
 * 엄청 어려운 개념은 없지만 3시간 반에 7문제를 풀어야 했던 그 때에는 그럴만 하다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2021 KAKAO BLIND RECRUITMENT> '카드 짝 맞추기'

public class Programmers72415_2 {
    int startX;
    int startY;
    int [][] map;
    int [] dx = {-1, 0, 1, 0};
    int [] dy = {0, -1, 0, 1};
    int pairCount;
    int [][] copyMap = new int[4][4];
    boolean [] visited;
    int [] temp;
    int answer;
    Point curPoint;

    public int solution(int[][] board, int r, int c) {
        map = board;
        startX = r;
        startY = c;
        answer = Integer.MAX_VALUE;

        pairCount = 0;
        for(int [] row : board){
            for(int data : row){
                pairCount = Math.max(pairCount, data);
            }
        }

        temp = new int[pairCount];
        visited = new boolean[pairCount];

        perm(0);

        return answer;
    }

    public void perm(int count){
        if(count == pairCount){
            //temp 순서대로 짝 찾기
            copy();

            curPoint = new Point(startX, startY);

            int totalMoveCount = 0;
            for(int target : temp){
                int moveCount = 0;
                Point result = bfs(target);
                moveCount += result.moveCount;
                curPoint = new Point(result.x, result.y);
                copyMap[result.x][result.y] = 0;

                result = bfs(target);
                moveCount += result.moveCount;
                curPoint = new Point(result.x, result.y);
                copyMap[result.x][result.y] = 0;

                totalMoveCount += moveCount;
            }

            answer = Math.min(answer, totalMoveCount);

            return;
        }

        for(int i=1;i<=pairCount;i++){
            if(!visited[i-1]){
                visited[i-1] = true;
                temp[count] = i;
                perm(count + 1);
                visited[i-1] = false;
            }
        }
    }

    public Point bfs(int target){
        Queue<Point> q = new ArrayDeque<>();
        boolean [][] visited = new boolean[4][4];

        q.offer(curPoint);
        visited[curPoint.x][curPoint.y] = true;

        while(!q.isEmpty()){
            Point cur = q.poll();
            if(copyMap[cur.x][cur.y] == target){
                cur.moveCount++;
                return cur;
            }

            for(int i=0;i<4;i++){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if(isIn(nx, ny) && !visited[nx][ny]){
                    visited[nx][ny] = true;
                    q.offer(new Point(nx, ny, cur.moveCount + 1));
                }
            }

            for(int i=0;i<4;i++){
                Point result = ctrlJump(cur.x, cur.y, i);

                if(!visited[result.x][result.y] && !(result.x == cur.x && result.y == cur.y)){
                    visited[result.x][result.y] = true;
                    q.offer(new Point(result.x, result.y, cur.moveCount + 1));
                }
            }
        }

        return null; //unreach point;
    }

    public Point ctrlJump(int x, int y, int d){
        int nx = x + dx[d];
        int ny = y + dy[d];

        while(isIn(nx, ny)){
            if(copyMap[nx][ny] != 0){
                return new Point(nx, ny);
            }
            nx += dx[d];
            ny += dy[d];
        }
        return new Point(nx - dx[d], ny - dy[d]);
    }

    public void copy(){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                copyMap[i][j] = map[i][j];
            }
        }
    }

    public boolean isIn(int x, int y){
        return x>=0 && x<4 && y>=0 && y<4;
    }

    class Point{
        int x;
        int y;
        int moveCount;

        Point(int x, int y, int moveCount){
            this.x = x;
            this.y = y;
            this.moveCount = moveCount;
        }

        Point(int x, int y){
            this.x = x;
            this.y = y;
            this.moveCount = 0;
        }
    }
}
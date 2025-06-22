/**
 * @author nakhoonchoi
 * @date 2025/06/22
 * @see https://leetcode.com/problems/surrounded-regions
 * @mem 46.36MB
 * @time 7ms
 * @caution
 * [고려사항]
 * 단순한 BFS 문제였다.
 * 특정 조건에 따라 일괄 수정해야하는 좌표를 저장할 때 Set에 좌표를 저장하는 편인데
 * Set을 사용하지 않고 visited 배열에 일괄 수정해야할 좌표임을 표시해줬다면
 * 아래와 같이 좌표 범위 안에 있는 O라면 일괄 수정해야할 좌표이고,
 * 좌표 범위 밖으로 나가는 Region이라면 대상이 아님을 Set을 이용하지 않고 구할 수 있다. + 재귀를 이용한 DFS(2ms 해법)
 *
 * public boolean check(int row,int col,char[][] board, boolean[][] vis){
 *     if(row<board.length && col<board[0].length && row>=0 && col>=0 && board[row][col]=='O' && vis[row][col]==false)
 *         return true;
 *     return false;
 * }
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Surrounded Regions'

public class LeetCode130 {
    int [] dx = {1,0,-1,0};
    int [] dy = {0,1,0,-1};

    public void solve(char[][] board) {
        boolean [][] visited = new boolean[board.length][board[0].length];
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(!visited[i][j] && board[i][j] == 'O') {
                    bfs(i, j, board, visited);
                }
            }
        }
    }

    public boolean isIn(int x, int y, char [][] board){
        return x>=0 && x<board.length && y>=0 && y<board[0].length;
    }

    public void bfs(int x, int y, char [][] board, boolean [][] visited){
        boolean isSurround = true;
        Queue<int []> q = new ArrayDeque<>();
        Set<int []> pointSet = new HashSet<>();
        int [] firstPoint = {x, y};
        q.offer(firstPoint);
        pointSet.add(firstPoint);
        visited[x][y] = true;

        while(!q.isEmpty()){
            int [] cur = q.poll();

            for(int i=0;i<4;i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if(isIn(nx, ny, board) && !visited[nx][ny] && board[nx][ny] == 'O'){
                    int [] nextPoint = {nx, ny};
                    q.offer(nextPoint);
                    pointSet.add(nextPoint);
                    visited[nx][ny] = true;
                }else if(!isIn(nx, ny, board)){
                    isSurround = false;
                }
            }
        }

        if(isSurround){
            for(int [] point : pointSet){
                board[point[0]][point[1]] = 'X';
            }
        }
    }
}
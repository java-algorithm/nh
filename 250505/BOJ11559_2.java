/**
 * @author nakhoonchoi
 * @date 2025/05/02
 * @see https://boj.ma/11559
 * @mem 11,712kb
 * @time 72ms
 * @caution
 * [고려사항]
 * 5년 만에 푼 문제다. 처음 봤을 때는 진짜 어려웠는데 이제는 금방 풀었다.
 *
 * 뿌요뿌요의 같은 색깔의 크기가 4 이상이 되면 터뜨리는 로직과
 * 한 턴에서 최대한 터뜨릴 수 있는 만큼 터뜨리고 각 칸을 최대한 내리는 로직이 필요했다.
 *
 * 터뜨리는 로직은 BFS로 같은 색깔의 칸을 List에 저장하며 size를 탐색했고,
 * BFS 탐색을 완료한 뒤에 size가 4 이상이라면 List에 저장된 좌표의 값을 전부 '.'으로 바꿔주었다.
 *
 * 그리고 최대한 터뜨린 후 중력에 의해(?) 각 칸을 내리는 로직에서도 큐를 이용했다.
 * 큐의 peek 값이 문자라면 map의 좌표에 값을 덮어씌우면서 현재까지의 행 index를 저장했고,
 * 큐의 peek 값이 '.'이라면 index 관리 없이 큐에서 값을 빼주었다.
 *
 * 아래의 로직대로 최대한 수행했을 때 turn을 출력하면 됐다.
 * ⚠️ 최대한 터뜨리고 부분에서 각 칸을 내리기 전에 현재 map 기준에서 터뜨릴 수 있는 칸을 최대한 터뜨려야한다.
 *
 * while(true){
 *     visited 초기화;
 *     터진 칸 있는지 없는지 flag 초기화;
 *
 *     최대한 터뜨리고();
 *
 *     if(터진 칸이 없다면) break;
 *
 *     최대한 내리고();
 *
 *     turn++;
 * }
 *
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <BFS> 'Puyo Puyo'

public class BOJ11559_2 {
    static int N = 12;
    static int M = 6;
    static char [][] map = new char[N][M];
    static boolean [][] visited = new boolean[N][M];
    static int [] dx = {-1, 0, 1, 0};
    static int [] dy = {0, -1, 0, 1};
    static boolean isBoom;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for(int i=0;i<N;i++){
            map[i] = br.readLine().toCharArray();
        }
        int turn = 0;

        while(true){
            isBoom = false;
            initVisited();

            //최대한 터뜨리고
            for(int i=N-1;i>=0;i--){
                for (int j=0;j<M;j++){
                    if(!visited[i][j] && map[i][j] != '.'){
                        bfs(i, j, map[i][j]);
                    }
                }
            }

            if(!isBoom){
                break;
            }

            //내리기
            for(int i=0;i<6;i++){
                if(isNothingColorColumn(i)){
                    continue;
                }

                Queue<Character> q = new LinkedList<>();

                for(int j=N-1;j>=0;j--){
                    q.offer(map[j][i]);
                }

                int j = N-1;
                while(!q.isEmpty()){
                    if(q.peek() == '.') {
                        q.poll();
                    }else{
                        map[j--][i] = q.poll();
                    }
                }

                for(;j>=0;j--){
                    map[j][i] = '.';
                }
            }
            turn++;
        }

        System.out.println(turn);
    }

    public static void bfs(int x, int y, char color){
        Queue<int []> q = new LinkedList<>();
        int size = 1;
        q.offer(new int[]{x, y});
        visited[x][y] = true;
        List<int []> routeList = new ArrayList<>();
        routeList.add(new int[]{x, y});

        while(!q.isEmpty()){
            int [] cur = q.poll();

            for(int i=0;i<4;i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if(isIn(nx, ny) && !visited[nx][ny] && map[nx][ny] == color){
                    size++;
                    visited[nx][ny] = true;
                    int [] next = {nx, ny};
                    q.offer(next);
                    routeList.add(next);
                }
            }
        }

        if(size >= 4){
            isBoom = true;

            for(int [] point : routeList){
                map[point[0]][point[1]] = '.';
            }
        }
    }

    public static boolean isNothingColorColumn(int col){
        for(int i=N-1;i>=0;i--){
            if(map[i][col] != '.'){
                return false;
            }
        }
        return true;
    }

    public static void initVisited(){
        visited = new boolean[N][M];
    }

    public static boolean isIn(int x, int y){
        return x>=0 && x<N && y>=0 && y<M;
    }
}
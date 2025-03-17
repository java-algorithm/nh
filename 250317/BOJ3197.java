/**
 * @author nakhoonchoi
 * @date 2025/03/17
 * @see https://boj.ma/3197
 * @mem 213,124kb
 * @time 724ms
 * @caution
 * [고려사항]
 * BFS 문제였다.
 * 맨 처음에는 물과 맞닿은 얼음을 찾는 BFS, 맞닿은 얼음을 지우는 과정,
 * 백조가 다른 백조를 만날 수 있는 지 판별하는 BFS로 구현했는데 시간 초과가 발생했다.
 * 전체적으로 로직을 수정해서 해결했다.
 * BFS에서 사용할 백조가 이동 가능한 좌표들을 저장할 큐, 제거할 얼음을 저장할 큐를 외부에 지정했다.
 *
 * 입력을 받으면서 물에 대한 위치를 모두 입력 받아서 인접한 얼음을 지우고,
 * 그 지운 얼음의 위치를 새로 큐에 저장해서 다음 날 얼음 지우는 로직에서는 현재 위치부터 탐방하도록 했다.
 *
 * 하루가 지날 때마다 큐를 갱신할 필요없이 백조는 방문 처리를 해주어서 새로 갈 수 있는 위치만 새로 큐에 담아준다.
 * 똑같이 제거할 얼음을 저장할 큐도 얼음이 제거되어 물이 되었다면 그 값만 다음 BFS에서 사용하기 위해 큐에 넣어준다.
 *
 * 하루동안 로직
 * - 백조가 최대한 이동을 한다. 이 때 다른 백조를 만났다면 break
 * - 물과 인접한 얼음을 제거한다.(다음 큐에는 제거한 얼음의 위치에서만 탐방한다.)
 * - 날짜++
 *
 * 이 문제의 주의할 점 : 백조도 '물' 위에 있는 것이다...
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <BFS> '백조의 호수'

public class BOJ3197 {
    static int R, C;
    static int [][] swanPoint; //백조의 좌표
    static char [][] map; //입력된 문자 2차원 배열
    static boolean [][] visited; //방문 체크 배열
    static Queue<int []> removeIcePointQueue; //제거할 얼음의 위치를 저장할 큐
    static Queue<int []> swanPointQueue; //백조의 현 위치를 저장할 큐
    static int [] dr = {-1, 0, 1, 0};
    static int [] dc = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        swanPoint = new int[2][2];

        removeIcePointQueue = new LinkedList<>();
        swanPointQueue = new LinkedList<>();

        map = new char[R][C];
        visited = new boolean[R][C];

        int curSwan = 0;
        for(int i=0;i<R;i++){
            String mapR = br.readLine();

            //백조의 위치를 지정해서 2*2 백조 배열에 저장한다.
            if(mapR.contains("L")){
                if(mapR.indexOf("L") != mapR.lastIndexOf("L")){
                    swanPoint[curSwan][0] = i;
                    swanPoint[curSwan++][1] = mapR.indexOf("L");
                    swanPoint[curSwan][0] = i;
                    swanPoint[curSwan][1] = mapR.lastIndexOf("L");
                }else{
                    swanPoint[curSwan][0] = i;
                    swanPoint[curSwan][1] = mapR.indexOf("L");
                    curSwan++;
                }
            }

            map[i] = mapR.toCharArray();

            //현재 물의 위치 기준으로 4방향 얼음을 탐색할 것이기 때문에 초기에 얼음 제거 큐에 담아준다.
            for(int j=0;j<C;j++){
                if(map[i][j] == '.' || map[i][j] == 'L'){
                    removeIcePointQueue.offer(new int[]{i, j});
                }
            }
        }

        swanPointQueue.offer(new int[]{swanPoint[0][0], swanPoint[0][1]});
        visited[swanPoint[0][0]][swanPoint[0][1]] = true;

        /**
         * 하루동안 로직
         * - 백조가 최대한 이동을 한다. 이 때 다른 백조를 만났다면 break
         * - 물과 인접한 얼음을 제거한다.(다음 큐에는 제거한 얼음의 위치에서만 탐방한다.)
         * - 날짜++
         */
        int day = 0;
        while(true){
            if(swanMove()){
                break;
            }
            removeIce();
            day++;
        }

        System.out.println(day);
    }

    /**
     * 백조를 최대한 이동시킨 좌표를 저장하도록 하면서, 다른 백조를 만나는 지 판별하는 BFS
     * X를 만나면 다음에 얼음이 제거된 뒤에 탐방 가능하므로 다음 큐에 넣는다.
     * L이나 .(물)을 만나면 백조가 갈 수 있는 좌표이므로 현재 큐에 넣는다.
     * @return 다른 백조를 만났는 지 여부
     */
    private static boolean swanMove(){
        Queue<int []> nextSwanPointQueue = new LinkedList<>();

        while(!swanPointQueue.isEmpty()){
            int [] curPoint = swanPointQueue.poll();

            if(curPoint[0] == swanPoint[1][0] && curPoint[1] == swanPoint[1][1]){
                return true;
            }

            for(int i=0;i<4;i++){
                int nr = curPoint[0] + dr[i];
                int nc = curPoint[1] + dc[i];

                if(isIn(nr, nc) && !visited[nr][nc]){
                    visited[nr][nc] = true;
                    if(map[nr][nc] == 'X'){
                        nextSwanPointQueue.offer(new int[]{nr, nc});
                    }else{
                        swanPointQueue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
        swanPointQueue = nextSwanPointQueue;

        return false;
    }

    /**
     * 얼음을 제거하는 메소드
     * 각 좌표 기준으로 4방향을 탐색하고 얼음이 있다면 제거하고
     * 얼음의 좌표를 다음 날을 위해 큐에 넣는다.
     */
    private static void removeIce(){
        int size = removeIcePointQueue.size();
        for(int i=0;i<size;i++){
            int [] removeIcePoint = removeIcePointQueue.poll();

            for(int j=0;j<4;j++){
                int nr = removeIcePoint[0] + dr[j];
                int nc = removeIcePoint[1] + dc[j];

                if(isIn(nr, nc) && map[nr][nc] == 'X'){
                    map[nr][nc] = '.';
                    removeIcePointQueue.offer(new int[]{nr, nc});
                }
            }
        }
    }

    //좌표 범위 확인 함수
    private static boolean isIn(int r, int c){
        return r>=0 && r<R && c>=0 && c<C;
    }
}
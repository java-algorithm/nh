/**
 * @author nakhoonchoi
 * @date 2025/05/01
 * @see https://boj.ma/2146
 * @mem 24,308kb
 * @time 232ms
 * @caution
 * [고려사항]
 * 우선 이 문제는 BFS를 이용해서 문제를 해결하였다. 한 번에 AC를 받아서 뿌듯했다.(TMI)
 * 정확히는 기억나지 않지만 이전에 비슷한 문제를 풀었던 적이 있다.
 *
 * 먼저 각 섬 별로 번호를 채번해서 외곽에만 먼저 flag 표시를 했다. (2, 3, 4...) (BFS 1번)
 * 왜 외곽에만 표시를 했냐면, 섬의 전체를 표시할 필요 없이 다리는 섬의 외곽에서 외곽에 닿았을 때 길이가 끝난다.
 * 그리고 모든 섬의 좌표를 기준으로 큐에 넣고 BFS를 돌리면 메모리 초과 or 시간 초과가 발생할 것이라고 생각했다.
 * 배열을 순회하면서 외곽을 찾지 않아도 되도록 Map에 key를 flag(district), 값을 외곽 좌표들의 모음으로 진행했다.
 *
 * 다음으로는 각 섬 index 별로 다른 섬의 외곽에 도달하는 최소의 거리 BFS로 구해서 min 연산을 진행했다.
 * 최소의 거리를 구할 때 같은 좌표를 중복해서 탐색하지 않도록 route 2차원 배열을 선언해서
 * 각 좌표 별로 지난 최소 거리를 저장한 뒤에 다른 섬의 외곽을 만났다면 바로 answer를 min 갱신하고 BFS 메소드를 빠져나왔다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <BFS> '다리 만들기'

public class BOJ2146 {
    static int [][] map;
    static boolean [][] visited;
    static int N;
    static int [] dx = {-1, 0, 1, 0};
    static int [] dy = {0, -1, 0, 1};
    static int district;
    static int [][] route;
    static Map<Integer, List<int []>> outLinePointMap;
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        route = new int[N][N];
        visited = new boolean[N][N];
        StringTokenizer st;
        outLinePointMap = new HashMap<>();

        district = 2;

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());

            for(int j=0;j<N;j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(!visited[i][j] && map[i][j] == 1){
                    separateDistrict(i, j);
                    district++;
                }
            }
        }

        for(int i=2;i<district;i++){
            findMinDistance(i);
        }

        System.out.println(answer - 1); //도착지에 대한 값은 빼고 차이만 계산하면 된다.
    }

    public static void findMinDistance(int district){
        Queue<Status> q = new LinkedList<>();
        initRoute();
        for(int [] point : outLinePointMap.get(district)){
            q.offer(new Status(point, 0));
            route[point[0]][point[1]] = 0;
        }

        while(!q.isEmpty()){
            Status cur = q.poll();

            if(map[cur.point[0]][cur.point[1]] > 1 && map[cur.point[0]][cur.point[1]] != district){
                answer = Math.min(answer, cur.distance);
                return;
            }

            for(int i=0;i<4;i++){
                int nx = cur.point[0] + dx[i];
                int ny = cur.point[1] + dy[i];

                if(isIn(nx, ny) && map[nx][ny] != 1 && cur.distance + 1 < route[nx][ny]){
                    route[nx][ny] = cur.distance + 1;
                    q.offer(new Status(new int[]{nx, ny}, cur.distance + 1));
                }
            }
        }

    }

    public static void separateDistrict(int x, int y){
        Queue<int []> q = new LinkedList<>();
        visited[x][y] = true;
        outLinePointMap.put(district, new ArrayList<>());
        q.offer(new int[]{x, y});

        while(!q.isEmpty()){
            int [] cur = q.poll();
            int count = 0;

            for(int i=0;i<4;i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if(isIn(nx, ny) && map[nx][ny] == 0){
                    count++;
                }

                if(isIn(nx, ny) && !visited[nx][ny] && map[nx][ny] == 1){
                    visited[nx][ny] = true;
                    q.offer(new int[]{nx, ny});
                }
            }

            if(count > 0) {
                map[cur[0]][cur[1]] = district;
                List<int []> outLinePointList = outLinePointMap.get(district);
                outLinePointList.add(new int[]{cur[0], cur[1]});
                outLinePointMap.put(district, outLinePointList);
            }
        }
    }

    public static void initRoute(){
        for(int i=0;i<N;i++){
            Arrays.fill(route[i], Integer.MAX_VALUE);
        }
    }

    public static boolean isIn(int x, int y){
        return x>=0 && x<N && y>=0 && y<N;
    }

    static class Status{
        int [] point;
        int distance;

        Status(int [] point, int distance){
            this.point = point;
            this.distance = distance;
        }
    }
}
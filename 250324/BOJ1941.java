/**
 * @author nakhoonchoi
 * @date 2025/03/18
 * @see https://boj.ma/1941
 * @mem 302,844kb
 * @time 1,012ms
 * @caution
 * [고려사항]
 * 단순한 BFS 문제인 줄 알았으나, 배열 크기가 5*5이고 7개의 숫자만 뽑으면 되기 때문에
 * 조합을 써야 되겠다는 힌트를 얻었다. (25C7 = 480,700)
 * 조합으로 이룬 숫자들이 가로 세로로 인접한 지 확인 후 인접한다면 S의 카운트를 세면 되었다.
 *
 * 2차원 배열 좌표는 아래와 같이 생각했다.
 * 1 2 3 4 5
 * 6 7 8 9 10
 * 11 12 13 14 15
 * 16 17 18 19 20
 * 21 22 23 24 25
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
//백준 <조합> '소문난 칠공주'

public class BOJ1941 {
    static int [] temp = new int[7];
    static char [][] map = new char[5][5];
    static int [] dx = {-1, 0, 1, 0};
    static int [] dy = {0, -1, 0, 1};
    static int answer = 0;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for(int i=0;i<5;i++){
            map[i] = br.readLine().toCharArray();
        }

        comb(0, 1);

        System.out.println(answer);
    }

    public static boolean isConnected(){
        Queue<Integer> q = new LinkedList<>();

        Set<Integer> tempSet = Arrays.stream(temp).boxed().collect(Collectors.toSet());
        Set<Integer> connectedSet = new HashSet<>();

        q.offer(temp[0]);
        connectedSet.add(temp[0]);

        while(!q.isEmpty()){
            int cur = q.poll();

            int x = (cur - 1) / 5;
            int y = (cur - 1) % 5;

            for(int i=0;i<4;i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                int nextNum = nx * 5 + ny + 1;

                if(isIn(nx, ny) && tempSet.contains(nextNum) && !connectedSet.contains(nextNum)){
                    q.offer(nextNum);
                    connectedSet.add(nextNum);
                }
            }
        }

        return connectedSet.size() == 7;
    }

    public static int getSCount(){
        int count = 0;
        for(int i=0;i<temp.length;i++){
            int x = (temp[i] - 1) / 5;
            int y = (temp[i] - 1) % 5;

            if(map[x][y] == 'S'){
                count++;
            }
        }
        return count;
    }

    public static void comb(int count, int start){
        if(count == 7){
            //여기에서 검증
            if(isConnected() && getSCount() >= 4){
                answer++;
            }
            return;
        }
        for(int i=start;i<=25;i++){
            temp[count] = i;
            comb(count + 1, i + 1);
        }
    }

    public static boolean isIn(int x, int y){
        return x>=0 && x<5 && y>=0 && y<5;
    }
}
/**
 * @author nakhoonchoi
 * @date 2025/05/09
 * @see https://boj.ma/8972
 * @mem 31,576kb
 * @time 192ms
 * @caution
 * [고려사항]
 * 삼성 기출 느낌이 드는 문제대로 코드에 작성하면 풀리는 문제였다.
 *
 * 문제의 각 과정에 대해 구현 방법을 간략하게 적어보려고 한다.
 *
 * 입력에서 마지막 줄 종수가 움직이려고 하는 방향에 대해서는 moveDir 배열에 숫자로 변환해서 입력해두었다.
 *
 * 1. 먼저, 종수가 아두이노를 8가지 방향(수직,수평,대각선)으로 이동시키거나, 그 위치에 그대로 놔둔다.
 * - dx, dy에 문제에 적힌 방향대로 방향 배열을 선언했다.
 * - 해당 방향대로 종수도, 아두이노도 이동이 가능하다.
 *
 * 2. 종수의 아두이노가 미친 아두이노가 있는 칸으로 이동한 경우에는 게임이 끝나게 되며, 종수는 게임을 지게 된다.
 * - 이 부분은 종수의 아두이노를 moveDir 방향대로 이동시킨 뒤에
 *   아두이노를 이동하면서 최소 맨해튼 거리가 0이 나온다면 바로 게임이 끝나도록 했다.
 * - 실제 문제를 풀 때 크게 고려하진 않았다.(?)
 *
 * 3. 미친 아두이노는 8가지 방향 중에서 종수의 아두이노와 가장 가까워 지는 방향으로 한 칸 이동한다. 즉, 종수의 위치를 (r1,s1), 미친 아두이노의 위치를 (r2, s2)라고 했을 때, |r1-r2| + |s1-s2|가 가장 작아지는 방향으로 이동한다.
 * - 각 8방향 중에서 종수와 맨해튼 거리가 가장 작은 방향으로 이동시켰다.
 * - 맨해튼 거리가 가장 작은 방향이 여러 방향으로 있지 않을까? 했지만 최소인 방향은 딱 하나만 나올 것이다.
 * - 그리고 아두이노가 map을 벗어나지 않는지 판별해주었다.(isIn)
 *
 * 4. 미친 아두이노가 종수의 아두이노가 있는 칸으로 이동한 경우에는 게임이 끝나게 되고, 종수는 게임을 지게 된다.
 * - 2번에서 적은대로 종수를 moveDir 방향대로 이동시킨 뒤에
 *   아두이노를 이동하면서 최소 맨해튼 거리가 0이 나온다면 바로 게임이 끝나도록 했다.
 *
 * 5. 2개 또는 그 이상의 미친 아두이노가 같은 칸에 있는 경우에는 큰 폭발이 일어나고, 그 칸에 있는 아두이노는 모두 파괴된다.
 * - 아두이노가 해당 좌표에 몇 개가 겹치는 지 arduinoCountMap에서 관리해주었으며,
 *   arduinoList에 아두이노 현황을 반영할 때 arduinoCountMap의 값이 1인 좌표만 List에 담아주었다.
 *
 * 중간에 끝나는 경우에는 "kraj (횟수)"를 출력해주었고
 * 종수가 moveDir대로 모든 이동을 마쳤을 경우에는
 * 현재 arduinoList와 startX, startY(종수의 좌표)를 기반으로
 * map을 입력과 같은 포맷으로 출력해주었다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <구현> '미친 아두이노'

public class BOJ8972{
    static int [] dx = {1,1,1,0,0,0,-1,-1,-1};
    static int [] dy = {-1,0,1,-1,0,1,-1,0,1};
    static int startX, startY, R, C;
    static char [][] map;
    static int [][] arduinoCountMap;
    static int [] moveDir;
    static List<int []> arduinoList;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];
        arduinoList = new ArrayList<>();

        for(int i=0;i<R;i++){
            String str = br.readLine();
            map[i] = str.toCharArray();

            for(int j=0;j<C;j++){
                if(map[i][j] == 'I'){
                    startX = i;
                    startY = j;
                }else if(map[i][j] == 'R'){
                    arduinoList.add(new int[]{i, j});
                }
            }
        }

        String str = br.readLine();
        moveDir = Arrays.stream(str.split("")).mapToInt(Integer::parseInt).toArray();

        for(int i=1;i<=moveDir.length;i++){
            arduinoCountMap = new int[R][C];

            int dir = moveDir[i-1] - 1;
            startX = startX + dx[dir];
            startY = startY + dy[dir];

            List<int []> newArduinoList = new ArrayList<>();

            for(int [] arduino : arduinoList){
                int minDir = -1;
                int minDistance = Integer.MAX_VALUE;

                for(int j=0;j<9;j++){
                    int nx = arduino[0] + dx[j];
                    int ny = arduino[1] + dy[j];

                    if(isIn(nx, ny) && minDistance > getDistance(startX, startY, nx, ny)){
                        minDir = j;
                        minDistance = getDistance(startX, startY, nx, ny);
                    }
                }

                if(minDistance == 0){
                    System.out.println("kraj " + i);
                    return;
                }

                int nx = arduino[0] + dx[minDir];
                int ny = arduino[1] + dy[minDir];

                arduinoCountMap[nx][ny]++;
            }

            for(int j=0;j<R;j++){
                for(int k=0;k<C;k++){
                    if(arduinoCountMap[j][k] == 1){
                        newArduinoList.add(new int[]{j, k});
                    }
                }
            }

            arduinoList = newArduinoList;
        }

        setMap();
        printMap();
    }

    public static boolean isIn(int x, int y){
        return x>=0 && x<R && y>=0 && y<C;
    }

    public static int getDistance(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static void setMap(){
        for(int i=0;i<R;i++) {
            Arrays.fill(map[i], '.');
        }
        map[startX][startY] = 'I';

        for(int [] arduino : arduinoList){
            map[arduino[0]][arduino[1]] = 'R';
        }
    }

    public static void printMap(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<R;i++){
            sb.append(String.valueOf(map[i])).append('\n');
        }
        System.out.println(sb.substring(0, sb.length()-1));
    }
}
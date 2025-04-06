/**
 * @author nakhoonchoi
 * @date 2025/04/08
 * @see https://boj.ma/3109
 * @mem 46,648kb
 * @time 280ms
 * @caution
 * [고려사항]
 * 첫 번째 열에서 출발해서 마지막 열까지 도달하는 최대의 파이프 연결 수를 구하는 문제였다.
 *
 * 최적의 파이프 개수를 구하려면 첫 번째 행부터 마지막 행까지 파이프를 만들되
 * 최대한 위쪽으로 파이프를 만들도록 우선순위를 정해서 파이프를 만들어야
 * 다음 행에서 파이프를 만들 때 최적의 파이프를 구할 수 있다.
 * (파이프를 구성할 때 우선순위를 오른쪽 위, 오른쪽, 오른쪽 아래로 정해서 파이프를 만든다.)
 *
 * 파이프를 만드는 로직은 재귀(dfs)를 이용했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <그리디> '빵집'

public class BOJ3109_4 {
    static int N, M;
    static char [][] map;
    static int [] dx = {-1, 0, 1};
    static int answer;
    static boolean flag;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];

        for(int i=0;i<N;i++){
            String str = br.readLine();
            map[i] = str.toCharArray();
        }

        for(int i=0;i<N;i++){
            flag = false;
            dfs(i, 0);
        }

        System.out.println(answer);
    }

    private static void dfs(int row, int col) {
        if(col == M-1){
            flag = true;
            answer++;
            return;
        }

        for(int i=0;i<3;i++){
            int nx = row + dx[i];
            int ny = col + 1;

            if(flag){
                return;
            }

            if(isIn(nx, ny) && map[nx][ny] == '.'){
                map[nx][ny] = 'x';
                dfs(nx, ny);
            }
        }
    }

    private static boolean isIn(int x, int y){
        return x>=0 && x<N && y>=0 && y<M;
    }
}
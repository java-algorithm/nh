/**
 * @author nakhoonchoi
 * @date 2025/03/28
 * @see https://boj.ma/2169
 * @mem 116,664kb
 * @time 528ms
 * @caution
 * [고려사항]
 * 일반적인 DP와 다르게 생각해야 하는 부분이 있어서 어려웠던 문제였다.
 *
 * 우선 로봇은 아래, 왼, 오른 방향으로만 이동이 가능하기 때문에
 * 최적의 값을 계산해서 다음 행으로 보내는 것이 중요하다고 생각했다.
 *
 * 메모이제이션 배열 dp는 [x좌표][y좌표][flag]를 정보로 두며,
 * flag는 0의 경우에는 위에서 내려온 값을 우선 담으며, 왼쪽과 오른쪽의 최적이 구해진다면 최종 최적의 값을 담을 flag이다.
 * 1은 왼쪽에서 오른쪽 방향으로 탐색할 때 넣을 값을 담는다.
 * 2은 오른쪽에서 왼쪽 방향으로 탐색할 때 넣을 값을 담는다.
 *
 * 일단 첫 번째 행은 위의 값이 없고 1,1에서 시작한다는 조건이 있기 때문에
 * 왼쪽에서 오른쪽 방향으로만 탐색이 진행된다.
 *
 * 그리고 두 번째 행부터 마지막 행까지는 다음 행에 넘어가기 전에 최적의 값을 세팅한 뒤에 넘어가야한다.
 * - 왼->오 방향으로 누적 합 방식으로 합을 구해준다. 방향의 누적 합과 '현재 좌표의 위 값'중 큰 값을 선택해서 현재 위치를 더한다.
 * - 오->왼 방향으로 누적 합 방식으로 합을 구해준다. 방향의 누적 합과 '현재 좌표의 위 값'중 큰 값을 선택해서 현재 위치를 더한다.
 * - 마지막에 dp[i][j][0]에 Math.max(dp[i][j][왼->오], dp[i][j][오->왼])을 대입한다.
 *
 * 이 방식이 되는 이유는 왼->오 방향과 오->왼 방향을 탐색할 때 윗 칸의 메모이제이션 값을 항상 고려했기 때문에
 * 왼쪽과 오른쪽, 아래 방향을 고려해서 최적의 값을 항상 가져올 수 있었다.
 *
 * 처음에는 윗 칸의 값을 가져오고, 윗 칸과 왼쪽과 오른쪽까지 세 값을 비교했었는데 결국은 두 값만 비교하면 되었다.
 * 
 * 시간 복잡도를 생각해보면, N과 M이 최대 1000이라는 가정하에
 * O(N * (2 * M))이지 않을까 싶다.
 * M의 크기 만큼 두 번씩 탐색하고 행의 크기 N만큼 순회해야하기 때문이다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP> '로봇 조종하기'

public class BOJ2169 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int [][] arr = new int[N][M];
        int [][][] dp = new int[N][M][3];
        //0: 위, 1: 왼쪽->오른쪽, 2: 오른쪽->왼쪽

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp[0][0][0] = dp[0][0][1] = arr[0][0];

        //첫 번째 행은 오른쪽으로만 탐색이 가능하다.
        for(int j=1;j<M;j++){
            dp[0][j][1] = dp[0][j-1][1] + arr[0][j];
            dp[0][j][0] = dp[0][j][1];
        }

        for(int i=1;i<N;i++){
            //왼 -> 오른 방향 배열 갱신(flag = 1)
            dp[i][0][1] = dp[i-1][0][0] + arr[i][0];
            for(int j=1;j<M;j++){
                dp[i][j][1] = Math.max(dp[i][j-1][1], dp[i-1][j][0]) + arr[i][j];
            }

            //오른 -> 왼 방향 배열 갱신(flag = 2)
            dp[i][M-1][2] = dp[i-1][M-1][0] + arr[i][M-1];
            for(int j=M-2;j>=0;j--){
                dp[i][j][2] = Math.max(dp[i][j+1][2], dp[i-1][j][0]) + arr[i][j];
            }

            //종합적으로 현재 위치의 위에서 내려온 값, 왼->오, 오->왼 방향으로 넘어온 값들 중 가장 최댓값을 저장
            for(int j=0;j<M;j++){
                dp[i][j][0] = Math.max(dp[i][j][1], dp[i][j][2]);
            }
        }

        //최적의 값 출력
        System.out.println(dp[N-1][M-1][0]);
    }
}
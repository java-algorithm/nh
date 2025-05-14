/**
 * @author nakhoonchoi
 * @date 2025/04/29
 * @see https://boj.ma/10836
 * @mem 312,408kb
 * @time 4,720ms
 * @caution
 * [고려사항]
 * 서브태스크가 존재했고, 1번 (15점), 1,2,3번 통과(83점)를 넘어 모든 태스크 통과(100점) 순서로 해결했다.
 *
 * 전체적으로는 누적합을 이용해야하는 문제였다.
 * 문제대로 그대로 입력 받은대로 0열과 0행에 날짜별로 애벌레의 성장 데이터를 받아서
 * 2차원 배열을 1행 1열부터 M-1행 M-1열까지 왼쪽, 위, 왼쪽위 대각 성장값 중 최대를 계산해
 * 계속 합해주었을 때에는 15점을 받았다.
 *
 * 이후에는 규칙을 찾아보려고 했다. 입력 받은 순서대로 0,1,2의 개수가
 * 왼쪽 아래(M-1,0)에서 위로 쭉 올라오고, 왼쪽 위(0,0)에서 오른쪽으로 쭉 오는 순서로 개수만큼 배열에 넣는다고 했을 때
 * dp[i][j]는 dp[0][j]의 값이 그대로 내려오는 규칙을 찾았다.
 * 왜냐하면 열의 숫자들보다 맨 윗 행의 숫자들이 훨씬 클 것이기 때문이다.
 *
 * 그리고 날짜별로 입력 받을 때마다 더해주는 게 아니라
 * 입력 받으면 누적으로 애벌레의 총 성장치를 저장해둔 뒤에
 * 한 번에 맨 윗 행의 숫자를 내려주면 효율적으로 처리할 수 있다.
 *
 * 여기까지 했을 때 1,2,3 서브태스크 통과(83점)를 얻었다...
 *
 * 그리고 100점을 얻는데에는 지금도 이유를 잘 모르겠지만
 * 2*M-1의 성장치를 growStatusMap 배열에 기록할 때의 방식 차이 때문에 시간 초과가 발생했다.
 *
 * 2*M-1 만큼의 성장치를 0열과 0행에 넣을 때 방식을 아래로 했을 때에는 83점이 나오지만
 * 코드에 나온대로 수정했더니 100점이 나왔다.
 * 예상을 해본다면 저 while문 때문이 아닐까 싶다.
 *
 * for(int i=0;i<N;i++){
 *     st = new StringTokenizer(br.readLine());
 *     int [] inputAddCountArr = new int[3];
 *
 *     for(int j=0;j<3;j++){
 *         inputAddCountArr[j] = Integer.parseInt(st.nextToken());
 *     }
 *
 *     int x = M-1;
 *     int y = 0;
 *     int cur = 0;
 *     for(int j=0;j<2*M-1;j++){
 *         while(inputAddCountArr[cur] == 0){
 *             cur++;
 *         }
 *         if(j < M-1){
 *             growStatusMap[x][y] += cur;
 *             inputAddCountArr[cur]--;
 *             x--;
 *         }else{
 *             growStatusMap[x][y] += cur;
 *             inputAddCountArr[cur]--;
 *             y++;
 *         }
 *     }
 * }
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <누적합> '여왕벌'

public class BOJ10836{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int [][] map = new int[M][M];
        int [][] growStatusMap = new int[M][M];

        for(int i=0;i<M;i++){
            Arrays.fill(map[i], 1);
        }

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            int [] inputAddCountArr = new int[3];

            for(int j=0;j<3;j++){
                inputAddCountArr[j] = Integer.parseInt(st.nextToken());
            }

            for(int j=M-1;j>0;j--){
                if(inputAddCountArr[0] > 0){
                    inputAddCountArr[0]--;
                }else if(inputAddCountArr[1] > 0){
                    growStatusMap[j][0]++;
                    inputAddCountArr[1]--;
                }else if(inputAddCountArr[2] > 0){
                    growStatusMap[j][0] += 2;
                    inputAddCountArr[2]--;
                }
            }

            for(int j=0;j<M;j++){
                if(inputAddCountArr[0] > 0){
                    inputAddCountArr[0]--;
                }else if(inputAddCountArr[1] > 0){
                    growStatusMap[0][j]++;
                    inputAddCountArr[1]--;
                }else if(inputAddCountArr[2] > 0){
                    growStatusMap[0][j] += 2;
                    inputAddCountArr[2]--;
                }
            }
        }

        for(int j=1;j<M;j++){
            for(int k=1;k<M;k++) {
                growStatusMap[j][k] = growStatusMap[j-1][k];
            }
        }

        for(int j=0;j<M;j++){
            for(int k=0;k<M;k++){
                map[j][k] += growStatusMap[j][k];
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<M;i++){
            for (int j=0;j<M;j++){
                sb.append(map[i][j]).append(" ");
            }
            sb.deleteCharAt(sb.lastIndexOf(" "));
            sb.append("\n");
        }
        bw.write(String.valueOf(sb.deleteCharAt(sb.lastIndexOf("\n"))));

        br.close();
        bw.close();
    }
}
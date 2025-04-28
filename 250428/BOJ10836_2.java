/**
 * @author nakhoonchoi
 * @date 2025/04/28
 * @see https://boj.ma/10836
 * @mem 302,604kb
 * @time 4,712ms
 * @caution
 * [고려사항]
 * BOJ10836.java와 같은 문제 풀이이다.
 * 2*M-1의 입력 받은 0,1,2의 수를 인덱스 기준으로 나눠서
 * growStatusMap에 누적합을 해주었다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <구현> '여왕벌'
public class BOJ10836_2{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
            for(int j=0;j<3;j++) {
                inputAddCountArr[j] = Integer.parseInt(st.nextToken());
            }

            int zeroEnd = inputAddCountArr[0] - 1; // 0 적용 끝 index
            int oneEnd = zeroEnd + inputAddCountArr[1]; // 1 적용 끝 index
            int twoEnd = oneEnd + inputAddCountArr[2]; // 2 적용 끝 index (== 2*M-2)

            int x = M - 1;
            int y = 0;
            for (int j=0;j<2*M-1;j++){
                if(j<=zeroEnd){
                    // 0은 변화 없음
                }else if(j<=oneEnd){
                    growStatusMap[x][y] += 1;
                }else{
                    growStatusMap[x][y] += 2;
                }

                if(j<M-1){
                    x--;
                }else{
                    y++;
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
        System.out.println(sb.deleteCharAt(sb.lastIndexOf("\n")));
    }
}
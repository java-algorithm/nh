/**
 * @author nakhoonchoi
 * @date 2025/08/05
 * @see https://boj.ma/3020
 * @mem 29,796kb
 * @time 244ms
 * @caution
 * [고려사항]
 * 딱 한 달 전에 풀어봤던 문제였다.
 * 당시에는 이분 탐색으로 풀었는데 이번에는 이모스법을 이용한 누적합으로 풀어봤다.
 * 이분 탐색은 N을 기준으로 up/down 배열을 나눠서 풀었는데,
 * 누적합은 H를 기준으로 up/down 배열을 나눠서 각 높이 별로 지나가는 칸의 개수를 고려했다.
 * 이후에 up은 오름차순으로, down은 내림차순 방향으로 누적합을 진행했고,
 * 두 경우의 수를 up에 합산해줬다.
 *
 * 이후에 최소값과 개수를 판단했다.
 *
 * 💡 이분 탐색의 소요 시간보다 누적합의 소요시간이 O(N)이어서 그런지 빨랐다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <누적합> '개똥벌레'

public class BOJ3020_2 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());

        int [] up = new int[H];
        int [] down = new int[H];

        for(int i=0;i<N;i++){
            int height = Integer.parseInt(br.readLine());
            if(i % 2 == 0){
                up[0]++;
                up[height]--;
            }else{
                down[H-1]++;
                down[H-height-1]--;
            }
        }

        for(int i=1;i<H;i++){
            up[i] += up[i-1];
            down[H-i-1] += down[H-i];
        }

        for(int i=0;i<H;i++){
            up[i] += down[i];
        }

        int min = Integer.MAX_VALUE;
        int count = 0;

        for(int i=0;i<H;i++){
            if(up[i] < min){
                min = up[i];
                count = 1;
            }else if(up[i] == min){
                count++;
            }
        }

        System.out.println(min + " " + count);
    }
}
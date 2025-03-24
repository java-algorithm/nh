/**
 * @author nakhoonchoi
 * @date 2025/03/24
 * @see https://boj.ma/11054
 * @mem 12,268kb
 * @time 100ms
 * @caution
 * [고려사항]
 * 주어진 배열의 크기 N이 최대가 1000이기 때문에 O(N^2)의 메모이제이션 방식으로 풀어도 시간 초과가 발생하지 않을 것이라고 생각했다.
 *
 * 입력 받은 배열에 따라 가장 긴 증가하는 부분 수열의 길이와 가장 긴 감소하는 부분 수열의 길이를 up과 down 배열에 따로 메모이제이션하고,
 * 인덱스 0부터 N까지 순회하면서 up[i] + down[i] - 1의 길이 중 가장 최댓값을 구하면 되는 문제였다.
 * 바이토닉 부분 수열이라는 것이 최대까지 증가했다가 감소하는 수열이기 때문에 양쪽 기준으로 구하면 됐다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP> '가장 긴 바이토닉 부분 수열'

public class BOJ11054_2 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int [] arr = new int[N];
        int [] up = new int[N];
        int [] down = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for(int i=0;i<N;i++){
            up[i] = 1;
            for(int j=0;j<i;j++){
                if(arr[j] < arr[i]){
                    up[i] = Math.max(up[i], up[j] + 1);
                }
            }
        }

        for(int i=N-1;i>=0;i--){
            down[i] = 1;
            for(int j=N-1;j>i;j--){
                if(arr[j] < arr[i]){
                    down[i] = Math.max(down[i], down[j] + 1);
                }
            }
        }

        int max = 0;
        for(int i=0;i<N;i++){
            max = Math.max(max, up[i] + down[i] - 1);
        }

        System.out.println(max);
    }
}
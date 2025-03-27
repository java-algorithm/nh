/**
 * @author nakhoonchoi
 * @date 2025/03/27
 * @see https://boj.ma/1959
 * @mem 11,572kb
 * @time 64ms
 * @caution
 * [고려사항]
 * 우선 Java 기준으로 제출 시점에 소요 시간 1등 코드여서 뿌듯하다.
 * 회전 수와 마지막 X, Y 좌표 각각 규칙이 있었다. 규칙을 찾아야하는 수학 문제였다.
 *
 * - 회전 수
 * 회전 수는 M과 N 중 최솟값, M과 N 중 어떤 값이 더 작은 지에 따라 분기처리를 했다.
 * min(M과 N 중 최솟값)을 구했다고 가정하고,
 * N이 M보다 작거나 같다면 회전 수는 2 * (min - 1), N이 더 크다면 2 * min - 1이다.
 *
 * 회전 수에 비해 마지막 좌표 구하는 규칙은 많이 어려웠다...
 *
 * - 마지막 X 좌표
 * 그림을 그려봤을 때 행을 N이라고 치고, 값(index)은 0부터 시작하고 M은 2부터 시작한다고 가정한다.
 *     2 3 4 5 6
 * 2 | 1...
 * 3 | 1...
 * 4 | 1 2...
 * 5 | 1 3 2...
 * 6 | 1 4 2 3...
 *
 * 위와 같이 마지막 X 좌표가 정해진다고 했을 때, M이 N-1보다 크거나 같다면 고정이다.
 * 그리고 M이 N-1보다 작다면 M이 홀수인지 짝수인지에 따라 분기처리해서 값을 구하면 된다.
 *
 * - 마지막 Y 좌표
 * 그림을 그려봤을 때 행을 M이라고 치고, 값(index)은 0부터 시작하고 N은 2부터 시작한다고 가정한다.
 *     2 3 4 5 6
 * 2 | 0...
 * 3 | 0 1...
 * 4 | 0 2 1...
 * 5 | 0 3 1 2...
 * 6 | 0 4 1 3 2...
 *
 * 위와 같이 마지막 Y 좌표가 정해진다고 했을 때, N이 M보다 크거나 같다면 고정이다.
 * 그리고 N이 M보다 작다면 N이 홀수인지 짝수인지에 따라 분기처리해서 값을 구하면 된다.
 *
 * 결국 내가 구한 규칙은 index를 0 기준으로 생각해서 구한 점화식이기 때문에 구한다음 + 1을 해서 출력해야한다.
 *
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <수학> '달팽이 3'

public class BOJ1959 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        long min = Math.min(N, M);

        long spinCount = N <= M ? 2 * (min - 1) : 2 * min - 1;

        System.out.println(spinCount);

        long lastX, lastY;
        if(M >= N - 1){
            lastX = N / 2;
        }else{
            if(M % 2 == 0){
                lastX = M / 2;
            }else{
                lastX = N - (M + 1) / 2;
            }
        }

        if(N >= M){
            lastY = (M - 1) / 2;
        }else{
            if(N % 2 == 0){
                lastY = N / 2 - 1;
            }else{
                lastY = M - 2 - (N / 2 - 1);
            }
        }

        lastX++;
        lastY++;

        System.out.println(lastX + " " + lastY);
    }
}
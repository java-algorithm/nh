/**
 * @author nakhoonchoi
 * @date 2025/06/08
 * @see https://boj.ma/3020
 * @mem 37,688kb
 * @time 600ms
 * @caution
 * [고려사항]
 * 오랜만에 푼 이분 탐색 문제였다.
 * 문제 해결 로직이 떠오르지 않아 오래 걸렸다.
 *
 * 종유석(아래 -> 위)와 석순(위 -> 아래) 방향으로 나누어서
 * 각 방향 별로 이분 탐색을 진행하면 되는 문제였다.
 * 방향 별로 A, B 배열에 높이를 입력 받고 이분 탐색을 위해 정렬했다.
 *
 * 높이를 1부터 H까지 탐색하며
 * 각 방향 별로 부술 수 있는 갯수를 세어줄 때 이분 탐색을 이용하면 된다.
 *
 * 이분 탐색에서 들어갈 left와 right는 정렬된 높이의 최소와 최대를 의미하고,
 * 이분 탐색을 마쳤을 때의 right는 현재 높이보다 큰 가장 최소(lower_bound)값이기 때문에
 * arr.length - right는 부술 수 있는 갯수를 의미한다.
 *
 * 각 높이 별로 (부술 수 있는 석순 갯수 + 부술 수 있는 종유석 갯수)의 최소와 경우의 수를 계산해주면된다.
 *
 * 알고리즘도 가끔씩은 풀어야겠다...
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <이분 탐색> '개똥벌레'

public class BOJ3020 {
    static int [] barInfoA;
    static int [] barInfoB;
    static int N, H;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        barInfoA = new int[N/2]; //종유석(아래 -> 위)
        barInfoB = new int[N/2]; //석순(위 -> 아래)

        for(int i=0;i<N;i++){
            if(i % 2 == 0) {
                barInfoA[i/2] = Integer.parseInt(br.readLine());
            }else{
                barInfoB[i/2] = Integer.parseInt(br.readLine());
            }
        }

        Arrays.sort(barInfoA);
        Arrays.sort(barInfoB);

        int min = N;
        int count = 0;

        for(int i=1;i<=H;i++){
            int down = getBreakCount(0, N/2, barInfoB, H-i+1);
            int up = getBreakCount(0, N/2, barInfoA, i);

            if(down + up < min){
                min = down + up;
                count = 1;
            }else if(down + up == min){
                count++;
            }
        }

        System.out.println(min + " " + count);
    }

    public static int getBreakCount(int left, int right, int [] arr, int height){
        while(left < right){
            int mid = left + (right - left) / 2;

            if(arr[mid] >= height){
                right = mid;
            }else{
                left = mid + 1;
            }
        }

        return arr.length - right;
    }
}
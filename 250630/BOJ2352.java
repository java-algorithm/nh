/**
 * @author nakhoonchoi
 * @date 2025/06/27
 * @see https://boj.ma/2352
 * @mem 20,188kb
 * @time 200ms
 * @caution
 * [고려사항]
 * 문제를 봤을 때 LIS(Longest Increasing Subsequence) 문제라는 것을 알았다.
 * 그리고 배열의 길이가 최대 40000인 것을 보고 O(N^2) 방식의 LIS를 사용해서 풀 수 없겠다고 생각했다.
 * (4 * 10^4) * (4 * 10^4) = 1.6 * 10^9 로
 * 보통 Java에서 1억 번의 연산이 1초가 걸린다고 가정하면 16초가 걸릴 것이기 때문에 다른 방식으로 풀어야했다.
 *
 * O(NlogN) 방식의 LIS로 풀 수도 있는데 예전에 풀었던 기억이 있지만 까먹어서 오래 걸렸다.
 *
 * lastMinValueArr[i]는 길이가 i+1인 모든 증가 부분 수열 중 끝나는 값이 가장 작은 값을 저장할 배열 lastMinValueArr이다.
 * lisLength는 LIS의 길이를 의미하는 변수이다.
 *
 * 전체적으로 흐름을 적어보면, 배열의 크기와 원소값을 입력받고 배열의 원소를 순회하면서 위 배열과 변수에 값을 갱신한다.
 *
 * for문 안의 로직을 순서대로 설명한다. (숫자 num)
 * - Arrays.binarySearch 메소드를 이용해서 lastMinValueArr의 인덱스 0부터 lisLength까지
 *  num이 몇 번째 인덱스에 들어갈 수 있는지 조회한다.
 * - 💡 binarySearch 메소드의 반환값이 음수라면 해당 num을 배열에서 찾지 못했다는 뜻이고
 *  (-1 * (반환값 + 1)) 인덱스에 값을 갱신한다면 lastMinValueArr의 목적에 맞게 증가 부분 수열 중 끝나는 값이 가장 작은
 *  값이 갱신될 것이다.
 * - lastMinValueArr의 인덱스 값을 num으로 갱신하고, 반환해서 변환한 index가 lisLength와 같았다면 num이 배열의 모든
 *  수보다 크다는 뜻이므로 LIS 길이가 늘어난다. lisLength++;를 진행한다.
 *
 * 모든 배열의 원소에 대해서 위 로직을 수행하고 마지막에 LIS의 길이 lisLength를 출력한다.
 *
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <LIS(O(NlogN))> '반도체 설계'

public class BOJ2352 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int [] arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i=0;i<N;i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int [] lastMinValueArr = new int[N];
        int lisLength = 0;

        for(int num : arr){
            int index = Arrays.binarySearch(lastMinValueArr, 0, lisLength, num);

            if(index < 0){
                index = (index + 1) * -1;
            }

            lastMinValueArr[index] = num;

            if(index == lisLength){
                lisLength++;
            }
        }

        System.out.println(Arrays.toString(lastMinValueArr));

        System.out.println(lisLength);
    }
}
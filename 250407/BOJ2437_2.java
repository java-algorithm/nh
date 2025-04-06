/**
 * @author nakhoonchoi
 * @date 2025/04/07
 * @see https://boj.ma/2437
 * @mem 11,848kb
 * @time 76ms
 * @caution
 * [고려사항]
 * 주어진 추를 오름차순으로 정렬해서 점진적으로 추만큼 무게를 늘리며
 * 측정할 수 없는 최소의 무게를 출력하는 문제이다.
 * 추가 아무 것도 없을 때 측정할 수 없는 최소의 무게는 1이므로 answer는 1부터 시작한다.
 * 오름차순으로 정렬된 추만큼 += 를 해주면서 현재 answer보다 추의 무게가 더 높다면
 * 현재 answer만큼 무게는 측정할 수 없다고 판단해서 answer를 출력해주었다.
 *
 * 번외로 아래와 같은 생각을 할 수도 있었다. 추가 2가 있다면,
 * 무게 1도 측정할 수 있는 것이 아닐까?
 * 무슨 의미인 지 이해가 안됐었지만 무게가 정수라고 보장된다면 위의 말이 맞지만
 * 실제 무게는 정수가 아니므로 보장이 되지 않는다.
 * https://www.acmicpc.net/board/view/57167
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <정렬> '저울'

public class BOJ2437_2 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int [] arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i=0;i<N;i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        int answer = 1;
        for(int i=0;i<N;i++) {
            if(arr[i] > answer) {
                break;
            }else {
                answer += arr[i];
            }
        }
        System.out.println(answer);
    }
}
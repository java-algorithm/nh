/**
 * @author nakhoonchoi
 * @date 2025/04/28
 * @see https://boj.ma/1011
 * @mem 13,068kb
 * @time 92ms
 * @caution
 * [고려사항]
 * 일단 start와 end는 의미가 없다.
 * end - start = gap (두 수의 차이 칸)
 * gap과 규칙으로 풀 수 있는 문제이다.
 *
 * 처음 k는 무조건 1이다.
 * 처음에 k=1만큼 이동한다면 최대한 k가 늘어서 한 번에 많은 이동을 하고
 * 마지막에 k=1만큼 이동해서 끝 점에 도달해야한다.
 * 그래서 최적의 값을 대칭 형태로 구할 수 있겠다고 생각했다.
 *
 * gap이
 * 3의 경우에는 1 1 1 -> 3
 * 4의 경우에는 1 2 1 -> 3
 * 5의 경우에는 1 2 1 1 -> 4로 최적의 k 맨 앞과 맨 뒤는 1로 같다.
 *
 * 점진적으로 k를 늘려가며 gap을 줄이는데
 * gap이 k이하 라면 1번의 이동으로 커버가 가능하기 때문에 gap -= k; answer++; 후에
 * while문을 빠져나올 것이다.
 * 그리고 gap이 k보다 크다면 gap -= (2 * k); answer += 2; 후 최대한 진행한다.
 *
 * while문의 조건은 gap > 0이기 때문에 0이 되거나 음수가 된다면 빠져나올 것이다.
 */
import java.io.*;
import java.util.*;
//백준 <수학> 'Fly me to the Alpha Centauri'

public class BOJ1011_3 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while(T-->0){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            int k = 1;
            int answer = 0;

            int gap = b - a;

            while(gap > 0){
                if(gap <= k){ //gap이 k 이하라면 1번 이동으로 해결 가능
                    gap -= k;
                    answer++;
                }else { //gap이 k 보다 큰 경우
                    gap -= (k * 2);
                    answer += 2;
                }
                k++;
            }

            sb.append(answer).append('\n');
        }
        System.out.println(sb.substring(0, sb.length() - 1));
    }
}
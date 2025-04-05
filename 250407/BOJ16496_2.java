/**
 * @author nakhoonchoi
 * @date 2025/04/06
 * @see https://boj.ma/16496
 * @mem 17,456kb
 * @time 148ms
 * @caution
 * [고려사항]
 * 리스트에 있는 수를 숫자 정렬이 아닌 사전 순으로 정렬해서 StringBuilder에 추가하면 되었다.
 * 0은 무조건 단독으로 하나만 주어지거나 하나만 주어지기 때문에 답이 0이 나올 경우는
 * 1
 * 0
 * 과 같은 케이스에만 나온다.
 *
 * 플래티넘 5 문제 치고는 단순했던 문제!
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <정렬> '큰 수 만들기'

public class BOJ16496_2 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String [] str = new String[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;i++){
            str[i] = st.nextToken();
        }

        Arrays.sort(str, (o1, o2) -> {
            StringBuilder sb1 = new StringBuilder(o1 + o2);
            StringBuilder sb2 = new StringBuilder(o2 + o1);

            return sb1.compareTo(sb2) * -1;
        });

        if(str[0].charAt(0) == '0'){
            System.out.println(0);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(String s : str){
            sb.append(s);
        }
        System.out.println(sb);
    }
}
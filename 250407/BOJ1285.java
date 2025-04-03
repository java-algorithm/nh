/**
 * @author nakhoonchoi
 * @date 2025/04/03
 * @see https://boj.ma/1285
 * @mem 89,888kb
 * @time 2,640ms
 * @caution
 * [고려사항]
 * 완전탐색으로 조합으로 뒤집을 행과 열을 모두 찾아서 비트의 T 개수를 구하려고 했다.
 * 하지만 N이 최대가 20이기 때문에 2 ^ 40으로 모든 경우의 수를 찾으려고 하면
 * 값이 1조가 넘기 때문에 메모리 초과가 발생했다.
 *
 * 반면에 2의 20승은 약 100만의 경우의 수로 행과 열 중에 하나(열)만 경우의 수만큼 뒤집고,
 * 그 현황에 행을 순회하며 count에 Math.min(map[j].cardinality(), N - map[j].cardinality())를 더해주면 되었다.
 * 이 의미는 T의 개수와 H의 개수 중 낮은 값을 더하는 것으로 어차피 뒤집으면 H가 T가 되는 것이기 때문에 위와 같은 최솟값 로직을 적용했다.
 *
 * 그리고 비트마스킹 연산자에 취약해서 보통 비트마스킹 문제가 나오면 BitSet 객체를 이용하는 편이다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <비트마스킹> '동전 뒤집기'

public class BOJ1285 {
    static int answer = Integer.MAX_VALUE;
    static BitSet [] map;
    static int N;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new BitSet[N];

        for(int i=0;i<N;i++){
            map[i] = new BitSet(N);

            String str = br.readLine();
            for(int j=0;j<N;j++){
                if(str.charAt(j) == 'T'){
                    map[i].set(j); //T인 경우 true로 설정.
                }
            }
        }

        for(int i=0;i<Math.pow(2, N);i++){
            BitSet bitSet = BitSet.valueOf(new long[]{i});

            //우선 열 기준으로 flip을 한 뒤에 T와 H 중 최솟값을 더해서 answer 값을 갱신했다.
            for(int j=0;j<bitSet.size();j++){
                if(bitSet.get(j)) {
                    bitSetRowFlip(j);
                }
            }

            int count = 0;
            for(int j=0;j<N;j++){
                int cardinality = map[j].cardinality();
                count += Math.min(cardinality, N - cardinality);
            }

            answer = Math.min(answer, count);

            for(int j=0;j<bitSet.size();j++){
                if(bitSet.get(j)) {
                    bitSetRowFlip(j);
                }
            }
        }

        System.out.println(answer);
    }

    //열 기준 flip하는 메소드
    public static void bitSetRowFlip(int index){
        for(int i=0;i<N;i++){
            map[i].flip(index);
        }
    }
}
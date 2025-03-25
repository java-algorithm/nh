/**
 * @author nakhoonchoi
 * @date 2025/03/25
 * @see https://boj.ma/12869
 * @mem 12,744kb
 * @time 84ms
 * @caution
 * [고려사항]
 * 문제를 처음 보고서 예전에 풀었던 문제 백준 9184번 '신나는 함수 실행'이 생각났다.
 * 당시에는 문제에 나온 그대로 재귀를 이용한 DP를 구현하면 되었는데
 * 이 문제도 똑같이 재귀로 DP를 구현했다.
 * 메모이제이션 해주지 않았다면 시간 초과가 발생했다.
 *
 * Array.fill 부분에서 O(N^3)이라고 생각되어졌지만 N이 최대가 60이므로
 * 60 * 60 * 60을 탐색하기에 메모이제이션을 해준다면 시간은 충분했다.
 *
 * {1,3,9}에 대해 순열을 따로 구하지 않고 메타데이터 배열을 선언해서
 * one, two, three 숫자를 각각 공격 당한 순서에 맞게 빼서,
 * 현재 one, two, three에 대한 메모이제이션을 dp[one][two][three]에 저장할 것이다.
 * one two three가 음수가 되면 안되기 때문에
 * max 연산으로 0과 각 숫자들 중 큰 값을 인덱스 one two three에 넣어서 메모이제이션 해주었다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DP> '뮤탈리스크'

public class BOJ12869 {
    static int [] mutalisk = new int[3];
    static int [][][] dp;
    static int [][] temp = {{1,3,9}, {1,9,3}, {3,1,9}, {3,9,1}, {9,1,3}, {9,3,1}};
    static int answer = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;i++){
            mutalisk[i] = Integer.parseInt(st.nextToken());
        }
        dp = new int[mutalisk[0] + 1][mutalisk[1] + 1][mutalisk[2] + 1];
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[0].length;j++){
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
        }

        dp[mutalisk[0]][mutalisk[1]][mutalisk[2]] = 0;

        dfs(mutalisk[0], mutalisk[1], mutalisk[2], 0);

        System.out.println(dp[0][0][0]);
    }

    static void dfs(int one, int two, int three, int count){
        if(one <= 0 && two <= 0 && three <= 0){
            dp[0][0][0] = Math.min(answer, count);
            return;
        }

        for(int i=0;i<temp.length;i++){
            int oneIndex = Math.max(one - temp[i][0], 0);
            int twoIndex = Math.max(two - temp[i][1], 0);
            int threeIndex = Math.max(three - temp[i][2], 0);

            if(dp[oneIndex][twoIndex][threeIndex] > count + 1){
                dp[oneIndex][twoIndex][threeIndex] = count + 1;
                dfs(oneIndex, twoIndex, threeIndex, count + 1);
            }
        }
    }
}
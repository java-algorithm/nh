/**
 * @author nakhoonchoi
 * @date 2025/02/20
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/1832
 * @caution
 * [고려사항]
 * dp로 문제를 해결해야하는 문제였다.
 * dp 배열은 3차원 배열로 [m][n][좌/상]을 나타냈다.
 *
 * 우선 초중등수학 때의 기억으로 첫 행과 첫 열에 대해 벽을 만나기 전까지 dp 배열에 1을 세팅한다.
 *
 * 1행 1열을 제외한 다른 칸들은 순회하면서 cityMap 값이 1이면 갈 수 없는 곳이기 때문에 무시한다.
 *
 * 윗 칸이나 왼 칸 값이 2(PASS)이건 0(EMPTY)이던 윗 칸의 [1(UP)] 값과 왼 칸의 [0(LEFT)] 값은 무조건 가져오기 때문에 가져오고
 * 0이라면 윗 칸의 [0] 값과 왼 칸의 [1] 값을 더해서 MOD 연산을 해서 값을 저장하면 되었다.
 * [입력사항]
 * [출력사항]
 */
//프로그래머스 <2017 카카오코드 예선> '보행자 탈출'

public class Programmers1832 {
    final int MOD = 20170805;

    public int solution(int m, int n, int[][] cityMap) {
        final int LEFT = 0;
        final int UP = 1;

        final int EMPTY = 0;
        final int WALL = 1;

        int [][][] dp = new int[m][n][2];

        //가로 첫 행 세팅
        for(int i=0;i<n;i++){
            if(cityMap[0][i] == WALL){
                break;
            }
            dp[0][i][LEFT] = 1;
        }

        //세로 첫 열 세팅
        for(int i=0;i<n;i++){
            if(cityMap[i][0] == WALL){
                break;
            }
            dp[i][0][UP] = 1;
        }

        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                if(cityMap[i][j] == WALL){
                    continue;
                }

                int left = dp[i][j-1][LEFT];

                if(cityMap[i][j-1] == EMPTY){
                    left += (dp[i][j-1][UP] % MOD);
                    left %= MOD;
                }

                int up = dp[i-1][j][UP];

                if(cityMap[i-1][j] == EMPTY){
                    up += (dp[i-1][j][LEFT] % MOD);
                    up %= MOD;
                }

                dp[i][j][LEFT] = left;
                dp[i][j][UP] = up;
            }
        }

        return (dp[m-1][n-1][LEFT] + dp[m-1][n-1][UP]) % MOD;
    }
}
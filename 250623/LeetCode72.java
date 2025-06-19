/**
 * @author nakhoonchoi
 * @date 2025/06/20
 * @see https://leetcode.com/problems/edit-distance
 * @mem 45.15MB
 * @time 5ms
 * @caution
 * [고려사항]
 * BFS로 모든 경우의 수를 탐방할까 했지만 문자열이기 때문에 경우의 수가 극도로 많아질 것 같아 해법이 아닌듯했다.
 *
 * 고민을 오래해봐도 떠오르지 않아서 해법이 어떤 알고리즘인지 힌트를 얻어서 풀었다.
 * DP 문제라는 힌트를 얻고 감을 잡으려고 했다.
 * dp = new int[word1.length() + 1][word2.length() + 1]; 으로 선언해서
 * dp[A][B]의 의미는 word1의 앞 A글자를 word2의 앞 B글자로 바꾸려면 몇 번 (삭제/치환/추가)를 수행해야하는 지를 나타낸다.
 *
 * 먼저 dp[i][0]과 dp[0][i]는 전부 i로 초기화한다.(i는 각각 word1과 word2의 길이로 탐색)
 *
 * 2중 for으로 dp 2차원 배열을 채우면서
 * 각 자리수별로 dp값을 채운다.
 * word1.charAt(i-1)와 word2.charAt(j-1)이 같다면
 * dp[i][j] = dp[i-1][j-1]
 * 를 대입해서 경우의 수를 그대로 가져와서 현재 글자를 바꾸지 않아도 됨을 나타낸다.
 *
 * 그리고 word1.charAt(i-1)와 word2.charAt(j-1)이 다르다면
 * dp[i-1][j] + 1 (word1의 i문자를 삭제하고, word1의 i-1글자 만큼의 문자를 word2의 j글자 만큼으로 만드는 경우의 수)
 * dp[i][j-1] + 1 (word1의 i문자를 그대로 두고, word1의 i글자 만큼의 문자를 word2의 j-1글자 만큼으로 만드는 경우의 수)
 * dp[i-1][j-1] + 1 (교체의 경우. word1의 i-1글자까지에서 word2의 j-1글자까지의 최적의 경우의 수에서 word1의 i글자를 word2의 j글자로 바꾸는 경우)
 *
 * 위 세 가지 삭제, 삽입, 교체 중 하나의 경우의 수 중 최적의 값으로 dp[i][j]을 넣으면 된다.
 *
 * 마지막으로 dp[word1.length()][word2.length()]를 반환하면 된다.
 *
 * DP 문제가 어려운 이유는, 문제를 막상 받고 어떤 문제일 지 모를 때 이 문제가 DP인지 전혀 인지하지 못한다는 점이다.
 * [입력사항]
 * [출력사항]
 */
//LeetCode <LeetCode> 'Edit Distance'

public class LeetCode72{
    public int minDistance(String word1, String word2){
        int [][] dp = new int[word1.length() + 1][word2.length() + 1];

        for(int i=1;i<=word1.length();i++){
            dp[i][0] = i;
        }
        for(int i=1;i<=word2.length();i++){
            dp[0][i] = i;
        }

        for(int i=1;i<=word1.length();i++){
            for(int j=1;j<=word2.length();j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    dp[i][j] = Integer.min(Integer.min(dp[i-1][j] + 1, dp[i-1][j-1] + 1), dp[i][j-1] + 1);
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }
}
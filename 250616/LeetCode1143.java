/**
 * @author nakhoonchoi
 * @date 2025/06/13
 * @see https://leetcode.com/problems/longest-common-subsequence/description
 * @mem 51.38MB
 * @time 20ms
 * @caution
 * [고려사항]
 * 매 번 생각날 때마다 풀어보는 LCS 알고리즘 문제이지만 생소하다.
 * LCS는 최장 공통 부분 문자열을 의미하고, DP를 이용해서 푸는 대표적인 문제이다.
 *
 * O(N^2)의 시간복잡도 DP 방식으로 문제를 해결했고,
 * 2차원 dp 배열을 선언해서 i와 j 포인터를 둔 뒤에
 * dp[i][j]에 text1의 i번째 문자와 text2의 j번째 문자가 같다면 dp[i-1][j-1] + 1을,
 * 다르다면 max(dp[i-1][j], dp[i][j-1])를 했다.
 *
 * 그 후 dp[text1의 길이][text2의 길이]에는 두 문자열에 대한 LCS 값이 저장되어있을 것이고
 * 해당 값을 반환했다.
 * [입력사항]
 * [출력사항]
 */
//LeetCode <LeetCode> 'Longest Common Subsequence'

public class LeetCode1143 {
    public int longestCommonSubsequence(String text1, String text2) {
        int length1 = text1.length();
        int length2 = text2.length();
        int [][] dp = new int[length1 + 1][length2+1];

        for(int i=1;i<=length1;i++){
            for(int j=1;j<=length2;j++){
                if(text1.charAt(i-1) == text2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Integer.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[length1][length2];
    }
}
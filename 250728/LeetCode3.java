/**
 * @author nakhoonchoi
 * @date 2025/07/24
 * @see https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
 * @mem 44.71MB
 * @time 5ms
 * @caution
 * [고려사항]
 * 헷갈렸던 문제이다. 문자열이 반복되지 않는 가장 긴 부분 문자열의 길이를 구해야하는 줄 알았는데
 * 같은 알파벳이 중복으로 없는 가장 긴 부분 문자열의 길이를 구해야하는 문제였다.
 *
 * 주기가 포함되지 않아야 한다고 해서 어려운 KMP 알고리즘까지 봐야하나 싶었는데
 * 슬라이딩 윈도우와 해시맵으로 문제를 풀 수 있었다.
 *
 * 맵에 각 문자와 문자가 나왔던 인덱스를 저장한 뒤에
 * 현재 문자가 이미 맵에 있는지, 그리고 맵에 있다면 이전에 나왔던 인덱스가 left보다 크다면
 * left를 맵에 있던 인덱스 + 1로 업데이트 시켜준다.(중복 문자를 없애기 위해서)
 *
 * 해시맵에 문자와 인덱스를 관리하도록 저장하는 문제가 유독 어렵다...
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Longest Substring Without Repeating Characters'

public class LeetCode3 {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> chIndexMap = new HashMap<>();
        int maxLen = 0;
        int left = 0;

        for(int right=0;right<s.length();right++){
            char c = s.charAt(right);

            if(chIndexMap.containsKey(c) && left <= chIndexMap.get(c)){
                left = chIndexMap.get(c) + 1;
            }

            maxLen = Math.max(maxLen, right - left + 1);
            chIndexMap.put(c, right);
        }

        return maxLen;
    }
}
/**
 * @author nakhoonchoi
 * @date 2025/04/25
 * @see https://boj.ma/2179
 * @mem 30,068kb
 * @time 260ms
 * @caution
 * [고려사항]
 * 이전에 '트라이' 자료구조 관련 문제를 풀면서 얻었던 힌트를 가지고 문제를 해결했다.
 * prefixMap으로 <String, Integer>로 해서 각 prefix 별 출처 index를 값으로 담았다.
 * 라인 별로 문자열을 입력받아 순회하면서 StringBuilder를 이용해 문자열에서 얻을 수 있는 prefix를 모두 찾았다.
 *
 * prefix가 Map에 없다면 현재 인덱스와 함께 Map에 담아주고,
 * Map에 이미 있다면 prefix의 길이가 이전에 구한 prefix보다 길다면 정답 배열을 갱신해주고,
 * prefix와 현재 prefix의 길이가 같고, prefixMap에서 찾은 index가 현재 인덱스보다 뒤라면 갱신해주었다.
 *
 * for문 외부에서는 순회하면서 찾은 prefix(접두사) 문자열과 접두사를 얻게된 처음의 인덱스를 가지고 계속 비교해주었다.
 *
 * ⚠️ prefix의 길이가 같을 경우에는 사전 순으로 정렬할 필요는 없었고 prefix의 출처 기준으로 비교해서 갱신하면 됐다.
 * 엣지 케이스가 생각보다 많아 어려웠던 문제이다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <문자열> '비슷한 단어'

public class BOJ2179 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Map<String, Integer> prefixMap = new HashMap<>();
        String [] arr = new String[N];

        String prefix = "";
        String [] answerWord = new String[2];
        int index = Integer.MAX_VALUE;

        for (int i=0;i<N;i++) {
            String word = br.readLine();

            StringBuilder sb = new StringBuilder();
            for(int j=0;j<word.length();j++){
                sb.append(word.charAt(j));

                String tempPrefix = sb.toString();
                if(prefixMap.containsKey(tempPrefix)){
                    if(tempPrefix.length() > prefix.length()){
                        prefix = tempPrefix;
                        answerWord[0] = arr[prefixMap.get(tempPrefix)];
                        answerWord[1] = word;
                        index = prefixMap.get(tempPrefix);
                    }else if(tempPrefix.length() == prefix.length()){
                        if(index > prefixMap.get(tempPrefix)) {
                            prefix = tempPrefix;
                            answerWord[0] = arr[prefixMap.get(tempPrefix)];
                            answerWord[1] = word;
                            index = prefixMap.get(tempPrefix);
                        }
                    }
                }else{
                    prefixMap.put(tempPrefix, i);
                }
            }

            arr[i] = word;
        }

        for(int i=0;i<2;i++){
            System.out.println(answerWord[i]);
        }
    }
}
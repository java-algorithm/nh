/**
 * @author nakhoonchoi
 * @date 2025/07/07
 * @see https://leetcode.com/problems/shortest-uncommon-substring-in-an-array/description
 * @mem 46.2MB
 * @time 105ms
 * @caution
 * [고려사항]
 * 일단 완전탐색으로 부분 문자열을 각각 부분 문자열 Set과 제외해야하는 Set에 저장한 뒤에
 * 마지막에 차집합을 이용해서 최적의 답을 구하는 완전탐색 방법은 시간 초과가 발생했다.
 *
 * 우선 HashMap의 key(를 저장하는 Set 자료구조)에 부분 문자열을 저장하고 값에 해당 부분 문자열이 나온 index를 저장하는 방식으로 해결한다.
 * arr을 순회하면서 전체 부분 문자열을 HashMap에 index와 함께 담는다.
 * 만약에 담으면서 이미 다른 index에 있었던 부분 문자열이라면 이미 있는 문자열임을 외부 Set에 담는다.
 *
 * arr 첫 순회와 함께 HashMap과 이미 있는 부분 문자열을 전부 체크했다면
 * 이미 있는 부분 문자열에 대해 key에서 전부 제거해주었다.(다른 index이 이미 있는 부분 문자열이기 때문에 이용할 수 없다.)
 *
 * 남아있는 HashMap의 키와 값(index)은 유효한 부분 문자열 들이기 때문에 index에 따라 각각 배열 형태의 리스트에 부분 문자열을 나누었다.
 * 각 배열에 담긴 리스트를 부분 문자열의 길이, 사전 순에 따라 정렬해서 리스트가 비어있다면 ""을, 리스트에 값이 있다면
 * 0번째 인덱스에 담긴 최적의 부분 문자열을 정답 배열에 담아서 반환한다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Shortest Uncommon Substring in an Array'

public class LeetCode3076{
    public String [] shortestSubstrings(String [] arr){
        String [] answerArr = new String[arr.length];

        Map<String, Integer> substringMap = new HashMap<>();
        Set<String> alreadySubstringSet = new HashSet<>();
        List<String> [] remainSubstringListArr = new ArrayList[arr.length];

        StringBuilder sb;
        for(int i=0;i<arr.length;i++){
            remainSubstringListArr[i] = new ArrayList();

            for(int j=0;j<arr[i].length();j++){
                sb = new StringBuilder();
                for(int k=0;k<arr[i].length() - j;k++){
                    sb.append(arr[i].charAt(j+k));

                    if(substringMap.containsKey(sb.toString()) && substringMap.get(sb.toString()) != i){
                        alreadySubstringSet.add(sb.toString());
                    }

                    substringMap.put(sb.toString(), i);
                }
            }
        }

        for(String alreadySubstring : alreadySubstringSet){
            substringMap.remove(alreadySubstring);
        }

        for(String key : substringMap.keySet()){
            int index = substringMap.get(key);

            remainSubstringListArr[index].add(key);
        }

        for(int i=0;i<arr.length;i++){
            Collections.sort(remainSubstringListArr[i], (o1, o2) -> {
                if(o1.length() == o2.length()){
                    return o1.compareTo(o2);
                }
                return Integer.compare(o1.length(), o2.length());
            });

            if(!remainSubstringListArr[i].isEmpty()) {
                answerArr[i] = remainSubstringListArr[i].get(0);
            }else{
                answerArr[i] = "";
            }
        }

        return answerArr;
    }

    public String [] shortestSubstrings2(String [] arr){
        String [] answerArr = new String[arr.length];

        Set<String> [] substringSetArr = new Set[arr.length];
        Set<String> [] substringExceptSetArr = new Set[arr.length];
        StringBuilder sb;

        for(int i=0;i<arr.length;i++){
            substringSetArr[i] = new TreeSet<>((o1, o2) -> {
                if(o1.length() == o2.length()){
                    return o1.compareTo(o2);
                }
                return Integer.compare(o1.length(), o2.length());
            });

            substringExceptSetArr[i] = new HashSet<>();
        }

        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length();j++){
                sb = new StringBuilder();
                for(int k=0;k<arr[i].length() - j;k++){
                    sb.append(arr[i].charAt(j+k));
                    substringSetArr[i].add(sb.toString());

                    for(int m=0;m<arr.length;m++){
                        if(m != i){
                            substringExceptSetArr[m].add(sb.toString());
                        }
                    }
                }
            }
        }

        for(int i=0;i<arr.length;i++){
            substringSetArr[i].removeAll(substringExceptSetArr[i]);

            if(substringSetArr[i].isEmpty()){
                answerArr[i] = "";
            }else{
                for(String str : substringSetArr[i]){
                    answerArr[i] = str;
                    break;
                }
            }
        }


        return answerArr;
    }
}
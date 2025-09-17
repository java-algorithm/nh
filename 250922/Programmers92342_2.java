/**
 * @author nakhoonchoi
 * @date 2025/09/17
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/92342
 * @caution
 * [고려사항]
 * 💡 IDE를 이용하지 않고 문제를 풀었다.(프로그래머스 웹 버전에서)
 * 다만 Comparator 구현과 String compare 구현에서 어려웠다.
 *
 * 배열에 저장되는 값은 10점부터 0점 순으로 화살이 몇 개 쏘였는지를 나타낸다.
 * 어피치나 라이언에 대한 배열의 값들을 각각 전부 더하면 n이 될 것이다.
 *
 * 우선 재귀로 n개의 화살을 10점부터 0점 과녁에 골고루 배분했다.
 *
 * 이후에 점수와 화살 개수 기반으로 조건대로 대결을 펼쳐서 어피치와 라이언의 점수를 계산했다.
 *
 * 라이언이 어피치보다 점수가 높다면
 * - 차이 중 가장 큰 값인지
 * -- 차이 중 가장 큰 값을 갱신하고
 * -- 기존 answerList를 초기화 후 현재 경우의 수 추가
 * - 기존의 최대 차이와 같은지
 * -- 기존 answerList에 현재 경우의 수를 추가
 *
 * 여기에서 정답이 되는 경우의 수가 여러 가지 있다면 점수가 낮은 화살을 많이 쏜 경우가 정답으로 나와야했다.
 * 하지만 기존 배열은 앞 인덱스가 높은 점수가 마지막 인덱스가 낮은 점수에 대한 값이기 때문에
 * 배열을 StringBuilder에 넣어서 reverse한 상태로 저장했다.
 * "[1, 1, 2, 0, 1, 2, 2, 0, 0, 0, 0]"의 배열은 "]0 ,0 ,0 ,0 ,2 ,2 ,1 ,0 ,2 ,1 ,1["의 형태로 저장되지 않을까 싶다.
 *
 * 이 기준으로 답을 반환할 때 maxGapPoint가 Integer.MIN_VALUE라면 라이언이 이긴 경우가 단 한 번도 없다고 생각하고 {-1}을 반환했다.
 * maxGapPoint 값이 1번 이상 갱신되었고, answerList에 여러 개의 경우의 수가 있다면
 * reverse된 배열들을 사전순 내림차순으로 정렬한 뒤에 0번 인덱스의 값을 다시 reverse해서 원래대로 복구한 뒤
 * 파싱해서 배열에 넣고 반환했다.
 *
 * ⚠️ 이전에 풀었던 문제인데, String을 뒤집거나 하지말고 배열 자체를 비교했다면 성능이 더 좋았을 것 같다.
 * (직관적으로 문자열 뒤집기가 먼저 생각났고 되나 해보고 싶어서 해봤다.)
 *
 * ⚠️ 배열 뒤집기 시에 배열의 값이 한 자리 수가 아니라 두 자리 수(10)여서 뒤집을 때 01이 되어 실패할 수도 있겠다는 생각이 들었는데,
 * TC 중에 정답인 경우의 수가 배열의 값이 10인(특정 과녁에 몰빵된 경우) 경우가 없나보다.
 * -> TC가 추가된다면 틀릴 수도 있겠다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2022 KAKAO BLIND RECRUITMENT> '양궁대회'

public class Programmers92342_2 {
    int [] apeechInfo; //어피치가 맞힌 화살 개수
    int [] ryanInfo = new int[11]; //라이언이 맞힌 화살 개수(순열 대상)
    List<String> answerList;
    int maxGapPoint;

    public int[] solution(int n, int[] info) {
        apeechInfo = info;
        answerList = new ArrayList<>();
        maxGapPoint = Integer.MIN_VALUE;

        perm(n, 0);

        if(maxGapPoint == Integer.MIN_VALUE){
            return new int[]{-1};
        }else{
            Collections.sort(answerList, new Comparator<>(){
                public int compare(String s1, String s2){
                    return s1.compareTo(s2) * -1;
                }
            });

            String answerStr = new StringBuilder(answerList.get(0)).reverse().toString();
            answerStr = answerStr.replace("[","");
            answerStr = answerStr.replace("]","");

            String [] input = answerStr.split(", ");

            int [] answer = new int[11];

            for(int i=0;i<=10;i++){
                answer[i] = Integer.parseInt(input[i]);
            }

            return answer;
        }

    }

    public void perm(int count, int cur){
        if(count == 0){
            //점수 계산
            fight();
            return;
        }

        for(int i=cur;i<=10;i++){
            for(int j=count;j>=0;j--){
                ryanInfo[i] = j;
                perm(count - j, i + 1);
            }
        }
    }

    public void fight(){
        int apeechPoint = 0;
        int ryanPoint = 0;

        for(int i=0;i<=10;i++){
            if(apeechInfo[i] == 0 && ryanInfo[i] == 0){
                continue;
            }

            if(apeechInfo[i] >= ryanInfo[i]){
                apeechPoint += (10 - i);
            }else{
                ryanPoint += (10 - i);
            }
        }

        if(ryanPoint > apeechPoint){
            if(ryanPoint - apeechPoint > maxGapPoint){
                maxGapPoint = ryanPoint - apeechPoint;
                answerList = new ArrayList<>();
                answerList.add(new StringBuilder(Arrays.toString(ryanInfo)).reverse().toString());
            }else if(ryanPoint - apeechPoint == maxGapPoint){
                answerList.add(new StringBuilder(Arrays.toString(ryanInfo)).reverse().toString());
            }
        }
    }
}
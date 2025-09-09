/**
 * @author nakhoonchoi
 * @date 2025/09/10
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/150368
 * @caution
 * [고려사항]
 * 각 할인율 케이스 별로 이모티콘 플러스 판매 count와 매출액 기준으로 정렬해서 풀 수 있는 문제였다.
 * 이모티콘의 개수가 최대 7개이고 할인율이 10, 20, 30, 40의 경우의 수만 가능했기 때문에
 * 7^4 = 2401로 완전탐색을 진행해도 충분할 것이라고 생각했다.
 *
 * temp 배열에 각 이모티콘에 대한 할인율을 중복 허용하는 조합을 통해 지정하고,
 * 할인율 모든 경우에 대해 이모티콘 플러스 판매 count와 매출액을 산정한 다음에
 * caseList에 담아서 우선순위 대로 (판매 count 우선, 매출액 후순위로) 정렬했다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2023 KAKAO BLIND RECRUITMENT> '이모티콘 할인행사'

public class Programmers150368 {
    int [] temp;
    int [] emoticonInfo;
    int [][] userInfo;
    List<Case> caseList;

    public int[] solution(int[][] users, int[] emoticons) {
        temp = new int[emoticons.length];
        emoticonInfo = new int[emoticons.length];
        userInfo = users;
        caseList = new ArrayList<>();

        for(int i=0;i<emoticons.length;i++){
            emoticonInfo[i] = emoticons[i] / 100;
        }

        comb(0);

        Collections.sort(caseList);

        int[] answer = new int[2];
        answer[0] = caseList.get(0).purchaseCount;
        answer[1] = caseList.get(0).total;

        return answer;
    }

    public void calculate(){
        int count = 0;
        int total = 0;

        for(int [] user : userInfo){
            int curTotal = 0;
            int ratio = user[0];
            int standard = user[1];

            for(int i=0;i<emoticonInfo.length;i++){
                if(temp[i] >= ratio){
                    curTotal += (emoticonInfo[i] * (100 - temp[i]));
                }
            }

            if(curTotal >= standard){
                curTotal = 0;
                count++;
            }

            total += curTotal;
        }

        caseList.add(new Case(count, total));
    }

    public void comb(int count){
        if(count == temp.length){
            //이모티콘 조합 실행
            calculate();
            return;
        }

        for(int i=1;i<=4;i++){
            temp[count] = i * 10;
            comb(count + 1);
        }
    }

    class Case implements Comparable<Case>{
        int purchaseCount;
        int total;

        Case(int purchaseCount, int total){
            this.purchaseCount = purchaseCount;
            this.total = total;
        }

        public int compareTo(Case o){
            if(this.purchaseCount == o.purchaseCount){
                return Integer.compare(this.total, o.total) * -1;
            }

            return Integer.compare(this.purchaseCount, o.purchaseCount) * -1;
        }
    }
}
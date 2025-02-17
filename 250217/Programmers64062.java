/**
 * @author nakhoonchoi
 * @date 2025/02/17
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/64062
 * @caution
 * [고려사항]
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2019 카카오 개발자 겨울 인턴십> '징검다리 건너기'

public class Programmers64062 {

    public int solution(int[] stones, int k) {
        int answer = 0;

        int start = 1;
        int end = Arrays.stream(stones).max().getAsInt();

        while(start <= end){
            int mid = start + (end - start) / 2;

            int gapResult = calculateGap(stones, mid);

            if(gapResult > k){
                end = mid - 1;
            }else if(gapResult <= k){
                answer = Math.max(answer, mid);
                start = mid + 1;
            }
        }

        return answer;
    }

    public int calculateGap(int [] stones, int target){
        int maxGap = 0;
        int gap = 0;

        for(int i=0;i<stones.length;i++){
            if(stones[i] < target){
                gap++;
            }else{
                maxGap = Math.max(maxGap, gap);
                gap = 0;
            }
        }

        maxGap = Math.max(maxGap, gap);

        return maxGap + 1;
    }
}
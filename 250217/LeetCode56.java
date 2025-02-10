/**
 * @author nakhoonchoi
 * @date 2025/02/11
 * @see https://leetcode.com/problems/merge-intervals/description/
 * @mem 46.63MB
 * @time 10ms
 * @caution
 * [고려사항]
 * {1,2}와 같은 int[2] 의 배열의 0번 인덱스를 기반으로 정렬을 하고
 * rightIndex를 최댓값 갱신해서 반영하면 되는 문제였다.
 * List<int []>를 int [][]로 변환하는 과정이 어려웠다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Merge Intervals'

public class LeetCode56 {
    public static void main(String[] args) {
        int [][] arr = {{1,2}, {3,4}, {1,10}};

        int [][] result = merge(arr);
        for(int i=0;i<result.length;i++){
            System.out.println(Arrays.toString(result[i]));
        }
    }

    public static int[][] merge(int[][] intervals) {
        List<int []> answerList = new ArrayList<>();

        if(intervals.length == 1){
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        int leftStandard = intervals[0][0];
        int rightStandard = intervals[0][1];
        for(int i=1;i<intervals.length;i++){
            if(intervals[i][0] <= rightStandard){
                rightStandard = Math.max(rightStandard, intervals[i][1]);
            }else{
                answerList.add(new int[]{leftStandard, rightStandard});
                leftStandard = intervals[i][0];
                rightStandard = intervals[i][1];
            }
        }
        answerList.add(new int[]{leftStandard, rightStandard});

        return answerList.toArray(new int[0][]);
    }
}
/**
 * @author nakhoonchoi
 * @date 2025/03/10
 * @caution
 * [고려사항]
 * 이 문제는 힌트를 얻어서 푼 문제다.
 * 가장 연속적인 부분을 만족해야하는 LIS 문제인 줄 알고 고려할 게 많다고 생각해서 못 풀었는데
 *
 * 정렬 + 슬라이딩 윈도우 문제였다. 정렬을 하면 안된다고 생각했는데 정렬해야 효율적으로 풀 수 있는 문제였다.
 * 두 수가 만족하는 지 확인하는 isOk 메소드를 만들어서 포함되는 지 판별했고
 * right 포인터를 0부터 리스트의 size까지 모두 고려했다.
 * 현재 right 포인터 기준으로 left 포인터의 값이 포함이 안된다면 left 포인터를 ++ 해주었다.
 * right 포인터를 순회할 때마다 최장 길이를 구했다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//'Find Server'

public class FindServer {
    public int [] solution(int [][] delays, int[] limits) {
        int n = delays.length; //참가자의 수
        int m = delays[0].length; //서버의 수
        int [] answer = new int[2];
        int delayRatioLimit = limits[0];
        int delayGapLimit = limits[1];
        int maxParticipants = -1;
        int bestServerIndex = 0;

        for(int i=0;i<m;i++){
            List<Integer> serverDelays = new ArrayList<>();

            for(int j=0;j<n;j++){
                serverDelays.add(delays[j][i]);
            }

            Collections.sort(serverDelays);

            int left = 0;
            for (int right = 0; right < serverDelays.size(); right++) {
                while (!isOk(serverDelays.get(left), serverDelays.get(right), delayRatioLimit, delayGapLimit)) {
                    left++;
                }

                int count = right - left + 1;
                if (count > maxParticipants) {
                    maxParticipants = count;
                    bestServerIndex = i + 1;
                }
            }
        }
        answer[0] = maxParticipants;
        answer[1] = bestServerIndex;

        return answer;
    }

    public boolean isOk(int num1, int num2, int delayRatioLimit, int delayGapLimit){
        int max = Math.max(num1, num2);
        int min = Math.min(num1, num2);

        if(max / min >= delayRatioLimit){
            return false;
        }

        if(max - min >= delayGapLimit * 1000){
            return false;
        }

        return true;
    }
}
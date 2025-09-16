/**
 * @author nakhoonchoi
 * @date 2025/09/16
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/92341
 * @caution
 * [고려사항]
 * 💡 IDE를 이용하지 않고 문제를 풀었다.(프로그래머스 웹 버전에서)
 *
 * 이 문제는 예전에 풀었던 문제였다.
 * 다음 날짜로 넘어가는 부분을 처리하지 않아도 되기 때문에 시간 계산 문제 치고 쉬웠다고 생각한다.
 * HH:MM의 문자열 형태를 int형 숫자로 변환했다.
 * 위 문자열 중(HH * 60 + MM) 값을 시간으로 관리했다. (23:59는 1439가 될 것이다.)
 *
 * 그리고 각 차번호의 입차시간을 관리할 inTimeMap,
 * 하루 중 총 주차 시간을 관리할 totalTimeMap,
 * 차 번호 별 요금을 차 번호 기준으로 정렬할 feeMap으로 총 세 개의 Map으로 관리했다.
 *
 * 문제 내에서 예외가 생길 수 있는 부분이 다수 있었다.
 * 이상한 시간이 들어온다거나, 동일한 차번호의 차가 두 번 입차된다거나
 * 동일한 시간에 같은 차 번호에 대한 내용이 두 번 이상 나타나지 않고, 마지막 시각에 차가 입차되는 경우는 없다.
 * 입차되지 않은 차가 출차하는 경우는 없다고 했으니 예외 처리하지않아도 되었다.
 *
 * ⚠️ TMI로 실제 겪고 있는 '주차'와 계산에 대한 문제라 공감이 많이 됐다...
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2022 KAKAO BLIND RECRUITMENT> '주차 요금 계산'

public class Programmers92341_2 {
    Map<String, Integer> inTimeMap;
    Map<String, Integer> totalTimeMap;
    Map<String, Integer> feeMap;
    final int lastTime = timeToInt("23:59");

    public int[] solution(int[] fees, String[] records) {
        inTimeMap = new HashMap<>();
        totalTimeMap = new HashMap<>();
        feeMap = new TreeMap<>();

        for(String record : records){
            String [] input = record.split(" ");

            int time = timeToInt(input[0]);
            String carNum = input[1];
            boolean isIn = input[2].equals("IN");

            if(isIn){
                inTimeMap.put(carNum, time);
            }else{
                int inTime = inTimeMap.get(carNum);

                totalTimeMap.put(carNum, totalTimeMap.getOrDefault(carNum, 0) + (time - inTime));

                inTimeMap.remove(carNum);
            }
        }

        for(String carNum : inTimeMap.keySet()){
            int inTime = inTimeMap.get(carNum);

            totalTimeMap.put(carNum, totalTimeMap.getOrDefault(carNum, 0) + (lastTime - inTime));
        }

        for(String carNum : totalTimeMap.keySet()){
            feeMap.put(carNum, calFee(fees, totalTimeMap.get(carNum)));
        }

        int[] answer = new int[feeMap.keySet().size()];

        int index = 0;
        for(String carNum : feeMap.keySet()){
            int fee = feeMap.get(carNum);

            answer[index++] = fee;
        }

        return answer;
    }

    public int calFee(int [] fee, int totalTime){
        int baseTime = fee[0];
        int baseFee = fee[1];
        int unitTime = fee[2];
        int unitFee = fee[3];

        int totalFee = 0;

        if(totalTime >= baseTime){
            totalTime -= baseTime;
            totalFee += baseFee;
        }else{
            return baseFee;
        }

        totalFee += ((int) Math.ceil((double) totalTime / unitTime) * unitFee);

        return totalFee;
    }

    public int timeToInt(String time){
        String [] input = time.split(":");

        int total = 0;

        total += Integer.parseInt(input[0]) * 60;
        total += Integer.parseInt(input[1]);

        return total;
    }
}
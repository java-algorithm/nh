/**
 * @author nakhoon
 * @date 2025/03/03
 * @see https://programmers.co.kr/learn/courses/30/lessons/72414
 * @caution
 * [고려사항]
 * 처음에는 총 재생 시간을 계산해서 최대값을 판별하려 했지만 시간 초과가 났다.(모든 logs를 탐방해야하기 때문에 O(N^2)인듯)
 * 이후에 투포인터를 통해 문제를 해결할 수 있었다.
 * (주의 사항 : for 문 부등호를 잘 고려해야 한다. 동영상 재생 시간은 이상, 미만으로 생각해야 한다.)
 * [입력사항]
 * [출력사항]
 */
//프로그래머스 <2021 카카오 공채> '광고 삽입'
public class Programmers72414 {

	public static String solution(String play_time, String adv_time, String[] logs) {
        int playSec = convertTime(play_time);
        int  advSec = convertTime(adv_time);
        int [] seeingCountArr = new int[playSec+1];
        
        for(int i=0;i<logs.length;i++) {
        	String [] timeArr = logs[i].split("-");
        	int startSec = convertTime(timeArr[0]);
        	int endSec = convertTime(timeArr[1]);
        	for(int j=startSec;j<endSec;j++) {
        		seeingCountArr[j] += 1;
        	}
        }
        
        long sum = 0;
        int start = 0;
        for(int i=0;i<advSec;i++) {
        	sum += seeingCountArr[i];
        }
        
        long maxSum = sum;
        
        for(int i=1;i<=playSec - advSec;i++) {
        	sum -= seeingCountArr[i - 1];
        	sum += seeingCountArr[i + advSec - 1];
        	if(sum > maxSum) {
        		maxSum = sum;
        		start = i;
        	}
        }
        return convertTime(start);
    }
	
	public static int convertTime(String time) {
		String [] numStr = time.split(":");
		int timeNum = 0;
		timeNum = (Integer.parseInt(numStr[0]) * 3600) + (Integer.parseInt(numStr[1]) * 60) + Integer.parseInt(numStr[2]);
		return timeNum;
	}
	
	public static String convertTime(int sec) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(sec / 3600 / 10 == 0 ? "0" + sec / 3600 : sec / 3600).append(":");
		sec %= 3600;
		sb.append(sec / 60 / 10 == 0 ? "0" + sec / 60 : sec / 60).append(":");
		sb.append(sec % 60 / 10 == 0 ? "0" + sec % 60 : sec % 60);
		
		return sb.toString();
	}
}
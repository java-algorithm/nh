/**
 * @author nakhoonchoi
 * @date 2025/02/17
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/17678
 * @caution
 * [고려사항]
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2018 Kakao Blind recruitment> '셔틀버스'

public class Programmers17678 {
    public String solution(int n, int t, int m, String[] timetable) {
        int answer = 0;

        PriorityQueue<Integer> waitingLine = new PriorityQueue<>();

        Arrays.stream(timetable).forEach(time -> waitingLine.offer(timeStrToInt(time)));

        int shuttleTime = timeStrToInt("09:00"); // 540
        int passengerTime = 0;

        for(int i=0;i<n-1;i++){ //마지막 버스 전까지 수행
            int passenger = 0;
            //로직 수행
            while(!waitingLine.isEmpty() && waitingLine.peek() <= shuttleTime && passenger < m){
                passengerTime = Math.max(waitingLine.poll(), passengerTime);
                passenger++;
            }

            if(waitingLine.isEmpty() && passenger < m){
                answer = shuttleTime;
            }

            shuttleTime += t;
        }

        //마지막 버스 로직 수행
        int passenger = 0;
        //로직 수행
        while(!waitingLine.isEmpty() && waitingLine.peek() <= shuttleTime && passenger < m){
            passengerTime = Math.max(waitingLine.poll(), passengerTime);
            passenger++;
        }

        if(passenger < m){
            answer = Math.max(shuttleTime, answer);
        }

        if(answer == 0){
            answer = Math.max(passengerTime - 1, answer);
        }

        return timeIntToStr(answer);
    }

    public int timeStrToInt(String time){
        int totalTime;

        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        totalTime = (hour * 60) + minute;

        return totalTime;
    }

    public String timeIntToStr(int time) {
        int hour = time / 60;
        int minute = time % 60;

        return String.format("%02d:%02d", hour, minute);
    }
}
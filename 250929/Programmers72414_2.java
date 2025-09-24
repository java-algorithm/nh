/**
 * @author nakhoonchoi
 * @date 2025/09/24
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/72414
 * @caution
 * [고려사항]
 * 💡 IDE를 이용하지 않고 문제를 풀었다.(프로그래머스 웹 버전에서)
 *
 * 이 문제 이전에 시험장에서 풀었던 문제였다.
 * 그 때와 다시 푼 지금 풀이를 보니 비슷하면서 조금 달랐다.
 *
 * 똑같이 누적합 + 시간 변환으로 문제를 풀었는데,
 * 누적합을 구하는 부분에서 범위를 순회하면서 전부 시청 카운트를 +1 해준 것이 아니라
 * 스터디에서 배운 이모스법(?)으로 한 번에 누적합을 계산해서 효율적으로 누적합 계산을 했다.
 *
 * [0, 0, 0, 0, 0] 배열의 값을 처음부터 끝까지 전부 +1 하려면 for문을 순회하면서 +1 하는 방법이 있지만
 * 배열의 크기를 하나 더 크게 선언해서 [1, 0, 0, 0, 0, -1]로
 * 처음에는 1을 더해주고 마지막 보다 하나 뒤의 인덱스 값은 -1로 깎은 뒤에
 * 누적합을 한다면 [1, 1, 1, 1, 1, 0]으로 원하는 부분만 깔끔하게 누적합을 할 수 있다.
 * (이모스 법을 찾아보면 좋을듯 하다.)
 *
 * 시간은 int 형으로 변환해도 큰 문제가 되지 않았는데,
 * HH:MM:SS가 있을 때 3600 * HH + 60 * MM + SS 형태로 변환해서 관리했고,
 * 반대로 int 형의 시간을 HH:MM:SS 형태로 변환하는 코드도 별도로 사용했다.
 *
 * 그리고 주의할 점은 두 가지가 있었는데,
 * logs 크기가 300,000이었기 때문에 입력된 시청 범위에 따라 누적 시청 시간이 int 형을 벗어나는 경우가 존재했다.
 * 그래서 누적 시청 시간을 계산할 때에는 long 형으로 계산을 해야했다.
 *
 * 그리고 timeToSeeCount로 누적 시청 시간을 관리하도록 count 배열을 작성했는데, 이 의미를 다르게 생각해야했다.
 * timeToSeeCount[1]은 00:00:00~00:00:01 동안 시청된 count를 담는 자리여야한다는 것이다.
 * 시간은 1초라는 단위가 시작 시간과 끝 시간이 별도로 있기 때문에 timeToSeeCount[0]은 존재할 수 없다.
 *
 * 글로 보면 헷갈릴 수 있는데 timeToSeeCount의 의미가
 * 특정 초에 몇 번의 시청 건 수가 있는 지로 관리해야하는 것이 아니라,
 * 특정 초 '구간'에 몇 번의 시청 건 수가 있는 지로 관리해야한다는 점이 주의해야할 점이었다.
 * 구간이라는 개념이 중요했던 문제였다.(맞왜틀이 발생했던 이유였고 아이디어는 금방 떠올렸는데 왜 틀렸는 지 찾기 힘들었다.)
 * [입력사항]
 * [출력사항]
 */
//프로그래머스 <2021 KAKAO BLIND RECRUITMENT> '광고 삽입'

public class Programmers72414_2 {
    public String solution(String play_time, String adv_time, String[] logs) {
        int playTime = timeToInt(play_time);
        int advTime = timeToInt(adv_time);

        int [] timeToSeeCount = new int[playTime + 2];

        for(String log : logs){
            String [] times = log.split("-");

            int [] time = new int[2];

            time[0] = timeToInt(times[0]);
            time[1] = timeToInt(times[1]);

            timeToSeeCount[time[0] + 1]++;
            timeToSeeCount[time[1] + 1]--;
        }

        for(int i=1;i<=playTime + 1;i++){
            timeToSeeCount[i] += timeToSeeCount[i - 1];
        }

        long seeCount = 0;
        for(int i=0;i<advTime;i++){
            seeCount += timeToSeeCount[i];
        }

        long maxSeeCount = seeCount;
        String answer = intToTime(0);

        for(int i=advTime;i<=playTime;i++){
            seeCount -= timeToSeeCount[i - advTime];

            seeCount += timeToSeeCount[i];

            if(seeCount > maxSeeCount){
                answer = intToTime(i - advTime);
                maxSeeCount = seeCount;
            }
        }

        return answer;
    }

    public int timeToInt(String time){
        String [] split = time.split(":");
        int h = Integer.parseInt(split[0]);
        int m = Integer.parseInt(split[1]);
        int s = Integer.parseInt(split[2]);

        return h * 3600 + m * 60 + s;
    }

    public String intToTime(int timeInt){
        StringBuilder sb = new StringBuilder();

        int h = timeInt / 3600;
        int remain = timeInt % 3600;
        int m = remain / 60;
        int s = remain % 60;

        sb.append(String.format("%02d", h)).append(':').append(String.format("%02d", m)).append(':').append(String.format("%02d", s));

        return sb.toString();
    }
}

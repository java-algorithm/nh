/**
 * @author nakhoonchoi
 * @date 2025/08/08
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/12979
 * @caution
 * [고려사항]
 * 근래에 이모스 문제를 많이 풀었어서 그런지 이모스에 매몰되어있었던 것같다.
 * 하지만 이모스의 규칙 덕분에 수식을 얻어서 풀 수 있었다.
 *
 * 문제의 힌트는 "💡 stations는 오름차순으로 정렬되어 있다." 였다.
 *
 * 매 기지국은 정렬되어있기 때문에 stations의 배열 변화 없이 있는 그대로의 배열을 사용했다.
 *
 *
 * 매 기지국의 범위 가장 오른쪽 끝을 저장할 left라는 포인터를 두었다.
 * 기지국 배열을 순회하기 전에는 기지국이 없기 때문에 left를 0으로 초기화시켜주었다.
 *
 * 기지국 배열을 순회하면서
 * left 포인터가 현재 기지국의 왼쪽 끝 범위보다 작다면 소숫점 올림을 통해
 * answer += Math.ceil((double) (station - w - left - 1) / range); 을 해주고
 * left를 기지국의 오른쪽 끝 값으로 대치해주었다.
 *
 * 기지국을 모두 순회하고, 끝처리를 해주어야한다. 마지막 left 값과 n 사이에 기지국이 닿지 않는 범위가 있다면
 * answer += Math.ceil((double) (n - left) / range);
 * 를 통해 마지막 연산을 외부에서 진행해줘야 answer를 얻을 수 있다.
 *
 * N의 크기가 2억이어서 O(N)의 방법이 아니라면 시간 초과가 발생할 것이라고 생각했고,
 * 최적화하는 방식이 쉽게 떠오르지 않았던 문제였다.
 * [입력사항]
 * [출력사항]
 */
//프로그래머스 <Summer/Winter Coding(~2018)> '기지국 설치'

public class Programmers12979 {
    public int solution(int n, int [] stations, int w){
        int answer = 0;
        int range = 2 * w + 1;
        int left = 0;

        for(int station : stations){
            if(left < station - w) {
                answer += Math.ceil((double) (station - w - left - 1) / range);
            }

            left = station + w;
        }

        if(left < n) {
            answer += Math.ceil((double) (n - left) / range);
        }

        return answer;
    }
}
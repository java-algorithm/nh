/**
 * @author nakhoonchoi
 * @date 2025/09/23
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/92335
 * @caution
 * [고려사항]
 * 💡 IDE를 이용하지 않고 문제를 풀었다.(프로그래머스 웹 버전에서)
 *
 * 문제를 간단하게 요약하면
 * n과 k가 주어지는데, 10진수 양수 n을 k진수로 변환한 다음 0을 기준으로 파싱해서
 * 나온 수들을 소수 판정하면 되는 문제였다. (numToStr.split("0")으로 파싱해서 풀어도 맞지 않았을까 싶다.
 *
 * 먼저 Integer.toString(number, radix) 메소드를 이용해서 손쉽게 k진수 n을 구했다.
 *
 * substring을 하기 위해 left를 0으로 설정하고, (Integer.toString 결과는 무조건 첫번째 인덱스에 0이 나오지 않는다.)
 * 그 뒤에 문자열을 순회하면서 0을 만나면 left부터 현재 인덱스까지 substring을 해서 소수인지 확인하고
 * left를 (현재 인덱스 + 1)로 바꿔주었다.
 *
 * 그리고 마지막까지 탐방한 뒤에 left부터 문자열 끝의 인덱스까지 한 번 더 소수 판정을 해주었다.(나머지 체크)
 *
 * 소수 판정은 에라토스테네스의 체 방식으로 진행했고,
 * 소수 판정 대상의 수가 int 형을 벗어날 수 있기 때문에 long 형으로 파싱해서 진행했다.
 *
 * long 형으로 변환해야한다는 점이 주의해야할 점이었고 나머지는 괜찮았다.
 * [입력사항]
 * [출력사항]
 */
//프로그래머스 <2022 KAKAO BLIND RECRUITMENT> 'k진수에서 소수 개수 구하기'

public class Programmers92335_2 {
    public int solution(int n, int k) {
        String numToStr = Integer.toString(n, k);

        int left = 0;
        int answer = 0;

        for(int i=1;i<numToStr.length();i++){
            if(numToStr.charAt(i) == '0'){
                if(isPrime(numToStr.substring(left, i))){
                    answer++;
                }
                left = i+1;
            }
        }

        if(isPrime(numToStr.substring(left, numToStr.length()))){ //numToStr.substring(left)
            answer++;
        }

        return answer;
    }

    public boolean isPrime(String numStr){
        if(numStr.isBlank() || numStr.equals("1")){
            return false;
        }
        long num = Long.parseLong(numStr);

        int sqrt = (int) Math.sqrt(num);

        for(int i=2;i<=sqrt;i++){
            if(num % i == 0){
                return false;
            }
        }

        return true;
    }
}

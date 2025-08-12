/**
 * @author nakhoonchoi
 * @date 2025/08/12
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/12927
 * @caution
 * [고려사항]
 * 남은 작업량 각각의 제곱을 합한 수의 최소를 구해야하기 때문에
 * 작업량을 높은 순부터 내림차순으로 골고루 깎는 것이 좋다고 생각했다.
 *
 * n이 최대 100만 번이기 때문에 값을 수정할 일이 빈번하다고 생각해서
 * 값의 삽입과 삭제에 유리한 우선순위 큐를 이용해서 문제를 해결했다.
 *
 * 내림차순으로 정렬하도록 우선순위 큐를 선언하고 works 배열의 값을 모두 담았다.
 * 그리고 n만큼 순회해서 우선순위 큐 맨 위의 값을 -1 해준 뒤에 다시 넣어주었다.
 * 이 순간에서 고려해야하는 점 두 가지가 pq가 비어있다면 더 이상 계산을 할 필요가 없고,
 * 우선순위 큐에서 나온 값이 1일 경우 작업을 하면 0이 되고 제곱수도 0이기 때문에 우선순위 큐에 다시 넣어줄 필요가 없다.
 *
 * 별개로 works 배열값의 합이 n보다 작다면 윗 작업을 전혀 수행하지 않고 0을 반환하고 끝냈다.(최적화)
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <연습문제> '야근 지수'

public class Programmers12927 {
    public long solution(int n, int[] works) {
        long answer = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1, o2) * -1);

        long sum = 0;
        for(int work : works){
            pq.offer(work);
            sum += work;
        }

        if(sum < n){
            return 0;
        }

        for(int i=0;i<n;i++){
            if(pq.isEmpty()){
                break;
            }

            int work = pq.poll();

            if(work == 1){
                continue;
            }

            pq.offer(work - 1);
        }

        while(!pq.isEmpty()){
            int work = pq.poll();
            answer += ((long) work * work);
        }

        return answer;
    }
}
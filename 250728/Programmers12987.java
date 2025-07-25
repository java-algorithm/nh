/**
 * @author nakhoonchoi
 * @date 2025/07/25
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/12987
 * @caution
 * [고려사항]
 * 프로그래머스는 백준과 다르게 문제 유형이 나와있지 않아서 난이도가 조금 더 높은 것 같다.
 * 실제로 문제를 풀어보니 다양한 방법으로 문제를 풀 수 있었어서 전부 해설해보려고 한다.
 * 모든 해법의 공통점은 A와 B를 정렬한 뒤에 로직을 수행해야한다는 점이다.
 *
 * solution1 - 이분 탐색(가장 먼저 떠올린 방법)
 *  A의 각각 점수보다 큰 수 중 작은 수를 찾아서 최대한 승수를 쌓아야 하기 때문에
 *  lower_bound를 가장 먼저 생각해서 문제를 풀었다.
 * solution2 - 투 포인터
 *  A와 B 배열에 각각 포인터를 둬서 각 포인터에 위치한 값을 계속 비교하면서
 *  승수를 쌓을 수 있는 count를 세주는 방법이다.
 * solution3 - TreeSet의 higher(int target) 메소드
 *  TreeSet의 higher 메소드는 현재 Set에 포함된 수중에 target보다 큰 수 중 가장 작은
 *  (lower_bound) 수를 반환하는 함수이기 때문에 이용하면서 remove를 해주려고 했다.
 *  하지만 Collection과 시간 복잡도에 걸렸는지 효율성 테스트에서 통과하지 못했다.
 * solution4 - 우선순위 큐
 *  B의 수들을 우선순위 큐에 전부 넣어주고, A를 순회하면서 승수를 쌓을 수 있을 때까지
 *  우선순위 큐에서 pop을 해서 최적의 값을 찾아 count하는 식으로 문제를 해결했다.
 *
 * 문제 유형은 '정렬 + 그리디' 라고 생각하면 될 것 같다.
 * 다양한 방법으로 문제를 해결할 수 있어서 재미있는 문제였다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <Summer/Winter Coding(~2018)> '숫자 게임'

public class Programmers12987 {
    final int INVALID = -1;
    public int solution(int[] A, int[] B) {
        Arrays.sort(A);
        Arrays.sort(B);

        int answer = 0;
        int recentlyEnd = 0;

        for(int i=0;i<A.length;i++){
            int index = lowerBound(B, A[i], recentlyEnd);

            if(index == INVALID){
                break;
            }

            if(A[i] < B[index]){
                answer++;
            }

            recentlyEnd = index+1;
        }

        return answer;
    }

    public int lowerBound(int [] B, int target, int recentlyEnd){
        if(recentlyEnd == B.length){
            return INVALID;
        }
        int left = recentlyEnd;
        int right = B.length - 1;

        while(left < right){
            int mid = left + (right - left) / 2;

            if(B[mid] <= target){
                left = mid + 1;
            }else{
                right = mid;
            }
        }

        return left;
    }

    public int solution2(int[] A, int[] B){
        Arrays.sort(A);
        Arrays.sort(B);

        int answer = 0;
        int recentlyEnd = 0;

        outer: for(int a : A) {
            while (a >= B[recentlyEnd]) {
                recentlyEnd++;

                if (recentlyEnd == B.length) {
                    break outer;
                }
            }
            answer++;
            recentlyEnd++;

            if (recentlyEnd == B.length) {
                break;
            }
        }

        return answer;
    }
    public int solution3(int[] A, int[] B){
        Arrays.sort(A);
        Arrays.sort(B);

        int answer = 0;
        TreeSet<Integer> bSet = new TreeSet<>();

        for(int b : B){
            bSet.add(b);
        }

        for(int a : A){
            if(Objects.nonNull(bSet.higher(a))){
                bSet.remove(bSet.higher(a));
                answer++;
            }else{
                bSet.pollFirst();
            }
        }

        return answer;
    }

    public int solution4(int[] A, int[] B){
        Arrays.sort(A);
        Arrays.sort(B);

        int answer = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for(int b : B){
            pq.offer(b);
        }

        for(int a : A){
            while(!pq.isEmpty()){
                if(pq.peek() > a){
                    pq.poll();
                    answer++;
                    break;
                }else{
                    pq.poll();
                }
            }
        }
        return answer;
    }
}
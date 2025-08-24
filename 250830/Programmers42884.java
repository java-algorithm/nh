/**
 * @author nakhoonchoi
 * @date 2025/08/25
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/42884
 * @caution
 * [고려사항]
 * 효율성 테스트도 존재하는 문제였다.
 * 문제 유형은 '그리디'라고 대놓고 나와있었지만 '정렬'로 문제를 해결했다.
 *
 * routes 2차원 배열을 routes[i][0] 기준으로 먼저 오름차순,
 * 같다면 routes[i][1] 기준으로 오름차순으로 정렬했다.
 *
 * 정렬된 routes 배열을 순회하기 전에
 * 각 카메라의 범위를 left와 right로 지정해주려고 했다.
 *
 * routes를 순회하면서
 * routes[i][0]이 left와 right 이상 이하로 사이에 있다면 left를 routes[i][0]으로 대입했고,
 * 위 조건을 만족하면서 routes[i][1]이 right 이하라면 right도 routes[i][1]로 대입했다.
 * (카메라가 체크할 수 있는 범위를 좁힘)
 *
 * 위 조건에 만족하지 않는다면 카메라가 해당 route를 단속할 수 없으므로
 * 새 카메라를 늘리고, 현재 route 정보로 카메라 범위를 갱신해주었다.
 *
 * 마지막까지 카메라 대수를 잘 조절한다면 답을 얻을 수 있었다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <탐욕법(Greedy)> '단속카메라'

public class Programmers42884 {
    public static void main(String[] args) {
        int [][] routes = {{-20,15}, {-14,-5}, {-18,-13}, {-5,-3}};
        System.out.println(solution(routes));
    }

    public static int solution(int[][] routes) {
        Arrays.sort(routes, (o1, o2) -> {
            if(o1[0] == o2[0]){
                return Integer.compare(o1[1], o2[1]);
            }
            return Integer.compare(o1[0], o2[0]);
        });

        int left = routes[0][0];
        int right = routes[0][1];
        int answer = 1;

        for(int i=1;i<routes.length;i++){
            if(routes[i][0] >= left && routes[i][0] <= right){
                left = routes[i][0];
                if(routes[i][1] <= right){
                    right = routes[i][1];
                }
            }else{
                answer++;
                left = routes[i][0];
                right = routes[i][1];
            }
        }
        return answer;
    }
}
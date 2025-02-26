/**
 * @author nakhoonchoi
 * @date 2025/02/26
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/64063
 * @caution
 * [고려사항]
 * 처음에 이분탐색으로 접근했는데, 중간의 방이 차있더라도 앞의 번호 방이 차있을 지 아닐 지 모르기 때문에 이분탐색 문제는 아니었다.
 * (현재 방, 비어있는 방)의 형태로 저장할 맵을 선언한다.
 * while 반복문 안에서 맵을 재귀적으로 탐색하며 비어있는 방을 찾는다. - (1)
 * 탐색하면서 경로를 저장하고, 저장된 경로만큼 모든 키 값에 비어있는 호텔방 + 1(무조건 비어있을 방)으로 갱신 및 저장한다. - (2)
 * 그리고 (1) 로직에서 반환한 비어있는 방을 정답 배열의 값으로 넣어준다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2019 카카오 개발자 겨울 인턴십> '호텔방 배정'

public class Programmers64063 {
    static Map<Long, Long> nextRoomMap;
    public static void main(String[] args) {
        long k = 10;
        long [] room_number = {1,3,4,1,3,1};

        System.out.println(Arrays.toString(solution(k, room_number)));
    }

    public static long[] solution(long k, long[] room_number) {
        nextRoomMap = new HashMap<>();
        long[] answer = new long[room_number.length];
        for(int i=0;i<room_number.length;i++){
            List<Long> updateTargetList = new ArrayList<>();
            long enterRoom = room_number[i];

            while(nextRoomMap.containsKey(enterRoom)){
                updateTargetList.add(enterRoom);
                enterRoom = nextRoomMap.get(enterRoom);
            }
            updateTargetList.add(enterRoom);

            for(long updateTarget : updateTargetList){
                nextRoomMap.put(updateTarget, enterRoom + 1);
            }

            answer[i] = enterRoom;
        }
        return answer;
    }
}
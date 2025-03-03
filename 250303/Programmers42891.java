/**
 * @author nakhoonchoi
 * @date 2025/03/02
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/42891
 * @caution
 * [고려사항]
 * food 객체를 만들어서 리스트에 넣고 우선 시간 순서대로 정렬한다.
 * 현재 음식을 먹는데에 필요한 시간에서 남은 음식 종류의 개수만큼 곱해서 k에서 곱한 값을 빼준다.
 * 현재 음식을 먹는데에 필요한 시간이 K보다 크면 네트워크 장애가 발생한다는 의미이므로 현재 리스트 기준에서 k를 size로 나눈 나머지를 먼저 계산해둔다.
 * 리스트를 subList로 자른 뒤에 index 기준으로 정렬하고 k번째 인덱스 값을 return 하면 된다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2019 Kakao Blind Recruitment> '무지의 먹방 라이브'

public class Programmers42891 {
    public static void main(String[] args) {
        int [] food_times = {3,1,2};
        int k = 5;
        System.out.println(solution(food_times, k));
    }

    public static int solution(int[] food_times, long k) {
        List<Food> foodList = new LinkedList<>();
        for(int i=0;i<food_times.length;i++){
            foodList.add(new Food(i+1, food_times[i]));
        }

        Collections.sort(foodList);

        int size = foodList.size();
        int currentTime = 0;
        int standardIndex = 0;
        for(Food food : foodList){
            long remainTime = food.time - currentTime;
            if(remainTime != 0){
                long spend = remainTime * size;
                if(spend <= k){
                    k -= spend;
                    currentTime = food.time;
                }else{
                    k %= size;
                    foodList.subList(standardIndex, food_times.length).sort(Comparator.comparingInt(o -> o.index));
                    return foodList.get((int)k).index;
                }
            }
            standardIndex++;
            size--;
        }
        return -1;
    }

    static class Food implements Comparable<Food>{
        int index;
        int time;

        Food(int index, int time){
            this.index = index;
            this.time = time;
        }

        @Override
        public int compareTo(Food o) {
            return Integer.compare(this.time, o.time);
        }
    }
}
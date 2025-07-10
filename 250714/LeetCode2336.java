/**
 * @author nakhoonchoi
 * @date 2025/07/10
 * @see https://leetcode.com/problems/smallest-number-in-infinite-set/description/
 * @mem 45.55MB
 * @time 31ms
 * @caution
 * [고려사항]
 * 문제의 이름 답게 Set을 이용해서 문제를 해결했다.
 * 실제 클래스에는 무한의 원소를 담고 있는 Set을 보여야하지만 반대로 Set에 불가능한 숫자들을 담아주었다.
 * 별도로 현재 Set에서 반환할 수 있는 최솟값을 담는 curMin 필드 값으로 관리했다.
 * 필자는 nextEnableNum 메소드를 통해서 popSmallest 호출 시 curMin 값을 갱신하도록 메소드를 별도로 만들었는데,
 * 다른 풀이를 참고하니 TreeSet 자체의 pollFirst를 이용해서 풀이한 해법이 있었다.
 * TreeSet 자체를 Queue처럼 쓸 수 있다는 것을 알았다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Smallest Number in Infinite Set'

public class LeetCode2336 {
    class SmallestInfiniteSet{
        Set<Integer> disableNumSet;
        int curMin;

        public SmallestInfiniteSet(){
            disableNumSet = new TreeSet<>();
            curMin = 1;
        }

        public int popSmallest(){
            int returnValue = curMin;

            disableNumSet.add(curMin);
            curMin = nextEnableNum(curMin + 1);

            return returnValue;
        }

        public void addBack(int num){
            disableNumSet.remove(num);

            curMin = Integer.min(num, curMin);
        }

        public int nextEnableNum(int num){
            while(true){
                if(!disableNumSet.contains(num)){
                    return num;
                }
                num++;
            }
        }
    }

    class SmallestInfiniteSet2 {
        Set<Integer> set = new TreeSet<>();
        int cur = 1;
        public SmallestInfiniteSet2(){

        }

        public int popSmallest(){
            if (!set.isEmpty()) {
                return ((TreeSet<Integer>) set).pollFirst();
            }
            return cur++;
        }

        public void addBack(int num){
            if(num < cur){
                set.add(num);
            }
        }
    }
}
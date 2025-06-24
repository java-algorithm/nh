/**
 * @author nakhoonchoi
 * @date 2025/06/24
 * @see https://leetcode.com/problems/lru-cache/description
 * @mem 111.88MB
 * @time 44ms
 * @caution
 * [고려사항]
 * LRU Cache의 생성자와 put, get 메소드를 구현해야하는 문제였다.
 *
 * 실제 LeetCode의 출제자의 목적은 util 클래스를 사용하지 않고
 * LRU Cache를 직접 구현하길 원했을 수도 있다.
 * 하지만 먼저 LinkedHashMap을 이용해서 LRU Cache를 구현해봤다.
 *
 * 우선 LinkedHashMap을 이용해서 LRU Cache를 구현하기 위해 필요한 변수와 메소드에 대해서 간략하게 설명한다.
 *
 * - initialCapacity : LinkedHashMap 내부 해시 테이블(배열)의 초기 버킷 수
 *
 * - loadFactor : 버킷의 재해싱(rehashing)이 일어나기 위한 임계점을 위한 변수
 *              (capacity * loadFactor)만큼 엔트리가 쌓이면 해시 테이블 크기가 두 배로 리사이징(resizing) 된다.
 *
 * - MAX_ENTRIES : LinkedHashMap 내의 최대 엔트리(Entry) 갯수
 *
 * - accessOrder(boolean) : true라면 접근 기준으로 key가 정렬되고, false라면 삽입 기준으로 key가 정렬된다.
 *
 * - removeEldestEntry 메소드 : : AccessOrder 기반으로 Least Recently(가장 오랫동안 사용되지 않은)한 Entry를 제거할 때 정의할 메소드이다.
 *           LinkedHashMap은 조건 없이 false를 반환하지만 재정의하려면 상속해야한다.
 *           return size() > MAX_ENTRIES; 를 반환하면 된다고 LinkedHashMap.java 479번째 줄에 적혀져 있다.
 *
 * 우선 위의 변수와 메소드를 가지고 LinkedHashMap을 상속받아서 LRU Cache를 구현하려면,
 * LinkedHashMap을 상속받은 객체를 생성할 때(removeEldestEntry를 재정의하기 위해 상속받은 객체를 선언해야한다.)
 * MAX_ENTRIES에 capacity를 담으면서 부모 클래스 생성자에서 accessOrder를 true로하고, loadFactor는 디폴트 값으로 선언한다.
 * 그리고 Entry를 accessOrder 조건에 맞게 remove하기 위해 removeEldestEntry 메소드를 재정의한다.
 *
 * get할 때 key가 없다면 기존에는 NPE가 발생하는데, 이 문제에서는 -1을 반환해야하기 때문에
 * LinkedHashMap에서 get할 때 반환값이 null이라면 -1을 반환하도록 분기처리를 해주었다.
 *
 * 추후에(?) LinkedList를 이용해서 LRU Cache 자체도 직접 구현해봐야겠다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'LRU Cache'

public class LeetCode146 {
    class LRUCache{
        Map<Integer, Integer> cacheMap;
        public LRUCache(int capacity) {
            cacheMap = new LRUCacheMap<>(capacity, true);
        }

        public int get(int key) {
            return Objects.isNull(cacheMap.get(key)) ? -1 : cacheMap.get(key);
        }

        public void put(int key, int value) {
            cacheMap.put(key, value);
        }

        class LRUCacheMap<K, V> extends LinkedHashMap<K, V>{
            private int MAX_ENTRIES;
            public LRUCacheMap(int initialCapacity, boolean accessOrder){
                super(initialCapacity, 0.75f, accessOrder);
                MAX_ENTRIES = initialCapacity;
            }

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > MAX_ENTRIES;
            }
        }
    }

//    public static void main(String[] args) {
//        LRUCache cache = new LRUCache(2);
//        cache.put(1, 1);
//        cache.put(2, 2);
//        System.out.println(cache.get(1));
//        cache.put(3, 3);
//        System.out.println(cache.get(2));
//        cache.put(4, 4);
//        System.out.println(cache.get(1));
//        System.out.println(cache.get(3));
//        System.out.println(cache.get(4));
//    }
}
/**
 * @author nakhoonchoi
 * @date 2025/03/16
 * @see https://boj.ma/16566
 * @mem 524,396kb
 * @time 2,736ms
 * @caution
 * [고려사항]
 * 문제를 보고 바로 이해가 되지 않았다.
 * 원래는 카드가 전체 N개가 있고 철수와 민수가 그 중에 M개 만큼 뽑는다.
 * 하지만 철수는 카드를 기존에 썼던 카드를 가져오거나, 안가져왔던 카드를 가져올 수 있다.
 * 먼저 철수가 카드를 내는데, 민수는 철수가 낸 카드의 숫자보다 큰 수들 중 가장 작으면서 안 썼던 카드를 찾아서 반환해야한다.
 * 특정 숫자보다 큰 수들 중 가장 작은 수를 뽑아내야하므로 이분 탐색과 관련된 문제라는 것을 바로 알았다.
 * 하지만 한 번 쓴 수의 카드를 다시는 쓸 수 없기 때문에 갱신하는 과정이 필요했다.
 * 이 부분을 Map으로 구현했다.
 * (N이 최대가 400만이기 때문에 NlogN의 알고리즘을 이용해야 1억으로 간당간당하게 통과할 것 같았다.)
 *
 * 이분탐색을 위해 chooseCard 배열을 정렬을 했다.
 * Map에는 각 chooseCard 의 수를 key로 하고, 그 수를 그대로 값으로 하도록 초기화했다.
 *
 * 우선 이분 탐색을 이용해서 철수가 낸 카드보다 큰 수들 중 가장 작은 수를 upperBound를 통해 찾는다.
 * 이 때 left와 right의 기준은 chooseCard 배열 중에서 최적의 카드를 찾아야하기 때문에 0, M-1로 두고 upperBound를 구현했다.
 * answer에 target보다 큰 수들을 계속 대입해서 최적화시켰고, chooseCard 배열에 target과 같은 값의 카드가 이미 있다면 그 다음 인덱스의 수를 반환했다.
 *
 * upperBound를 통해 찾은 수는 철수가 낸 카드 target보다 크면서 그들 중 가장 작은 카드 뭉치의 수다.
 * 이 카드 숫자가 유효한 지 확인하기 위해 getNextCard에서 다음 카드 숫자를 가져온다.
 * 초기화된 수 그대로라면 그대로를 반환하고, 아니라면 union-find의 루트 노드 갱신 과정 처럼 최대한 끝의 다음 카드를 찾아서 반환한다.
 *
 * 다음 유효한(철수가 낸 카드보다 큰 수들 중 가장 작으며 사용된 적 없는)카드를 출력하고, union 작업을 해야한다.
 * 이 때 union 작업은 chooseCard 배열 중 가장 큰 값이 아니라면 진행한다.
 * 문제에서 민수가 카드를 낼 수 없는 일은 없다고 했고, 이 말은 chooseCard의 최댓값을 두 번 방문할 일은 없다는 의미였다고 생각했다.
 * 철수가 낸 카드가 chooseCard의 최댓값보다 높은 카드를 요구한다면, 민수는 카드를 낼 수 없을 것이다.
 * 그리고 union 메소드가 사용된 카드와 사용된 카드보다 큰 수들 중 가장 작은(upperBound) 수를 이용해야 하기 때문에
 * chooseCard의 최댓값은 upperBound가 될 수 없어서 union 되면 안된다.
 *
 * union을 통해 사용된 카드의 다음 카드를 upperBound된 수의 다음 카드로 갱신한다.
 * 
 *
 *
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <분리 집합> '카드 게임'

public class BOJ16566 {
    static int N, M, K;
    static int [] chooseCard;
    static Map<Integer, Integer> nextCardMap = new TreeMap<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        chooseCard = new int[M];

        st = new StringTokenizer(br.readLine());
        for(int i=0;i<M;i++){
            chooseCard[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(chooseCard);

        init();

        st = new StringTokenizer(br.readLine());
        for(int i=0;i<K;i++){
            int target = Integer.parseInt(st.nextToken());
            int pickCard = upperBound(target); //현재 타겟보다 큰 수들 중 가장 작은 카드의 수를 찾는다.
            int nextBiggerCard = getNextCard(pickCard); //upperBound로 찾은 수의 다음 유효한 카드를 구한다.

            System.out.println(nextBiggerCard);

            if(nextBiggerCard != chooseCard[M-1]){
                //사용된 카드와 사용된 카드보다 큰 수들 중 가장 작은 수를 union
                union(nextBiggerCard, upperBound(nextBiggerCard));
            }
        }
    }

    /**
     * nextCardMap 초기화 함수
     */
    public static void init(){
        for(int i=0;i<M;i++){
            nextCardMap.put(chooseCard[i], chooseCard[i]);
        }
    }

    /**
     * @param target upperBound된 카드의 숫자
     * @return nextCardMap에 저장된 다음 카드를 갱신하면서 반환한다.
     */
    public static int getNextCard(int target){
        if(nextCardMap.get(target) == target){
            return target;
        }
        int nextCard = nextCardMap.get(target);
        if(nextCardMap.containsKey(nextCard)){
            nextCard = nextCardMap.get(nextCard);
        }
        nextCardMap.put(target, nextCard);
        return nextCardMap.get(target);
    }

    /**
     * 현재 사용된 카드(useCard)의 다음 카드 수를 useCard의 현재 사용된 카드의 upperBound 된 수로 갱신한다.
     * @param useCard 현재 사용된 카드
     * @param biggerCard 현재 사용된 카드의 upperBound 된 수
     */
    public static void union(int useCard, int biggerCard){
        int parentA = getNextCard(useCard);
        int parentB = getNextCard(biggerCard);

        if(parentA != parentB) {
            nextCardMap.put(useCard, parentB);
        }
    }

    /**
     * 이분탐색을 이용한 target보다 큰 수들 중 가장 작은 수 찾기
     * @param target target
     * @return target보다 큰 수들 중 가장 작은 수
     */
    public static int upperBound(int target){
        int left = 0;
        int right = M-1;
        int answer = target;

        while(left <= right){
            int mid = left + (right - left) / 2;

            if(chooseCard[mid] > target){
                answer = chooseCard[mid];
                right = mid - 1;
            }else if(chooseCard[mid] < target){
                left = mid + 1;
            }else{
                answer = chooseCard[mid + 1];
                break;
            }
        }
        return answer;
    }
}
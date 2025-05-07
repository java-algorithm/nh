/**
 * @author nakhoonchoi
 * @date 2025/05/07
 * @see https://boj.ma/6086
 * @mem 14,384kb
 * @time 112ms
 * @caution
 * [고려사항]
 * 한 번에 내용이 이해되지 않아서 어려웠던 문제다.
 * 정답을 참고하진 않았지만 개념은 블로그를 참고했다. https://iknoom.tistory.com/13
 *
 * 입력 받은 모든 배수관은 양방향으로 흐를 수 있고, 알파벳의 순서는 방향과 전혀 관련이 없으며, [a-zA-Z]의 문자열로 위치가 정해져있다.
 * 문제처럼 파이프를 합쳐서 같은 방향의 파이프가 두 개라면 유량을 합치는 식으로 생각하니 어려웠다.
 *
 * 그래서 처음에 입력 받을 때 빼고는 파이프를 합산하지 않고 별개의 파이프로 봤다.
 * 대신에 아래와 같은 파이프가 들어온다면 답을 3을 출력하도록 입력 받을 때 capacity Map은 합산해서 입력받았다.
 * 2
 * A Z 1
 * A Z 2
 *
 * 결론은 A에서 Z 방향으로 유량이 최대한 얼만큼 흐를 수 있는지 DFS를 이용해서 구했다.
 * 입력 받으면서 파이프의 크기 정보를 저장할 맵(capacityMap)과 현재 유량 흐름 정보를 저장할 맵(flowMap)을 초기화했다.
 * 그리고 a와 b, 파이프의 크기를 입력 받아서 크기 맵(capacityMap)에 양방향으로 넣어주었다.
 *
 * DFS를 이용해서 A에서 가능한 파이프 탐색을 하면서 유량을 가능한 방향으로 흘려서
 * Z에 도달했다면 지난 파이프 중 최소의 크기를 출력하고 flowMap에 흐름을 반영해주었다.
 * 여기에서 흐름을 반영할 때 a -> b 방향으로 3만큼 흘렸다면
 * 반대로 b -> a 방향으로 -3을 흘렸다고 반영했다.
 * dfs 함수 값이 0이라면 더이상 흐를 수 없다는 의미로 판단해서 다음 dfs를 진행하지 않았다.
 *
 * ⚠️ 파이프의 정보 중에 A와 Z는 무조건 연결되어있는 지 보장이 되어있지 않아서 의아했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <DFS> '최대 유량'

public class BOJ6086 {
    static final char START = 'A';
    static final char END = 'Z';
    static Map<Character, Map<Character, Integer>> capacityMap = new HashMap<>();
    static Map<Character, Map<Character, Integer>> flowMap = new HashMap<>();
    static Set<Character> visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            char a = st.nextToken().charAt(0);
            char b = st.nextToken().charAt(0);
            int capacity = Integer.parseInt(st.nextToken());

            //capacityMap과 flowMap 초기화
            if(!capacityMap.containsKey(a)){
                capacityMap.put(a, new HashMap<>());
            }

            if(!capacityMap.containsKey(b)){
                capacityMap.put(b, new HashMap<>());
            }

            if(!flowMap.containsKey(a)){
                flowMap.put(a, new HashMap<>());
            }

            if(!flowMap.containsKey(b)){
                flowMap.put(b, new HashMap<>());
            }

            //a -> b 방향 파이프 크기 저장
            Map<Character, Integer> firstMap = capacityMap.get(a);
            firstMap.put(b, capacityMap.get(a).getOrDefault(b, 0) + capacity);
            capacityMap.put(a, firstMap);

            //b -> a 방향 파이프 크기 저장
            Map<Character, Integer> secondMap = capacityMap.get(b);
            secondMap.put(a, capacityMap.get(b).getOrDefault(a, 0) + capacity);
            capacityMap.put(b, secondMap);
        }

        int totalFlow = 0;

        while(true){
            visited = new HashSet<>();
            int flow = dfs(START, Integer.MAX_VALUE);
            if(flow == 0){
                break;
            }
            totalFlow += flow;
        }

        System.out.println(totalFlow);
    }

    public static int dfs(char current, int minFlow){
        if(current == END){
            return minFlow;
        }
        visited.add(current);

        Map<Character, Integer> possibleCases = capacityMap.getOrDefault(current, Collections.emptyMap());

        for(char next : possibleCases.keySet()){
            if(visited.contains(next)){
                continue;
            }

            int capacity = capacityMap.get(current).getOrDefault(next, 0);
            int curFlow = flowMap.getOrDefault(current, Collections.emptyMap()).getOrDefault(next, 0);
            int remain = capacity - curFlow;

            if(remain > 0){
                int flowFuel = dfs(next, Math.min(minFlow, remain));

                if(flowFuel > 0){
                    flowMap.get(current).put(next, curFlow + flowFuel);

                    int reverseFlow = flowMap.getOrDefault(next, Collections.emptyMap()).getOrDefault(current, 0);
                    flowMap.get(next).put(current, reverseFlow - flowFuel);

                    return flowFuel;
                }
            }
        }
        return 0;
    }
}
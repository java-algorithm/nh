/**
 * @author nakhoonchoi
 * @date 2025/04/24
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/92343?language=java
 * @caution
 * [고려사항]
 * 처음에 접근을 잘못했던 문제이다.
 *
 * 각 노드 별로 현재 노드 아래의 늑대 마리 수, 양의 마리 수를 DFS를 통해서 기록한 뒤에
 * 우선순위 큐를 이용한 BFS로 최적을 판별하려고 했다.
 * 
 * 상태의 우선순위는 기본 TC에서 아래와 같이 정했지만 외부 변수와 반례가 존재했다.
 * 
 * - 양이 늑대보다 우선
 * - (같은 동물이라면) 비교될 노드들보다 아래에 양의 마리 수가 많아야 한다.
 * - (양의 마리 수도 같다면) 비교될 노드들보다 아래에 늑대의 마리 수가 적어야 한다.
 * 
 * 예를 들어서 현재 상황이 양 5마리, 늑대 3마리라고 했을 때 
 * 늑대A(아래 늑대 2, 양 2), 늑대B(아래 늑대 1, 양 1) 두 선택지가 있다고 하면
 * 최적의 선택은 늑대B이지만 위의 우선 순위에서는 늑대A가 나올 것이다.
 *
 * 우선순위 큐에 정렬 기준이 현재 양과 늑대 상태 기준으로 계속 갱신되어야할 듯하다.
 *
 * 우선순위 큐 BFS로 푼 해결법이 있다면 더 해보려고 했는데 없는듯해서 다른 방법을 생각했다.
 * ------------------------------------------------------------------------------------------
 * 풀이를 전부 바꿔서 완전 탐색을 했다.(DFS)
 *
 * 루트 0번 노드는 방문 처리를 한 뒤에 DFS 로직을 실행한다.
 *
 * DFS 로직은 매개변수가 (현재 방문 상태, 현재 양 마리 수, 현재 늑대 마리 수)이다.
 * 우선 현재 양의 마리 수와 늑대 마리 수가 같다면 불가능한 상태이기 때문에 바로 return하고
 * 가능한 상태라면 answer를 갱신했다.
 *
 * 다음에 접근 가능한 노드들을 모두 가져올 때
 * 현재 BitSet에 방문된 노드들(true)의 adjList 포함 노드들 중 방문 가능한(BitSet false인)
 * 노드들에 대해서 BitSet을 갱신한 후에 다음 DFS를 진행했다.
 * 여기에서 방문할 노드가 양인지 늑대인지에 따라 아래와 같이 호출이 달라졌다.
 * DFS(BitSet, 양 + 1, 늑대);
 * DFS(BitSet, 양, 늑대 + 1);
 *
 * 실제 시험에서 풀었다면 못 풀었을 것 같다. 완전탐색이라고 생각했으면 간단했을 것 같은데
 * 우선순위로 최적을 생각하려니 어려웠다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2022 Kakao Blind Recruitment> '양과 늑대'

public class Programmers92343 {
    int [] sheepOrWolf;
    Set<BitSet> visited;
    Map<Integer, List<Integer>> edgeMap;
    int answer;
    final int SHEEP = 0;
    final int WOLF = 1;

    public int solution(int[] info, int[][] edges){
        sheepOrWolf = info;
        visited = new HashSet<>();
        edgeMap = new HashMap<>();

        for(int i=0;i<edges.length;i++){
            List<Integer> edgeList = edgeMap.getOrDefault(edges[i][0], new ArrayList<>());

            edgeList.add(edges[i][1]);
            edgeMap.put(edges[i][0], edgeList);
        }

        BitSet initial = new BitSet(info.length);
        initial.set(0);

        dfs(initial, 1, 0);

        return answer;
    }

    public void dfs(BitSet visitedBs, int sheepCount, int wolfCount){
        if(sheepCount == wolfCount){
            return;
        }else{
            answer = Math.max(sheepCount, answer);
        }

        for(int i=0;i<sheepOrWolf.length;i++){
            if(visitedBs.get(i) && edgeMap.containsKey(i)){
                List<Integer> adjList = edgeMap.get(i);

                for(int next : adjList){
                    if(!visitedBs.get(next)) { //!visited[next]
                        visitedBs.set(next); //visited[next] = true;

                        if (visited.contains(visitedBs)){
                            visitedBs.clear(next); //visited[next] = false;
                            continue;
                        }

                        visited.add(visitedBs);

                        if(sheepOrWolf[next] == SHEEP){
                            dfs(visitedBs, sheepCount + 1, wolfCount);
                        }else{
                            dfs(visitedBs, sheepCount, wolfCount + 1);
                        }

                        visitedBs.clear(next); //visited[next] = false;
                    }
                }
            }
        }
    }
}
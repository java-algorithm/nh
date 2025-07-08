/**
 * @author nakhoonchoi
 * @date 2025/07/08
 * @see https://boj.ma/2637
 * @mem 12,184kb
 * @time 76ms
 * @caution
 * [고려사항]
 * 위상 정렬 문제였다.
 * 그리고 최근에 공부한 ConcurrentHashMap을 사용해 푼 문제였다.
 *
 * 우선 위상 정렬을 위해 indegree 배열을 선언하고,
 * 각 부품에 대해 필요한 세부 부품 개수 정보를 담기 위해 Map<Integer, Integer>를 배열로 선언했다.
 * Map<Integer, Integer> [] kitInfo = new Map[N+1];
 * 배열의 인덱스는 입력의 X(대상 부품), 해당 Map의 key는 입력의 Y(세부 부품의 인덱스), value는 입력의 K(필요한 세부 부품의 개수)
 * 를 위 kitInfo에 담으려고 했다.
 *
 * 그리고 현재 부품(노드)의 정보가 확실해진다면 다음 부품의 최적을 판별할 수 있도록
 * 현재 부품이 완성된다면 만들 수 있는 다음 부품에 대한 정보를 List의 배열에 담았다.
 *
 * 그리고 문제에서 기본 부품과 완제품, 그리고 중간 부품에 대한 개념이 있는데
 * 문제에 대한 답은 완제품을 만들 때 필요한 기본 부품의 총 개수를 구해야하므로
 * (중간 부품은 pass) 입력을 다 받은 뒤에 1번 부품부터 indegree 배열 값이 0이라면
 * 기본 부품이라고 생각해서 TreeSet에 담아주었다.(기본 부품의 인덱스 오름차순으로 출력해야하기 때문에)
 *
 * M줄 만큼 각 부품 사이의 정보를 입력 받으면서 indegree 배열, kitInfo, nextNodeList에 정보를 알맞게 저장한다.
 *
 * 그리고 기본 부품을 판별(indegree 배열 값이 0)할 때 basicKitSet에 담아주면서
 * 위상 정렬 탐색을 위해 큐에 전부 넣어주었다.
 *
 * 큐에서 사전 부품에 대한 정보가 확실해진 부품들을 꺼내면서
 * 중간 부품에 대한 하위 부품과 개수를 기반으로 필요한 기본 부품의 개수를 업데이트하고,
 * 현재 부품에 대한 indegree 배열의 값을 -1씩 해서 0이라면 큐에 넣어주는 방식으로 해결했다.
 * (일반적인 위상 정렬 풀이법)
 *
 * 위 과정 중 for(int kitNum : kitInfo[curNode].keySet()){ (for-each loop) 안에서
 * kitInfo[curNode] (기존에는 HashMap) HashMap의 내용(구조)이 바뀌는 이슈가 있어서
 * ConcurrentModificationException이 발생했다.
 * 문제 풀이를 위해 HashMap 대신 내부적으로 keySet이 변화하더라도
 * 스냅샷에 따라 대응할 수 있는 KeyIterator가 구현된 ConcurrentHashMap 구현체를 사용해서
 * 문제를 해결했다.(ConcurrentHashMap의 코드는 보니 매우 복잡하다...)
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
//백준 <위상 정렬> '장난감 조립'

public class BOJ2637 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());

        int [] indegree = new int[N+1];
        Map<Integer, Integer> [] kitInfo = new Map[N+1];
        List<Integer> [] nextNodeList = new List[N+1];

        for(int i=1;i<=N;i++){
            kitInfo[i] = new ConcurrentHashMap<>();
            nextNodeList[i] = new ArrayList<>();
        }

        Set<Integer> basicKit = new TreeSet<>();

        StringTokenizer st;
        for(int i=0;i<M;i++){
            st = new StringTokenizer(br.readLine());

            int X = Integer.parseInt(st.nextToken());
            int Y = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            indegree[X]++;

            kitInfo[X].put(Y, K);
            nextNodeList[Y].add(X);
        }

        Queue<Integer> indegreeQueue = new ArrayDeque<>();
        for(int i=1;i<=N;i++){
            if(indegree[i] == 0){
                indegreeQueue.offer(i);
                basicKit.add(i);
            }
        }

        while(!indegreeQueue.isEmpty()){
            int curNode = indegreeQueue.poll();

            for(int kitNum : kitInfo[curNode].keySet()){
                int count = kitInfo[curNode].get(kitNum);
                for(int subKitNum : kitInfo[kitNum].keySet()){
                    if(basicKit.contains(subKitNum)){
                        kitInfo[curNode].put(subKitNum, kitInfo[curNode].getOrDefault(subKitNum, 0) + kitInfo[kitNum].get(subKitNum) * count);
                    }
                }
            }

            for(int nextNode : nextNodeList[curNode]){
                if(kitInfo[nextNode].containsKey(curNode)){
                    indegree[nextNode]--;

                    if(indegree[nextNode] == 0){
                        indegreeQueue.offer(nextNode);
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int basicKitNum : basicKit){
            sb.append(basicKitNum).append(' ').append(kitInfo[N].get(basicKitNum)).append('\n');
        }
        System.out.println(sb.substring(0, sb.length() - 1));
    }
}
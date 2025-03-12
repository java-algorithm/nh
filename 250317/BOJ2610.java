/**
 * @author nakhoonchoi
 * @date 2025/03/12
 * @see https://boj.ma/2610
 * @mem 16,768kb
 * @time 172ms
 * @caution
 * [고려사항]
 * 분리 집합 문제로 판단되어 그룹을 구성하는 부분을 구현하는 데에는 어려움이 없었으나,
 * 그룹 중 대표를 구하는 과정에 어려움이 있었다.
 * 
 * 그룹 중 대표의 기준은 하나의 노드에서
 * 그룹 안의 다른 노드까지의 거리가 최대인 구간 중 가장 최소(?)인 노드가 대표가 된다.
 * 말이 헷갈려서 다른 노드까지 거리들을 모두 합해서 그 중 최소인 노드인 줄 알았다.
 *
 * 예를 들어, 1-3-2-5-4와 같은 그룹이 있다고 했을 때,
 * 1과 4는 최대 구간 거리가 4,
 * 3과 5는 최대 구간 거리가 3,
 * 2는 최대 구간 거리가 2로 대표는 2가 된다.
 * (합과는 전혀 관련이 없었다.)
 * 
 * 각 구간 거리를 구하는 알고리즘에는 플로이드-와샬을 사용했다.
 * N이 100이라서 충분히 가능했다.
 * 플로이드-와샬은 O(N^3)의 알고리즘으로 N이 400이하일 때 괜찮다고 한다.
 *
 * 그리고 하나 주의할 점은 union-find 과정에서
 * parent 배열이 갱신이 안됐을 수 있기 때문에 그룹을 구하기 전에
 * findParent 함수를 1부터 N까지 한 번 더 호출해서 제대로 갱신을 해주어야한다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <분리 집합 + 플로이드-와샬> '회의준비'

public class BOJ2610 {
    static int N, M;
    static int [] parent;
    static int [][] dp;
    static final int INF = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        parent = new int[N+1];
        dp = new int[N+1][N+1];
        StringTokenizer st;

        init();

        for(int i=0;i<M;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            union(a, b);
            dp[a][b] = 1;
            dp[b][a] = 1;
        }

        //대표찾는 과정
        Map<Integer, List<Integer>> groupMap = new HashMap<>();
        for(int i=1;i<=N;i++){
            findParent(i);
            int groupNum = parent[i];
            List<Integer> groups = groupMap.getOrDefault(groupNum, new ArrayList<>());
            groups.add(i);
            groupMap.put(groupNum, groups);
        }

        Set<Integer> representativeSet = new TreeSet<>();
        for(int groupNum : groupMap.keySet()){
            representativeSet.add(getRepresentative(groupMap.get(groupNum)));
        }

        System.out.println(representativeSet.size());
        for(int representative : representativeSet){
            System.out.println(representative);
        }
    }

    static void init(){
        for(int i=1;i<=N;i++){
            parent[i] = i;
            Arrays.fill(dp[i], INF);
            dp[i][i] = 0;
        }
    }

    static boolean union(int a, int b){
        int parentA = findParent(a);
        int parentB = findParent(b);

        if(parentA == parentB){
            return false;
        }else{
            parent[parentB] = parent[parentA];
        }

        return true;
    }

    static int findParent(int n){
        if(parent[n] == n){
            return parent[n];
        }

        return parent[n] = findParent(parent[n]);
    }

    public static int getRepresentative(List<Integer> group){
        int representative = group.get(0);
        int minMaxDist = Integer.MAX_VALUE;

        for (int mid : group) { // 거쳐가는 노드
            for (int start : group) { // 출발 노드
                for (int end : group) { // 도착 노드
                    if(start == end) continue;
                    if (dp[start][mid] != INF && dp[mid][end] != INF) { // 경로가 존재하는 경우만 갱신
                        dp[start][end] = Math.min(dp[start][end], dp[start][mid] + dp[mid][end]);
                    }
                }
            }
        }


        for(int member : group){
            int max = Integer.MIN_VALUE;
            for(int target : group){
                if(dp[member][target] != INF){
                    max = Math.max(max, dp[member][target]);
                }
            }
            if(max < minMaxDist){
                representative = member;
                minMaxDist = max;
            }
        }

        return representative;
    }
}
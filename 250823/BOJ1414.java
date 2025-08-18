/**
 * @author nakhoonchoi
 * @date 2025/08/18
 * @see https://boj.ma/1414
 * @mem 11,900kb
 * @time 76ms
 * @caution
 * [고려사항]
 * 최소신장 트리의 유니온파인드 문제였다.
 * 크루스칼 알고리즘을 이용해서 문제를 풀었고,
 * 연결점과 비용을 기반으로 우선순위 큐를 통해 정렬했다.
 * 큐에서 경로를 뽑으며 이미 연결이 되어있다면 pass하고 새로 연결을 할 수 있다면 parent 배열을 갱신하며 union을 진행했다.
 * 총 연결 갯수가 N-1이라면 모든 정점이 연결되어있다고 판단해서 (총합(sum) - 최소 비용)을 반환했다.
 * 모든 정점이 연결되지 않았다면 -1을 반환했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <union-find> '불우이웃돕기'

public class BOJ1414 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int answer = 0;
        int connection = 0;
        int [] parent = new int[N];
        int sum = 0;

        PriorityQueue<Route> pq = new PriorityQueue<>();
        init(parent);

        for(int i=0;i<N;i++){
            String str = br.readLine();

            for(int j=0;j<N;j++){
                char c = str.charAt(j);
                int value = 0;

                if(c >= 'A' && c <= 'Z'){
                    value = (c - 'A') + 27;
                }else if(c >= 'a' && c <= 'z'){
                    value = (c - 'a') + 1;
                }

                sum += value;
                if(value != 0) {
                    pq.offer(new Route(i, j, value));
                }
            }
        }

        while(!pq.isEmpty() && connection < N){
            Route cur = pq.poll();

            if(union(parent, cur.a, cur.b)){
                //union
                answer += cur.cost;

                connection++;
            }
        }

        if(connection != N-1){
            System.out.println(-1);
        }else{
            System.out.println(sum - answer);
        }
    }

    public static void init(int [] parent){
        for(int i=0;i<parent.length;i++){
            parent[i] = i;
        }
    }

    public static int getParent(int [] parent, int index){
        if(parent[index] == index){
            return index;
        }else {
            return parent[index] = getParent(parent, parent[index]);
        }
    }

    public static boolean union(int [] parent, int a, int b){
        int parentA = getParent(parent, a);
        int parentB = getParent(parent, b);

        if(getParent(parent, parentA) == getParent(parent, parentB)){
            return false;
        }

        if(parentA < parentB){
            parent[parentB] = parentA;
        }else{
            parent[parentA] = parentB;
        }

        return true;
    }

    static class Route implements Comparable<Route>{
        int a;
        int b;
        int cost;

        Route(int a, int b, int cost){
            this.a = a;
            this.b = b;
            this.cost = cost;
        }

        @Override
        public int compareTo(Route o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
}
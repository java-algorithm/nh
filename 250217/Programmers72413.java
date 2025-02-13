/**
 * @author nakhoon
 * @date 2025/02/13
 * @see https://programmers.co.kr/learn/courses/30/lessons/72413
 * @caution
 * 	[고려사항]
 * 다익스트라 알고리즘의 반복문 탈출 조건을 잘 생각해야 했던 문제이다.
 * 합승해서 내리는 곳을 C라고 하면, S~C, C~A, C~B의 합 중 최소를 구하면 되는 문제이다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
// 프로그래머스 <2021 카카오 공채> '합승 택시 요금'
public class Programmers72413 {
	static List<Edge> [] edgeList;
	static int [] distance;
	static int size;
	static final int INF = 10000001;
	public static void main(String[] args) {
		int[][] fares = { { 4, 1, 10 }, 
				{ 3, 5, 24 }, 
				{ 5, 6, 2 },
				{ 3, 1, 41 }, 
				{ 5, 1, 24 }, 
				{ 4, 6, 50 }, 
				{ 2, 4, 66 }, 
				{ 2, 3, 22 }, 
				{ 1, 6, 25 } };
		int n = 6;
		int s = 4;
		int a = 6;
		int b = 2;
		System.out.println(solution(n, s, a, b, fares));
	}

	public static int solution(int n, int s, int a, int b, int[][] fares) {
		int answer = Integer.MAX_VALUE;
		size = n;
		edgeList = new ArrayList[n+1];
		
		for(int i=1;i<=n;i++) {
			edgeList[i] = new ArrayList<>();
		}
		
		for(int i=0;i<fares.length;i++) {
			int first = fares[i][0];
			int second = fares[i][1];
			int cost = fares[i][2];
			
			edgeList[first].add(new Edge(second,cost));
			edgeList[second].add(new Edge(first,cost));
		}
		
		distance = new int[n+1];
		
		
		for(int i=1;i<=n;i++) {
			Arrays.fill(distance, INF);
			dijkstra(s,i);
			int total = distance[i];
			Arrays.fill(distance, INF);
			dijkstra(i,a);
			total += distance[a];
			Arrays.fill(distance, INF);
			dijkstra(i,b);
			total += distance[b];
			
			answer = Math.min(answer, total);
		}
		
		return answer;
	}
	
	static void dijkstra(int start, int n) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		//첫 시작의 자기 자신은 0으로 표현한다.
		pq.offer(new Edge(start, 0));
		distance[start] = 0;
		
		while(!pq.isEmpty()) {
			Edge temp = pq.poll();
			int cur = temp.target;
            
            if(cur == n)
                break;
			
			for(Edge edge : edgeList[cur]) {
				if(distance[edge.target] > distance[cur] + edge.cost) {
					distance[edge.target] = distance[cur] + edge.cost;
					pq.offer(new Edge(edge.target, distance[edge.target]));
				}
			}
		}
	}
	
	static class Edge implements Comparable<Edge>{
		int target;
		int cost;
		public Edge(int target, int cost) {
			super();
			this.target = target;
			this.cost = cost;
		}
		@Override
		public int compareTo(Edge o) {
			// TODO Auto-generated method stub
			return Integer.compare(this.cost, o.cost);
		}
	}
}
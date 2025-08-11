/**
 * @author nakhoonchoi
 * @date 2025/08/11
 * @see https://boj.ma/2836
 * @mem 103,340kb
 * @time 648ms
 * @caution
 * [고려사항]
 * 입력 받은 경로 중 start > end인 경우(역방향)만 커버했다.
 * 왜냐하면 start < end인 경우(순방향)는
 * 어차피 수상 택시의 목적지가 0에서 M이기 때문에 오름차순으로 올라가면서 무조건 거쳐갈 것이라고 생각했다.
 *
 * 원래 M의 범위는 최소가 3이지만 역방향의 경로가 없다면 우선순위 큐가 빌 수도 있으니
 * 우선순위 큐가 비었다면 M을 그대로 반환하고 종료했다.
 *
 * 역방향의 경로를 모두 입력 받은 다음 end를 오름차순으로 우선 정렬하고, end가 같다면 start를 오름차순으로 정렬했다.
 * 우선순위 큐에서 경로를 pop하면서 최대한 오른쪽으로 갔다가 최소로 돌아올 것이기 때문에 구간에 따른 lastMax와 lastMin을 지정했다.
 * 또한 별도로 마지막에 방문한 좌표를 curPoint로 초기화하기 위해 맨 처음에는 0으로 초기화했다.
 *
 * 예를 들어 아래의 경우에는
 * 2 7
 * 6 4
 * 3 1
 * 0 -> 6 -> 1 -> 7의 경로보다(6+5+6 = 17)
 * 0 -> 3 -> 1 -> 6 -> 4 -> 7의 경로(3+2+5+2+3 = 15)가 최선의 경로로
 * 각 경로마다 겹치치 않는다면 빨리 도착지점을 찍고 다음 경로를 탐방하는 것이 유리하다.
 *
 * if(cur.end > lastMax) 경로의 끝이 마지막 최댓값(오른쪽)보다 크다면 경로의 구간이 나눠졌다고 판단해서
 * answer += Math.abs(lastMax - curPoint);//수상 택시의 역방향 이동 거리
 * answer += (lastMax - lastMin); //수상 택시의 순방향 이동 거리
 * curPoint = lastMin; //현재 좌표 갱신
 * lastMin = cur.end; //최소 지점 갱신
 * 위 로직을 수행해주었다.
 *
 * 우선순위 큐에서 경로를 다 고려했어도 마지막으로 M까지의 이동 거리도 고려해야하기 때문에 한 번 더 위 거리 계산 로직을 수행했다.
 *
 * 💡 주의해야할 점은 N이 30만이지만 M이 10억이기 때문에 answer가 int형을 벗어날 확률이 높아서 long 형으로 관리해주어야했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <정렬> '수상 택시'

public class BOJ2836 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long answer = 0;
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        PriorityQueue<Route> pq = new PriorityQueue<>();

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            if(start > end) {
                pq.offer(new Route(start, end));
            }
        }

        int lastMax;
        int lastMin;
        int curPoint = 0;

        if(!pq.isEmpty()){
            Route firstRoute = pq.poll();
            lastMax = firstRoute.start;
            lastMin = firstRoute.end;
        }else{
            System.out.println(M);
            return;
        }

        while(!pq.isEmpty()){
            Route cur = pq.poll();

            if(cur.end > lastMax){
                answer += Math.abs(lastMax - curPoint);
                answer += (lastMax - lastMin);
                curPoint = lastMin;
                lastMin = cur.end;
            }

            lastMax = Math.max(lastMax, cur.start);
            lastMin = Math.min(lastMin, cur.end);
        }

        answer += Math.abs(lastMax - curPoint);
        answer += (lastMax - lastMin);
        answer += Math.abs(M - lastMin);

        System.out.println(answer);
    }

    static class Route implements Comparable<Route>{
        int start;
        int end;

        Route(int start, int end){
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Route o) {
            if(this.end == o.end){
                return Integer.compare(this.start, o.start);
            }
            return Integer.compare(this.end, o.end);
        }
    }
}
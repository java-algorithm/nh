/**
 * @author nakhoonchoi
 * @date 2025/04/23
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/92343?language=java
 * @caution
 * [고려사항]
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2022 Kakao Blind Recruitment> '양과 늑대'

public class Programmers92343_Fail {
    static int [] sheepOrWolf;
    static int [] underSheepCountArr;
    static int [] underWolfCountArr;
    static Map<Integer, List<Integer>> edgeMap;
    static int answer;
    static final int SHEEP = 0;
    static final int WOLF = 1;

    public static void main(String[] args) {
//        int [] info = {0,0,1,1,1,0,1,0,1,0,1,1};
//        int [][] edges = {{0,1},{1,2},{1,4},{0,8},{8,7},{9,10},{9,11},{4,3},{6,5},{4,6},{8,9}};
//        int [] info = {0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0};
//        int [][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {2, 6}, {3, 7}, {4, 8}, {6, 9}, {9, 10}};
//        int [] info = {0, 0, 1, 1, 0, 0, 0};
//        int [][] edges = {{0, 1}, {1, 2}, {1, 3}, {2, 4}, {3, 5}, {3, 6}};
        int [] info = {0, 1, 0, 1, 1, 0, 0, 0, 0};
        int [][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 4}, {3, 5}, {3, 6}, {4, 7}, {6, 8}};

        System.out.println(solution(info, edges));
    }

    public static int solution(int[] info, int[][] edges) {
        sheepOrWolf = info;
        underSheepCountArr = new int[info.length];
        underWolfCountArr = new int[info.length];

        edgeMap = new HashMap<>();
        for(int i=0;i<edges.length;i++){
            List<Integer> edgeList = edgeMap.getOrDefault(edges[i][0], new ArrayList<>());

            edgeList.add(edges[i][1]);
            edgeMap.put(edges[i][0], edgeList);
        }

        calSheepCount(0, 0);
        calWolfCount(0, 0);

        bfs();

        return answer;
    }

    public static int calSheepCount(int index, int curSheepCount){
        if(!edgeMap.containsKey(index)){
            if(sheepOrWolf[index] == SHEEP) {
                underSheepCountArr[index] = curSheepCount + 1;
                return curSheepCount + 1;
            }else{
                underSheepCountArr[index] = curSheepCount;
                return curSheepCount;
            }
        }

        int count = 0;
        for(int nextIndex : edgeMap.getOrDefault(index, new ArrayList<>())){
            count += calSheepCount(nextIndex, curSheepCount);
        }
        underSheepCountArr[index] = count;

        return underSheepCountArr[index];
    }

    public static int calWolfCount(int index, int curWolfCount){
        if(!edgeMap.containsKey(index)){
            if(sheepOrWolf[index] == WOLF) {
                underWolfCountArr[index] = curWolfCount + 1;
                return curWolfCount + 1;
            }else{
                underWolfCountArr[index] = curWolfCount;
                return curWolfCount;
            }
        }

        int count = 0;
        for(int nextIndex : edgeMap.getOrDefault(index, new ArrayList<>())){
            count += calWolfCount(nextIndex, curWolfCount);
        }
        underWolfCountArr[index] = count;
        if(sheepOrWolf[index] == WOLF){
            underWolfCountArr[index]++;
        }

        return underWolfCountArr[index];
    }

    public static void bfs(){
        PriorityQueue<Status> pq = new PriorityQueue<>();
        pq.offer(new Status(0, SHEEP, underSheepCountArr[0], underWolfCountArr[0])); //루트는 무조건 양.
        int curSheepCount = 0;
        int curWolfCount = 0;
        answer = Math.max(curSheepCount, answer);

        while(!pq.isEmpty()){
            Status cur = pq.poll();

            if(sheepOrWolf[cur.index] == SHEEP){
                curSheepCount++;
            }else{
                curWolfCount++;
            }

            if(curSheepCount == curWolfCount){
                break;
            }

            answer = Math.max(curSheepCount, answer);

            if(!edgeMap.containsKey(cur.index)){
                continue;
            }

            for(int nextIndex : edgeMap.get(cur.index)){
                if(sheepOrWolf[nextIndex] == SHEEP){
                    pq.offer(new Status(nextIndex, SHEEP, underSheepCountArr[nextIndex], underWolfCountArr[nextIndex]));
                }else{
                    pq.offer(new Status(nextIndex, WOLF, underSheepCountArr[nextIndex], underWolfCountArr[nextIndex]));
                }
            }
        }
    }

    static class Status implements Comparable<Status>{
        int index;
        int sheepOrWolf;
        int underSheepCount;
        int underWolfCount;

        Status(int index, int sheepOrWolf, int underSheepCount, int underWolfCount){
            this.index = index;
            this.sheepOrWolf = sheepOrWolf;
            this.underSheepCount = underSheepCount;
            this.underWolfCount = underWolfCount;
        }

        @Override
        public int compareTo(Status o) {
            if(this.sheepOrWolf == o.sheepOrWolf){
                if(this.underSheepCount == o.underSheepCount){
                    return Integer.compare(this.underWolfCount, o.underWolfCount);
                }
                return Integer.compare(this.underSheepCount, o.underSheepCount) * -1;
            }
            return Integer.compare(this.sheepOrWolf, o.sheepOrWolf);
        }
    }
}
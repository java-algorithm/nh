/**
 * @author nakhoonchoi
 * @date 2025/03/191
 * @see https://boj.ma/2461
 * @mem 157,540kb
 * @time 940ms
 * @caution
 * [고려사항]
 * 각 학급 별로 능력치를 각각 정렬해서 대표선수 포인터를 가지고 최대 능력치와 최소 능력치의 차이 중 가장 최소가 되는 값을 출력해야했다.
 * 각 학급 별로 우선순위 큐를 쓰려고 했으나 실패했다.
 *
 * pointer 배열(대표 선수 인덱스를 저장할 배열)을 두었고, 각 학급 별로 능력치를 오름차순 정렬했다.
 * 그리고 대표 선수를 모은 우선 순위 큐를 선언해서 학급의 개수만큼 맞춰서 계속 넣을 것이다.
 * 우선순위 큐는 학급의 개수 크기만큼만 항상 가지고 있을 것이고,
 * 전체적인 로직은 대표 선수 우선순위 큐 중 최소 능력치의 값을 뽑아 같은 학급의 다음 능력치를 넣어서 최대 최소 계산을 해줄 것이다.
 *
 * 이 때 주의 해야할 점은 한 학급이 끝나는(pointer 배열 값 == M-1이 되는) 시점의 능력치 최솟값은 변하지 않고 끝까지 간다.
 * -> flag 처리
 * 그리고 대표 선수 모음 중 최댓값은 끝까지 갱신해준다.
 *
 * 3 2
 * 1 7
 * 2 7
 * 3 7
 * ans : 0
 *
 * 위와 같은 TC가 있을 수 있기에 모든 학급의 모든 학생들을 기준으로 대표 선수 선발 경우의 수를 모두 계산해야했다.
 * 고로 끝은 모든 학급의 pointer[i] == M-1이 만족해야 while문은 끝이 날 수 있다.
 * [입력사항]
 * [출력사항]
 */

import java.io.*;
import java.util.*;
//백준 <우선순위 큐> '대표 선수'

public class BOJ2461 {
    static int [][] score;
    static int N, M;
    static int [] pointer;
    static int min, max, answer;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        score = new int[N][M];
        pointer = new int[N];

        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
        answer = Integer.MAX_VALUE;

        PriorityQueue<Score> pq = new PriorityQueue<>();

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++){
                score[i][j] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(score[i]);

            pq.offer(new Score(score[i][0], i, 0));
            if(score[i][0] > max){
                max = score[i][0];
            }
        }

        min = pq.peek().point;

        answer = Math.min(answer, max - min);

        boolean flag = false;

        while(!isEnd()){
            Score outScore = pq.poll();

            //마지막 인덱스까지 방문한 학급이 나왔다면 최대한 outScore를 뽑는다.(min을 고정시키기 위한 flag 처리)
            while(pointer[outScore.classNum] == M-1){
                outScore = pq.poll();
                flag = true;
            }

            pointer[outScore.classNum]++;

            int point = score[outScore.classNum][pointer[outScore.classNum]];
            if(point > max){
                max = point;
            }
            pq.offer(new Score(score[outScore.classNum][pointer[outScore.classNum]], outScore.classNum, pointer[outScore.classNum]));

            if(!flag) {
                min = pq.peek().point;
            }

            answer = Math.min(answer, max - min);
        }

        System.out.println(answer);
    }

    static boolean isEnd(){
        for(int i=0;i<N;i++){
            if(pointer[i] != M-1){
                return false;
            }
        }

        return true;
    }

    static class Score implements Comparable<Score>{
        int point;
        int classNum;
        int index;

        Score(int point, int classNum, int index){
            this.point = point;
            this.classNum = classNum;
            this.index = index;
        }

        @Override
        public int compareTo(Score o) {
            return Integer.compare(this.point, o.point);
        }
    }
}
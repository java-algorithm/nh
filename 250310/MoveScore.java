/**
 * @author nakhoonchoi
 * @date 2025/03/09
 * @caution
 * [고려사항]
 * 내 현재 등수를 m등까지 올리기 위해 점수를 조작해야하는 그리디? 문제였다.
 *
 * - 우선, score 점수를 현재 내 점수에 대해 상대값으로 전부 바꿔주었다.(배열의 원소에 k를 전부 빼주었다.)
 * - 그리고 0(현재 점수)보다 크면 Queue에, 작다면 Deque에 넣어준다.
 * (현재 점수와 같은 0은 고려할 필요가 없기 때문에 Queue와 Deque에 넣지 않는다.)
 * - Queue의 사이즈로 현재 등수를 판별하고 원하는 등수까지 가기 위해 고려해야할 높은 수 빼고 나머지 최상위 수는 Queue에서 제거한다.
 * - Queue의 큰 점수부터 조작할 점수들을 찾는다. 이 때 Deque이 비어있다면 조작할 수 없으므로 -1을 반환한다.
 * - 조작할 점수는 Deque에서 찾을 수 있는데 Deque는 앞 부분이 아닌 뒷 부분(더 작은 점수이며 절댓값이 크다.)부터 빼야 효율적인 답을 구할 수 있다.
 * - 뒷 부분부터 뺄 때 현재 Queue의 값을 상쇄 시킬 수 있는 지 없는 지 같은 지를 비교해서 각각에 따라 다르게 처리하면 된다.
 *
 * - 그리고 값이 변하는 점수의 인덱스를 Set에 보관해서 마지막에 Set의 크기를 출력하면 정답이 된다.
 * - O(N)만큼은 무조건 순회해야할 것 같다고 생각해서 등수 판별 및 Queue/Deque 나누기를 위해 순회했다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//'Move Score'

public class MoveScore {
    public int solution(int cap, int k, int[] score, int m) {
        minusArr(score, k);
        Set<Integer> changeScoreIndexSet = new HashSet<>();
        Queue<Point> upperPointDeque = new LinkedList<>();
        Deque<Point> lowerPointQ = new ArrayDeque<>();

        for(int i=0;i<score.length;i++){
            if(score[i] > 0){
                upperPointDeque.offer(new Point(i, score[i]));
            }else if(score[i] < 0){
                lowerPointQ.offer(new Point(i, score[i]));
            }
        }

        int curGrade = upperPointDeque.size(); //현재 등수를 배열 인덱스로 표시(1등 - 0, 4등 - 3)

        for(int i=0;i<m-1;i++){
            upperPointDeque.poll();
        }

        for(int i=curGrade;i>=m;i--){
            Point upPoint = upperPointDeque.poll();
            int upScore = upPoint.score;

            if(lowerPointQ.isEmpty()){
                return -1;
            }

            while(!lowerPointQ.isEmpty() && upScore > 0){
                Point downPoint = lowerPointQ.pollLast();
                int downScore = downPoint.score;

                if(downScore > upScore * -1){ //upScore를 한 번에 상쇄 못 시키는 경우
                    lowerPointQ.offer(new Point(downPoint.index, downScore + upScore));
                    upScore = 0;
                }else if(downScore < upScore * -1){ //upScore를 한 번에 상쇄 가능
                    upScore -= (downScore * -1);
                }else{ //같을 경우
                    upScore = 0;
                }

                changeScoreIndexSet.add(downPoint.index);
            }

            changeScoreIndexSet.add(upPoint.index);
        }

        return changeScoreIndexSet.size();
    }

    public void minusArr(int [] score, int k){
        for(int i=0;i<score.length;i++){
            score[i] -= k;
        }
    }

    class Point implements Comparable<Point>{
        int index;
        int score;

        Point(int index, int score){
            this.index = index;
            this.score = score;
        }

        @Override
        public int compareTo(Point o) {
            return Integer.compare(this.score, o.score);
        }
    }
}
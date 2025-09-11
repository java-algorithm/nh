/**
 * @author nakhoonchoi
 * @date 2025/09/11
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/258709
 * @caution
 * [고려사항]
 * 요약하면 비트마스킹과 DP를 이용해서 문제를 해결했다.
 *
 * 완전 탐색으로 풀 수 있을 지 생각해봤다.
 *
 * 주사위의 최대 갯수는 10이기 때문에
 * 내 팀의 주사위를 뽑는 최대 경우의 수는 10C5(=252)이다. 여기까지는 괜찮다.
 *
 * 하지만 각 주사위 값들의 합을 구할 때의 경우의 수는
 * 한 팀에 최대 주사위는 다섯 개이기 때문에 6^5이다.
 *
 * 하지만 내 팀과 상대 팀이 있고, 각 팀의 모든 결과 값을 비교한다면
 * 6^10의 경우의 수가 적용될 것 같다. (=60,466,176)
 * 
 * 여기에서 위의 10C5를 곱한다면 12억이 넘어가서 시간 초과가 발생할 것이라고 예상되어 최적화의 수를 생각했다.
 * 
 * 우선 첫 번째로, 주사위가 1~6까지 있을 때
 * 내 팀 (1,2,3)이고 상대 팀 (4,5,6)일 때와
 * 내 팀 (4,5,6)이고 상대 팀 (1,2,3)일 때의 결과는 승/패에 대한 수만 뒤집는다면 계산을 두 번 하지 않아도 될 것이라고 생각했다.
 *
 * 그리고 내 팀의 주사위와 상대 팀의 주사위는 반대이기 때문에 이를 비트마스킹(BitSet 객체)을 이용해서 처리했다.
 *
 * 각 주사위의 합을 구할 때에는 냅-색과 비슷한 방식으로 누적합 DP 배열을 구상해서
 * 주사위의 총 합 경우의 수 중 최솟값부터 최댓값까지 비교해서
 * 승리한 수와 패배한 수를 누적합의 곱 연산으로 처리했다.
 *
 * 전체적인 FLOW는 (주사위 팀 구성 목적 조합)
 * -> (내 팀과 상대 팀 주사위 BitSet 기반 구성) -> (내 팀과 상대 팀 주사위 정보 기반으로 누적합 DP 적용)
 * -> (누적합 끼리 비교해서 승리 횟수 및 패배 횟수 측정)
 * -> (패배 횟수는 상대 편의 승리라고 생각하고 경우의 수 resultMap에 입력)
 * -> (TreeMap으로 구성된 key의 맨 윗값에 대한 정보를 반환)
 *
 * 💡 IDE 없이 푸는 연습을 했다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2024 KAKAO WINTER INTERNSHIP> '주사위 고르기'

public class Programmers258709 {
    Set<BitSet> caseSet;
    Map<Integer, BitSet> resultMap;
    int [] temp;
    int [][] diceInfo;

    public int[] solution(int[][] dice) {
        caseSet = new HashSet<>();
        resultMap = new TreeMap<>(new Comparator<Integer>(){
            public int compare(Integer o1, Integer o2){
                return Integer.compare(o1, o2) * -1;
            }
        });
        temp = new int[dice.length / 2];
        diceInfo = dice;

        int[] answer = new int[dice.length / 2];

        comb(0, 0);

        for(int key : resultMap.keySet()){
            BitSet bestTeam = resultMap.get(key);
            int index = 0;
            for(int i=0;i<dice.length;i++){
                if(bestTeam.get(i)){
                    answer[index++] = i+1;
                }
            }
            break;
        }

        return answer;
    }

    public void comb(int count, int cur){
        if(count == temp.length){
            //여기서 상대 주사위와 내 주사위를 나눠서 계산한다.
            BitSet myTeam = new BitSet(temp.length * 2);

            int [] oppositeTemp = new int[temp.length];

            for(int i=0;i<temp.length;i++){
                myTeam.set(temp[i]);
            }

            BitSet oppositeTeam = (BitSet) myTeam.clone();

            int index = 0;
            for(int i=0;i<temp.length * 2;i++){
                oppositeTeam.flip(i);
                if(oppositeTeam.get(i)){
                    oppositeTemp[index++] = i;
                }
            }

            if(caseSet.contains(myTeam) || caseSet.contains(oppositeTeam)){
                return;
            }

            caseSet.add(myTeam);
            caseSet.add(oppositeTeam);

            fight(temp, oppositeTemp, myTeam, oppositeTeam);

            return;
        }

        for(int i=cur;i<temp.length * 2;i++){
            temp[count] = i;
            comb(count + 1, i + 1);
        }
    }

    public void fight(int [] temp, int [] oppositeTemp, BitSet myTeam, BitSet oppositeTeam){
        int winCount = 0;
        int loseCount = 0;

        //계산
        int [][] myTeamDp = new int[temp.length][601];
        int [][] oppositeTeamDp = new int[temp.length][601];

        int myTeamMinSum = 0;
        int myTeamMaxSum = 0;

        int curMin = 101;
        int curMax = -1;

        for(int i=0;i<6;i++){
            curMin = Math.min(curMin, diceInfo[temp[0]][i]);
            curMax = Math.max(curMax, diceInfo[temp[0]][i]);
            myTeamDp[0][diceInfo[temp[0]][i]]++;
        }

        myTeamMinSum += curMin;
        myTeamMaxSum += curMax;

        for(int i=1;i<temp.length;i++){
            curMin = 101;
            curMax = -1;

            for(int j=0;j<6;j++){
                curMin = Math.min(curMin, diceInfo[temp[i]][j]);
                curMax = Math.max(curMax, diceInfo[temp[i]][j]);

                for(int k=diceInfo[temp[i]][j];k<601;k++){
                    if(myTeamDp[i-1][k - diceInfo[temp[i]][j]] >= 1){
                        myTeamDp[i][k] += myTeamDp[i-1][k - diceInfo[temp[i]][j]];
                    }
                }
            }

            myTeamMinSum += curMin;
            myTeamMaxSum += curMax;
        }

        int oppositeMinSum = 0;
        int oppositeMaxSum = 0;

        curMin = 101;
        curMax = -1;

        for(int i=0;i<6;i++){
            curMin = Math.min(curMin, diceInfo[oppositeTemp[0]][i]);
            curMax = Math.max(curMax, diceInfo[oppositeTemp[0]][i]);
            oppositeTeamDp[0][diceInfo[oppositeTemp[0]][i]]++;
        }

        oppositeMinSum += curMin;
        oppositeMaxSum += curMax;

        for(int i=1;i<temp.length;i++){
            curMin = 101;
            curMax = -1;

            for(int j=0;j<6;j++){
                curMin = Math.min(curMin, diceInfo[oppositeTemp[i]][j]);
                curMax = Math.max(curMax, diceInfo[oppositeTemp[i]][j]);

                for(int k=diceInfo[oppositeTemp[i]][j];k<601;k++){
                    if(oppositeTeamDp[i-1][k - diceInfo[oppositeTemp[i]][j]] >= 1){
                        oppositeTeamDp[i][k] += oppositeTeamDp[i-1][k - diceInfo[oppositeTemp[i]][j]];
                    }
                }
            }

            oppositeMinSum += curMin;
            oppositeMaxSum += curMax;
        }

        for(int mySum = myTeamMinSum;mySum <= myTeamMaxSum;mySum++){
            if(myTeamDp[temp.length-1][mySum] == 0){
                continue;
            }
            for(int oppSum = oppositeMinSum;oppSum <= oppositeMaxSum;oppSum++){
                if(oppositeTeamDp[oppositeTemp.length-1][oppSum] == 0){
                    continue;
                }
                if(mySum > oppSum) {
                    winCount += myTeamDp[temp.length-1][mySum] * oppositeTeamDp[oppositeTemp.length-1][oppSum];
                }else if(mySum < oppSum){
                    loseCount += myTeamDp[temp.length-1][mySum] * oppositeTeamDp[oppositeTemp.length-1][oppSum];
                }
            }
        }
        resultMap.put(winCount, myTeam);
        resultMap.put(loseCount, oppositeTeam);
    }
}
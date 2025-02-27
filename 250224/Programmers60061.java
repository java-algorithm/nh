/**
 * @author nakhoonchoi
 * @date 2025/02/27
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/60061
 * @caution
 * [고려사항]
 * 이 문제... 정말 오래 걸렸다. 맞왜틀이 계속 발생했는데, 기존 build_frame을 정렬하면 안됐다.
 *
 * 분기 처리의 경우의 수가 엄청 많았다. 경우의 수를 정리해보겠다.
 *
 * 모든 기둥이나 보의 좌표는 map을 벗어나면 안된다.
 * 2차원 배열을 기둥의 상태, 보의 상태 배열 두 개를 선언했다.
 * 그리고 2차원 배열은 map[y][x]라고 했을때, x좌표가 커지면 오른쪽, y좌표가 커지면 아래 방향으로 이동하기 때문에
 * 배열도 반대로 생각했다.
 *
 * 기둥은 위에서 아래 방향, 보는 왼쪽에서 오른쪽 방향이라고 생각했다.
 *
 * 우선 건설에 대한 조건은 아래와 같다. 건설은 문제의 규칙을 그대로 코드에 적용했다.
 *
 * 범위를 체크할 때 기둥은 세로의 범위를 0 ~ n-1로,
 * 보는 가로의 범위를 0 ~ n-1로 생각했다.
 *
 * 1. 기둥 건설(아래 조건이면 건설)
 *   - 바닥이라면
 *   - 바로 아래에 '기둥'이 있다면
 *   - 기둥이 '|' 라면 아래 왼쪽에 '보'가 있는 경우
 *   - 기둥이 '|' 라면 아래 오른쪽(현재 위치)에 '보'가 있는 경우
 *
 * 2. 보 건설(아래 조건이면 건설)
 *   - 보가 '-'라면 왼편의 아래에 '기둥'이 있는 경우
 *   - 보가 '-'라면 오른편의 아래(현재 위치 바로 아래)에 '기둥'이 있는 경우
 *   - 보의 양쪽에 보가 둘 다 있는 경우
 *
 * 그리고 삭제를 구현하는 것이 엄청 오래 걸렸다. 해당 건축물이 제거됐을 때 주변의 어떤 건축물에 영향을 미치는 지 보고,
 * 해당 건축물을 제거했을 때 영향을 받은 건축물이 건설될 수 없다고 나오면 삭제가 어렵다고 판단했다.
 * 영향을 미치는 건축물은 위의 건설과 반대로 생각하면 된다.
 *
 * 3. 기둥 제거(제거하면 영향이 미치는 건축물)
 *   - 바로 위에 기둥이 있는 경우
 *   - 제거할 기둥이 '|'라면 위의 왼쪽에 '보'가 있는 경우
 *   - 제거할 기둥이 '|'라면 위의 오른쪽에 '보'가 있는 경우
 *
 * 4. 보 제거(제거하면 영향이 미치는 건축물)
 *   - 보가 '-'라면 왼편의 위에 '기둥'이 있는 경우
 *   - 보가 '-'라면 오른편의 위에 '기둥'이 있는 경우
 *   - 제거 대상의 보 기준 왼편의 보와 오른편의 보
 *
 * 건축물이 제거 대상인지 판단하기 전에 먼저 flag를 false로 한 뒤에 영향을 미치는 건축물을 판단한다.
 * 건축물이 제거 대상이면 과감히 제거하고, 제거 대상이 아니라면 flag를 복원시켜주었다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2020 Kakao Blind Recruitment> '기둥과 보 설치'

public class Programmers60061 {
    final int COLUMN = 0; //기둥
    final int BEAM = 1; //보
    final int BUILD = 1;
    final int REMOVE = 0;

    boolean [][] columnStatus; // 기둥의 상태
    boolean [][] beamStatus; // 보의 상태

    public int[][] solution(int n, int[][] build_frame) {
        List<int[]> answerList = new ArrayList<>();

        columnStatus = new boolean[n+1][n+1];
        beamStatus = new boolean[n+1][n+1];

        for (int i = 0; i < build_frame.length; i++) {
            int x = build_frame[i][0];
            int y = build_frame[i][1];

            if (build_frame[i][2] == COLUMN) { //기둥
                //삭제
                if (build_frame[i][3] == REMOVE) {
                    columnStatus[y][x] = false; //삭제를 해놓고 삭제가 안되는 '기둥'이라면 복원
                    if(!isPossibleColumnRemove(x, y, n)){
                        columnStatus[y][x] = true; //복원
                    }
                }

                //건설
                if (build_frame[i][3] == BUILD) {
                    if(isPossibleColumnBuild(x, y, n)){
                        columnStatus[y][x] = true;
                    }
                }
            } else if (build_frame[i][2] == BEAM) { //보
                //삭제
                if (build_frame[i][3] == REMOVE) {
                    beamStatus[y][x] = false; //삭제를 해놓고 삭제가 안되는 '보'라면 복원
                    if(!isPossibleBeamRemove(x, y, n)){
                        beamStatus[y][x] = true; //복원
                    }
                }

                //건설
                if (build_frame[i][3] == BUILD) {
                    if(isPossibleBeamBuild(x, y, n)){
                        beamStatus[y][x] = true;
                    }
                }
            }
        }

        for(int i=0;i<=n;i++){ // x 방향
            for(int j=0;j<=n;j++){ // y 방향
                if(isIn(j, i, n, COLUMN) && columnStatus[j][i]){
                    answerList.add(new int[]{i, j, COLUMN});
                }
                if(isIn(j, i, n, BEAM) && beamStatus[j][i]){
                    answerList.add(new int[]{i, j, BEAM});
                }
            }
        }
        return answerList.toArray(new int[0][]);
    }

    //'기둥'을 건설할 수 있는 지
    public boolean isPossibleColumnBuild(int x, int y, int n){
        if (!isIn(y, x, n, COLUMN)) { //범위 체크
            return false;
        }

        if (y == 0) { //바닥이면
            return true;
        }

        if (isIn(y-1, x, n, COLUMN) && columnStatus[y-1][x]) {
            return true;
        }

        //아래 기준 기둥의 왼편에 '보'가 있을 경우
        if (isIn(y, x-1, n, BEAM) && beamStatus[y][x-1]) {
            return true;
        }

        //아래 기준 기둥의 오른편에 '보'가 있을 경우
        if (isIn(y, x, n, BEAM) && beamStatus[y][x]) {
            return true;
        }

        return false;
    }

    //'보'를 건설할 수 있는 지
    public boolean isPossibleBeamBuild(int x, int y, int n){
        if (!isIn(y, x, n, BEAM)) { //범위 체크
            return false;
        }

        //한쪽을 기둥이 지탱하고 있는 경우
        if (isIn(y-1, x, n, COLUMN) && columnStatus[y-1][x]) {
            return true;
        }

        if (isIn(y-1, x+1, n, COLUMN) && columnStatus[y-1][x+1]) {
            return true;
        }

        //양쪽에 '보'가 둘 다 있는 경우
        if (isIn(y, x+1, n, BEAM) && beamStatus[y][x+1] && isIn(y, x-1, n, BEAM) && beamStatus[y][x-1]) {
            return true;
        }

        return false;
    }

    public boolean isPossibleColumnRemove(int x, int y, int n){
        if (!isIn(y, x, n, COLUMN)) {
            return false;
        }

        if(isIn(y+1, x, n, COLUMN) && columnStatus[y+1][x]){
            if(!isPossibleColumnBuild(x, y+1, n)){
                return false;
            }
        }

        if(isIn(y+1, x, n, BEAM) && beamStatus[y+1][x]){
            if(!isPossibleBeamBuild(x, y+1, n)){
                return false;
            }
        }

        if(isIn(y+1,x-1, n, BEAM) && beamStatus[y+1][x-1]){
            if(!isPossibleBeamBuild(x-1, y+1, n)){
                return false;
            }
        }

        return true;
    }

    public boolean isPossibleBeamRemove(int x, int y, int n){
        if (!isIn(y, x, n, BEAM)) {
            return false;
        }

        if(isIn(y, x, n, COLUMN) && columnStatus[y][x]){
            if(!isPossibleColumnBuild(x, y, n)){
                return false;
            }
        }

        if(isIn(y, x+1, n, COLUMN) && columnStatus[y][x+1]){
            if(!isPossibleColumnBuild(x+1, y, n)){
                return false;
            }
        }

        if(isIn(y, x+1, n, BEAM) && beamStatus[y][x+1]){
            if(!isPossibleBeamBuild(x+1, y, n)){
                return false;
            }
        }

        if(isIn(y, x-1, n, BEAM) && beamStatus[y][x-1]){
            if(!isPossibleBeamBuild(x-1, y, n)){
                return false;
            }
        }

        return true;
    }

    public boolean isIn(int y, int x, int n, int isColumn){
        if(isColumn == COLUMN) {
            return y >= 0 && y < n && x >= 0 && x <= n;
        }else{
            return y >= 0 && y <= n && x >= 0 && x < n;
        }
    }
}
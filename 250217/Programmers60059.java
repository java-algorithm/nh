/**
 * @author nakhoonchoi
 * @date 2025/02/12
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/60059
 * @caution
 * [고려사항]
 * 테스트 케이스 1번, 23~27번, 29~33번이 계속 틀렸었다.
 *
 * 기존에는 lock 배열 자체에 자물쇠의 빈 공간과 열쇠의 돌기가 맞다면 CHECK(2)로 배열의 값을 갱신해주고
 * lockInit 메소드에서 lock 배열을 입력과 같이 초기화 시켜주려했지만, 초기화 시점이 잘못되었다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2020 KAKAO BLIND RECRUITMENT> '자물쇠와 열쇠'

public class Programmers60059 {
    private final int CHECK = 2;
    private final int DUMMY_DATA = -1;

    public boolean solution(int[][] key, int[][] lock) {
        int N = lock.length;
        int M = key.length;

        if(isAllTrue(lock)){
            return true;
        }

        for(int i=0;i<4;i++){
            key = rotate(key);

            for(int j=0;j<M+N-1;j++){
                for(int k=0;k<M+N-1;k++){
                    lockInit(lock);
                    if(isMatch(key, j, k, lock)){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isMatch(int [][] key, int x, int y, int [][] lock){
        int N = lock.length;
        int M = key.length;
        int [][] keyStatusArr = new int[N + (2*M - 2)][N + (2*M - 2)];

        for(int i=0;i< keyStatusArr.length;i++){
            Arrays.fill(keyStatusArr[i], DUMMY_DATA);
        }

        for(int i=0;i<M;i++){
            for(int j=0;j<M;j++){
                keyStatusArr[x + i][y + j] = key[i][j];
            }
        }

        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(keyStatusArr[M-1+i][M-1+j] == 1 && lock[i][j] == 1){
                    return false;
                }else if(keyStatusArr[M-1+i][M-1+j] == 1 && lock[i][j] == 0){
                    lock[i][j] = CHECK;
                }
            }
        }

        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++) {
                if (lock[i][j] == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public void lockInit(int [][] lock){
        for(int i=0;i< lock.length;i++){
            for(int j=0;j< lock.length;j++){
                if(lock[i][j] == CHECK){
                    lock[i][j] = 0;
                }
            }
        }
    }

    public int [][] rotate(int [][] key){
        int M = key.length;
        int [][] temp = new int[M][M];

        for(int i=0;i<M;i++){
            for(int j=0;j<M;j++){
                temp[i][j] = key[j][M-i-1];
            }
        }

        return temp;
    }

    public boolean isAllTrue(int [][] lock){
        for(int i=0;i<lock.length;i++){
            for (int j = 0; j < lock.length; j++) {
                if(lock[i][j] != 1){
                    return false;
                }
            }
        }
        return true;
    }
}
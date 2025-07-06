/**
 * @author nakhoonchoi
 * @date 2025/07/06
 * @see https://leetcode.com/problems/valid-sudoku/description/
 * @mem 44.36MB
 * @time 2ms
 * @caution
 * [고려사항]
 * 문제 제목이 '유효한 스도쿠'여서 게임에 유효한 스도쿠 판인지 구하는 문제인 줄 알고 어려운 문제라고 생각했다.
 * 다행히 스도쿠의 규칙에 의해 현재 판의 정보가 가로, 세로, 구역에 1~9가 유효하게 잘 들어가있는 지만 보면 되는 문제였다.
 * a.k.a A Sudoku board (partially filled) could be valid but is not necessarily solvable.
 * 주어진 셀만 유효한 지 검사하면 되는 문제였고,
 * 가로, 세로, 구역을 나눠서 검사했다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//LeetCode <LeetCode> 'Valid Sudoku'

public class LeetCode36{
    BitSet checkBitSet = new BitSet(9);
    public boolean isValidSudoku(char[][] board) {
        for(int i=0;i<board.length;i++){
            if(!horizonCheck(board, i)){
                return false;
            }
        }
        for(int i=0;i<board.length;i++){
            if(!verticalCheck(board, i)){
                return false;
            }
        }
        for(int i=0;i<board.length/3;i++){
            for(int j=0;j<board.length/3;j++) {
                if(!sectionCheck(board, i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean horizonCheck(char[][] board, int i){
        checkBitSet.clear();
        for(int j=0;j<board.length;j++){
            if(board[i][j] == '.') continue;
            int num = board[i][j] - '0';
            if(checkBitSet.get(num - 1)){
                return false;
            }
            checkBitSet.set(num - 1);
        }
        return true;
    }

    public boolean verticalCheck(char[][] board, int i){
        checkBitSet.clear();
        for(int j=0;j<board.length;j++){
            if(board[j][i] == '.') continue;
            int num = board[j][i] - '0';
            if(checkBitSet.get(num - 1)){
                return false;
            }
            checkBitSet.set(num - 1);
        }
        return true;
    }

    public boolean sectionCheck(char[][] board, int i, int j){
        checkBitSet.clear();
        for(int k=0;k<3;k++){
            for(int m=0;m<3;m++){
                if(board[i*3+k][j*3+m] == '.') continue;
                int num = board[i*3+k][j*3+m] - '0';
                if(checkBitSet.get(num - 1)){
                    return false;
                }
                checkBitSet.set(num - 1);
            }
        }
        return true;
    }
}
/**
 * @author nakhoonchoi
 * @date 2025/03/22
 * @see https://boj.ma/18808
 * @mem 13,440kb
 * @time 116ms
 * @caution
 * [고려사항]
 * 삼성 기출과 비슷한 구현 문제였다.
 * 문제에 나와있는대로 코드로 구현하면 되는 문제였고, 주의할 점이 몇 개 있었다.
 * 우선 문제에서 특정 문장의 의미를 해석해보겠다.
 * - '스티커를 다 붙인 상태' : 개수만큼 주어진 스티커를 처음부터 끝까지 최대한 붙이고(검증하고) 마지막까지 확인한 뒤
 *
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <구현> '스티커 붙이기'

public class BOJ18808 {
    static int N, M, K;
    static List<int [][]> stickerList;
    static int [][] map;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        stickerList = new ArrayList<>();
        map = new int[N][M];

        for(int i=0;i<K;i++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int [][] sticker = new int[n][m];

            for(int j=0;j<n;j++){
                st = new StringTokenizer(br.readLine());
                for(int k=0;k<m;k++){
                    sticker[j][k] = Integer.parseInt(st.nextToken());
                }
            }
            stickerList.add(sticker);
        }

        for(int i=0;i<K;i++){
            int [][] sticker = stickerList.get(i);

            outer: for(int j=0;j<4;j++){
                int stickerN = sticker.length;
                int stickerM = sticker[0].length;

                for(int k=0;k<N-stickerN+1;k++){
                    for(int m=0;m<M-stickerM+1;m++){
                        if(isPointAvailable(k, m, sticker)){
                            printSticker(k, m, sticker);
                            break outer;
                        }
                    }
                }

                sticker = rotate(sticker);
            }
        }
        System.out.println(getStickerCount());
    }

    public static int getStickerCount(){
        int count = 0;
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                if(map[i][j] == 1){
                    count++;
                }
            }
        }
        return count;
    }

    public static void printSticker(int x, int y, int [][] sticker){
        int n = sticker.length;
        int m = sticker[0].length;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(sticker[i][j] == 1) {
                    map[x + i][y + j] = sticker[i][j];
                }
            }
        }
    }

    public static boolean isPointAvailable(int x, int y, int [][] sticker){
        int n = sticker.length;
        int m = sticker[0].length;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(!isIn(x+i, y+j)){
                    return false;
                }
                if(isIn(x+i, y+j) && sticker[i][j] * map[x+i][y+j] == 1){
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isEnd(){
        int flag = 1;

        int count = 0;
        outer : for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                if(map[i][j] == 0){
                    continue outer;
                }
            }
            count++;
        }

        flag *= count;
        if(flag == 0){
            return false;
        }

        outer : for(int i=0;i<M;i++){
            for(int j=0;j<N;j++){
                if(map[j][i] == 0){
                    continue outer;
                }
            }
            count++;
        }
        flag *= count;

        return flag > 0;
    }

    public static int [][] rotate(int [][] sticker){
        int n = sticker.length;
        int m = sticker[0].length;

        int [][] temp = new int[m][n];

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                temp[i][j] = sticker[n-j-1][i];
            }
        }

        return temp;
    }

    public static boolean isIn(int x, int y){
        return x>=0 && x<N && y>=0 && y<M;
    }
}
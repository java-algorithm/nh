/**
 * @author nakhoonchoi
 * @date 2025/03/20
 * @see https://boj.ma/9576
 * @mem 20,144kb
 * @time 240ms
 * @caution
 * [고려사항]
 * union-find를 기반으로 한 경로 압축으로 문제를 해결했다.
 *
 * 먼저 책의 정보가 start와 end가 있다고 하면, 아래와 같은 테스트케이스 때문에 start가 아닌 end 기준으로 정렬해야한다.
 * start로 정렬했었다면 3이 나올 것이지만 정답은 4이기 때문에 end를 기준으로 오름차순 정렬해야한다.

 * 1
 * 4 4
 * 1 2 
 * 1 4 
 * 2 2 
 * 3 3
 *
 * 경로 압축은 현재 가능한 책이 end 이하라면 배정 받을 수 있는 사람이라고 생각했고,
 * 배정 받았다면 현재 가능한 책 인덱스의 nextBook 배열 값을 findNextBook(현재 가능한 책 + 1)로 갱신해주었다.
 *
 * union-find의 경로 갱신 과정에서 헤매서 많이 오답을 받았었다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
import java.io.*;
//백준 <분리 집합> '책 나눠주기'

public class BOJ9576 {
    static int [] nextBook;
    static int [][] bookList;
    static int N, M;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;

        while(T-->0){
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            bookList = new int[M][2];
            nextBook = new int[N+2];

            initNextBook();

            int count = 0;

            for(int i=0;i<M;i++){
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());

                bookList[i][0] = start;
                bookList[i][1] = end;
            }

            Arrays.sort(bookList, (o1, o2) -> {
                if(o1[1] == o2[1]){
                    return Integer.compare(o1[0], o2[0]);
                }
                return Integer.compare(o1[1], o2[1]);
            });

            for(int i=0;i<M;i++){
                int start = bookList[i][0];
                int end = bookList[i][1];

                int nextIndex = findNextBook(start);

                if(nextIndex <= end){
                    count++;
                    nextBook[nextIndex] = findNextBook(nextIndex + 1);
                }
            }

            System.out.println(count);
        }
    }

    private static int findNextBook(int target){
        if(nextBook[target] != target) {
            nextBook[target] = findNextBook(nextBook[target]);
        }
        return nextBook[target];
    }

    private static void initNextBook() {
        for(int i=1;i<=N+1;i++){
            nextBook[i] = i;
        }
    }
}
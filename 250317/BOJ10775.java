/**
 * @author nakhoonchoi
 * @date 2025/03/12
 * @see https://boj.ma/10775
 * @mem 21,748kb
 * @time 172ms
 * @caution
 * [고려사항]
 * union-find, 분리집합 문제였다.
 *
 * 문제가 잘 이해되지 않았는데 1부터 g까지의 게이트에만 접근할 수 있기 때문에
 * 입력받은 g 게이트에 우선으로 먼저 도킹시켜 다음 비행기가 더 작은 g가 오더라도 도킹할 수 있도록 했다.
 *
 * init으로 각 게이트의 정보를 초기화하고,
 * parent 배열에는 현재 게이트가 이미 도킹 되었을 때 접근할 다음 게이트의 인덱스를 저장한다.
 *
 * findParent 메소드에서 도킹 될 시에 다음 게이트의 인덱스를 갱신하며 반환했고,
 * 0이 반환된다면 도킹할 게이트가 없다는 의미로 생각했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <분리 집합> '공항'

public class BOJ10775 {
    static int [] parent;
    static int G, P;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        G = Integer.parseInt(br.readLine());
        P = Integer.parseInt(br.readLine());

        parent = new int[G+1];

        init();

        int count = 0;

        for(int i=0;i<P;i++){
            int target = Integer.parseInt(br.readLine());

            if(findParent(target) != 0){
                count++;
            }else{
                break;
            }
        }

        System.out.println(count);
    }

    static void init(){
        for(int i=1;i<=G;i++){
            parent[i] = i;
        }
    }

    static int findParent(int target){
        if(target > 0 && parent[target] == target){
            parent[target] = parent[target - 1];
            return target;
        }else if(target == 0){
            return target;
        }

        return parent[target] = findParent(parent[target]);
    }
}
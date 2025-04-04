/**
 * @author nakhoonchoi
 * @date 2025/04/04
 * @see https://boj.ma/2141
 * @mem 47,596kb
 * @time 504ms
 * @caution
 * [고려사항]
 * 누적 합과 중앙값을 기반으로 한 그리디 알고리즘 문제였다.
 * 우체국에서 각 마을까지의 거리 합이 최소가 되어야하는 우체국 최적의 위치를 찾는 문제였다.
 * 인구 수(100,000)와 거리(1,000,000,000)의 곱이 int 형을 벗어나기 때문에 long 형으로 선언해야한다.
 * 마을과 마을의 거리는 위치 차의 절대값이다. 거리 = | a - b |
 * 예를 들어 각 마을마다 인구수가 1명으로 고정 되어있다면,
 * 마을의 위치가 1,5,7이라고 했을 때
 * f(x) = | x - 1 | + | x - 5 | + | x - 7 | 이고
 * x는 범위가 1~7 사이에 있을 것이고 중앙에 위치해야 유리할 것이라고 생각했다.
 * 먼저 중간값 4를 대입해보았는데 f(4)는 3 + 1 + 3 = 7로 우체국과 각 마을까지의 거리 합이 7이었다.
 * 그리고 가운데 | x - 5 |를 0으로 만들 수 있는 5를 우체국 위치로 대입해보았는데
 * f(5) = 4 + 0 + 2 = 6으로 더 최적의 값이 나왔고 다른 값을 넣어도 이보다 더 최적의 값이 나올 수 없었다.
 * 우체국이 마을에 있는 것(절대값 묶음 하나를 상쇄하는 것)이 유리하다는 것을 알았다.
 *
 * 하지만 이 문제는 각 마을마다 인구 수가 여러 명 있다.
 *
 * 3
 * 2 2
 * 4 1
 * 5 1
 * 의 경우에는
 * f(x) = | x - 2 | + | x - 2 | + | x - 4 | + | x - 5 | 가 될 것이다.
 * 여기에서 대략적으로 중간값인 3을 넣으면 f(3) = 1 + 1 + 2 = 5가 나온다.
 * 그리고 f(4) = 2 + 2 + 0 + 1 = 5
 * f(2) = 0 + 0 + 2 + 3 = 5
 * 같은 거리 이지만 우체국의 위치가 가장 작은 2가 최적의 값이다.
 * 이 규칙을 봤을 때 인구 수가 전체의 중간에 있는 마을의 중앙값이 최적이라고 생각했고, 이를 구하려고 했다.
 *
 * 처음에는 각 마을의 인구 수만큼 for문을 돌려서 List에 전부 넣고 가운데 인덱스의 값을 출력하려고 했으니
 * 100,000 * 1,000,000,000만큼 리스트에 값을 넣으려니 시간 초과가 발생했다.
 *
 * 입력 받은 마을의 정보를 위치 기준으로 오름차순하고
 * for문으로 순회하며 전체 인구 수의 중앙값이 위치한 마을의 위치를 출력해주었다.
 *
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <그리디> '우체국'

public class BOJ2141 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Town [] towns = new Town[N];
        StringTokenizer st;

        long totalPeople = 0;

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());

            long index = Long.parseLong(st.nextToken());
            long people = Long.parseLong(st.nextToken());

            towns[i] = new Town(index, people);
            totalPeople += people;
        }
        Arrays.sort(towns);

        long currentAddPeople = 0;

        for(int i=0;i<N;i++){
            currentAddPeople += towns[i].people;
            if (currentAddPeople >= (totalPeople + 1) / 2){
                System.out.println(towns[i].index);
                break;
            }
        }
    }

    static class Town implements Comparable<Town>{
        long index;
        long people;

        Town(long index, long people){
            this.index = index;
            this.people = people;
        }

        @Override
        public int compareTo(Town o) {
            return Long.compare(this.index, o.index);
        }
    }
}
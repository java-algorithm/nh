/**
 * @author nakhoonchoi
 * @date 2025/03/13
 * @see https://boj.ma/4195
 * @mem 57,948kb
 * @time 448ms
 * @caution
 * [고려사항]
 * union-find 문제였다.
 * parent 배열은 총 F명까지 들어올 수 있기 때문에 크기를 2*F + 1로 초기화했다.
 * 그리고 parent 배열의 모든 인덱스가 안 찰 수도 있기 때문에 모두 값을 -1로 설정했다.
 * 
 * parent 배열에 root의 인덱스를 갱신하되, 가장 최상단의 parent[root 인덱스]에는 (친구 네트워크의 총 크기 * -1)을 담았다.
 * 
 * 고로 parent[root index]에는 음수로서 자식노드들의 크기를 담고 있고, parent[자식 노드 index]에는 root index를 담고 있다.
 *
 * union 로직은 둘 다 root 노드일 경우 값 비교를 통해 절댓값이 작은 root 노드를 큰 root 노드에 이어붙인다.
 * find 메소드 재귀 호출을 통해 parent 배열 값을 root 노드로 갱신하면서 최상단 root 노드를 찾아 반환할 수 있다.
 * union 함수에서 친구 네트워크의 크기 계산과 root 노드 index 갱신을 동시에 할 수 있었다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <분리 집합> '친구 네트워크'
public class BOJ4195_2 {
    static int [] parent;
    static Map<String, Integer> names;
    static StringBuilder sb;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        sb = new StringBuilder();
        while(T-->0) {
            int F = Integer.parseInt(br.readLine());
            int nowIndex = 0;
            parent = new int[(2 * F) + 1];
            Arrays.fill(parent, -1);
            names = new HashMap<>();

            for(int i=0;i<F;i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                String first = st.nextToken();
                String second = st.nextToken();
                int firstKey, secondKey;

                if(names.containsKey(first)) {
                    firstKey = names.get(first);
                }else {
                    names.put(first, nowIndex);
                    firstKey = nowIndex++;
                }

                if(names.containsKey(second)) {
                    secondKey = names.get(second);
                }else {
                    names.put(second, nowIndex);
                    secondKey = nowIndex++;
                }

                int result = union(firstKey, secondKey) * -1;

                sb.append(result).append("\n");
            }
        }
        System.out.println(sb.toString());
    }

    public static int union(int a, int b) {
        int parentA = findParent(a);
        int parentB = findParent(b);

        if(parentA != parentB) {
            if(parentA > parentB) {
                parent[parentB] += parent[parentA];
                parent[parentA] = parentB;
            }
            else {
                parent[parentA] += parent[parentB];
                parent[parentB] = parentA;
            }
        }

        return parent[parentA] < 0 ? parent[parentA] : parent[parentB];
    }

    // 루트 노드를 찾는 함수
    public static int findParent(int index) {
        if(parent[index] < 0) { //음수라면 그 자체로 root이다.
            return index;
        }
        return parent[index] = findParent(parent[index]);
    }
}
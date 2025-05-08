/**
 * @author nakhoonchoi
 * @date 2025/05/08
 * @see https://boj.ma/1091
 * @mem 111,488kb
 * @time 584ms
 * @caution
 * [고려사항]
 * 삼성 기출에 비하면 비교적 간단한(?) 구현 문제였다.
 *
 * 입력은 카드 뭉치의 크기 N, 카드 뭉치 오름차순대로 몇 번 플레이어에 가야하는 지에 대한 정보 P,
 * 섞을 때 i번째 카드가 이동할 위치에 대한 정보 S로 주어졌다. 이 입력이 헷갈렸다.
 *
 * 플레이어가 0,1,2로 친절하게 되어있었기 때문에 먼저 ArrayList 타입 배열을 크기 3으로 초기화 했다.
 * N을 입력받고
 * P를 i=0부터 오름차순으로 입력받으면서 cardToPlayerArr[플레이어].add(i);
 * 로 각 플레이어에게 가야할 숫자를 리스트에 담아주었다.
 * S는 섞을 때의 규칙이므로 일반적인 배열 입력과 같이 값을 담았다.
 *
 * 섞은 횟수를 비슷하게 turn이라고 표현해본다.
 * 섞는 과정을 진행하면서 현재 카드 상황이 지난 turn에 나온적이 있다면,
 * 다시 탐색하면 안되기 때문에 방문처리가 필요해서 visited를 Set<String> 형태로 선언했다.
 * 그리고 Set에 String 타입을 제네릭으로 넣은 이유는 현재 카드 상태를
 * 문자열로 변환해서 처리하기 위해 String 타입으로 했다.
 *
 * 그리고 외에는 현재 카드 상태가 각 카드 번호가 적합한 플레이어에게 갔는지 판별하는 로직과
 * 카드를 섞는 로직이 필요하다.
 *
 * 조건에 적합한 카드 상태를 찾았는지 판별하는 flag 변수를 선언하고
 * while문을 반복했다.
 *
 * 카드를 섞는 로직은 새 배열을 선언해서
 * 현재 인덱스 i의 카드 번호를 rules[i] 위치의 새 배열에 순차적으로 저장한 뒤에
 * cards 배열을 덮어씌웠다.
 *
 * 이미 방문처리가 된 카드 상태라면 그대로 while 반복문을 탈출해서
 * 적합한 카드 상태를 못 찾았기 때문에 flag 기반으로 -1을 출력하도록 했다.
 *
 * 현재 카드 상태를 방문처리하고 조건에 적합한 카드 상태인지 판별하는 로직을 기반으로
 * 맞다면 flag를 true로 바꾸고 반복문을 탈출했다.
 *
 * 조건에 적합한 카드 상태인지 판별하는 로직은
 * cards 배열의 인덱스 i를 순회하면서 cards[i]가 i%3번 플레이어에게 있는지
 * cardToPlayerArr[i%3].contains(cards[i])로 찾아서
 * 리스트에 없다면 바로 false, 끝까지 적합하다면 true를 반환했다.
 *
 * flag가 true라면 shuffleCount, false라면 -1을 출력했다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <구현> '카드 섞기'

public class BOJ1091{
    static Set<String> visited;
    static List<Integer> [] cardToPlayerArr = new ArrayList[3];
    static int [] cards, rules;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        cards = new int[N];
        rules = new int[N];
        visited = new HashSet<>();

        for(int i=0;i<3;i++){
            cardToPlayerArr[i] = new ArrayList<>();
        }

        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i=0;i<N;i++){
            int player = Integer.parseInt(st.nextToken());
            cardToPlayerArr[player].add(i);
            cards[i] = i;
        }

        st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;i++){
            rules[i] = Integer.parseInt(st.nextToken());
        }

        boolean flag = false;
        int shuffleCount = 0;
        while(true){
            String cardToStr = intArrToStr(cards);
            if(visited.contains(cardToStr)){
                break;
            }
            visited.add(cardToStr);

            if(isMatch()){
                flag = true;
                break;
            }

            cards = shuffle(cards, rules);
            shuffleCount++;
        }

        System.out.println(flag ? shuffleCount : -1);
    }

    public static boolean isMatch(){
        for(int i=0;i<cards.length;i++){
            if(!cardToPlayerArr[i%3].contains(cards[i])){
                return false;
            }
        }
        return true;
    }

    public static int [] shuffle(int [] cards, int [] rules){
        int [] newArr = new int[cards.length];

        for(int i=0;i<rules.length;i++){
            newArr[rules[i]] = cards[i];
        }

        return newArr;
    }

    public static String intArrToStr(int [] arr){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<arr.length;i++){
            sb.append(arr[i]).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
/**
 * @author nakhoonchoi
 * @date 2025/06/11
 * @see https://boj.ma/2613
 * @mem 14,288kb
 * @time 112ms
 * @caution
 * [고려사항]
 * 이분 탐색을 이용해서 구슬 묶음의 개수를 만족하는 그룹 들의 최댓값의 최소(최적의 값)를 구하는 문제였다.
 * 최근에 풀었던 백준 17951 문제에서 힌트를 얻어서 이분 탐색의 left와 right 포인터의 기준을 정할 때
 * left는 구슬 중 최댓값, right는 전체 구슬의 합으로 구성해서 최적을 찾으려고 했다.
 * 다만 17951 문제와의 차이점은 해당 문제는 그룹들 중 각 그룹 합의 최소를 구하면 되는 문제였고,
 * 2613 문제는 그룹들 중 최댓값을 구해야하는 문제로 살짝 달랐다. 여기에서 오는 추가적인 과정이 있었다.
 *
 * 게시판에서 찾은 반례를 기준으로 설명하면 아래의 입력을 받았을 때
 * 4 4
 * 3 1 1 1
 *
 * 정답은
 * 3
 * 1 1 1 1
 * 이다.
 *
 * 하지만 최댓값을 기준으로 그룹을 나눠주기만 하면 아래와 같이 출력돼서 문제에서 원하는 대로 그룹을 네 개 만들 수가 없다.
 * 3
 * 1 3
 *
 * 💡 그래서 묶여 있는 구슬 세 개짜리 (1,1,1) 그룹을 split하는 작업이 필요하다.
 *   3      3
 *   1 3 -> 1 1 1 1 로 만들기 위해서
 *
 * 위에서 구한 것처럼 이분 탐색으로 구슬 그룹의 최적 값을 찾은 뒤에
 * 해당 반례처럼 그룹의 개수가 문제에서 원하는 M보다 낮을 경우에는 그룹의 개수를 맞춰줄 때까지 split 작업을 진행했다.
 *
 * split 작업이란 각 그룹을 순회하면서 그룹의 합이 최적값이 아닌 그룹 중에 그룹의 구성이 2개 이상이라면 해당 그룹을 반으로 쪼개는 작업이다.
 *
 * 💡 문제에서 정답이 가능한 경우의 수가 여러 개라면 그 중 아무거나 하나를 출력해도 된다고 했기 때문에
 * 최댓값 그룹만 고정하고 나머지는 split해도 괜찮다고 생각했다.
 *
 * 해당 split 로직을 통해서 AC를 받을 수 있었다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <이분 탐색> '숫자구슬'

public class BOJ2613 {
    static int N, groupCount;
    static int [] arr;
    static int [] answer;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        groupCount = Integer.parseInt(st.nextToken());

        arr = new int[N];
        st = new StringTokenizer(br.readLine());

        int sum = 0;
        int max = 0;
        for(int i=0;i<N;i++){
            arr[i] = Integer.parseInt(st.nextToken());
            sum += arr[i];
            max = Integer.max(max, arr[i]);
        }

        int left = max;
        int right = sum;

        while(left <= right){
            int mid = left + (right - left) / 2;

            int [] groupInfo = getGroupInfo(mid);

            if(groupInfo.length <= groupCount){
                right = mid - 1;
                answer = groupInfo;
            }else{
                left = mid + 1;
            }
        }

        while(answer.length != groupCount){
            int [] groupSum = getGroupSum();

            List<Integer> newGroupList = new ArrayList<>();

            int splitIndex = 0;
            boolean alreadyExists = false;
            int maxCount = 1;
            for(int i=0;i<answer.length;i++){
                if(maxCount < answer[i]){
                    if(alreadyExists || groupSum[i] != left) {
                        splitIndex = i;
                    }
                    maxCount = answer[i];
                }
                if(groupSum[i] == left){
                    alreadyExists = true;
                }
            }

            for(int i=0;i<answer.length;i++){
                if(i == splitIndex){
                    int half = answer[i] / 2;
                    newGroupList.add(half);
                    newGroupList.add(answer[i] - half);
                }else{
                    newGroupList.add(answer[i]);
                }
            }

            answer = newGroupList.stream().mapToInt(Integer::intValue).toArray();
        }

        System.out.println(left);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<answer.length;i++){
            sb.append(answer[i]).append(' ');
        }
        System.out.println(sb.substring(0, sb.length() - 1));
    }

    public static int [] getGroupSum(){
        int [] groupSum = new int[answer.length];
        int index = 0;
        for(int i=0;i<answer.length;i++){
            int sum = 0;
            for(int j=0;j<answer[i];j++){
                sum += arr[index++];
            }
            groupSum[i] = sum;
        }
        return groupSum;
    }

    public static int [] getGroupInfo(int target){
        int count = 0;
        int sum = 0;
        List<Integer> groupCountList = new ArrayList<>();
        for(int i=0;i<N;i++){
            if(sum + arr[i] > target){
                groupCountList.add(count);
                count = 1;
                sum = arr[i];
            }else{
                sum += arr[i];
                count++;
            }
        }
        groupCountList.add(count);

        return groupCountList.stream().mapToInt(Integer::intValue).toArray();
    }
}
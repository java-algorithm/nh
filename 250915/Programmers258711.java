/**
 * @author nakhoonchoi
 * @date 2025/09/14
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/258711
 * @caution
 * [고려사항]
 * 프로그래머스 레벨2의 문제 치고는 어려웠다.
 * 그래프 관련 문제는 항상 어렵게 다가오는 것같다.
 *
 * 방향이 있는 간선이기 때문에 DFS를 이용해서 각 그래프에 대한 갯수를 세다가
 * 8자 그래프를 계산할 때 중복 처리가 쉽지 않아서
 * 문제를 다시 읽어보고 단순하게 생각해보기로 했다.
 * - 입력된 그래프는 1개 이상의 정점과, 정점들을 연결하는 단방향 간선으로 이루어져 있다.
 * - 도넛 모양 그래프, 막대 모양 그래프, 8자 모양 그래프 총 수의 합은 2 이상이다.
 * - answer 배열에는 {시작 정점, 도넛, 막대, 8자} 의 정보를 담아서 반환한다.
 *
 * 우선 각 정점에 대해서 정점으로 들어오는 방향의 간선이 몇 개인지, 나가는 방향의 간선이 몇 개인지 계산했다.
 * 그리고 문제에서 각 그래프와 시작 정점의 특징에 대해 나와있는 점을 힌트로 해서 문제를 풀었다.
 * - 시작 정점은 들어오는 간선이 없고, 나가는 방향의 간선이 있다.
 * - 막대 그래프는 방향의 끝 정점 기준으로 들어오는 간선은 있지만, 나가는 방향의 간선은 없다.
 * - 도넛 그래프는 덩그러니 혼자 있는 경우를 비롯해서, 나가는 방향의 간선과 들어오는 방향의 간선이 둘 다 1개 이상이다.
 * - 8자 그래프는 도넛 그래프와 비슷하게 나가는 방향의 간선과 들어오는 방향의 간선이 둘 다 2개 이상이다.
 *
 * 막대 그래프와 도넛/8자 그래프는 성격이 완전히 다르지만, 도넛/8자 그래프의 개수 기준을 파악하는 데 어려웠다.
 * 일반적으로 8자 그래프를 생각하면 도넛 그래프 두 개로 이루어질 수 있다고 생각해서 어려웠다.
 * 8자 그래프의 개수 하나는 도넛 그래프 두 개로 될 수 없고, 8자 그래프에 구성되는 도넛 그래프는 도넛 그래프의 개수에 포함될 수 없다.
 *
 * ⚠️ 도넛 그래프의 개수를 파악하는 규칙은 찾지 못해서 힌트를 얻어서 풀었다.
 * (시작 정점에서 나가는 방향의 간선의 개수는 모든 그래프 경우의 수의 총 합과 대응된다.)
 * [입력사항]
 * [출력사항]
 */
//프로그래머스 <2024 KAKAO WINTER INTERNSHIP> '도넛과 막대 그래프'

public class Programmers258711{
    public int [] solution(int[][] edges) {
        int [] answer = new int[4]; //{시작 정점, 도넛, 막대, 8자}

        int maxIndex = 0;

        for(int [] edge : edges){
            maxIndex = Math.max(Math.max(maxIndex, edge[0]), edge[1]);
        }

        int [] inDir = new int[maxIndex + 1];
        int [] outDir = new int[maxIndex + 1];

        for(int [] edge : edges){
            inDir[edge[1]]++;
            outDir[edge[0]]++;
        }

        for(int i=1;i<=maxIndex;i++){
            if(outDir[i] - inDir[i] > 1){
                answer[0] = i;
                break;
            }
        }

        for(int i=1;i<=maxIndex;i++){
            if(outDir[i] == 0 && inDir[i] >= 1){ //막대 그래프
                answer[2]++;
            }

            if(outDir[i] >= 2 && inDir[i] >= 2){
                answer[3]++;
            }
        }

        answer[1] = outDir[answer[0]] - answer[2] - answer[3];

        return answer;
    }
}
/**
 * @author nakhoonchoi
 * @date 2025/08/13
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/42579
 * @caution
 * [고려사항]
 * 장르별 총 판매량을 담은 totalMap, 장르마다 각 판매량과 인덱스를 정렬할 우선순위 큐를 담을 genreMap으로
 * 총 두 개의 HashMap을 이용해서 풀었다.
 * 외부에 총 판매량 별로 정렬을 편하게 하기 위해 우선순위 큐를 하나 더 선언했다.
 *
 * 각 genres와 plays 값을 기반으로 totalMap과 genreMap에 값을 넣어주었다.
 * genre 별로 판매량의 인덱스가 '최대 2개'까지 answer에 반영될 수 있기 때문에
 * 장르 별로 2개가 되기 전에 우선순위 큐가 빈다면 NPE가 발생하지 않도록 방어 로직을 정해주었다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <해시> '베스트앨범'

public class Programmers42579 {
    public int[] solution(String[] genres, int[] plays) {
        Map<String, PriorityQueue<Info>> genreMap = new HashMap<>();
        Map<String, Integer> totalMap = new HashMap<>();
        PriorityQueue<GenreTotal> genreTotalPQ = new PriorityQueue<>();

        for(int i=0;i<genres.length;i++){
            String genre = genres[i];
            int play = plays[i];

            genreMap.putIfAbsent(genre, new PriorityQueue<>());
            genreMap.get(genre).offer(new Info(i, play));

            totalMap.put(genre, totalMap.getOrDefault(genre, 0) + play);
        }

        for(String key : totalMap.keySet()){
            genreTotalPQ.offer(new GenreTotal(key, totalMap.get(key)));
        }

        List<Integer> answerList = new ArrayList<>();

        while(!genreTotalPQ.isEmpty()){
            String key = genreTotalPQ.poll().genre;

            PriorityQueue<Info> pq = genreMap.get(key);

            int count = 0;
            while(!pq.isEmpty() && count < 2){
                answerList.add(pq.poll().index);
                count++;
            }
        }

        return answerList.stream().mapToInt(Integer::intValue).toArray();
    }

    class GenreTotal implements Comparable<GenreTotal>{
        String genre;
        int total;

        GenreTotal(String genre, int total){
            this.genre = genre;
            this.total = total;
        }

        @Override
        public int compareTo(GenreTotal o) {
            return Integer.compare(this.total, o.total) * -1;
        }
    }

    class Info implements Comparable<Info>{
        int index;
        int amount;

        Info(int index, int amount){
            this.index = index;
            this.amount = amount;
        }

        @Override
        public int compareTo(Info o) {
            return Integer.compare(this.amount, o.amount) * -1;
        }
    }
}
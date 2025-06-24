/**
 * @author nakhoonchoi
 * @date 2025/06/25
 * @see https://leetcode.com/problems/hash-divided-string/description/
 * @mem 45.38MB
 * @time 3ms
 * @caution
 * [고려사항]
 * 해시의 modular 연산 개념을 이용해서 풀 수 있는 문제였다.
 * 전체 문자열을 k 길이별로 잘라서 알파벳을 0~25의 형태로 만들고,
 * 그 묶음의 합을 26으로 나눈 나머지가 해시의 버킷 인덱스가 되어 압축(?) 하는 느낌으로 StringBuilder에 더해주면 된다.
 *
 * 문자열을 k 길이씩 자를 때 substring 메소드를 사용했는데 마지막 묶음에서
 * s.length()를 넘어가는 인덱스가 발생할 수 있기 때문에 substring의 두번째 인자는
 * Integer.min((i+1) * k, s.length() + 1)로 최솟값 연산을 해주었다.
 *
 * 💡 String의 substring은 Java 내부 String 버퍼에 값이 계속 쌓여서 비효율적인건가
 * substring을 사용하지 않고 단순 for문 반복한 로직이 2ms로 통과되었다.
 * [입력사항]
 * [출력사항]
 */
//LeetCode <LeetCode> 'Hash Divided String'

public class LeetCode3271 {
    final int ALPHABET_SIZE = 26;
    public String stringHash(String s, int k) {
        StringBuilder sb = new StringBuilder();
        int n = s.length();

        for(int i=0;i<n/k;i++){
            String str = s.substring(i*k, Integer.min((i+1) * k, n+1));

            int hash = 0;
            for(int j=0;j<str.length();j++){
                hash += alphabetToInt(str.charAt(j));
            }

            sb.append(intToAlphabet(hash % ALPHABET_SIZE));
        }

        return sb.toString();
    }

    public int alphabetToInt(char c){
        return c - 'a';
    }

    public char intToAlphabet(int gap){
        return (char)('a' + gap);
    }
}
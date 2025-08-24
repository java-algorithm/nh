/**
 * @author nakhoonchoi
 * @date 2025/08/24
 * @see https://boj.ma/11688
 * @mem 12,552kb
 * @time 92ms
 * @caution
 * [고려사항]
 * 최소공배수(LCM), 최대공약수(GCD) 문제였다.
 * 두 수의 최소공배수를 구하는 건 공식이 있어서 쉬운데
 * 세 수에 대한 최소공배수를 구하는 건 규칙을 조금 생각해야했다.
 *
 * 일단 문제는 a와 b, 타겟 L이 주어지고,
 * LCM(a,b,c) = L을 만족하는 가장 작은 c를 구하는 것이었다.
 *
 * 최적의 c 값을 찾는 과정은
 * a와 b의 최소공배수와 L의 약수들을 순회하면서,
 * 'LCM(a와 b의 최소공배수, L의 약수) = L'을 만족하는
 * L의 약수 최솟값을 구하면 된다.
 *
 * a와 b의 최소공배수부터 구하는 식을 공유하면,
 * LCM(a, b) = a * b / GCD(a, b);로 구한다.
 *
 * 먼저 GCD를 구하는 공식은 매번 외우려고 해도 잘 외워지지 않는데
 * 아래의 재귀 호출을 기반으로 구현했다.
 * 이에 따라 a와 b에 대한 최소공배수(LCM)도 자동으로 구할 수 있다.
 *
 * 그리고 L의 약수를 구하는 방법은
 * 1부터 √L 까지 순회하면서
 * 나누어 떨어지는 수에 대해서 L = divisor * back; 에 대해
 * divisor과 back을 관리하는 리스트에 각각 넣어주었다.
 *
 * 그리고 마지막에 두 리스트를 합치면서 back을 역순으로 해서 divisor에 합치면
 * 48의 약수가 divisor 리스트에 [1, 2, 3, 4, 6, 8, 12, 16, 24, 48]로 세팅될 것이다.
 *
 * 위에서 말한 a와 b의 최소공배수와 L의 약수들을 순회하면서,
 * 'LCM(a와 b의 최소공배수, L의 약수) = L'을 만족하는
 * L의 약수 최솟값을 구하면 된다.
 *
 * 이 문제는 L의 약수를 구해서 최적화하는 과정을 떠올리는 게 어려웠다.
 * L이 최대 10^12이기 때문에 O(N)의 시간 복잡도도 시간 초과가 발생할 수 있었다.
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.util.*;
//백준 <수학> '최소공배수 찾기'

public class BOJ11688 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());
        long L = Long.parseLong(st.nextToken());

        long A = lcm(a, b);
        List<Long> divisors = getDivisors(L);

        long result;
        for (long d : divisors) {
            if (lcm(A, d) == L) {
                result = d;
                System.out.println(result);
                return;
            }
        }

        System.out.println(-1);
    }

    public static long gcd(long x, long y) {
        if(y == 0){
            return x;
        }else{
            return gcd(y, x%y);
        }
    }

    public static long lcm(long x, long y) {
        return (x / gcd(x, y)) * y; // 오버플로 방지 위해 x/gcd 후 곱셈
    }

    public static List<Long> getDivisors(long n) {
        List<Long> divisors = new ArrayList<>();
        List<Long> back = new ArrayList<>();

        long sqrtN = (long)Math.sqrt(n);
        for (long i = 1; i <= sqrtN; i++) {
            if (n % i == 0) {
                divisors.add(i);
                if (i != n / i) {
                    back.add(n / i);
                }
            }
        }

        Collections.reverse(back);
        divisors.addAll(back);
        return divisors;
    }
}
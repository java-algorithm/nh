/**
 * @author nakhoonchoi
 * @date 2025/06/19
 * @see https://boj.ma/10573
 * @mem 18,128kb
 * @time 136ms
 * @caution
 * [고려사항]
 * 맨처음 봤을 때에는 수학 문제인 줄 알았다. 그래서 계산식을 세워야하나 했는데 분류를 보니 DP 문제였다.
 * DP라고 하기에는 어떻게 메모이제이션 배열을 설계해야할 지, 점화식을 어떻게 짜야할 지 감이 안 잡혔다.
 *
 * 우선, 입력 받는 수가 최대 80자리 라는 점이 눈에 띄어서 자릿수 기준으로 2차원 배열을 만들었다.
 * long [][] dp = new long[81][10]; //dp[자릿수][해당 자릿수가 0~9] = (10^(자릿수-1))의 자릿수가 0~9일 때 증가하는 수의 갯수
 * 예를 들어 dp[2][0]은 10^(2-1)의 자리가 0인 경우의 총 증가하는 수의 개수이다. 수의 범위로 하면 0부터 9까지의 총 증가하는 수가 될 것이다.
 *
 * 또 마찬가지로 dp[4][2]는 10^(4-1)의 자리가 2인 경우의 총 증가하는 수의 개수가 될 것이다.
 * 수의 범위로 하면 2000 ~ 2999 범위의 총 증가하는 수가 된다.
 *
 * 이 dp 배열을 입력 받기 전에 dp 초기값을 설정하고 dp[81][10] 크기만큼 모두 채워놓는다.
 * dp 초기 값 설정 : dp[1][0] ~ dp[1][9]는 전부 1이다.
 * 왜냐하면 0부터 9까지는 모두 증가하는 수이다.
 * ⚠️ 0이 포함된 이유는 증가하는 수는 '정수'이기 때문이다.
 *
 * 아래 3중 for 문을 이용해서 dp[81][10] 크기만큼 모두 채워놓는다.
 * for(int i=2;i<81;i++){
 *    for(int j=0;j<10;j++){
 *       for(int k=9;k>=j;k--){
 *          dp[i][j] += dp[i-1][k];
 *       }
 *    }
 * }
 * 에서 k를 9부터 낮추는 이유는 10 ~ 19를 세팅한다고 했을 때
 * 10의 자리수가 1이면 1의 자리수는 1~9가 모두 올 수 있기 때문에 역순으로 했다.
 * (10의 자리수가 3이면 1의 자리수는 3~9가 올 수 있다.)
 *
 * 그 뒤에 숫자를 입력 받는데 입력 받는 부분은 일반적으로 똑같고
 * 입력 받은 숫자 자체가 증가하는 수인지 판별해야하는 함수는 간단하다.
 * 문자열 길이가 최대 80이기 때문에 숫자를 두 자리씩 비교해서 증가하는 부분이 아니라면 -1을 출력해주었다.
 *
 * 입력 받은 숫자가 증가하는 수라면, dp 배열을 이용해서 총 증가하는 수의 갯수를 구해야한다.
 *
 * 여기에서 점화식을 구하는 힌트를 아래에 살짝 적겠다.
 * 입력을 13579라는 숫자를 입력 받았다고 했을 때,
 * 13579라는 숫자 앞에 (안보이는) beforeNumber 0이 있다고 치자.
 * 013579가 되겠다.
 * 0부터 013579까지의 증가하는 수를 구하려면 아래와 같은 값들이 필요하다.
 *
 * 1. 0부터 9999까지의 증가하는 수
 * 2. 11000부터 11999까지의 증가하는 수
 * 3. 12000부터 12999까지의 증가하는 수
 * 4. 13300부터 13399까지의 증가하는 수
 * 5. 13400부터 13499까지의 증가하는 수
 * 6. 13550부터 13559까지의 증가하는 수
 * 7. 13560부터 13569까지의 증가하는 수
 * 8. 13577
 * 9. 13578
 *
 * 이 중에서 1번은 쉽게 dp 배열에서 가져올 수 있다.
 * 하지만 2번은 11000부터 11999까지라고 하지만 1000부터 1999의 증가하는 수와도 같을 것이다.
 * 그래서 dp[4][1]로 커버가 가능하다. 해당 기준으로 아래에 변환해서 적어보겠다.
 * 1. 0부터 9999까지의 증가하는 수 -> dp[5][0]
 * 2. 1000부터 1999까지의 증가하는 수 -> dp[4][1]
 * 3. 2000부터 2999까지의 증가하는 수 -> dp[4][2]
 * 4. 300부터 399까지의 증가하는 수 -> dp[3][3]
 * 5. 400부터 499까지의 증가하는 수 -> dp[3][4]
 * 6. 50부터 59까지의 증가하는 수 -> dp[2][5]
 * 7. 60부터 69까지의 증가하는 수 -> dp[2][6]
 * 8. 13577 -> dp[1][7]
 * 9. 13578 -> dp[1][8]
 *
 * 💡 여기에서 궁금한 점이 있다. 0부터 13579의 증가하는 수를 구하는데, 왜 10000부터 10999 사이는 안볼까?
 * -> 이미 10000부터 10999까지는 증가하는 수가 아니기 때문에 경우의 수가 없어서 볼 일이 없다.
 *
 * 그래서 확실하게 증가하는 수가 아닌 범위는 보지 않기 위해
 * beforeNumber에 이전 자릿수를 저장해둬서
 * for문에서 i의 범위를 이전 자릿수부터(증가하는 수는 이전 자릿수와 같아도 되기 때문에) 현재 자리의 숫자까지로 정했다.
 *
 * int beforeDigitNum = 0;
 * while(num.compareTo(BigInteger.ZERO) > 0) {
 *     for(int i=beforeDigitNum;i<(num.divide(BigInteger.TEN.pow(digit - 1))).intValue();i++){
 *         total += dp[digit][i];
 *     }
 *     beforeDigitNum = (num.divide(BigInteger.TEN.pow(digit - 1))).intValue();
 *     num = num.remainder(BigInteger.TEN.pow(digit - 1));
 *     digit--;
 * }
 *
 * ⚠️ 그리고 중요한 점, 80자리의 수를 입력 받아야하기 때문에 BigInteger 클래스를 이용해서 입력 받고 계산해주어야한다.
 *
 * 위 식대로 하면 AC를 얻을 수 있다.
 *
 * 점화식을 짜는 데에 집중했던 문제다. 나중에 이 문제를 봤을 때 설명을 보고 이해할 수 있을 지 모르겠다...
 * [입력사항]
 * [출력사항]
 */
import java.io.*;
import java.math.BigInteger;
//백준 <DP> '증가하는 수'

public class BOJ10573 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        long [][] dp = new long[81][10];
        //dp[2][1] -> 10 ~ 19 중 증가하는 수 값
        //dp[2][0] -> 0 ~ 9 중 증가하는 수 값 -> 10
        //dp[1][1] -> 1 에 대한 증가하는 수 값 -> 1
        //dp[4][0] -> 0 ~ 999에 대한 증가하는 수 값 -> 220

        for(int i=0;i<10;i++){
            dp[1][i] = 1;
        }

        for(int i=2;i<81;i++){
            for(int j=0;j<10;j++){
                for(int k=9;k>=j;k--){
                    dp[i][j] += dp[i-1][k];
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        while(N-->0){
            String input = br.readLine();

            if(!isImproveNum(input)){
                sb.append(-1).append('\n');
            }else{
                long total = 0;
                BigInteger num = new BigInteger(input);

                int digit = input.length();

                //예를 들어 12345가 입력 받아졌다면,
                //0~9999의 증가하는 수 개수
                //11000~11999 증가하는 수 개수
                //12200~12299 증가하는 수 개수
                //12330~12339 증가하는 수 개수
                //12345 증가하는 수 개수
                //위 합을 구하면 0~12345 증가하는 수 개수가 나타난다.
                int beforeDigitNum = 0;
                while(num.compareTo(BigInteger.ZERO) > 0) {
                    for(int i=beforeDigitNum;i<(num.divide(BigInteger.TEN.pow(digit - 1))).intValue();i++){
                        total += dp[digit][i];
                    }
                    beforeDigitNum = (num.divide(BigInteger.TEN.pow(digit - 1))).intValue();
                    num = num.remainder(BigInteger.TEN.pow(digit - 1));
                    digit--;
                }

                sb.append(total).append('\n');
            }
        }

        System.out.println(sb);
    }

    public static boolean isImproveNum(String input){
        for(int i=1;i<input.length();i++){
            if(input.charAt(i-1) > input.charAt(i)){
                return false;
            }
        }
        return true;
    }
}
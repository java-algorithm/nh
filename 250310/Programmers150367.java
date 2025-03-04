/**
 * @author nakhoonchoi
 * @date 2025/03/04
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/150367
 * @caution
 * [고려사항]
 * 처음 문제를 봤을 때에는 이게 무슨 문제인가 싶었다.
 * 문제에서는 이진 트리를 수로 바꾸는 과정을 보여주고 있어서 반대로 생각했더니 이해됐다.
 * 주어진 수를 이진 트리로 표현해서 가능하면 1, 불가능하다면 0을 출력하면 된다.
 *
 * 우선 주어진 수에 Long.binaryString() 메소드를 이용해서 '문자열' 이진수로 표현했다.
 * 해당 이진수가 이진 트리의 길이로 맞춰주기 위해 부족한 자리수만큼 앞에 0을 붙인다.
 * 
 * 맞춰준 자리의 이진수 문자열을 재귀로 탐색하며 logN 방식으로 탐색했다.
 * 왼쪽과 오른쪽 문자열을 나눌 때에는 substring을 이용했다.
 * 가운데 숫자가 1인지 0인지에 따라 조건을 나누어서 가능/불가능을 판별했다.
 *
 * 조건에 따라 이진 트리로 표현이 가능한 수라면 1, 불가능하면 0을 반환했다.
 * [입력사항]
 * [출력사항]
 */
//프로그래머스 <2023 Kakao Blind Recruitment> '표현 가능한 이진트리'

public class Programmers150367 {
    public int[] solution(long[] numbers) {
        int[] answer = new int[numbers.length];
        for(int i=0;i<numbers.length;i++){
            answer[i] = isConvertBinaryTree(numbers[i]);
        }
        return answer;
    }

    /**
     * 자릿수를 0으로 채운 이진 트리 길이의 이진수가 이진 트리가 되는 지 판별하고 반환하는 함수
     * @param number
     * @return isConvertBinaryTree(이진수) = 가능하면 1, 불가능하면 0
     */
    public int isConvertBinaryTree(long number){
        String binaryString = getBinaryString(number);

        return isConvertBinaryTree(binaryString);
    }

    /**
     * 문자열을 이진수로 표현하고, 이진 트리에 맞는 길이로 맞춰준다.
     * @param number
     * @return 이진 트리에 맞는 길이로 맞춰진 이진수
     */
    public String getBinaryString(long number){
        StringBuilder sb = new StringBuilder();

        String binaryString = Long.toBinaryString(number);

        int binaryLength = getBinaryLength(binaryString.length());

        for(int i=0;i<binaryLength - binaryString.length();i++){
            sb.append('0');
        }

        sb.append(binaryString);

        return sb.toString();
    }

    /**
     * 이진 트리의 길이로 맞춰진 이진수가 이진 트리가 가능한 지 판별하는 함수
     *
     * - 만약 입력 받은 이진수의 가운데 숫자가 1이라면
     *      - 가운데 자리를 제외한 왼쪽과 오른쪽 문자열 둘 다 이진트리로 표현 가능해야만 가능하다.
     *      - 곱 연산으로 해결.
     * - 입력 받은 이진수의 가운데 숫자가 0이라면
     *      - 왼쪽과 오른쪽 문자열에 1이 하나라도 있으면 불가능, 없다면 가능하다.
     * @param binaryString
     * @return 가능하다면 1, 불가능 하다면 0 반환
     */
    public int isConvertBinaryTree(String binaryString){
        if(binaryString.length() == 1){
            return 1;
        }
        int mid = binaryString.length() / 2;
        String leftStr = binaryString.substring(0, mid);
        String rightStr = binaryString.substring(mid+1);
        if(binaryString.charAt(mid) == '1'){
            return isConvertBinaryTree(leftStr) * isConvertBinaryTree(rightStr);
        }else{
            if(leftStr.contains("1") || rightStr.contains("1")){
                return 0;
            }
        }
        
        return 1;
    }

    /**
     * 가능한 이진 트리의 길이를 반환하는 함수
     * 이진 트리의 길이는 (2^N - 1)이다.
     *
     * 💡 최대로 들어올 수 있는 10^15 값의 경우에는 이진수로 나타내면 50비트이므로
     * 가능한 이진 트리의 길이의 최대는 2^6 - 1 = 63이다.
     * @param length
     * @return 현재 길이보다 길면서 가능한 이진 트리의 길이 반환
     */
    public int getBinaryLength(int length){
        int i = 1;
        while(true){
            int binaryLength = (int)(Math.pow(2, i) - 1);

            if(binaryLength >= length){
                System.out.println(binaryLength);
                return binaryLength;
            }

            i++;
        }
    }
}
/**
 * @author nakhoon
 * @date 2025/02/19
 * @see  https://programmers.co.kr/learn/courses/30/lessons/81303
 * @caution
 * [고려사항]
 * 실제로 시험을 봤을 때 스택을 이용해서 정확성을 맞출 수 있었지만, OX를 판별하는 배열을 선언하는 것 보다는 StringBuilder의 insert 함수를 사용함으로써
 * 문제를 해결하는 것이 시간 복잡도 상 더 효율적이였다.
 * 삭제된 row만 스택에 담아놓고, 스택에 있는 인덱스 값에만 문자열 X를 insert 함수를 통해 추가해주면 해결된다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//프로그래머스 <2021 카카오 인턴십> '표 편집'
public class Programmers81303 {

	public String solution(int n, int k, String[] cmd) {
		Stack<Integer> stack = new Stack<>();
		int table_size = n;
        for(int i=0;i<cmd.length;i++) {
        	String [] splitStr = cmd[i].split(" ");

        	switch(splitStr[0]) {
        	case "U":
        		k -= Integer.parseInt(splitStr[1]);
        		break;
        	case "D":
        		k += Integer.parseInt(splitStr[1]);
        		break;
        	case "C":
        		stack.push(k);
        		table_size--;
        		if(k == table_size)
        			k--;
        		break;
        	case "Z":
        		if(stack.pop() <= k) {
        			k++;
        		}
        		table_size++;
        		break;
        	}
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<table_size;i++) {
        	sb.append("O");
        }
        while(!stack.isEmpty()) {
        	sb.insert(stack.pop().intValue(), "X");
        }
        return sb.toString();
    }
}
import java.util.*;

public class ListExample {
    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        long start;
        long end;

        start = System.currentTimeMillis();

        for(int i=1;i<=100000;i++){
            arrayList.add(i);
        }

        end = System.currentTimeMillis();

        System.out.println("arrayList 100000 add time : " + (end - start));

        start = System.currentTimeMillis();

        for(int i=1;i<=100000;i++){
            linkedList.add(i);
        }

        end = System.currentTimeMillis();

        System.out.println("linkedList 100000 add time : " + (end - start));

        start = System.currentTimeMillis();

        int sum = 0;
        for(int i=0;i<arrayList.size();i++){
            int num = arrayList.get(i);
            sum += num;
        }

        end = System.currentTimeMillis();

        System.out.println("sum : " + sum);
        System.out.println("arrayList for loop by index elapsed time : " + (end - start));

        start = System.currentTimeMillis();

        sum = 0;
        for(int i=0;i<linkedList.size();i++){
            int num = linkedList.get(i);
            sum += num;
        }

        end = System.currentTimeMillis();

        System.out.println("sum : " + sum);
        System.out.println("linkedList for loop by index elapsed time : " + (end - start));

        start = System.currentTimeMillis();

        sum = 0;
        for(int num : arrayList){
            sum += num;
        }

        end = System.currentTimeMillis();

        System.out.println("sum : " + sum);
        System.out.println("arrayList enhanced for loop by index elapsed time : " + (end - start));

        sum = 0;
        for(int num : linkedList){
            sum += num;
        }

        end = System.currentTimeMillis();

        System.out.println("sum : " + sum);
        System.out.println("linkedList enhanced for loop by index elapsed time : " + (end - start));

        start = System.currentTimeMillis();

        Iterator<Integer> it = linkedList.iterator();

        sum = 0;
        while(it.hasNext()){
            sum += it.next();
        }

        end = System.currentTimeMillis();

        System.out.println("sum : " + sum);
        System.out.println("linkedList iterator elapsed time : " + (end - start));

        start = System.currentTimeMillis();

        Iterator<Integer> descendingIterator = ((LinkedList<Integer>) linkedList).descendingIterator();

        sum = 0;
        while(descendingIterator.hasNext()){
            sum += descendingIterator.next();
        }

        end = System.currentTimeMillis();

        System.out.println("sum : " + sum);
        System.out.println("linkedList iterator reverse order elapsed time : " + (end - start));
    }
}
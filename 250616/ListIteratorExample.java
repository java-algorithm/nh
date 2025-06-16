import java.util.*;

public class ListIteratorExample {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        for(int i=0;i<26;i++){
            list.add(i);
        }

        StringBuilder sb;
        System.out.println("---------ArrayList 정방향 Iterator---------");

        Iterator<Integer> iterator = list.iterator();

        sb = new StringBuilder();
        while(iterator.hasNext()){
            sb.append(iterator.next()).append(' ');
        }
        System.out.println(sb);

        System.out.println("---------ArrayList 정방향 Iterator---------");
        System.out.println();
        System.out.println("---------ArrayList 양방향 Iterator---------");

        ListIterator<Integer> listIterator = list.listIterator(list.size());

        sb = new StringBuilder();
        while(listIterator.hasPrevious()){
            sb.append(listIterator.previous()).append(' ');
        }
        System.out.println(sb);

        System.out.println("---------ArrayList 양방향 Iterator---------");
        System.out.println();

        List<Integer> linkedList = new LinkedList<>();

        for(int i=0;i<26;i++){
            linkedList.add(i);
        }

        System.out.println("---------LinkedList 정방향 Iterator---------");

        iterator = linkedList.iterator();

        sb = new StringBuilder();
        while(iterator.hasNext()){
            sb.append(iterator.next()).append(' ');
        }
        System.out.println(sb);

        System.out.println("---------LinkedList 정방향 Iterator---------");
        System.out.println();
        System.out.println("---------LinkedList 역방향 Iterator---------");

        Iterator descendingIterator = ((LinkedList) linkedList).descendingIterator();

        sb = new StringBuilder();
        while(descendingIterator.hasNext()){
            sb.append(descendingIterator.next()).append(' ');
        }
        System.out.println(sb);

        System.out.println("---------LinkedList 역방향 Iterator---------");
        System.out.println();
        System.out.println("---------LinkedList 양방향 Iterator---------");

        listIterator = linkedList.listIterator(list.size());

        sb = new StringBuilder();
        while(listIterator.hasPrevious()){
            sb.append(listIterator.previous()).append(' ');
        }
        System.out.println(sb);

        System.out.println("---------LinkedList 양방향 Iterator---------");
    }
}

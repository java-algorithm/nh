/**
 * @author nakhoonchoi
 * @date 2025/03/06
 * @caution
 * [고려사항]
 * 인덱스 기준으로 식물을 HashMap으로 관리했다.
 *
 * 하나의 사이클의 로직을 순서대로 말하면,
 * - orders[i]의 인덱스 기준 식물이 살아있다면 상태를 초기화한다.
 * - hashMap keySet을 순회해서 orders[i]을 제외한 식물의 상태를 1씩 깎는다.
 * - 식물의 상태값이 0이 되면 맵에서 지워준다.
 * - 하나의 사이클을 돌고 HashMap의 keySet의 사이즈를 결과로 담았다.
 * [입력사항]
 * [출력사항]
 */
import java.util.*;
//'Happy Plant'

public class HappyPlant {
    public int [] getResult(int [] emotions, int [] orders){
        int [] result = new int[orders.length];
        Map<Integer, Plant> plantMap = new HashMap<>();
        for(int i=0;i<emotions.length;i++){
            plantMap.put(i+1, new Plant(emotions[i]));
        }

        for(int i=0;i<orders.length;i++){
            if(plantMap.containsKey(orders[i])){
                plantMap.get(orders[i]).init();
            }

            List<Integer> removeTargetList = new ArrayList<>();

            for(int index : plantMap.keySet()){
                if(index != orders[i]){
                    Plant plant = plantMap.get(index);
                    plant.doCycle();

                    if(plant.isDead()){
                        removeTargetList.add(index);
                    }
                }
            }

            for(int removeTarget : removeTargetList){
                plantMap.remove(removeTarget);
            }

            result[i] = plantMap.keySet().size();
        }

        return result;
    }

    class Plant{
        int curEmotion;
        int initEmotion;


        Plant(int initEmotion){
            this.curEmotion = initEmotion;
            this.initEmotion = initEmotion;
        }

        void init(){
            this.curEmotion = initEmotion;
        }

        void doCycle(){
            this.curEmotion--;
        }

        boolean isDead(){
            return this.curEmotion == 0;
        }
    }
}

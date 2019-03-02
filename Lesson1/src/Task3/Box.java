package Task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Box <E extends Fruit> {

    private  List<E> boxFruit = new ArrayList<>();

    public Box (List<E> nameFruit) {

        this.boxFruit.addAll(nameFruit);
    }

    public Box (E...nameFruit) {
        this.boxFruit.addAll(Arrays.asList(nameFruit));
    }


    public void intersperse (Box <E> newBox) { //пересыпаем из этой коробки в коробку в параметрах

        for (E fruit: boxFruit) {
            newBox.addFruit(fruit);
        }
        boxFruit.clear();
    }


    public void addFruit (E newFruit){
        boxFruit.add(newFruit);
    }

    public float getWeightBox(){

        return boxFruit.size()*boxFruit.get(0).getWeight();
    }

    public void compareBox (Box<?> newBox) {

        if (Math.abs(this.getWeightBox() - newBox.getWeightBox()) < 0.0001) {
            System.out.println("Массы коробок равны");
        }else {
            System.out.println("Массы коробок не равны");
        }
    }

    public void printBoxFruit () {
        System.out.println(boxFruit.getClass().getName());
        System.out.println(getWeightBox());

    }



}

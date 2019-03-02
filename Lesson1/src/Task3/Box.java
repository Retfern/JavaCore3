package Task3;

import java.util.ArrayList;
import java.util.List;

public class Box <E extends Fruit> {
    private int numberFruit;
    private E nameFruit;
    private  List<E> boxFruit;

    public Box (E nameFruit, int numberFruit) {

        if (numberFruit>=0) {
            this.numberFruit=numberFruit;
            boxFruit = new ArrayList<>();
            this.nameFruit=nameFruit;
            addArray(numberFruit);
        }else {
            System.out.println("Количество фруктов должно быть целым числом больше нуля");
        }
    }

    private E getNameFruit () {
        return nameFruit;
    }

    private int getNumberFruit () {
        return numberFruit;
    }

    private void addArray (int numbers) {
        for (int i=0; i<numbers; i++) {
            boxFruit.add(nameFruit);
        }
    }

    public void intersperse (Box newBox) { //пересыпаем в эту коробку из коробки в параметрах

        addFruit((E)newBox.getNameFruit(), newBox.getNumberFruit());
        if (compareFruit ((E)newBox.getNameFruit())) {newBox.removeFruit();}
    }

    private void removeFruit(){
        boxFruit.clear();
        numberFruit=0;
    }

    public void addFruit (E newFruit, int numbers){
        if (compareFruit(newFruit)) {
            addArray(numbers);
            numberFruit=numberFruit+numbers;
        }else {
            System.out.println("вы не можите положить в эту коробку " + newFruit.getClass().getName());
        }
    }

    public float getWeightBox(){
        return numberFruit*nameFruit.getWeight();
    }

    public void compareBox (Box newBox) {

        if (Math.abs(this.getWeightBox() - newBox.getWeightBox()) < 0.0001) {
            System.out.println("Массы коробок равны");
        }else {
            System.out.println("Массы коробок не равны");
        }
    }

    private boolean compareFruit (E newFruit) {
       return nameFruit.equals(newFruit);
    }

    public void printBoxFruit () {
        System.out.println(nameFruit.getClass().getName());
        System.out.println(numberFruit);

    }



}

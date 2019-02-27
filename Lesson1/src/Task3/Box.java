package Task3;

import java.util.ArrayList;
import java.util.List;

public class Box <E extends Fruit & Comparable> {
    private int numberFruit;
    private E nameFruit;
    private  List<Float> boxFruit;

    public Box (E nameFruit, int numberFruit) {
        this.numberFruit=numberFruit;
        this.nameFruit=nameFruit;
        boxFruit = new ArrayList<>();
        addArray(numberFruit);
    }

    private E getNameFruit () {
        return nameFruit;
    }

    private int getNumberFruit () {
        return numberFruit;
    }

    private void addArray (int numbers) {
        for (int i=0; i<numbers; i++) {
            boxFruit.add(nameFruit.getWeight());
        }
    }

    public void intersperse (Box newBox) { //пересыпаем в эту коробку из коробки в параметрах

        addFruit(newBox.getNameFruit(), newBox.getNumberFruit());
        if (compareFruit (newBox.getNameFruit())) {newBox.removeFruit(newBox.getNumberFruit());}
    }

    private void removeFruit(int numbers){
        if ((numbers>=0)&(numbers<=numberFruit)) {
            for (int i=0; i<numbers; i++) {
                boxFruit.remove(nameFruit.getWeight());
                numberFruit--;
            }
        }else {
            System.out.println("Введите корректное число удалённых фруктов, текущее количество фруктов в коробке " + numberFruit);
        }
    }

    public void addFruit (Fruit newFruit, int numbers){
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
        if (newBox.getWeightBox()==getWeightBox()) {
            System.out.println("Массы коробок равны");
        }else {
            System.out.println("Массы коробок не равны");
        }
    }

    private boolean compareFruit (Fruit newFruit) {
       return nameFruit.equals(newFruit);
    }

    public void printBoxFruit () {
        System.out.println(nameFruit.getClass().getName());
        System.out.print(boxFruit);
        System.out.println(" ");
    }



}

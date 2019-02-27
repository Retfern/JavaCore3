import java.util.ArrayList;
import java.util.List;

public class Task1 <E> {

    private E[] array;
    private List<E> list;
    private int currentSize;

    public Task1(int maxSize) {
        this.array = (E[]) new Object[maxSize];
        this.list= new ArrayList<>();
    }

    public void add(E value) {
        add(value, currentSize);
    }

    public void add(E value, int index) {
        array[index] = value;
        currentSize++;
    }

    public void remove(int index) {
        array[index] = null;
        currentSize--;
    }

    public void display() {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public void checkArray (int index1, int index2) {
        if (checkIndex(index1) & checkIndex(index2)) {
            E el = array[index1];
            array[index1]=array[index2];
            array[index2]=el;
        }else {
            System.out.println("Укажите корректные позиции элементов массива");
        }

    }

    public  void toList() {
        for (int i=0; i<array.length; i++){
            list.add(array[i]);
        }
    }

    public  void printList ()
    {
        System.out.print(list);
    }

    private boolean checkIndex (int index) {
        return (index>=0)&(index<=currentSize);
    }


}

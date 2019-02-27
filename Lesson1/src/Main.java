
public class Main {

    public static void main(String[] args) {

        Task1<Object> array = new Task1<>(5);
        array.add("2");
        array.add("ttt");
        array.add(3);
        array.add(4.0);
        array.add("s");
        array.display();
        System.out.println("---");
        array.checkArray(0, 1);
        array.display();

        array.toList();
        array.printList();

    }



}

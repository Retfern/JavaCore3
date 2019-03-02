package Task3;

public class MainBox {
    public static void main(String[] args) {
        Fruit apple = new Apple();
        Fruit orange = new Orange();
        Box apBox = new Box (apple, 3);
        Box orBox = new Box (orange, 4);
        apBox.printBoxFruit();
        orBox.printBoxFruit();
        apBox.addFruit(apple, 3);
        apBox.printBoxFruit();
        apBox.addFruit(orange, 1);
        apBox.printBoxFruit();

        apBox.compareBox(orBox);

        apBox.intersperse(orBox);

        Box apBox2 = new Box (apple, 2);
        apBox2.printBoxFruit();
        apBox.intersperse(apBox2);
        apBox2.printBoxFruit();
        apBox.printBoxFruit();
    }
}

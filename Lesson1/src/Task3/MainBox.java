package Task3;

public class MainBox {
    public static void main(String[] args) {
        Box <Apple> apBox = new Box<>();
        Box <Orange> orBox = new Box<>();
        apBox.addFruit(new Apple());
        apBox.addFruit(new Apple());
        orBox.addFruit(new Orange());

        apBox.printBoxFruit();
        orBox.printBoxFruit();
        //apBox.addFruit(new Orange());
        //orBox.addFruit(new Apple());

        apBox.compareBox(orBox);

        //apBox.intersperse(orBox);

        Box <Apple> apBox2 = new Box <>();
        apBox2.addFruit(new Apple());

        apBox2.printBoxFruit();

        apBox.intersperse(apBox2);

        apBox2.printBoxFruit();
        apBox.printBoxFruit();
    }
}

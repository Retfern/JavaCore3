import MyAnnotation.AfterSuite;
import MyAnnotation.BeforeSuite;
import MyAnnotation.Test;

public class Test1 {
    public String name;
    public Test1 () {
        name="Test1";
    }
    @AfterSuite
    public void initTest1 () {
        System.out.println("Тест1 начат");
    }

    @Test(priority = 1)
    public void executTest1 () {
        System.out.println("Тест1 выполняется, приоритет 1");
    }

    //@Test(priority = 1)
    public void executTest2 () {
        System.out.println("Тест1 выполняется, приоритет 1");
    }

    @Test(priority = 3)
    public void executTest4 () {
        System.out.println("Тест1 выполняется, приоритет 3");
    }

    @Test(priority = 6)
    public void executTest1Next () {
        System.out.println("Тест1 выполняется, приоритет 6");
    }
    @Test(priority = 6)
    public void executTest1Next2 () {
        System.out.println("Тест1 выполняется, приоритет 6");
    }

    @BeforeSuite
    public void endTest1 () {
        System.out.println("Тест1 закончен");
    }


}

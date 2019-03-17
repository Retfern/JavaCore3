

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static AtomicInteger ready = new AtomicInteger();
    //public static AtomicBoolean winner = new AtomicBoolean(false);
    public static void main(String[] args) throws InterruptedException {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT);
        Semaphore smp = new Semaphore(CARS_COUNT/2);
        Thread [] threads = new Thread[CARS_COUNT];

        //System.out.println("AtomicBoolean " + winner.get());
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cb, smp, ready);
        }


        for (int i = 0; i < cars.length; i++) {
            threads[i]= new Thread(cars[i]);
            threads[i].start();
        }

        while (true) {
            if (ready.get()==CARS_COUNT){
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
                break;
            }
        }


        for (int i = 0; i < cars.length; i++) {
            threads[i].join();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}





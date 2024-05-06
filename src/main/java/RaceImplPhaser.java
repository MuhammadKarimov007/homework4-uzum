import java.util.concurrent.Phaser;

public class RaceImplPhaser {
    private static final Phaser START = new Phaser(8);
    private static final int trackLength = 500000;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            new Thread(new Car1(i, (int) (Math.random()*100+50))).start();
            Thread.sleep(1000);
        }



        Thread.sleep(1000);
        System.out.println("На старт!");
        START.arrive();
        Thread.sleep(1000);
        System.out.println("Внимание!");
        START.arrive();

        Thread.sleep(1000);
        System.out.println("Марш!");
        START.arrive();


        START.forceTermination();
    }

    public static class Car1 implements Runnable {
        private final int carNumber;
        private final int carSpeed;
        public Car1(int carNumber, int carSpeed) {
            this.carNumber = carNumber;
            this.carSpeed = carSpeed;
        }
        public void run() {
            try {
                System.out.printf("Автомобиль №%d подъехал к стартовой прямой.\n", carNumber);
                START.register();

                START.awaitAdvance(8);
                START.arriveAndAwaitAdvance();
                Thread.sleep(trackLength/carSpeed);
                System.out.printf("Автомобиль №%d финишировал!\n", carNumber);
            } catch (InterruptedException ignore) {

            }
        }
    }
}

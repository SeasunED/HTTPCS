package sample;

import java.util.Random;
public class Ayudante {
    public Ayudante(){}

    public static abstract class fn {
        /**
         * Duerme el hilo unos segundos (random)
         */
        public static void sleep() {
            Random r = new Random();
            try {
                int t = r.nextInt(6000) + 500;
                Thread.sleep(t);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}

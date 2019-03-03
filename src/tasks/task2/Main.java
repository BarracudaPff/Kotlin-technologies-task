package tasks.task2;

import java.util.*;

public class Main {
    private Collection<Object> results;
    private boolean continueWork = true;

    public static void main(String[] args) {
        Prod<Integer>[] prods = new Prod[100];
        for (int i = 0; i < 100; i++) {
            prods[i] = new Prod(i * 100);
        }
        Main main = new Main();
        main.results = Collections.synchronizedSet(new HashSet<>());
        Con<Integer, Object>[] cons = new Con[30];

        for (int i = 0; i < cons.length; i++) {
            cons[i] = new Con();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> main.calculate(prods, cons)).start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        main.stop();
        System.out.println(main.results);
    }

    private void calculate(Prod<Integer>[] prods, Con<Integer, Object>[] cons) {
        while (continueWork) {
            for (Prod<Integer> p : prods) {
                for (Con<Integer, Object> c : cons) {
                    if (!c.isBusy) {
                        Object calculated = c.take(p.give());
                        results.add(calculated);
                        break;
                    }
                }
            }
        }
    }

    private void stop() {
        continueWork = false;
    }

    public static class Prod<T> {
        public boolean isReady;
        private int seed;

        public Prod(int seed) {
            this.seed = seed;
        }

        public T give() {
            T result = null;
            if (isReady) {
                seed++;
                //calculate result from seed here
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

    }

    public static class Con<T, R> {
        public boolean isBusy;

        public R take(T x, Prod<T> p) {
            T t = p.give();
            return take(t);
        }

        private R take(T x) {
            isBusy = true;
            R result = null;
            //calculate result
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isBusy = false;
            return result;
        }

    }
}

package ru.job4j.pool;

import org.junit.Test;

public class ThreadPoolTest {

    @Test
    public void when() {
        ThreadPool threadPool = new ThreadPool(Runtime.getRuntime().availableProcessors());
        Runnable runnableOne = () -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        };
        Runnable runnableTwo = () -> {
            for (int i = 10; i >= 0; i--) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        };
        threadPool.work(runnableOne);
        threadPool.work(runnableTwo);
    }
}

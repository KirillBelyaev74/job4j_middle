package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public ThreadPool(int size) {
        for (int i = 0; i <= size; i++) {
            this.threads.add(new Task());
            this.threads.get(i).start();
        }
    }

    public void work(Runnable job) {
        this.tasks.offer(job);
        threads.forEach(t -> System.out.println(t.getState()));
    }

    public void shutdown() {
        this.threads.forEach(Thread::interrupt);
    }

    private class Task extends Thread {

        @Override
        public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

            }
        }
    }


    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(Runtime.getRuntime().availableProcessors());
        Runnable runnable1 = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
        };
        Runnable runnable2 = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
        };
        Runnable runnable3 = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
        };
        Runnable runnable4 = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
        };
        threadPool.work(runnable1);
        threadPool.work(runnable2);
        threadPool.work(runnable3);
        threadPool.work(runnable4);
        threadPool.shutdown();
    }
}
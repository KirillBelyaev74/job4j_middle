package ru.job4j.wait;

public class CountBarrier {

    private final int total;

    private int count;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() {
        while (this.count == this.total) {
            try {
                notify();
                System.out.println(Thread.currentThread().getName());
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.count++);
    }

    public synchronized void await() {
        while (this.count != this.total) {
            try {
                System.out.println(Thread.currentThread().getName());
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.count = 0;
        notify();
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(2);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                countBarrier.count();
            }
        });
        Thread thread2 = new Thread(countBarrier::await);
        thread2.start();
        thread1.start();
    }
}

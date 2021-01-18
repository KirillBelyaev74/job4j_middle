package ru.job4j.concurrent;

public class ThreadSleep {

    public static void main(String[] args) {
        for (int index = 0; index <= 100; index++) {
            try {
                Thread.sleep(1000);
                System.out.print("\rLoading : " + index  + "%");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

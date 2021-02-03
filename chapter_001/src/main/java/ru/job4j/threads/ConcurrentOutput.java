package ru.job4j.threads;

public class ConcurrentOutput {

    public static void main(String[] args) {

        Thread first = new Thread();
        Thread second = new Thread();

        System.out.println(first.getName());
        System.out.println(second.getName());

        second.start();
        first.start();

        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
            System.out.println(second.getState());
        }

        System.out.println(first.getState());
        System.out.println(second.getState());
    }
}

package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }

    @Override
    public void run(){
        int count = 0;
        String progress;
        while (!Thread.currentThread().isInterrupted()) {
            progress = "\\";
            if (count % 2 == 0) {
                progress = "/";
            }
            System.out.print("\rLoad: \\ | " + progress);
            count++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

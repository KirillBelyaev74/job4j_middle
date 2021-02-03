package ru.job4j.threads;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }

    @Override
    public void run() {
        String[] progress = {"|", "/", "_", "\\"};
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if (count == 4) {
                count = 0;
            }
            System.out.print("\rLoad: " + progress[count]);
            count++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

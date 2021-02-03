package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService executorService;
    private final StringBuffer stringBuffer = new StringBuffer();

    public EmailNotification(int numberProcessors) {
        executorService = Executors.newFixedThreadPool(numberProcessors);
    }

    public void emailTo(User user) {
       executorService.execute(() -> {
           String subject = getSubject(user);
           String body = getBody(user);
           send(subject, body, user.getEmail());
       });
    }

    public String getSubject(User user) {
        return "Notification "
                + user.getName()
                + " to email "
                + user.getEmail();
    }

    public String getBody(User user) {
        return "Add a new event to "
                + user.getName();
    }

    public void close() {
        executorService.shutdown();
    }

    public void send(String subject, String body, String email) {
    }
}

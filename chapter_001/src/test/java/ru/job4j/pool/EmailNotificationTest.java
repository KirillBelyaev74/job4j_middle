package ru.job4j.pool;

import org.junit.Test;

public class EmailNotificationTest {

    @Test
    public void whenSendLetter() {
        int numberProcessors = Runtime.getRuntime().availableProcessors();
        EmailNotification emailNotification = new EmailNotification(numberProcessors);
        User kirill = new User("Kirill", "Kirill@email.com");
        User nelli = new User("Nelli", "Nelli@gmail.com");
        emailNotification.emailTo(kirill);
        emailNotification.emailTo(nelli);
        emailNotification.close();
        String result = emailNotification.getLetter();
        System.out.println(result);
    }
}

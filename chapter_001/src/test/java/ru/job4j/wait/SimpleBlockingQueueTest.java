package ru.job4j.wait;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void when() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();

        Runnable producer = () -> simpleBlockingQueue.offer(1);
        Thread threadProducer = new Thread(producer);
        threadProducer.start();
        threadProducer.join();

        Integer result = simpleBlockingQueue.poll();
        assertThat(result, is(1));

        producer = () -> simpleBlockingQueue.offer(2);
        threadProducer = new Thread(producer);
        threadProducer.start();
        threadProducer.join();
        
        result = simpleBlockingQueue.poll();
        assertThat(result, is(2));
    }
}

package ru.job4j.cas;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CASСountTest {

    @Test
    public void whenTwoThreadThenNumberTwo() {
        CASСount casСount = new CASСount();
        Thread threadOne = new Thread(casСount::increment);
        Thread threadTwo = new Thread(casСount::increment);
        threadOne.start();
        threadTwo.start();
        try {
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(casСount.get(), is(2));
    }
}

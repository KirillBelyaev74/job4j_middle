package ru.job4j.cas;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CasCountTest {

    @Test
    public void whenTwoThreadThenNumberTwo() {
        CasCount casCount = new CasCount();
        Thread threadOne = new Thread(casCount::increment);
        Thread threadTwo = new Thread(casCount::increment);
        threadOne.start();
        threadTwo.start();
        try {
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(casCount.get(), is(2));
    }
}

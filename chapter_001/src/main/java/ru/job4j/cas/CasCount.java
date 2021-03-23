package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CasCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int expected = 0;
        int next = 0;
        do {
            expected = this.count.get();
            next = expected + 1;
        } while (!this.count.compareAndSet(expected, next));
    }

    public int get() {
        return this.count.get();
    }
}

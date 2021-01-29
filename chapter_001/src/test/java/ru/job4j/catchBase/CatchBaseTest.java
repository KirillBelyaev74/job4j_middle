package ru.job4j.catchBase;

import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicReference;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CatchBaseTest {

    private final CatchBase catchBase = new CatchBase();

    @Before
    public void start() {
        this.catchBase.add(new Base(1, "baseOne", 0));
        this.catchBase.add(new Base(2, "baseTwo", 0));
        this.catchBase.add(new Base(3, "baseThree", 0));
    }

    @Test
    public void whenOneThreadAddAndUpdate() {
        this.catchBase.update(new Base(1, "One", 1));
        Base result = this.catchBase.delete(new Base(1, "", 0));
        assertThat(result, is(new Base(1, "One", 1)));
    }

    @Test
    public void whenSomeThreadAddAndUpdateTheSameBase() throws InterruptedException {
        AtomicReference<OptimisticException> exception = new AtomicReference<>();
        Thread threadOne = new Thread(() -> {
            try {
                this.catchBase.update(new Base(1, "One", 1));
            } catch (OptimisticException oe) {
                exception.set(oe);
            }
        });
        Thread threadTwo = new Thread(() -> {
            try {
                this.catchBase.update(new Base(1, "Two", 1));
            } catch (OptimisticException oe) {
                exception.set(oe);
            }
        });
        Thread threadThree = new Thread(() -> {
            try {
                this.catchBase.update(new Base(1, "Three", 1));
            } catch (OptimisticException oe) {
                exception.set(oe);
            }
        });
        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadOne.join();
        threadTwo.join();
        threadThree.join();
        assertThat(exception.get().getMessage(), is("Base has a another version"));
    }

    @Test
    public void whenSomeThreadAddAndUpdateOtherBase() throws InterruptedException {
        AtomicReference<Base> basesOne = new AtomicReference<>();
        AtomicReference<Base> basesTwo = new AtomicReference<>();
        Thread threadOne = new Thread(() -> {
            this.catchBase.update(new Base(1, "One", 1));
            basesOne.set(this.catchBase.delete(new Base(1, "", 0)));
        });
        Thread threadTwo = new Thread(() -> {
            this.catchBase.update(new Base(2, "Two", 1));
            basesTwo.set(this.catchBase.delete(new Base(2, "", 0)));
        });
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        assertThat(basesOne.get(), is(new Base(1, "One", 1)));
        assertThat(basesTwo.get(), is(new Base(2, "Two", 1)));
    }
}

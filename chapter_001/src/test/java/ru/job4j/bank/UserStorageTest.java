package ru.job4j.bank;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserStorageTest {

    @Test
    public void whenAdd() {
        UserStorage userStorage = new UserStorage();
        Thread threadOne = new Thread(() ->userStorage.add(new User(1, 100)));
        Thread threadTwo = new Thread(() ->userStorage.add(new User(2, 200)));
        Thread threadThree = new Thread(() ->userStorage.add(new User(3, 300)));
        threadOne.start();
        threadTwo.start();
        threadThree.start();
        try {
            threadOne.join();
            threadTwo.join();
            threadThree.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(userStorage.getCountUser(), is(3));
    }

    @Test
    public void whenUpdate() {
        UserStorage userStorage = new UserStorage();
        userStorage.add(new User(1, 100));
        userStorage.add(new User(2, 200));
        assertThat(userStorage.update(new User(1, 300)), is(true));
    }

    @Test
    public void whenChange() {
        UserStorage userStorage = new UserStorage();
        userStorage.add(new User(1, 100));
        userStorage.add(new User(2, 100));
        Thread threadOne = new Thread(() ->userStorage.transfer(1, 2, 50));
        threadOne.start();
        try {
            threadOne.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(userStorage.findUserID(1), is(new User(1, 50)));
    }
}
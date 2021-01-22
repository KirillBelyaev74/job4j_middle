package ru.job4j.bank;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> map = new HashMap<>();

    public synchronized boolean add(User user) {
        boolean result = false;
        if (user != null) {
            this.map.putIfAbsent(user.getId(), user);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(User user) {
        if (user != null) {
            this.map.replace(user.getId(), user);
        }
        return this.map.containsValue(user);
    }

    public synchronized boolean delete(User user) {
        return this.map.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User fromUser = this.map.get(fromId);
        User toUser = this.map.get(toId);
        boolean result = false;
        if (fromUser != null && toUser != null) {
            this.map.replace(fromId, new User(fromId, fromUser.getAmount() - amount));
            this.map.replace(toId, new User(toId, toUser.getAmount() - amount));
            result = true;
        }
        return result;
    }

    public synchronized User findUserID(int id) {
        return this.map.get(id);
    }

    public synchronized int getCountUser() {
        return this.map.size();
    }
}
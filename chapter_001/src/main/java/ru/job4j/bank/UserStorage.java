package ru.job4j.bank;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final List<User> list = new ArrayList<>();

    public synchronized boolean add(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        return this.list.add(user);

    }

    public synchronized boolean update(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        boolean result = false;
        for (int index = 0; index != this.list.size(); index++) {
            if (user.getId() == this.list.get(index).getId()) {
                this.list.set(index, user);
                result = true;
                break;
            }
        }

        return result;
    }

    public synchronized boolean delete(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        return this.list.remove(user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        int fromIndex = this.findIndexID(fromId);
        int toIndex = this.findIndexID(toId);
        boolean result = false;
        if (fromIndex != -1 && toIndex != -1) {
            User fromUser = this.list.get(fromIndex);
            User toUser = this.list.get(toIndex);
            this.list.set(fromIndex, new User(fromUser.getId(), fromUser.getAmount() - amount));
            this.list.set(toIndex, new User(toUser.getId(), toUser.getAmount() + amount));
            result = true;
        }
        return result;
    }

    private synchronized int findIndexID(int id) {
        int result = -1;
        for (int index = 0; index != this.list.size(); index++) {
            if (this.list.get(index).getId() == id) {
                result = index;
            }
        }
        return result;
    }

    public synchronized User findUserID(int id) {
        User user = null;
        for (int index = 0; index != this.list.size(); index++) {
            if (this.list.get(index).getId() == id) {
                user = this.list.get(index);
            }
        }
        return user;
    }

    public synchronized int getCountUser() {
        return this.list.size();
    }
}
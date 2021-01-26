package ru.job4j.bank;

import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    private final DynamicList<T> dynamicList = new DynamicList<>();

    public synchronized void add(T value) {
        this.dynamicList.add(value);
    }

    public synchronized T get(int index) {
        return this.dynamicList.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return this.copy(this.dynamicList).iterator();
    }

    public DynamicList<T> copy(DynamicList<T> list) {
        DynamicList<T> result = new DynamicList<>();
        for (T t : this.dynamicList) {
            result.add(t);
        }
        return result;
    }
}

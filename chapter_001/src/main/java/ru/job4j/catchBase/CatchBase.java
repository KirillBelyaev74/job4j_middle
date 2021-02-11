package ru.job4j.catchBase;

import java.util.concurrent.ConcurrentHashMap;

public class CatchBase {

    ConcurrentHashMap<Integer, Base> hashMap = new ConcurrentHashMap<>();

    public void add(Base base) {
        this.hashMap.put(base.getId(), base);
    }

    public void update(Base base) {
        this.hashMap.computeIfPresent(base.getId(), (i, b) -> {
            int version = b.getVersion() + 1;
            if (version != base.getVersion()) {
                throw new OptimisticException("Base has a another version");
            }
            b = new Base(base.getId(), base.getName(), version);
            return b;
        });
    }

    public Base delete(Base base) {
        return this.hashMap.remove(base.getId());
    }
}

package ru.job4j.catchBase;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class CatchBase {

    ConcurrentHashMap<Integer, Base> hashMap = new ConcurrentHashMap<>();

    public void add(Base base) {
        this.hashMap.put(base.getId(), base);
    }

    public void update(Base base) {
        this.hashMap.computeIfPresent(base.getId(), (i, b) -> {
            int version = b.getVersion() + 1;
            if (version == base.getVersion()) {
                b = new Base(base.getId(), base.getName(), version);
            } else {
                throw new OptimisticException("Base has a another version");
            }
            return b;
        });
//        int version = this.hashMap.get(base.getId()).getVersion();
//        if (version == this.hashMap.get(base.getId()).getVersion()) {
//            this.hashMap.computeIfPresent(
//                    base.getId(),
//                    ((i, b) -> b = new Base(base.getId(), base.getName(), version + 1)));
//            result = true;
//        } else {
//            throw new OptimisticException("!");
//        }
//        return result;
    }

    public Base delete(Base base) {
        return this.hashMap.remove(base.getId());
    }
}

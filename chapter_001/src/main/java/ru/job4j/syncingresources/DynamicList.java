package ru.job4j.syncingresources;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynamicList<E> implements Iterable<E> {

    private Object[] container;
    private int position = 0;

    public DynamicList() {
        this.container = new Object[10];
    }

    public void add(E value) {
        if (this.container.length == this.position + 1) {
            this.container = Arrays.copyOf(this.container, this.container.length + 1);
        }
        this.container[position] = value;
        position++;
    }

    public E get(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > this.container.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return (E) this.container[index];
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            int modCount = position;

            int itPosition = 0;

            @Override
            public boolean hasNext() throws ConcurrentModificationException {
                boolean result = false;
                if (itPosition != position) {
                    result = true;
                }
                if (modCount != position) {
                    throw new ConcurrentModificationException();
                }
                return result;
            }

            @Override
            public E next() throws NoSuchElementException {
                if (hasNext()) {
                    return (E) container[itPosition++];
                }
                throw new NoSuchElementException();
            }
        };
    }
}

package ru.job4j.catchbase;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String exception) {
        super(exception);
    }
}

package ru.job4j.catchBase;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String exception) {
        super(exception);
    }
}

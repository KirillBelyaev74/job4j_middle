package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndexOfArray<T> extends RecursiveTask<Integer> {

    private final T[] value;
    private final int startIndex;
    private final int endIndex;
    private final T findValue;

    public ParallelFindIndexOfArray(T[] value, int startIndex, int endIndex, T findValue) {
        this.value = value;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.findValue = findValue;
    }

    @Override
    protected Integer compute() {
        int medium = (endIndex - startIndex) + 1;
        if (value.length < 10) {
            for (int index = 0; index != value.length; index++) {
                if (value[index].equals(findValue)) {
                    return index;
                }
            }
        } else if (value.length >= 10) {
            ParallelFindIndexOfArray<T> left =
                    new ParallelFindIndexOfArray<>(value, startIndex, startIndex + (medium / 2) - 1, findValue);
            ParallelFindIndexOfArray<T> right =
                    new ParallelFindIndexOfArray<>(value, startIndex + (medium / 2), endIndex, findValue);
            left.fork();
            right.fork();
            Integer resultLeft = left.join();
            Integer resultRight = right.join();
            System.out.println(resultLeft + resultRight);
            if (findValue.equals(resultLeft)) {
                return resultLeft;
            } else if (findValue.equals(resultRight)) {
                return resultRight;
            }
        } else {
            if (value[startIndex].equals(findValue)) {
                return startIndex;
            } else if (value[endIndex].equals(findValue)) {
                return endIndex;
            }
        }
        return -1;
//        int result = -1;
//        System.out.println("!");
//        for (int index = fromValue; index <= toValue; index++) {
//            if (value[index].equals(findValue)) {
//                result = index;
//            }
//        }
//        return result;
    }

    public static void main(String[] args) {
        int numberThreads = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(numberThreads);
        Integer[] value = {1,2,3,4,5,6,7};
        ParallelFindIndexOfArray<Integer> parallelFindIndexOfArray =
                new ParallelFindIndexOfArray<>(value, 0, value.length - 1, 2);
        int result = forkJoinPool.invoke(parallelFindIndexOfArray);
        System.out.println(result);
    }
}

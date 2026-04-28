package org.algobench.examples.sorting;

import org.algobench.api.Algorithm;

import java.util.Arrays;

public abstract class AbstractIntArraySortAlgorithm implements Algorithm<int[], int[]> {
    private final String name;

    public AbstractIntArraySortAlgorithm(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int[] run(int[] input) throws Exception {
        int[] copy = Arrays.copyOf(input, input.length);
        sort(copy);
        return copy;
    }

    protected abstract void sort(int[] arr);
}

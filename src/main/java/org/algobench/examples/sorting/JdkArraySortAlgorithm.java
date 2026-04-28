package org.algobench.examples.sorting;

import org.algobench.discovery.BenchmarkAlgorithm;

import java.util.Arrays;

@BenchmarkAlgorithm
public final class JdkArraySortAlgorithm extends AbstractIntArraySortAlgorithm {

    public JdkArraySortAlgorithm() {
        super("Arrays.sort");
    }

    @Override
    protected void sort(int[] array) {
        Arrays.sort(array);
    }
}
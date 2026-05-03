package org.algobench.examples.sorting;

import org.algobench.discovery.BenchmarkAlgorithm;

@BenchmarkAlgorithm
public final class ShellSortAlgorithm extends AbstractIntArraySortAlgorithm {
    public ShellSortAlgorithm() {
        super("ShellSort");
    }

    @Override
    protected void sort(int[] arr) {
        // first part uses the Knuth's interval sequence
        int h = 1;
        while (h <= arr.length / 3) {
            h = 3 * h + 1; // h is equal to highest sequence of h<=length/3
            // (1,4,13,40...)
        }

        // next part
        while (h > 0) { // for array of length 10, h=4

            // This step is similar to insertion sort below
            for (int i = 0; i < arr.length; i++) {

                int temp = arr[i];
                int j;

                for (j = i; j > h - 1 && arr[j - h] >= temp; j = j - h) {
                    arr[j] = arr[j - h];
                }
                arr[j] = temp;
            }
            h = (h - 1) / 3;
        }
    }
}

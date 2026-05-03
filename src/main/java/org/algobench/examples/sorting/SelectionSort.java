package org.algobench.examples.sorting;

import org.algobench.discovery.BenchmarkAlgorithm;

@BenchmarkAlgorithm
public class SelectionSort extends AbstractIntArraySortAlgorithm {

    public SelectionSort() {
        super("SelectionSort");
    }

    @Override
    protected void sort(int[] arr) {
        int pos, temp;
        for (int i = 0; i < arr.length; i++) {
            pos = i;
            for (int j = i + 1; j < arr.length; j++) {
                //find the index of the minimum element
                if (arr[j] < arr[pos]) {
                    pos = j;
                }
            }
            //swap the current element with the minimum element
            temp = arr[pos];
            arr[pos] = arr[i];
            arr[i] = temp;
        }
    }
}

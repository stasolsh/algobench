package examples.sorting;

import discovery.BenchmarkAlgorithm;

@BenchmarkAlgorithm
public class BubbleSortAlgorithm extends AbstractIntArraySortAlgorithm {

    public BubbleSortAlgorithm() {
        super("BubbleSort");
    }

    @Override
    protected void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }
}

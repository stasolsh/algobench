package examples.sorting;

import org.algobench.api.Algorithm;

import java.util.List;

public final class SortAlgorithms {

    private SortAlgorithms() {
    }

    public static Algorithm<int[], int[]> bubbleSort() {
        return new BubbleSortAlgorithm();
    }

    public static Algorithm<int[], int[]> jdkSort() {
        return new JdkArraySortAlgorithm();
    }

    public static Algorithm<int[], int[]> heapSort() {
        return new HeapSortAlgorithm();
    }

    public static List<Algorithm<int[], int[]>> all() {
        return List.of(
                bubbleSort(),
                jdkSort(),
                heapSort()
        );
    }
}
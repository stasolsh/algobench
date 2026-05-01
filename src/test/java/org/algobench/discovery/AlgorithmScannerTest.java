package org.algobench.discovery;

import org.algobench.api.Algorithm;
import org.algobench.examples.sorting.AbstractIntArraySortAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlgorithmScannerTest {

    @Test
    void shouldFindOnlyAnnotatedAlgorithmsAssignableToBaseType() {
        List<Algorithm<int[], int[]>> algorithms = AlgorithmScanner.findAlgorithms(
                "org.algobench.examples.sorting",
                AbstractIntArraySortAlgorithm.class
        );

        List<String> names = algorithms.stream()
                .map(Algorithm::name)
                .toList();

        assertEquals(List.of("Arrays.sort", "BubbleSort", "HeapSort"), names);
    }

    @Test
    void shouldSortAlgorithmsByName() {
        List<Algorithm<int[], int[]>> algorithms = AlgorithmScanner.findAlgorithms(
                "org.algobench.examples.sorting",
                AbstractIntArraySortAlgorithm.class
        );

        List<String> names = algorithms.stream()
                .map(Algorithm::name)
                .toList();

        assertEquals(names.stream().sorted().toList(), names);
    }

    @Test
    void shouldReturnEmptyListWhenPackageHasNoAlgorithms() {
        List<Algorithm<int[], int[]>> algorithms = AlgorithmScanner.findAlgorithms(
                "org.algobench.discovery.empty",
                AbstractIntArraySortAlgorithm.class
        );

        assertTrue(algorithms.isEmpty());
    }

}
package org.algobench.discovery;

import org.algobench.api.Algorithm;
import org.algobench.examples.sorting.AbstractIntArraySortAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlgorithmScannerTest {

    private static final String PACKAGE_NAME = "org.algobench.discovery.empty";

    @Test
    void shouldFindOnlyAnnotatedAlgorithmsAssignableToBaseType() {
        //given
        List<Algorithm<int[], int[]>> algorithms = AlgorithmScanner.findAlgorithms(
                "org.algobench.examples.sorting",
                AbstractIntArraySortAlgorithm.class
        );
        //then
        List<String> names = algorithms.stream()
                .map(Algorithm::name)
                .toList();
        //when
        assertEquals(List.of("Arrays.sort", "BubbleSort", "HeapSort"), names);
    }

    @Test
    void shouldSortAlgorithmsByName() {
        //given
        List<Algorithm<int[], int[]>> algorithms = AlgorithmScanner.findAlgorithms(
                "org.algobench.examples.sorting",
                AbstractIntArraySortAlgorithm.class
        );
        //then
        List<String> names = algorithms.stream()
                .map(Algorithm::name)
                .toList();
        //when
        assertEquals(names.stream().sorted().toList(), names);
    }

    @Test
    void shouldReturnEmptyListWhenPackageHasNoAlgorithms() {
        //given then
        List<Algorithm<int[], int[]>> algorithms = AlgorithmScanner.findAlgorithms(
                PACKAGE_NAME,
                AbstractIntArraySortAlgorithm.class
        );
        //when
        assertTrue(algorithms.isEmpty());
    }

}
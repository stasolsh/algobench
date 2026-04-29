package org.algobench.api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultRecordsTest {

    @Test
    void sizeResultShouldDefensivelyCopyTimings() {
        //given
        List<Long> timings = new ArrayList<>(List.of(10L, 20L));
        //then
        SizeResult result = new SizeResult(100, timings);
        timings.add(30L);
        //when
        assertEquals(100, result.size());
        assertEquals(List.of(10L, 20L), result.timingsNanos());
    }

    @Test
    void sizeResultTimingsShouldBeImmutable() {
        //given then
        SizeResult result = new SizeResult(100, List.of(10L, 20L));
        //when
        assertThrows(UnsupportedOperationException.class, () ->
                result.timingsNanos().add(30L)
        );
    }

    @Test
    void algorithmResultShouldDefensivelyCopySizeResults() {
        //given
        List<SizeResult> sizeResults = new ArrayList<>();
        sizeResults.add(new SizeResult(100, List.of(10L)));
        // then
        AlgorithmResult result = new AlgorithmResult("Algo", sizeResults);
        sizeResults.add(new SizeResult(1000, List.of(20L)));
        //when
        assertEquals("Algo", result.algorithmName());
        assertEquals(1, result.sizeResults().size());
    }

    @Test
    void benchmarkResultShouldDefensivelyCopyAlgorithmResults() {
        //given
        List<AlgorithmResult> algorithmResults = new ArrayList<>();
        algorithmResults.add(new AlgorithmResult("Algo", List.of(
                new SizeResult(100, List.of(10L))
        )));
        // then
        BenchmarkResult result = new BenchmarkResult("Benchmark", algorithmResults);
        algorithmResults.add(new AlgorithmResult("Other", List.of()));
        //when
        assertEquals("Benchmark", result.benchmarkName());
        assertEquals(1, result.algorithmResults().size());
    }

    @Test
    void benchmarkResultAlgorithmResultsShouldBeImmutable() {
        //given then
        BenchmarkResult result = new BenchmarkResult("Benchmark", List.of(
                new AlgorithmResult("Algo", List.of())
        ));

        //when
        assertThrows(UnsupportedOperationException.class, () ->
                result.algorithmResults().add(new AlgorithmResult("Other", List.of()))
        );
    }
}
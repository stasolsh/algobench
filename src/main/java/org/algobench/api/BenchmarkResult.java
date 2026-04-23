package org.algobench.api;

import java.util.List;

public record BenchmarkResult(String benchmarkName, List<AlgorithmResult> algorithmResults) {
    public BenchmarkResult(String benchmarkName, List<AlgorithmResult> algorithmResults) {
        this.benchmarkName = benchmarkName;
        this.algorithmResults = List.copyOf(algorithmResults);
    }

}

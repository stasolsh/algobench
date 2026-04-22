package org.algobench.api;

import java.util.List;

public final class BenchmarkResult {
    private final String benchmarkName;
    private final List<AlgorithmResult> algorithmResults;

    public BenchmarkResult(String benchmarkName, List<AlgorithmResult> algorithmResults) {
        this.benchmarkName = benchmarkName;
        this.algorithmResults = List.copyOf(algorithmResults);
    }

    public String getBenchmarkName() {
        return benchmarkName;
    }

    public List<AlgorithmResult> getAlgorithmResults() {
        return algorithmResults;
    }

}

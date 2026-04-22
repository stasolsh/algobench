package org.algobench.api;

import java.util.List;

public final class AlgorithmResult {
    private final String algorithmName;
    private final List<SizeResult> sizeResults;

    public AlgorithmResult(String algorithmName, List<SizeResult> sizeResults) {
        this.algorithmName = algorithmName;
        this.sizeResults = List.copyOf(sizeResults);
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public List<SizeResult> getSizeResults() {
        return sizeResults;
    }

}

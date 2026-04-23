package org.algobench.api;

import java.util.List;

public record AlgorithmResult(String algorithmName, List<SizeResult> sizeResults) {
    public AlgorithmResult(String algorithmName, List<SizeResult> sizeResults) {
        this.algorithmName = algorithmName;
        this.sizeResults = List.copyOf(sizeResults);
    }

}

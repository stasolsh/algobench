package org.algobench.api;

public record BenchmarkContext(int size, long seed, int iteration, int fork) {
}

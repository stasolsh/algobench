package org.algobench.runner;

import org.algobench.api.Benchmark;
import org.algobench.api.BenchmarkResult;

public interface BenchmarkRunner {
    <I, O>BenchmarkResult run(Benchmark<I, O> benchmark) throws Exception;
}

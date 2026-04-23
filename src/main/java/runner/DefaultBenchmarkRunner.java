package runner;

import org.algobench.api.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultBenchmarkRunner implements BenchmarkRunner {
    @Override
    public <K, L> BenchmarkResult run(Benchmark<K, L> benchmark) throws Exception {
        benchmark.getLifecycle().beforeAll();
        List<AlgorithmResult> algorithmResults = new ArrayList<>();
        for (Algorithm<K, L> algorithm : benchmark.getAlgorithms()) {
            List<SizeResult> sizeResults = new ArrayList<>();
            fillSizeResults(benchmark, algorithm, sizeResults);
            algorithmResults.add(new AlgorithmResult(algorithm.name(), sizeResults));
        }
        benchmark.getLifecycle().afterAll();
        return new BenchmarkResult(benchmark.getName(), algorithmResults);
    }

    private <K, L> void fillSizeResults(Benchmark<K, L> benchmark, Algorithm<K, L> algorithm, List<SizeResult> sizeResults) throws Exception {
        for (int size : benchmark.getConfig().getSizes()) {
            List<Long> timings = new ArrayList<>();
            fillSizeResults(benchmark, algorithm, size, timings);
            sizeResults.add(new SizeResult(size, timings));
        }
    }

    private <K, L> void fillSizeResults(Benchmark<K, L> benchmark, Algorithm<K, L> algorithm, int size, List<Long> timings) throws Exception {
        for (int fork = 0; fork < benchmark.getConfig().getForks(); fork++) {
            for (int i = 0; i < benchmark.getConfig().getWarmupIterations(); i++) {
                BenchmarkContext context = new BenchmarkContext(size, seedFor(fork, i), i, fork);
                K input = benchmark.getInputGenerator().generateInput(context);

                benchmark.getLifecycle().beforeEach(input);

                long start = System.nanoTime();
                L output = algorithm.run(input);
                long end = System.nanoTime();

                benchmark.getValidator().validate(input, output);
                benchmark.getLifecycle().afterEach(input);

                timings.add(end - start);
            }
        }
    }

    private long seedFor(int fork, int iterations) {
        return 31L * fork * iterations * 12345L;
    }
}

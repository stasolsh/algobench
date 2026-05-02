package org.algobench.runner;

import org.algobench.api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultBenchmarkRunnerTest {

    private final BenchmarkRunner runner = new DefaultBenchmarkRunner();

    @Test
    void shouldRunSingleAlgorithmAndProduceResult() throws Exception {
        //given
        Benchmark<String, String> benchmark = simpleBenchmark("test");
        //then
        BenchmarkResult result = runner.run(benchmark);
        //when
        assertEquals("Test Benchmark", result.benchmarkName());
        assertEquals(1, result.algorithmResults().size());

        AlgorithmResult algoResult = result.algorithmResults().getFirst();
        assertEquals("Echo", algoResult.algorithmName());

        assertEquals(1, algoResult.sizeResults().size());
    }

    @Test
    void shouldRespectSizesFromConfig() throws Exception {
        //given
        BenchmarkConfig config = BenchmarkConfig.builder()
                .sizes(List.of(100, 1000))
                .warmupIterations(1)
                .forks(1)
                .build();
        //then
        Benchmark<String, String> benchmark = Benchmark.<String, String>builder("Test")
                .addAlgorithm(echo())
                .inputGenerator(ctx -> "x")
                .config(config)
                .build();
        //then
        BenchmarkResult result = runner.run(benchmark);

        List<SizeResult> sizes = result.algorithmResults().getFirst().sizeResults();

        assertEquals(2, sizes.size());
        assertEquals(100, sizes.get(0).size());
        assertEquals(1000, sizes.get(1).size());
    }

    @Test
    void shouldRespectForksAndIterations() throws Exception {
        //given
        BenchmarkConfig config = BenchmarkConfig.builder()
                .sizes(List.of(1))
                .warmupIterations(2)
                .forks(3)
                .build();
        //then
        Benchmark<String, String> benchmark = Benchmark.<String, String>builder("Test")
                .addAlgorithm(echo())
                .inputGenerator(ctx -> "x")
                .config(config)
                .build();
        //when
        BenchmarkResult result = runner.run(benchmark);

        SizeResult sizeResult = result.algorithmResults().getFirst().sizeResults().getFirst();

        // forks * iterations = 3 * 2 = 6 timings
        assertEquals(6, sizeResult.timingsNanos().size());
    }

    @Test
    void shouldCallLifecycleMethods() throws Exception {
        //given
        TrackingLifecycle lifecycle = new TrackingLifecycle();
        Benchmark<String, String> benchmark = Benchmark.<String, String>builder("Test")
                .addAlgorithm(echo())
                .inputGenerator(ctx -> "x")
                .lifecycle(lifecycle)
                .build();
        //then
        runner.run(benchmark);
        //when
        assertTrue(lifecycle.beforeAllCalled);
        assertTrue(lifecycle.afterAllCalled);
        assertTrue(lifecycle.beforeEachCalled);
        assertTrue(lifecycle.afterEachCalled);
    }

    @Test
    void shouldCallValidatorForEachExecution() throws Exception {
        //given
        TrackingValidator validator = new TrackingValidator();

        BenchmarkConfig config = BenchmarkConfig.builder()
                .sizes(List.of(1))
                .warmupIterations(3)
                .forks(2)
                .build();

        Benchmark<String, String> benchmark = Benchmark.<String, String>builder("Test")
                .addAlgorithm(echo())
                .inputGenerator(ctx -> "x")
                .validator(validator)
                .config(config)
                .build();
        //then
        runner.run(benchmark);

        //when
        // forks * iterations = 2 * 3 = 6 validations
        assertEquals(6, validator.calls);
    }

    @Test
    void shouldGenerateDifferentSeedsPerIteration() throws Exception {
        //given
        List<Long> seeds = new ArrayList<>();

        Benchmark<String, String> benchmark = Benchmark.<String, String>builder("Test")
                .addAlgorithm(echo())
                .inputGenerator(ctx -> {
                    seeds.add(ctx.seed());
                    return "x";
                })
                .config(BenchmarkConfig.builder()
                        .sizes(List.of(1))
                        .warmupIterations(3)
                        .forks(2)
                        .build())
                .build();
        //then
        runner.run(benchmark);
        //when
        assertFalse(seeds.isEmpty());
        assertTrue(seeds.stream().distinct().count() > 1);
    }

    @Test
    void shouldSupportMultipleAlgorithms() throws Exception {
        //given
        Benchmark<String, String> benchmark = Benchmark.<String, String>builder("Test")
                .addAlgorithm(echo())
                .addAlgorithm(reverse())
                .inputGenerator(ctx -> "abc")
                .build();
        //then
        BenchmarkResult result = runner.run(benchmark);
        //when
        assertEquals(2, result.algorithmResults().size());
    }

    // ============================
    // Helpers
    // ============================

    private Benchmark<String, String> simpleBenchmark(String input) {
        return Benchmark.<String, String>builder("Test Benchmark")
                .addAlgorithm(echo())
                .inputGenerator(ctx -> input)
                .config(BenchmarkConfig.builder()
                        .sizes(List.of(1))
                        .warmupIterations(1)
                        .forks(1)
                        .build())
                .build();
    }

    private Algorithm<String, String> echo() {
        return new Algorithm<>() {
            @Override
            public String name() {
                return "Echo";
            }

            @Override
            public String run(String input) {
                return input;
            }
        };
    }

    private Algorithm<String, String> reverse() {
        return new Algorithm<>() {
            @Override
            public String name() {
                return "Reverse";
            }

            @Override
            public String run(String input) {
                return new StringBuilder(input).reverse().toString();
            }
        };
    }

    private static class TrackingLifecycle implements BenchmarkLifecycle<String> {
        boolean beforeAllCalled;
        boolean afterAllCalled;
        boolean beforeEachCalled;
        boolean afterEachCalled;

        @Override
        public void beforeAll() {
            beforeAllCalled = true;
        }

        @Override
        public void afterAll() {
            afterAllCalled = true;
        }

        @Override
        public void beforeEach(String input) {
            beforeEachCalled = true;
        }

        @Override
        public void afterEach(String input) {
            afterEachCalled = true;
        }
    }

    private static class TrackingValidator implements ResultValidator<String, String> {
        int calls = 0;

        @Override
        public void validate(String input, String output) {
            calls++;
        }
    }
}
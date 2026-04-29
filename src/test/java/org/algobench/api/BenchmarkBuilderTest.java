package org.algobench.api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BenchmarkBuilderTest {
    private static final String TEST = "Test";
    private static final String INPUT = "input";
    private static final String ECHO = "Echo";

    @Test
    void shouldBuildBenchmarkWithRequiredFields() {
        //given
        Algorithm<String, String> algorithm = echoAlgorithm(ECHO);
        //then
        Benchmark<String, String> benchmark = Benchmark.<String, String>builder("Test Benchmark")
                .addAlgorithm(algorithm)
                .inputGenerator(ctx -> INPUT)
                .build();
        //when
        assertEquals("Test Benchmark", benchmark.getName());
        assertEquals(1, benchmark.getAlgorithms().size());
        assertEquals(ECHO, benchmark.getAlgorithms().getFirst().name());
        assertNotNull(benchmark.getConfig());
        assertNotNull(benchmark.getValidator());
        assertNotNull(benchmark.getLifecycle());
    }

    @Test
    void shouldBuildBenchmarkWithMultipleAlgorithms() {
        //given
        List<Algorithm<String, String>> algorithms = List.of(
                echoAlgorithm("A"),
                echoAlgorithm("B")
        );
        //then
        Benchmark<String, String> benchmark = Benchmark.<String, String>builder("Multi")
                .addAlgorithms(algorithms)
                .inputGenerator(ctx -> INPUT)
                .build();
        //when
        assertEquals(2, benchmark.getAlgorithms().size());
    }

    @Test
    void shouldThrowWhenNoAlgorithmsProvided() {
        //given then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Benchmark.<String, String>builder("Invalid")
                        .inputGenerator(ctx -> INPUT)
                        .build()
        );
        //when
        assertEquals("At list one algorithm is required", exception.getMessage());
    }

    @Test
    void shouldThrowWhenInputGeneratorIsMissing() {
        //given then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Benchmark.<String, String>builder("Invalid")
                        .addAlgorithm(echoAlgorithm(ECHO))
                        .build()
        );
        //when
        assertEquals("Input generator is required", exception.getMessage());
    }

    @Test
    void shouldRejectNullName() {
        //given then when
        assertThrows(NullPointerException.class, () ->
                Benchmark.<String, String>builder(null)
        );
    }

    @Test
    void shouldRejectNullAlgorithm() {
        //given then when
        assertThrows(NullPointerException.class, () ->
                Benchmark.<String, String>builder(TEST)
                        .addAlgorithm(null)
        );
    }

    @Test
    void shouldRejectNullConfig() {
        //given then when
        assertThrows(NullPointerException.class, () ->
                Benchmark.<String, String>builder(TEST)
                        .config(null)
        );
    }

    @Test
    void shouldRejectNullInputGenerator() {
        //given then when
        assertThrows(NullPointerException.class, () ->
                Benchmark.<String, String>builder(TEST)
                        .inputGenerator(null)
        );
    }

    @Test
    void shouldRejectNullValidator() {
        //given then when
        assertThrows(NullPointerException.class, () ->
                Benchmark.<String, String>builder(TEST)
                        .validator(null)
        );
    }

    @Test
    void shouldRejectNullLifecycle() {
        //given then when
        assertThrows(NullPointerException.class, () ->
                Benchmark.<String, String>builder(TEST)
                        .lifecycle(null)
        );
    }

    @Test
    void shouldDefensivelyCopyAlgorithms() {
        //given
        List<Algorithm<String, String>> algorithms = new ArrayList<>();
        algorithms.add(echoAlgorithm("A"));
        //then
        Benchmark<String, String> benchmark = Benchmark.<String, String>builder(TEST)
                .addAlgorithms(algorithms)
                .inputGenerator(ctx -> INPUT)
                .build();
        algorithms.add(echoAlgorithm("B"));
        //when
        assertEquals(1, benchmark.getAlgorithms().size());
    }

    @Test
    void returnedAlgorithmsShouldBeImmutable() {
        //given then
        Benchmark<String, String> benchmark = Benchmark.<String, String>builder(TEST)
                .addAlgorithm(echoAlgorithm("A"))
                .inputGenerator(ctx -> INPUT)
                .build();
        //when
        assertThrows(UnsupportedOperationException.class, () ->
                benchmark.getAlgorithms().add(echoAlgorithm("B"))
        );
    }

    private static Algorithm<String, String> echoAlgorithm(String name) {
        return new Algorithm<>() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public String run(String input) {
                return input;
            }
        };
    }
}

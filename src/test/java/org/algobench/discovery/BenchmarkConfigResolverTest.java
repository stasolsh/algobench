package org.algobench.discovery;

import org.algobench.api.BenchmarkConfig;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BenchmarkConfigResolverTest {

    @Test
    void shouldResolveBenchmarkConfigFromAnnotation() {
        BenchmarkConfig config = BenchmarkConfigResolver.resolve(AnnotatedBenchmark.class);

        assertEquals(2, config.getWarmupIterations());
        assertEquals(7, config.getMeasurementIterations());
        assertEquals(3, config.getForks());
        assertEquals(List.of(100, 1000, 10_000), config.getSizes());
    }

    @Test
    void shouldUseDefaultConfigWhenAnnotationIsMissing() {
        BenchmarkConfig config = BenchmarkConfigResolver.resolve(NotAnnotatedBenchmark.class);

        assertEquals(5, config.getWarmupIterations());
        assertEquals(10, config.getMeasurementIterations());
        assertEquals(1, config.getForks());
        assertEquals(List.of(1000), config.getSizes());
    }

    @Test
    void shouldUseBenchmarkSettingsDefaultsWhenAnnotationHasNoValues() {
        BenchmarkConfig config = BenchmarkConfigResolver.resolve(DefaultAnnotatedBenchmark.class);

        assertEquals(3, config.getWarmupIterations());
        assertEquals(5, config.getMeasurementIterations());
        assertEquals(5, config.getForks());
        assertEquals(List.of(1000), config.getSizes());
    }

    @BenchmarkSettings(
            warmupIterations = 2,
            measurementIterations = 7,
            forks = 3,
            sizes = {100, 1000, 10_000}
    )
    private static class AnnotatedBenchmark {
    }

    @BenchmarkSettings
    private static class DefaultAnnotatedBenchmark {
    }

    private static class NotAnnotatedBenchmark {
    }
}
package org.algobench.api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BenchmarkConfigTest {

    @Test
    void shouldCreateDefaultConfig() {
        //given then
        BenchmarkConfig config = BenchmarkConfig.builder().build();
        //when
        assertEquals(5, config.getWarmupIterations());
        assertEquals(10, config.getMeasurementIterations());
        assertEquals(1, config.getForks());
        assertEquals(List.of(1000), config.getSizes());
        assertFalse(config.isCopyInputPerRan());
    }

    @Test
    void shouldCreateCustomConfig() {
        //given then
        BenchmarkConfig config = BenchmarkConfig.builder()
                .warmupIterations(3)
                .measurementIterations(7)
                .forks(2)
                .sizes(List.of(100, 1000, 3000))
                .copyInputPerRan(true)
                .build();
        //when
        assertEquals(3, config.getWarmupIterations());
        assertEquals(7, config.getMeasurementIterations());
        assertEquals(2, config.getForks());
        assertEquals(List.of(100, 1000, 3000), config.getSizes());
        assertTrue(config.isCopyInputPerRan());
    }

    @Test
    void shouldDefensivelyCopySizes() {
        //given
        List<Integer> sizes = new ArrayList<>(List.of(100, 1000));
        //then
        BenchmarkConfig config = BenchmarkConfig.builder()
                .sizes(sizes)
                .build();
        sizes.add(10_000);
        //when
        assertEquals(List.of(100, 1000), config.getSizes());
    }

    @Test
    void returnedSizesShouldBeImmutable() {
        //given then
        BenchmarkConfig config = BenchmarkConfig.builder()
                .sizes(List.of(100))
                .build();
        //when
        assertThrows(UnsupportedOperationException.class, () ->
                config.getSizes().add(1000)
        );
    }
}
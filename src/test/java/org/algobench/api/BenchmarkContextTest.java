package org.algobench.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BenchmarkContextTest {

    @Test
    void shouldExposeContextValues() {
        //given then
        BenchmarkContext context = new BenchmarkContext(1000, 12345L, 2, 1);
        //when
        assertEquals(1000, context.size());
        assertEquals(12345L, context.seed());
        assertEquals(2, context.iteration());
        assertEquals(1, context.fork());
    }
}
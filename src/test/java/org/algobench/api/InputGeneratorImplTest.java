package org.algobench.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputGeneratorImplTest {

    @Test
    void shouldGenerateArrayWithRequestedSize() throws Exception {
        //given
        InputGenerator<int[]> generator = InputGeneratorImpl.getInputGenerator();
        //then
        int[] result = generator.generateInput(new BenchmarkContext(10, 123L, 0, 0));
        //when
        assertEquals(10, result.length);
    }

    @Test
    void shouldGenerateEmptyArrayWhenSizeIsZero() throws Exception {
        //given
        InputGenerator<int[]> generator = InputGeneratorImpl.getInputGenerator();
        //then
        int[] result = generator.generateInput(new BenchmarkContext(0, 123L, 0, 0));
        //when
        assertEquals(0, result.length);
    }

    @Test
    void shouldGenerateDeterministicArrayForSameSeedAndSize() throws Exception {
        //given
        InputGenerator<int[]> generator = InputGeneratorImpl.getInputGenerator();
        //then
        int[] first = generator.generateInput(new BenchmarkContext(10, 123L, 0, 0));
        int[] second = generator.generateInput(new BenchmarkContext(10, 123L, 5, 2));
        //when
        assertArrayEquals(first, second);
    }

    @Test
    void shouldUsuallyGenerateDifferentArraysForDifferentSeeds() throws Exception {
        //given
        InputGenerator<int[]> generator = InputGeneratorImpl.getInputGenerator();
        //then
        int[] first = generator.generateInput(new BenchmarkContext(10, 123L, 0, 0));
        int[] second = generator.generateInput(new BenchmarkContext(10, 456L, 0, 0));
        //when
        assertFalse(java.util.Arrays.equals(first, second));
    }

    @Test
    void shouldThrowNegativeArraySizeExceptionForNegativeSize() {
        //given
        InputGenerator<int[]> generator = InputGeneratorImpl.getInputGenerator();
        //then when
        assertThrows(NegativeArraySizeException.class, () ->
                generator.generateInput(new BenchmarkContext(-1, 123L, 0, 0))
        );
    }
}
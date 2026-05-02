package org.algobench.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultValidatorImplTest {

    @Test
    void shouldPassForSortedArray() {
        //given
        ResultValidator<int[], int[]> validator = ResultValidatorImpl.getResultValidator();
        //then
        int[] input = {3, 2, 1};
        int[] output = {1, 2, 3};
        //when
        assertDoesNotThrow(() -> validator.validate(input, output));
    }

    @Test
    void shouldPassForArrayWithDuplicates() {
        //given
        ResultValidator<int[], int[]> validator = ResultValidatorImpl.getResultValidator();
        //then
        int[] input = {3, 1, 2, 2};
        int[] output = {1, 2, 2, 3};
        //when
        assertDoesNotThrow(() -> validator.validate(input, output));
    }

    @Test
    void shouldPassForEmptyArray() {
        //given
        ResultValidator<int[], int[]> validator = ResultValidatorImpl.getResultValidator();
        //then when
        assertDoesNotThrow(() -> validator.validate(new int[]{}, new int[]{}));
    }

    @Test
    void shouldPassForSingleElementArray() {
        //given
        ResultValidator<int[], int[]> validator = ResultValidatorImpl.getResultValidator();
        //then when
        assertDoesNotThrow(() -> validator.validate(new int[]{1}, new int[]{1}));
    }

    @Test
    void shouldThrowForUnsortedArray() {
        //given
        ResultValidator<int[], int[]> validator = ResultValidatorImpl.getResultValidator();
        //then
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> validator.validate(new int[]{3, 2, 1}, new int[]{1, 3, 2})
        );
        //when
        assertEquals("Output is not sorted.", exception.getMessage());
    }

    @Test
    void shouldIgnoreInputAndValidateOnlyOutput() {
        //given
        ResultValidator<int[], int[]> validator = ResultValidatorImpl.getResultValidator();
        //then
        int[] input = {100, -50, 7};
        int[] output = {-10, 0, 50};
        //when
        assertDoesNotThrow(() -> validator.validate(input, output));
    }

    @Test
    void shouldThrowNullPointerExceptionWhenOutputIsNull() {
        //given
        ResultValidator<int[], int[]> validator = ResultValidatorImpl.getResultValidator();
        //then when
        assertThrows(NullPointerException.class, () ->
                validator.validate(new int[]{1, 2, 3}, null)
        );
    }
}
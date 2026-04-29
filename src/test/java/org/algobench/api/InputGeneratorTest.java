package org.algobench.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputGeneratorTest {
    @Test
    void inputGeneratorShouldGenerateInput() throws Exception {
        //given
        InputGenerator<String> generator = context ->
                "size=" + context.size() + ",seed=" + context.seed();
        //then
        String result = generator.generateInput(new BenchmarkContext(100, 42L, 0, 0));
        //when
        assertEquals("size=100,seed=42", result);
    }

    @Test
    void resultValidatorShouldValidateSuccessfully() {
        //given then
        ResultValidator<String, String> validator = (input, output) -> {
            if (!input.equals(output)) {
                throw new IllegalStateException("Invalid output");
            }
        };
        //when
        assertDoesNotThrow(() -> validator.validate("abc", "abc"));
    }

    @Test
    void resultValidatorShouldThrowOnInvalidOutput() {
        //given then
        ResultValidator<String, String> validator = (input, output) -> {
            if (!input.equals(output)) {
                throw new IllegalStateException("Invalid output");
            }
        };
        //when
        assertThrows(IllegalStateException.class, () ->
                validator.validate("abc", "xyz")
        );
    }

    @Test
    void defaultLifecycleMethodsShouldDoNothing() {
        //given then
        BenchmarkLifecycle<String> lifecycle = new BenchmarkLifecycle<>() {
        };
        //when
        assertDoesNotThrow(lifecycle::beforeAll);
        assertDoesNotThrow(() -> lifecycle.beforeEach("input"));
        assertDoesNotThrow(() -> lifecycle.afterEach("input"));
        assertDoesNotThrow(lifecycle::afterAll);
    }
}

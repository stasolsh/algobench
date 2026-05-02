package org.algobench.api;

public final class ResultValidatorImpl {
    public static ResultValidator<int[], int[]> getResultValidator() {
        return (input, output) -> {
            for (int i = 1; i < output.length; i++) {
                if (output[i - 1] > output[i]) {
                    throw new IllegalStateException("Output is not sorted.");
                }
            }
        };
    }

}
